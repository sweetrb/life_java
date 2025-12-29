/**
  * LifeMain
  *
  * Main() application entry point class for Conway's Life
  */
package life;

public class Main {

/**
  * main()
  *
  * Entry point for the Life application
  */
public static void main(String args[]) {
	int columns = Integer.parseInt(args[0]);	// maxX
	int lines = Integer.parseInt(args[1]);		// maxY

	Life life = new Life(columns, lines);
	life.game();
} // main()

} // class Main

