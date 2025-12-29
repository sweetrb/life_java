/**
  * Generation
  *
  * Class to hold a single generation of a game of Life grid of cells
  */

package life;

import java.util.Random;

public class Generation {

private boolean [][] cell;	// create 2D array.  It will be initialized to false automatically
private int maxX, maxY;

/**
  * Generation() Constructor
  */
public Generation(int x, int y) {
	maxX = x;
	maxY = y;
	cell = new boolean[maxX][maxY];	// create 2D array.  It will be initialized to false automatically
} // Generation()

// getter for maxX
public int getMaxX() {
	return maxX;
}

// getter for maxY
public int getMaxY() {
	return maxY;
}

/**
  * randomize()
  *
  * randomly initialize cells to alive or dead status in the matrix
  */
public void randomize() {
	Random r = new Random();
	for (int y=0; y<maxY ; y++) {
		for (int x=0; x<maxX ; x++) {
			cell[x][y] = r.nextBoolean();  // 50% alive
		}
	}
} // randomize()

/**
  * get the state (live | dead) at coordinates x,y
  */
public boolean get(int x, int y) {
	return cell[x][y];
} // get()

/**
  * set the state (live | dead) at coordinates x,y
  */
public void set(int x, int y, boolean state) {
	cell[x][y] = state;
} // set()

/** 
  * return the number of neighbors for a given cell at x,y
  */
public int neighbors(int x, int y) {
	int n=0;					// the number of neighbors that this cell has
	int l, r, u, d; 	// left, right, up, down coordinates
	l = x-1; if (l < 0) { l = maxX-1; }
	r = x+1; if (r == maxX) { r = 0; }
	u = y-1; if (u < 0) { u = maxY-1; }
	d = y+1; if (d == maxY) { d = 0; }
	if (get(l,u)) n++;  if (get(x,u)) n++;  if (get(r,u)) n++;
	if (get(l,y)) n++;  /* center */        if (get(r,y)) n++;
	if (get(l,d)) n++;  if (get(x,d)) n++;  if (get(r,d)) n++;
	return n;
}

} // class Generation
