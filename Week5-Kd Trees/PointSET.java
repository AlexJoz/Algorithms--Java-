import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;

/*
 * Brute-force implementation. Write a mutable data type PointSET.java that
 * represents a set of points in the unit square.
 * 
 */

public class PointSET {
    private TreeSet<Point2D> jSet;

    // construct an empty set of points
    public PointSET() {
        jSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return jSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return jSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        jSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null input =(");
        }
        return jSet.contains(p);
    }

    // draw all points to standard draw
    // TODO: Changed Type of TreeSet to jNodes, so remake draw too ^^
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point2d : jSet) {
            point2d.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> jStack = new Stack<>();
        for (Point2D point2d : jSet) {
            //should i check on the rect border? if so - done =)
            if (point2d.x() >= rect.xmin() 
                    && point2d.x() <= rect.xmax() 
                    && point2d.y() >= rect.ymin() 
                    && point2d.y() <= rect.ymax()){
                jStack.push(point2d);
                System.err.println(point2d+" Pushed");
            }
        }
        return jStack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET brute = new PointSET();
        // dots from circle 10
        Point2D p1 = new Point2D(0.206107, 0.095492);
        Point2D p2 = new Point2D(0.975528, 0.654508);
        Point2D p3 = new Point2D(0.024472, 0.345492);
        Point2D p4 = new Point2D(0.793893, 0.095492);
        Point2D p5 = new Point2D(0.793893, 0.904508);
        Point2D p6 = new Point2D(0.975528, 0.345492);
        Point2D p7 = new Point2D(0.206107, 0.904508);
        Point2D p8 = new Point2D(0.500000, 0.000000);
        Point2D p9 = new Point2D(0.024472, 0.654508);
        Point2D pt = new Point2D(0.500000, 1.000000);

        brute.insert(p1);
        brute.insert(p2);
        brute.insert(p3);
        brute.insert(p4);
        brute.insert(p5);
        brute.insert(p6);
        brute.insert(p7);
        brute.insert(p8);
        brute.insert(p9);
        brute.insert(pt);
        //make simple rect
        RectHV rect = new RectHV(0.0, 0.0, 0.8, 0.8);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        rect.draw();
        brute.draw();
        //draw that in rect
        StdDraw.setPenRadius(.03);
        StdDraw.setPenColor(StdDraw.RED);
        for (Point2D p : brute.range(rect))
            p.draw();
        
    }
}
