/**
  * LifeMain
  *
  * Main() application entry point class for Conway's Life
  */
public class LifeMain {

/**
  * main()
  *
  * Entry point for the Life application
  */
public static void main(String args[]) {
	System.out.println("Life::main() application entrypoint called");

	int columns = Integer.parseInt(args[0]);	// maxX
	int lines = Integer.parseInt(args[1]);		// maxY

	Life life = new Life(columns, lines);
	System.out.println("About to call life.game()");
	life.game();
} // main()

} // class LifeMain

