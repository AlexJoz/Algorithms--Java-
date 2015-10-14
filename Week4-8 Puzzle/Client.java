import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdOut;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner =  new Scanner(new File("C:/Users/Skazzi/Desktop/Joz/prusa/models/8puzzle-testing/8puzzle/puzzle04.txt"));
        // read the N points from a file
       // In in = new In(args[0]);
        int N = scanner.nextInt();

        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = scanner.nextInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        System.out.println(initial +" INIT");
        System.out.println(solver.isSolvable());
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
