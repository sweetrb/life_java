/**
  * Term
  *
  * Terminal control class using JLine for cross-platform support.
  * Provides cursor control, screen clearing, and non-blocking keyboard input.
  */
package life;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Attributes;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;
import java.io.PrintWriter;

class Term {
	private Terminal terminal;
	private NonBlockingReader reader;
	private PrintWriter writer;
	private Attributes savedAttributes;
	private int LINES;
	private int COLUMNS;
	private static final char ESC = 0x1b;

	public Term(int lines, int columns) throws IOException {
		terminal = TerminalBuilder.builder()
			.system(true)
			.jansi(true)
			.build();

		// Save current attributes and enter raw mode
		savedAttributes = terminal.enterRawMode();

		reader = terminal.reader();
		writer = terminal.writer();
		LINES = lines;
		COLUMNS = columns;
	}

	public void clear() {
		// Use ANSI escape sequence directly for compatibility
		writer.printf("%c[H%c[J", ESC, ESC);
		writer.flush();
	}

	public void move(int x, int y) {
		// Use ANSI escape sequence directly for compatibility
		writer.printf("%c[%d;%dH", ESC, (y+1), (x+1));
	}

	public void printat(int x, int y, String s) {
		move(x, y);
		writer.print(s);
		writer.flush();
	}

	public int lines() {
		return LINES;
	}

	public int columns() {
		return COLUMNS;
	}

	/**
	 * Non-blocking key read.
	 * @return the character read, -1 for EOF, or -2 if no key pressed (timeout)
	 */
	public int getKey() throws IOException {
		return reader.read(1);  // 1ms timeout
	}

	public void close() {
		try {
			// Restore terminal attributes
			if (savedAttributes != null) {
				terminal.setAttributes(savedAttributes);
			}
			terminal.close();
		} catch (IOException e) {
			// ignore
		}
	}
}
