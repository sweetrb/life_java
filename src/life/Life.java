/**
  * Life
  *
  * Application class for Conway's Life, with main()
  * Holds two Earth objects and implement the rules and loop
  */

package life;

import java.io.IOException;

public class Life {

private Generation today, tomorrow;
public static final int defaultMaxX = 55;
public static final int defaultMaxY = 40;
private int maxX;
private int maxY;
private int restartAfter = 0;	// generations between automatic restarts, 0 = never
private boolean showGenerations = false;	// put the generation count in the pane title
private boolean showTime = false;		// put the time per generation in the pane title

// how often the pane title is refreshed, in milliseconds.  Rewriting it every
// generation would cost more than the game does.
private static final long titleIntervalMs = 100;

// default constructor
public Life() {
	this(defaultMaxX, defaultMaxY);
}

// constructor with world dimensions, an automatic restart interval and what
// the pane title should report
public Life(int maxX, int maxY, int restartAfter, boolean showGenerations, boolean showTime) {
	this(maxX, maxY);
	this.restartAfter = restartAfter;
	this.showGenerations = showGenerations;
	this.showTime = showTime;
} // Life(x,y,restartAfter,showGenerations,showTime) Constructor

// constructor with world dimensions and an automatic restart interval
public Life(int maxX, int maxY, int restartAfter) {
	this(maxX, maxY);
	this.restartAfter = restartAfter;
} // Life(x,y,restartAfter) Constructor

/**
  * title()
  *
  * what the pane title should say for this generation and rate
  */
private String title(long generations, double usPerGen) {
	StringBuilder t = new StringBuilder("Java");
	if (showGenerations) {
		t.append(" | gen ").append(generations);
	}
	if (showTime && usPerGen > 0.0) {
		t.append(String.format(" | %.1f us/gen", usPerGen));
	}
	return t.toString();
} // title()

// constructor with world dimensions
public Life(int maxX, int maxY) {
	this.maxX = maxX;
	this.maxY = maxY;
	today = new Generation(maxX, maxY);
	tomorrow = new Generation(maxX, maxY);
	today.randomize();
} // Life(x,y) Constructor

/**
  * game()
  *
  * main application loop, each iteration is one generation
  */
public void game() {
	Term term = null;
	try {
		term = new Term(maxY, maxX);
		term.clear();

		boolean done = false;
		int gen = 0;			// generations since the last (re)start
		long generations = 0;	// generations since the program started

		boolean titled = showGenerations || showTime;
		long sampled = System.nanoTime();
		long sampledAt = 0;
		double usPerGen = 0.0;

		while (!done) {
			generations++;
			for (int y=0 ; y < today.getMaxY() ; y++) {
				for (int x=0 ; x < today.getMaxX() ; x++) {
					// display current generation
					if (today.get(x,y) != tomorrow.get(x,y)) {
						term.printat(x, y, today.get(x,y) ? "O" : " " );
					}

					// calculate next generation
					int n = today.neighbors(x,y);
					boolean state = false;	// default to dead
					if (n == 2) {
						state = today.get(x,y);
					} else if (n == 3) {
						state = true;
					}
					tomorrow.set(x,y,state);
				} // for x
			} // for y

			// write this generation's changes in one go, as curses' refresh()
			// does at the end of the C++ version's draw()
			term.refresh();

			// swap the generations
			Generation tmp = today;
			today = tomorrow;
			tomorrow = tmp;

			// automatic restart, the same effect as the user hitting 'r'.
			// Deliberately no clear(): tomorrow still holds the generation on
			// the screen, so the next frame's diff repaints exactly what
			// changed.  Clearing would blank cells the diff then skips.
			if (restartAfter != 0 && ++gen >= restartAfter) {
				today.randomize();
				gen = 0;
			}

			// refresh the pane title, at most every titleIntervalMs
			if (titled) {
				long now = System.nanoTime();
				long elapsedUs = (now - sampled) / 1000;
				if (elapsedUs >= titleIntervalMs * 1000) {
					if (generations > sampledAt) {
						usPerGen = (double) elapsedUs / (generations - sampledAt);
					}
					term.setTitle(title(generations, usPerGen));
					term.refresh();
					sampled = now;
					sampledAt = generations;
				}
			}

			// check for keyboard input
			int key = term.getKey();
			if (key == 'q') {
				done = true;
			} else if (key == 'r') {
				today.randomize();
			}

		} // while ! done
	} catch (IOException e) {
		System.err.println("Terminal error: " + e.getMessage());
	} finally {
		if (term != null) {
			term.close();
		}
	}
} // game()

} // class Life

