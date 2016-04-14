/**
  * Term 
  *
  * Simple class to control linux terminal, clear screen, move cursor, get size, etc.
  */
package life;

class Term {
	private char ESC = 0x1b;
	private int LINES=0;
	private int COLUMNS=0;

	public Term(int lines, int columns) {
		LINES=lines;
		COLUMNS=columns;
	}

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

	public int lines () {
		//int lines = Integer.parseInt(System.getenv("LINES"));
		int lines = LINES;
		return lines;
	}

	public int columns () {
		//int cols = Integer.parseInt(System.getenv("COLUMNS"));
		int cols = COLUMNS;
		return cols;
	}
}

