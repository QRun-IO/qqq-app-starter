#!/usr/bin/env python3
"""Verify exact project notices in explicitly listed Maven archives."""

import argparse
from pathlib import Path
import sys
from zipfile import BadZipFile, ZipFile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, required=True,
                        help="consumer repository containing LICENSE and NOTICE")
    parser.add_argument("--prefix", default="META-INF",
                        help="directory containing the project's archive copies")
    parser.add_argument("archives", type=Path, nargs="+")
    args = parser.parse_args()
    expected = {name: (args.root / name).read_bytes() for name in ("LICENSE", "NOTICE")}
    failures = []
    for archive in args.archives:
        try:
            with ZipFile(archive) as jar:
                for name, content in expected.items():
                    entry = f"{args.prefix.rstrip('/')}/{name}"
                    if jar.namelist().count(entry) != 1:
                        failures.append(f"{archive}: expected exactly one {entry}")
                    elif jar.read(entry) != content:
                        failures.append(f"{archive}: {entry} differs from root {name}")
        except (OSError, BadZipFile) as error:
            failures.append(f"{archive}: {error}")
    if failures:
        print("\n".join(failures), file=sys.stderr)
        return 1
    print(f"PASS: exact LICENSE/NOTICE in {len(args.archives)} archives")
    return 0


if __name__ == "__main__":
    sys.exit(main())
