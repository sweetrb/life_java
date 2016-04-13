// test harnes for Tput class

class Tput_test  {
	public static void main (String args[]) {
		System.out.println("Tput_test");

		Tput tput = new Tput();

		int lines = tput.getLines();
		System.out.println("Lines: "+lines);

		int cols = tput.getCols();
		System.out.println("Cols: "+cols);

		while (--lines > 0) {
			while (--cols > 0) {
				tput.printat(lines,cols,".");
			}
		}
	}
}
