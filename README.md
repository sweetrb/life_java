# Conway's Game of Life in Java

A terminal-based implementation of Conway's Game of Life using Java and ANSI escape sequences.

This version uses escape sequences for cursor control and screen clearing, and was developed on a Raspberry Pi 2 running Raspbian. It has also been verified on OS X El Capitan 10.11.4 through macOS Sequoia.

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
