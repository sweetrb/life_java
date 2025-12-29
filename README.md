# Conway's Game of Life in Java

A terminal-based implementation of Conway's Game of Life using Java with JLine for cross-platform terminal control.

This version uses JLine 3.x for terminal manipulation and keyboard input, providing consistent behavior across different platforms. Originally developed on a Raspberry Pi 2 running Raspbian, it has also been verified on OS X El Capitan 10.11.4 through macOS Sequoia.

## Author

Rob Sweet

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
