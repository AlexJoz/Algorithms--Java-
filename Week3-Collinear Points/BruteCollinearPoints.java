import java.util.ArrayList;
import java.util.Arrays;

/*
 * Write a program BruteCollinearPoints.java 
 * that examines 4 points at a time and checks whether they all
 * lie on the same line segment, returning all such line segments.
 * 
 * To check whether the 4 points p, q, r, and s are collinear, 
 * check whether the three slopes between p and q, between p and r, 
 * and between p and s are all equal.
 */

public class BruteCollinearPoints {
    private ArrayList<LineSegment> jSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new NullPointerException();
        // and now show must go on )))

        Point[] jCopy = points.clone();
        Arrays.sort(jCopy);

        if (hasDuplicate(jCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }

        for (int first = 0; first < jCopy.length - 3; first++) {
            for (int second = first + 1; second < jCopy.length - 2; second++) {
                double slopeFS = jCopy[first].slopeTo(jCopy[second]);
                for (int third = second + 1; third < jCopy.length - 1; third++) {
                    double slopeFT = jCopy[first].slopeTo(jCopy[third]);
                    if (slopeFS == slopeFT) {
                        for (int forth = third + 1; forth < jCopy.length; forth++) {
                            double slopeFF = jCopy[first].slopeTo(jCopy[forth]);
                            if (slopeFS == slopeFF) {
                                jSegments.add(new LineSegment(jCopy[first], jCopy[forth]));
                            }
                        }
                    }
                }

            }
        }
    }
    
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;

    }
    
    // the number of line segments
    public int numberOfSegments() {
        return jSegments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }
}