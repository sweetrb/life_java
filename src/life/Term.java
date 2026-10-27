/**
  * Term
  *
  * Terminal control class using JLine for cross-platform support.
  * Provides cursor control, screen clearing, and non-blocking keyboard input.
  *
  * Output is buffered a frame at a time: printat() appends to an in-memory
  * frame and refresh() writes it in one go, the same way curses batches
  * output between refresh() calls in the C++ and Perl versions.  Writing and
  * flushing each cell separately costs one write() syscall per changed cell,
  * which is what the terminal layer, not the Life algorithm, was spending
  * nearly all of its time on.
  */
package life;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Attributes;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class Term {
	private Terminal terminal;
	private NonBlockingReader reader;
	private PrintWriter writer;
	private Attributes savedAttributes;
	private int LINES;
	private int COLUMNS;
	private static final char ESC = 0x1b;

	// the current frame, written out by refresh()
	private StringBuilder frame = new StringBuilder(8192);

	// where the terminal's cursor will be once the frame so far is written,
	// so a cell adjacent to the previous one needs no cursor move at all.
	// -1 means "unknown", which forces the next printat() to emit a move.
	private int cursorX = -1;
	private int cursorY = -1;

	// the most recent keystroke, parked here by the reader thread.
	// -2 means "nothing pending", matching what getKey() returns.
	private final AtomicInteger pendingKey = new AtomicInteger(-2);

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

		startKeyReader();
	}

	/**
	 * JLine's reader offers no working non-blocking poll over a pty:
	 * available(), ready() and input().available() all report nothing with a
	 * keystroke already waiting, and read(timeout) with a timeout <= 0 blocks
	 * forever.  The only non-blocking option left, read(1), costs a 1 ms
	 * timeout every generation, which alone would cap the game at ~1000
	 * generations/sec.  So a daemon thread does the blocking read instead and
	 * parks the key for the game loop to pick up, which is the equivalent of
	 * what nodelay()/Console.KeyAvailable give the other three versions.
	 */
	private void startKeyReader() {
		Thread t = new Thread(() -> {
			try {
				int c;
				while ((c = reader.read()) >= 0) {
					pendingKey.set(c);
				}
			} catch (IOException e) {
				// terminal closed out from under us; nothing left to read
			}
		}, "life-key-reader");
		t.setDaemon(true);
		t.start();
	}

	public void clear() {
		// Use ANSI escape sequence directly for compatibility
		frame.setLength(0);
		writer.printf("%c[H%c[J", ESC, ESC);
		writer.flush();
		cursorX = cursorY = -1;
	}

	public void move(int x, int y) {
		// Use ANSI escape sequence directly for compatibility.  Built by
		// appending rather than printf() so that a cell costs no format-string
		// parse and no intermediate String.
		frame.append(ESC).append('[').append(y+1).append(';').append(x+1).append('H');
		cursorX = x;
		cursorY = y;
	}

	public void printat(int x, int y, String s) {
		if (x != cursorX || y != cursorY) {
			move(x, y);
		}
		frame.append(s);

		// the terminal advances the cursor by what we just wrote; at the right
		// margin where it would wrap, give up and move explicitly next time.
		cursorX += s.length();
		if (cursorX >= COLUMNS) {
			cursorX = cursorY = -1;
		}
	}

	/**
	 * Set the terminal's (or iTerm pane's) title.  OSC 1 leaves the cursor and
	 * attributes alone, so it rides along in the frame buffer harmlessly.
	 */
	public void setTitle(String title) {
		frame.append(ESC).append(']').append('1').append(';').append(title).append((char) 0x07);
	}

	/**
	 * Write the frame built up since the last refresh() and start a new one.
	 */
	public void refresh() {
		if (frame.length() > 0) {
			writer.write(frame.toString());
			writer.flush();
			frame.setLength(0);
		}
	}

	public int lines() {
		return LINES;
	}

	public int columns() {
		return COLUMNS;
	}

	/**
	 * Non-blocking key read.
	 * @return the character read, -1 for EOF, or -2 if no key pressed
	 */
	public int getKey() throws IOException {
		return pendingKey.getAndSet(-2);
	}

	public void close() {
		try {
			refresh();

			// Restore terminal attributes
			if (savedAttributes != null) {
				terminal.setAttributes(savedAttributes);
			}
			terminal.close();
		} catch (IOException e) {
			System.err.println("Warning: Error closing terminal: " + e.getMessage());
		}
	}
}
