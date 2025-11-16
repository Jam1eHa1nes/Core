#!/usr/bin/env python3
"""
Generate a Markdown summary for PR comments with per-module test results and code coverage.

It expects an environment variable REPORT_MODULES with a space-separated list of module
directories to include (e.g., "Common Selenium Playwright"). For each module, the script:
 - Parses Surefire XML in target/surefire-reports to count tests, failures, and skipped.
 - Parses JaCoCo XML in target/site/jacoco/jacoco.xml to compute instruction coverage.

If a module has no JaCoCo report, coverage is reported as "n/a". If there are reports but
no instructions found, coverage is reported as 0.0%.
"""

import os
import glob
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def parse_surefire(module_dir: Path, *, test_name_prefixes=None):
    report_dir = module_dir / 'target' / 'surefire-reports'
    total = passed = failed = skipped = 0

    if not report_dir.exists():
        return total, passed, failed, skipped

    for xml_file in report_dir.glob('TEST-*.xml'):
        try:
            tree = ET.parse(xml_file)
            root = tree.getroot()
            # Filter by testsuite name when prefixes provided
            if test_name_prefixes:
                suite_name = root.attrib.get('name', '')
                if not any(suite_name.startswith(pfx) for pfx in test_name_prefixes):
                    continue
            tests = int(root.attrib.get('tests', '0'))
            failures = int(root.attrib.get('failures', '0'))
            errors = int(root.attrib.get('errors', '0'))
            skips = int(root.attrib.get('skipped', root.attrib.get('skip', '0')))
            total += tests
            failed += failures + errors
            skipped += skips
            passed += max(tests - (failures + errors + skips), 0)
        except Exception:
            # Ignore malformed files; keep counting others
            continue
    return total, passed, failed, skipped


def parse_jacoco(module_dir: Path, *, package_prefixes=None):
    jacoco_xml = module_dir / 'target' / 'site' / 'jacoco' / 'jacoco.xml'
    if not jacoco_xml.exists():
        return None  # n/a
    try:
        tree = ET.parse(jacoco_xml)
        root = tree.getroot()
        covered = missed = 0
        # When filtering by package prefixes, only sum counters within matching packages
        if package_prefixes:
            for pkg in root.iter('package'):
                name = pkg.attrib.get('name', '')
                if any(name.startswith(pfx) for pfx in package_prefixes):
                    for counter in pkg.iter('counter'):
                        if counter.attrib.get('type') == 'INSTRUCTION':
                            missed += int(counter.attrib.get('missed', '0'))
                            covered += int(counter.attrib.get('covered', '0'))
        else:
            for counter in root.iter('counter'):
                if counter.attrib.get('type') == 'INSTRUCTION':
                    missed += int(counter.attrib.get('missed', '0'))
                    covered += int(counter.attrib.get('covered', '0'))
        total = missed + covered
        if total == 0:
            return 0.0
        return round(covered * 100.0 / total, 1)
    except Exception:
        return None


def format_row(module, total, passed, failed, skipped, coverage):
    pass_rate = (passed * 100.0 / total) if total else (100.0 if passed == 0 and failed == 0 else 0.0)
    if coverage is None:
        cov_str = 'n/a'
    else:
        cov_str = f"{coverage:.1f}%"
    return f"{module}\t{total}\t{passed}\t{failed}\t{skipped}\t{pass_rate:.1f}%\t{cov_str}"


def main():
    modules_env = os.environ.get('REPORT_MODULES', '').strip()
    raw_modules = [m for m in modules_env.split() if m]
    # Support alias syntax: Label:DirName (e.g., "RestAssured:Common").
    # If no colon provided, label == dirname.
    modules = []  # list of tuples (label, dirname)
    for item in raw_modules:
        if ':' in item:
            label, dirname = item.split(':', 1)
            if label and dirname:
                modules.append((label, dirname))
        else:
            modules.append((item, item))
    if not modules:
        # Fallback: detect modules that have a pom.xml next to parent
        detected = [p.name for p in ROOT.iterdir() if (p / 'pom.xml').exists()]
        modules = [(name, name) for name in detected]

    # Print GitHub-friendly Markdown table
    print("| Module | Tests | Passed | Failed | Skipped | Pass rate | Coverage |")
    print("|---|---:|---:|---:|---:|---:|---:|")
    for label, module in modules:
        module_dir = ROOT / module
        # Optional fine-grained filters when summarizing Common into RestAssured/Selenium/Playwright
        test_prefixes = None
        pkg_prefixes = None
        if module == 'Common':
            mapping = {
                'RestAssured': {
                    'tests': ['core.api.impl.'],
                    'packages': ['core/api/impl', 'core.api.impl', 'core/api', 'core'],  # handle both xml notations
                },
                'Selenium': {
                    # Include SeleniumActions and TargetFactory Selenium tests
                    'tests': ['ui.SeleniumActionsTest', 'ui.TargetFactorySeleniumTest'],
                    # Include selenium package and shared core.ui code covered by these tests
                    'packages': ['selenium', 'core/ui', 'core.ui'],
                },
                'Playwright': {
                    # Include PlaywrightActions and TargetFactory Playwright tests
                    'tests': ['ui.PlaywrightActionsTest', 'ui.TargetFactoryPlaywrightTest'],
                    # Include playwright package and shared core.ui code covered by these tests
                    'packages': ['playwright', 'core/ui', 'core.ui'],
                },
            }
            if label in mapping:
                test_prefixes = mapping[label]['tests']
                # JaCoCo package names in XML use slash-separated names, but in our generated file
                # packages appear as simple names like "selenium" or "playwright" for those namespaces,
                # and "core.api.impl" for API code. We'll check both forms just in case.
                pkg_prefixes = mapping[label]['packages']

        total, passed, failed, skipped = parse_surefire(module_dir, test_name_prefixes=test_prefixes)
        coverage = parse_jacoco(module_dir, package_prefixes=pkg_prefixes)
        pass_rate = (passed * 100.0 / total) if total else (100.0 if passed == 0 and failed == 0 else 0.0)
        cov_str = 'n/a' if coverage is None else f"{coverage:.1f}%"
        print(f"| {label} | {total} | {passed} | {failed} | {skipped} | {pass_rate:.1f}% | {cov_str} |")


if __name__ == '__main__':
    main()
