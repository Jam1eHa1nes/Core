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


def parse_surefire(module_dir: Path):
    report_dir = module_dir / 'target' / 'surefire-reports'
    total = passed = failed = skipped = 0

    if not report_dir.exists():
        return total, passed, failed, skipped

    for xml_file in report_dir.glob('TEST-*.xml'):
        try:
            tree = ET.parse(xml_file)
            root = tree.getroot()
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


def parse_jacoco(module_dir: Path):
    jacoco_xml = module_dir / 'target' / 'site' / 'jacoco' / 'jacoco.xml'
    if not jacoco_xml.exists():
        return None  # n/a
    try:
        tree = ET.parse(jacoco_xml)
        root = tree.getroot()
        covered = missed = 0
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
    modules = [m for m in modules_env.split() if m]
    if not modules:
        # Fallback: detect modules that have a pom.xml next to parent
        modules = [p.name for p in ROOT.iterdir() if (p / 'pom.xml').exists()]

    lines = []
    lines.append('Module\tTests\tPassed\tFailed\tSkipped\tPass rate\tCoverage')
    for module in modules:
        module_dir = ROOT / module
        total, passed, failed, skipped = parse_surefire(module_dir)
        coverage = parse_jacoco(module_dir)
        lines.append(format_row(module, total, passed, failed, skipped, coverage))

    # Print Markdown code block with a TSV table for readability in PR comment
    print("```")
    for ln in lines:
        print(ln)
    print("```")


if __name__ == '__main__':
    main()
