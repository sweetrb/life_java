# Conway's Game of Life in Java

A terminal-based implementation of Conway's Game of Life using Java with JLine for cross-platform terminal control.

This version uses JLine 3.x for terminal manipulation and keyboard input, providing consistent behavior across different platforms. Originally developed on a Raspberry Pi 2 running Raspbian, it has also been verified on OS X El Capitan 10.11.4 through macOS Sequoia.

## Why This Project?

I implement Conway's Game of Life in various programming languages as a benchmark for comparing:

- Runtime performance across languages
- Development overhead and complexity
- Language-specific idioms and patterns

This modest-sized application provides consistent insight into each language's strengths and characteristics.

## About Me

I am a software developer and IT leader with over 35 years of experience in the industry.

As partner and CEO of Superior Technologies Research (<www.superiortech.io>) in Houghton, MI, I lead a team of highly skilled developers working on diverse projects including large enterprise systems, cybersecurity solutions, and custom software development.

## Building

```bash
ant
```

## Running

```bash
./life
```

The `life` script automatically passes terminal dimensions to the jar file.

## Controls

- `q` - Quit
- `r` - Restart with a new random pattern

## Dependencies

- Java JDK
- Apache Ant
- JLine 3.x (bundled in JAR, download to `lib/` before building)
