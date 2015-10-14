import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class ClientOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner =  new Scanner(new File("C:/Users/Skazzi/Desktop/Joz/prusa/models/collinear-testing/collinear/input6.txt"));
        // read the N points from a file
       // In in = new In(args[0]);
        int N = scanner.nextInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
            System.out.println(p);
        }
        Arrays.sort(points);
        System.out.println("sorted");
        for (Point p : points) {
            p.draw();
            System.out.println(p);
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        scanner.close();
    }
}