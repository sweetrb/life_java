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
	// Validate command-line arguments
	if (args.length < 2) {
		System.err.println("Usage: java life.Main <columns> <lines>");
		System.err.println("Example: java life.Main 80 24");
		System.exit(1);
	}

	int columns, lines;
	try {
		columns = Integer.parseInt(args[0]);	// maxX
		lines = Integer.parseInt(args[1]);		// maxY
	} catch (NumberFormatException e) {
		System.err.println("Error: Both columns and lines must be valid integers");
		System.err.println("Usage: java life.Main <columns> <lines>");
		System.exit(1);
		return; // unreachable, but satisfies compiler
	}

	// Validate dimensions are positive
	if (columns <= 0 || lines <= 0) {
		System.err.println("Error: Both columns and lines must be positive integers");
		System.exit(1);
	}

	Life life = new Life(columns, lines);
	life.game();
} // main()

} // class Main

