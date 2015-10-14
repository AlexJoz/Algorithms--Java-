import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> jSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new NullPointerException();
        
        Point[] jCopy = points.clone();
        Arrays.sort(jCopy);
        
        if (hasDuplicate(jCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }
        // and now show must go on )))

        for (int i = 0; i < jCopy.length - 3; i++) {
            Arrays.sort(jCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(jCopy, jCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < jCopy.length; last++) {
                // find last collinear to p point
                while (last < jCopy.length
                        && Double.compare(jCopy[p].slopeTo(jCopy[first]), jCopy[p].slopeTo(jCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && jCopy[p].compareTo(jCopy[first]) < 0) {
                    jSegments.add(new LineSegment(jCopy[p], jCopy[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
        // finds all line segments containing 4 or more points
    }

    // the number of line segments
    public int numberOfSegments() {
        return jSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }

    // test the whole array fo duplicate points
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

}
