# qqq-app-starter

Minimal example/learning QQQ application (one RDBMS table + Material Dashboard).
For production scaffolding use `new-qqq-application-template` instead (per README).

## QQQ 4.0 baseline — 2026-09-24

This learning starter targets Java 21, QQQ 4.0.0 and Material Dashboard 0.41.0. Its release dependencies resolve from Maven Central; use README.md for the database, environment and build commands. The historical review below predates this migration. The separate production scaffold is outside this release train's certification.

## Knowledge base

Deep-review dossiers live in the second-brain vault:

- Domain hub: `R:/Git.Local/KofTwentyTwo/second-brain/knowledge/qqq/qqq-hub.md`
- This repo's dossier: `R:/Git.Local/KofTwentyTwo/second-brain/knowledge/qqq/repos/qqq-app-starter.md`
- Reviewed at commit `f4e51d14afa1` (branch `main`, 2026-07-04).

Key facts from that review:

- Pins qqq 0.24.0-era artifacts (Jan 2025) from the legacy
  `maven.pkg.github.com/Kingsrook/qqq-maven-registry` — requires a GitHub PAT;
  three version eras behind current (0.40 GA / 4.0.0-RC.2 on Maven Central).
- Will not compile against qqq 4.0: `StarterAppMetaDataProvider.java:67` uses
  `QInstance.setAuthentication(...)` (removed; use
  `withInstanceDefaultAuthentication` / `registerAuthenticationProvider`), and
  the pom pins Java 17 vs 4.0's Java 21 floor.
- Licensing is split: LICENSE/NOTICE say Apache-2.0, but source headers, the
  pom header, and the checkstyle-enforced header template
  (`checkstyle/license.txt`) are still AGPL-3.0/Kingsrook; pom has no
  `<licenses>` block.
