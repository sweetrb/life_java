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

	Life life = new Life(60,40);
	System.out.println("About to call life.game()");
	life.game();
} // main()

} // class LifeMain

