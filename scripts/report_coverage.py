#!/usr/bin/env python3
import os
import glob
import xml.etree.ElementTree as ET

def resolve_modules():
    env = os.environ.get("REPORT_MODULES")
    if env:
        return [m.strip() for m in env.split() if m.strip()]
    # Fallback heuristic: any immediate subdir with a pom.xml
    modules = []
    for name in os.listdir(os.getcwd()):
        p = os.path.join(os.getcwd(), name, "pom.xml")
        if os.path.isfile(p):
            modules.append(name)
    # Stable order
    modules.sort()
    return modules

def parse_surefire(module_dir):
    reports_dir = os.path.join(module_dir, "target", "surefire-reports")
    total = passed = failed = skipped = 0
    if not os.path.isdir(reports_dir):
        return total, passed, failed, skipped
    for f in glob.glob(os.path.join(reports_dir, "TEST-*.xml")):
        try:
            tree = ET.parse(f)
            root = tree.getroot()
            # Surefire XML attributes on testsuite
            t = int(root.attrib.get("tests", 0))
            failures = int(root.attrib.get("failures", 0))
            errors = int(root.attrib.get("errors", 0))
            skips = int(root.attrib.get("skipped", 0))
            total += t
            failed += failures + errors
            skipped += skips
        except Exception:
            # ignore malformed entries
            continue
    passed = max(0, total - failed - skipped)
    return total, passed, failed, skipped

def parse_jacoco_coverage(module_dir):
    # Prefer XML if present: target/site/jacoco/jacoco.xml
    xml_path = os.path.join(module_dir, "target", "site", "jacoco", "jacoco.xml")
    if os.path.isfile(xml_path):
        try:
            tree = ET.parse(xml_path)
            root = tree.getroot()
            instr = None
            for counter in root.iter("counter"):
                if counter.attrib.get("type") == "INSTRUCTION":
                    instr = counter
                    break
            if instr is not None:
                missed = int(instr.attrib.get("missed", 0))
                covered = int(instr.attrib.get("covered", 0))
                total = missed + covered
                pct = (covered / total * 100.0) if total > 0 else 0.0
                return pct
        except Exception:
            pass
    # Fallback: try to read index.html and grep a percentage (best-effort)
    html_path = os.path.join(module_dir, "target", "site", "jacoco", "index.html")
    if os.path.isfile(html_path):
        try:
            with open(html_path, "r", encoding="utf-8", errors="ignore") as fh:
                content = fh.read()
            # crude heuristic: look for "Instructions" row percent like > 87% <
            import re
            m = re.search(r"Instructions[\s\S]*?(\d+)%", content)
            if m:
                return float(m.group(1))
        except Exception:
            pass
    return None

def format_pct(v):
    return f"{v:.1f}%" if v is not None else "n/a"

def main():
    modules = resolve_modules()
    rows = []
    agg_total = agg_passed = agg_failed = agg_skipped = 0
    for m in modules:
        module_dir = os.path.join(os.getcwd(), m)
        total, passed, failed, skipped = parse_surefire(module_dir)
        cov = parse_jacoco_coverage(module_dir)
        pass_rate = (passed / total * 100.0) if total > 0 else 0.0
        rows.append({
            "module": m,
            "total": total,
            "passed": passed,
            "failed": failed,
            "skipped": skipped,
            "pass_rate": pass_rate,
            "coverage": cov,
        })
        agg_total += total
        agg_passed += passed
        agg_failed += failed
        agg_skipped += skipped

    agg_rate = (agg_passed / agg_total * 100.0) if agg_total > 0 else 0.0

    print("### CI Test Summary")
    print()
    print("- Overall tests: {} | Passed: {} | Failed: {} | Skipped: {} | Pass rate: {}".format(
        agg_total, agg_passed, agg_failed, agg_skipped, format_pct(agg_rate)))
    print()
    print("### Module breakdown")
    print()
    print("| Module | Tests | Passed | Failed | Skipped | Pass rate | Coverage |")
    print("|---|---:|---:|---:|---:|---:|---:|")
    for r in rows:
        print("| {} | {} | {} | {} | {} | {} | {} |".format(
            r["module"], r["total"], r["passed"], r["failed"], r["skipped"], format_pct(r["pass_rate"]), format_pct(r["coverage"])) )

if __name__ == "__main__":
    main()
