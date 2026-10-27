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
		usage();
		System.exit(1);
	}

	int columns, lines;
	try {
		columns = Integer.parseInt(args[0]);	// maxX
		lines = Integer.parseInt(args[1]);		// maxY
	} catch (NumberFormatException e) {
		System.err.println("Error: Both columns and lines must be valid integers");
		usage();
		System.exit(1);
		return; // unreachable, but satisfies compiler
	}

	// Validate dimensions are positive
	if (columns <= 0 || lines <= 0) {
		System.err.println("Error: Both columns and lines must be positive integers");
		System.exit(1);
	}

	// Optional automatic restart interval and pane title reporting
	int restartAfter = 0;
	boolean showGenerations = false;
	boolean showTime = false;
	for (int i=2 ; i < args.length ; i++) {
		if (args[i].equals("-r") || args[i].equals("--restart")) {
			if (++i >= args.length) {
				System.err.println("Error: " + args[i-1] + " requires a generation count");
				usage();
				System.exit(1);
			}
			try {
				restartAfter = Integer.parseInt(args[i]);
			} catch (NumberFormatException e) {
				System.err.println("Error: invalid generation count '" + args[i] + "'");
				usage();
				System.exit(1);
			}
			if (restartAfter < 0) {
				System.err.println("Error: generation count must not be negative");
				System.exit(1);
			}
		} else if (args[i].equals("-g") || args[i].equals("--generations")) {
			showGenerations = true;
		} else if (args[i].equals("-t") || args[i].equals("--time")) {
			showTime = true;
		} else {
			System.err.println("Error: unknown option '" + args[i] + "'");
			usage();
			System.exit(1);
		}
	}

	Life life = new Life(columns, lines, restartAfter, showGenerations, showTime);
	life.game();
} // main()

/**
  * usage()
  */
private static void usage() {
	System.err.println("Usage: java life.Main <columns> <lines> [-r | --restart <generations>] [-g | --generations] [-t | --time]");
	System.err.println("Example: java life.Main 80 24 --restart 20");
	System.err.println("  -r, --restart <generations>  restart with a new random pattern");
	System.err.println("                               every <generations> generations (0 = never)");
	System.err.println("  -g, --generations            show the generation count in the pane title");
	System.err.println("  -t, --time                   show the time per generation in the pane title");
} // usage()

} // class Main

