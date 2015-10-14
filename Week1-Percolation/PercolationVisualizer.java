

/****************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java StdDraw.java In.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ****************************************************************************/

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class PercolationVisualizer {

	// draw N-by-N percolation system
	public static void draw(Percolation perc, int N) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, N);
		StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

		// draw N-by-N grid
		int opened = 0;
		for (int row = 1; row <= N; row++) {
			for (int col = 1; col <= N; col++) {
				if (perc.isFull(row, col)) {
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
					opened++;
				} else if (perc.isOpen(row, col)) {
					StdDraw.setPenColor(StdDraw.WHITE);
					opened++;
				} else
					StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
			}
		}

		// write status text
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(.25 * N, -N * .025, opened + " open sites");
		if (perc.percolates())
			StdDraw.text(.75 * N, -N * .025, "percolates");
		else
			StdDraw.text(.75 * N, -N * .025, "does not percolate");

	}

	public static void main(String[] args) throws FileNotFoundException {
	    
	    Scanner scanner = new Scanner(new File("C:/Users/Skazzi/Desktop/Joz/prusa/models/percolation-testing/percolation/input6.txt"));
	   
		int N = scanner.nextInt(); // N-by-N percolation system

		// repeatedly read in sites to open and draw resulting system
		Percolation perc = new Percolation(N);
		draw(perc, N);
		while (scanner.hasNextInt()) {
			StdDraw.show(0); // turn on animation mode
			int i = scanner.nextInt();
			int j = scanner.nextInt();
			perc.open(i, j);
			draw(perc, N);
			StdDraw.show(500); // pause for 100 miliseconds
		}
	}
}