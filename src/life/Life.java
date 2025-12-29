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

// default constructor
public Life() {
	this(defaultMaxX, defaultMaxY);
}

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
		while (!done) {
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

			// swap the generations
			Generation tmp = today;
			today = tomorrow;
			tomorrow = tmp;

			// check for keyboard input
			int key = term.getKey();
			if (key == 'q') {
				done = true;
			} else if (key == 'r') {
				today.randomize();
				term.clear();
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

