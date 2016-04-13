/**
  * Term 
  *
  * Simple class to control linux terminal, clear screen, move cursor, get size, etc.
  */
class Term {
	private char ESC = 0x1b;

	public void clear() {
		System.out.format("%c[H%c[J", ESC, ESC);
	}

	public void move(int x, int y) {
		System.out.format("%c[%d;%dH", ESC, (y+1), (x+1));
	}

	public void printat(int x, int y, String s) {
		move(x, y);
		System.out.print(s);
	}

	public int LINES () {
		String s = System.getenv("LINES");
		System.out.format("LINES = '%s'\n", s);
		int lines = Integer.parseInt(System.getenv("LINES"));
		return lines;
	}

	public int COLUMNS () {
		int cols = Integer.parseInt(System.getenv("COLUMNS"));
		return cols;
	}
}

