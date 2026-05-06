# Java script (no, not that one)

A live demo repository for the talk **"Java as a scripting language"** — showing how modern Java (21+) can be used for quick, dependency-free (or dependency-rich) scripts, without Maven or Gradle.

> **How to follow along:** browse the commit history step by step. Each commit is tagged `[STEP-N]` and introduces one new concept.

---

## The story, commit by commit

| Step | Commit message | What it introduces                                         |
|------|---------------|------------------------------------------------------------|
| 0 | add readme summary | Starting point                                             |
| 1 | initial list files | Plain Java single-file program (JEP-330/JEP-458)           |
| 2 | multiple file | Multi-file compilation without a build tool                |
| 3 | bye bye class | Unnamed class — `void main()` without boilerplate (JEP-512) |
| 4 | simpler IO | Implicit `System.out` / `System.err` helpers (JEP-512)     |
| 5 | shebang | Direct execution via shebang `#!/usr/bin/env` (JEP-330)    |
| 6 | jbang | JBang: inline deps, templates, zero-config toolchain       |
| 7 | fix native | GraalVM native image via JBang `--native`                  |
| 8 | picocli | CLI argument parsing with PicoCLI                          |
| 9 | TamboUI | Terminal UI with TamboUI + jline3                          |
| 10 | add local hook settings and DukeRunner | Claude Code hooks + DukeRunner demo                        |

---

## Key JEPs covered

- **JEP-330** — Launch single-file source-code programs (`java HelloWorld.java`)
- **JEP-458** — Multi-file program launch without compilation step
- **JEP-512** — Unnamed classes and instance `main` methods (no `public static void main(String[] args)` required)

---

## Running the demos

This repo uses **JBang** — no Maven or Gradle needed.

```sh
# Run any script directly
jbang <file>.java
```
```sh
# Or execute via shebang (after chmod +x)
./ls.java
```
```sh
# Scaffold a new CLI script
jbang init --template=cli <name>.java
```
```sh
# Build a GraalVM native binary
jbang export native hello.java
```

---

## Scripts in this repo

| File | What it does |
|------|-------------|
| `ListFiles.java` | Minimal file listing — the starting point |
| `ListFilesAdvanced.java` | Multi-file version using the `files/` package |
| `ls.java` | Full `ls` clone: PicoCLI flags, color output, human-readable sizes |
| `GithubInspector.java` | Browse GitHub issues in the terminal — TamboUI + GitHub API + jline3 |
| `hello.java` | LangChain4j + Ollama LLM integration, GraalVM native-ready |

---

## Tech stack

- [JBang](https://www.jbang.dev/) — single-file Java toolchain
- [PicoCLI](https://picocli.info/) — CLI argument parsing
- [TamboUI](https://github.com/tamboui/tamboui) — terminal UI toolkit
- [jline3](https://github.com/jline/jline3) — terminal input/output
- [LangChain4j](https://docs.langchain4j.dev/) — LLM integration
- [GraalVM native image](https://www.graalvm.org/native-image/) — ahead-of-time compilation
