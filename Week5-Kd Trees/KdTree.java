import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final RectHV TILE = new RectHV(0, 0, 1, 1);
    private static final boolean DEBUGNOTES = false;
    private Node root;
    private int rootSize;

    private enum Direction {
        VERTICAL {
            public Direction opposite() {
                return HORIZONTAL;
            }
        },
        HORIZONTAL {
            public Direction opposite() {
                return VERTICAL;
            }
        };

        public abstract Direction opposite();
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return rootSize == 0;

    }

    public int size() {
        return rootSize;

    }

    public boolean contains(Point2D p) {
        return getD(root, p) != null;
    }

    private Direction getD(Node x, Point2D p) {
        if (x == null)
            return null;
        int cmp = x.compareTo(p);
        if (cmp < 0)
            return getD(x.getLeft(), p);
        else if (cmp > 0)
            return getD(x.getRight(), p);
        else
            return x.getDirection();
    }

    public void insert(Point2D p) {
        if (DEBUGNOTES) {
            System.out.println("this.insert point " + p);
        }
        if (p == null)
            throw new NullPointerException();
        root = put(root, p, Direction.VERTICAL);
    }

    private Node put(Node x, Point2D p, Direction d) {

        if (x == null) {
            if (DEBUGNOTES) {
                System.out.println("this.put new node; point " + p + "; direction " + d);
            }
            rootSize = rootSize + 1;
            return new Node(p, d);
        }

        Point2D nodePoint = x.getPoint();
        if (nodePoint.equals(p)) {
            return x;
        }

        int cmp = x.compareTo(p);
        if (cmp < 0) {
            x.setLeft(put(x.getLeft(), p, d.opposite()));
        } else {
            x.setRight(put(x.getRight(), p, d.opposite()));
        }
        return x;
    }

    public void draw() {
        drawNode(root, TILE);
    }

    private void drawNode(Node x, RectHV rect) {
        if (x != null) {
            if (x.isVertical()) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                x.getPoint().draw();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(x.getX(), rect.ymax(), x.getX(), rect.ymin());
            }
            if (!x.isVertical()) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                x.getPoint().draw();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(rect.xmax(), x.getY(), rect.xmin(), x.getY());
            }
            drawNode(x.getLeft(), getChildRect(x, rect, true));
            drawNode(x.getRight(), getChildRect(x, rect, false));
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> jQueue = new Queue<>();
        inRect(root, TILE, rect, jQueue);
        return jQueue;
    }

    private Iterable<Point2D> inRect(Node node, RectHV nodeRect, RectHV rect, Queue<Point2D> jQueue) {
        if (node != null && rect.intersects(nodeRect)) {
            if (rect.contains(node.p)) {
                jQueue.enqueue(node.p);
            }
            inRect(node.getLeft(), getChildRect(node, nodeRect, true), rect, jQueue);
            inRect(node.getRight(), getChildRect(node, nodeRect, false), rect, jQueue);
        }
        return new Queue<Point2D>();
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return theNearest(root, TILE, p, null, Double.MAX_VALUE);
    }

    /**
     * @param node
     *            node to proceed
     * @param rect
     *            child rectangle
     * @param p
     *            point to proceed
     * @param champion
     *            nearest point
     * @param minDistance
     *            min distance to nearest
     * @return nearest point
     */
    private Point2D theNearest(Node node, RectHV rect, Point2D p, Point2D champion, double minDistance) {

        // distance to childRect less than distance to current champion
        if (node != null && rect.distanceSquaredTo(p) < minDistance) {
            // distance from curent node to current point
            double tmpDist = node.getPoint().distanceSquaredTo(p);
            if (tmpDist < minDistance) {
                champion = node.getPoint();
                minDistance = tmpDist;
            }
            int cmp = node.compareTo(p);
            // always check the side where the point's in first
            if (cmp < 0) {
                champion = theNearest(node.getLeft(), getChildRect(node, rect, true), p, champion, minDistance);
                champion = theNearest(node.getRight(), getChildRect(node, rect, false), p, champion,
                        champion.distanceSquaredTo(p));
            } else if (cmp > 0) {
                champion = theNearest(node.getRight(), getChildRect(node, rect, false), p, champion, minDistance);
                champion = theNearest(node.getLeft(), getChildRect(node, rect, true), p, champion,
                        champion.distanceSquaredTo(p));
            }
        }
        return champion;
    }

    private RectHV getChildRect(Node x, RectHV rect, boolean left) {
        if (x.isVertical()) {
            if (left) {
                return new RectHV(rect.xmin(), rect.ymin(), x.getX(), rect.ymax());
            } else {
                return new RectHV(x.getX(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        } else {
            if (left) {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.getY());
            } else {
                return new RectHV(rect.xmin(), x.getY(), rect.xmax(), rect.ymax());
            }
        }
    }

    private static class Node {
        private Point2D p;
        private Direction d;
        private Node left, right;

        public Node(Point2D p, Direction d) {
            this.p = p;
            this.d = d;
        }

        public Point2D getPoint() {
            return this.p;
        }

        public Direction getDirection() {
            return this.d;
        }

        /** Returns the vertical flag. */
        public boolean isVertical() {
            return d == Direction.VERTICAL;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int compareTo(Point2D that) {
            if (that == null)
                throw new NullPointerException("Null item");
            if (this.p.y() == that.y() && this.p.x() == that.x())
                return 0;
            if (isVertical() && that.x() < this.p.x())
                return -1;
            if (!isVertical() && that.y() < this.p.y())
                return -1;
            return +1; // right
        }

        public double getX() {
            return this.p.x();
        }

        public double getY() {
            return this.p.y();
        }

    }

    public static void main(String[] args) {
        if (DEBUGNOTES) {
            KdTree test = new KdTree();
            Point2D p1 = new Point2D(0.5, 0.5);
            System.out.println(test.size() + " - size");
            System.out.println(test.isEmpty() + " - isEmpty");
            test.insert(p1);
            System.out.println(test.size() + " - size");

            Point2D p2 = new Point2D(0.1, 0.1);
            test.insert(p2);
            System.out.println(test.size() + " - size");

            Point2D p3 = new Point2D(0.7, 0.7);
            test.insert(p3);
            System.out.println(test.size() + " - size");

            Point2D p4 = new Point2D(0.7, 0.1);
            test.insert(p4);
            System.out.println(test.size() + " - size");

            System.out.println("contains p3? " + test.contains(p3));
            System.out.println("contains new Point 0.2-0.2? " + test.contains(new Point2D(0.2, 0.2)));

            System.out.println("nearest to 0.2-0.2" + test.nearest(new Point2D(0.2, 0.2)));

            test.draw();
        }
    }

}
