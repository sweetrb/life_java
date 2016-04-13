import java.io.*;

class Tput {

private int lines;
private int cols;
Runtime rt;

public Tput() {
	rt = Runtime.getRuntime();

	lines = getLines();
	cols = getCols();
}

public int getLines() {
	int n=0;
	try {
		Process pr = rt.exec("tput lines");
		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		while ((line=input.readLine()) != null) {
			// line has the current line of output in string form
			n = Integer.parseInt(line);
		}
		int exitVal = pr.waitFor();
	} catch (Exception e) {
		System.out.println(e.toString());
		e.printStackTrace();
	}
	return n;
}

public int getCols() {
	int n=0;
	try {
		Process pr = rt.exec("tput cols");
		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		while ((line=input.readLine()) != null) {
			// line has the current line of output in string form
			n = Integer.parseInt(line);
		}
		int exitVal = pr.waitFor();
	} catch (Exception e) {
		System.out.println(e.toString());
		e.printStackTrace();
	}
	return n;
}

public void printat(int l, int c, String s) {
	// tput l c
	try {
		Process pr = rt.exec("tput cup "+l+" "+c+";echo "+s);
		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		while ((line=input.readLine()) != null) {
			// do nothing with the line, this is an output only command
		}
		int exitVal = pr.waitFor();
	} catch (Exception e) {
		System.out.println(e.toString());
		e.printStackTrace();
	}
}

} // class Tput
