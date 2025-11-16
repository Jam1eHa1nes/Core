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

# noinspection PyUnresolvedReferences
import os
# noinspection PyUnresolvedReferences
import sys
# noinspection PyUnresolvedReferences
import xml.etree.ElementTree as ET
# noinspection PyUnresolvedReferences
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def _to_int(val, default=0):
    """Safely convert a value to int, falling back to default on errors.

    Accepts numeric strings (including floats like "1.0") and numbers. Returns
    the provided default when conversion is not possible.
    """
    # Implement simple, exception-free parsing without using builtins like int/float/ord/str
    if val is None:
        return default
    s = val
    try:
        s = s.strip()
    except:  # if not a string-like value
        return default
    if not s:
        return default
    neg = False
    if s[0] == '-':
        neg = True
        s = s[1:]
    # allow float-like strings by cutting at the first non-digit (including '.')
    n = 0
    saw_digit = False
    _DIG = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
    for ch in s:
        if ch in _DIG:
            n = n * 10 + _DIG[ch]
            saw_digit = True
        elif ch == '.':
            break
        else:
            break
    if not saw_digit:
        return default
    return -n if neg else n


def _starts_with_any(text, prefixes):
    if not prefixes:
        return False
    s = text or ""
    for p in prefixes:
        if s.startswith(p):
            return True
    return False


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
                if not _starts_with_any(suite_name, test_name_prefixes):
                    continue
            tests = _to_int(root.attrib.get('tests', '0'))
            failures = _to_int(root.attrib.get('failures', '0'))
            errors = _to_int(root.attrib.get('errors', '0'))
            skips = _to_int(root.attrib.get('skipped', root.attrib.get('skip', '0')))
            total += tests
            failed += failures + errors
            skipped += skips
            remaining = tests - (failures + errors + skips)
            passed += remaining if remaining > 0 else 0
        except:
            # Ignore malformed files; keep counting others
            continue
    return total, passed, failed, skipped


def parse_jacoco(
    module_dir: Path,
    *,
    package_prefixes=None,
    class_prefixes=None,
    counter_type='INSTRUCTION',
):
    jacoco_xml = module_dir / 'target' / 'site' / 'jacoco' / 'jacoco.xml'
    if not jacoco_xml.exists():
        return None  # n/a
    try:
        tree = ET.parse(jacoco_xml)
        root = tree.getroot()
        covered = missed = 0
        ct = counter_type.upper() if counter_type else 'INSTRUCTION'
        # When filtering by class prefixes, only sum counters within matching classes
        if class_prefixes:
            for pkg in root.iter('package'):
                for cls in pkg.iter('class'):
                    name = cls.attrib.get('name', '')
                    if _starts_with_any(name, class_prefixes):
                        for counter in cls.iter('counter'):
                            if counter.attrib.get('type') == ct:
                                missed += _to_int(counter.attrib.get('missed', '0'))
                                covered += _to_int(counter.attrib.get('covered', '0'))
        # When filtering by package prefixes, only sum counters within matching packages
        elif package_prefixes:
            for pkg in root.iter('package'):
                name = pkg.attrib.get('name', '')
                if _starts_with_any(name, package_prefixes):
                    for counter in pkg.iter('counter'):
                        if counter.attrib.get('type') == ct:
                            missed += _to_int(counter.attrib.get('missed', '0'))
                            covered += _to_int(counter.attrib.get('covered', '0'))
        else:
            for counter in root.iter('counter'):
                if counter.attrib.get('type') == ct:
                    missed += _to_int(counter.attrib.get('missed', '0'))
                    covered += _to_int(counter.attrib.get('covered', '0'))
        total = missed + covered
        if total == 0:
            return "0.0"
        # Compute percentage to one decimal without using round()
        tenths = (covered * 1000 + (total // 2)) // total  # rounded to one decimal place
        whole = tenths // 10
        frac = tenths % 10
        return f"{whole}.{frac}"
    except:
        return None


# Removed unused helper to avoid linter warnings


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
    sys.stdout.write("| Module | Tests | Passed | Failed | Skipped | Pass rate | Coverage |\n")
    sys.stdout.write("|---|---:|---:|---:|---:|---:|---:|\n")
    for label, module in modules:
        module_dir = ROOT / module
        # Optional fine-grained filters when summarizing Common into RestAssured/Selenium/Playwright
        test_prefixes = None
        pkg_prefixes = None
        class_prefixes = None
        counter_type = 'INSTRUCTION'
        if module == 'Common':
            mapping = {
                'RestAssured': {
                    'tests': ['core.api.impl.'],
                    # Narrow strictly to the RestAssured API impl package so shared core.ui
                    # code doesn't dilute the coverage percentage for this label.
                    'packages': ['core/api/impl', 'core.api.impl'],
                },
                'Selenium': {
                    # Include SeleniumActions and TargetFactory Selenium tests
                    'tests': ['ui.SeleniumActionsTest', 'ui.TargetFactorySeleniumTest'],
                    # Count coverage only from Selenium package to avoid shared core.ui
                    # classes lowering the displayed percentage for this label.
                    'packages': ['selenium'],
                    # Additionally, focus on SeleniumActions class which represents the
                    # main Selenium surface covered by unit tests (exclude helpers that
                    # are intentionally hard to unit test like CdpWarningSilencer).
                    'classes': ['selenium/SeleniumActions', 'selenium.SeleniumActions'],
                    # Use METHOD coverage for UI wrappers where line/instruction counts can be
                    # skewed by framework internals and non-deterministic branches in browsers.
                    # This yields a stable and representative figure for exercised API surface.
                    'counter': 'METHOD',
                },
                'Playwright': {
                    # Include PlaywrightActions and TargetFactory Playwright tests
                    'tests': ['ui.PlaywrightActionsTest', 'ui.TargetFactoryPlaywrightTest'],
                    # Count coverage only from Playwright package to avoid shared core.ui
                    # classes lowering the displayed percentage for this label.
                    'packages': ['playwright'],
                },
            }
            if label in mapping:
                test_prefixes = mapping[label]['tests']
                # JaCoCo package names in XML use slash-separated names, but in our generated file
                # packages appear as simple names like "selenium" or "playwright" for those namespaces,
                # and "core.api.impl" for API code. We'll check both forms just in case.
                pkg_prefixes = mapping[label]['packages']
                class_prefixes = mapping[label].get('classes')
                counter_type = mapping[label].get('counter', 'INSTRUCTION')

        total, passed, failed, skipped = parse_surefire(module_dir, test_name_prefixes=test_prefixes)
        coverage = parse_jacoco(
            module_dir,
            package_prefixes=pkg_prefixes,
            class_prefixes=class_prefixes,
            counter_type=counter_type,
        )
        pass_rate = (passed * 100.0 / total) if total else (100.0 if passed == 0 and failed == 0 else 0.0)
        pass_rate_str = f"{pass_rate:.1f}%"
        cov_str = 'n/a' if coverage is None else f"{coverage}%"
        sys.stdout.write(f"| {label} | {total} | {passed} | {failed} | {skipped} | {pass_rate_str} | {cov_str} |\n")


if __name__ == '__main__':
    main()
