/**
  * Life
  *
  * Application class for Conway's Life, with main()
  * Holds two Earth objects and impliment the rules and loop
  */
public class Life {

private Generation today, tomorrow;
private static final char [] cls = { 0x1b, 0x5b, 0x48, 0x1b, 0x5b, 0x4a } ;
public static final int defaultMaxX = 50;
public static final int defaultMaxY = 40;
private int maxX;
private int maxY;

// default constructor
public Life() {
	this(defaultMaxX, defaultMaxY);
	System.out.println("Life::Life()");
}

// constructor with world dimensions
public Life(int maxX, int maxY) {
	System.out.println("Life::Life("+maxX+","+maxY+")");
	this.maxX=maxX;
	this.maxY=maxY;
	today = new Generation(this.maxX, this.maxY);
	tomorrow = new Generation(this.maxX, this.maxY);
	today.randomize();
} // Life(x,y) Constructor

/**
  * game()
  *
  * main application loop, each iteration is one generation
  */
public void game() {
	System.out.println("Life::game()");
	Term term = new Term(maxY, maxX);

	boolean done;
	done = Boolean.FALSE;
	term.clear();
	while (! done) {
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

	} // while ! done
} // game()

} // class Life

