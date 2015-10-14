
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] opendGrid;
    // row length
    private int gLength;
    private WeightedQuickUnionUF quickF;
    private byte[] backwash;
    private boolean perc;
    // final private int topConnect = 2; // 010
    // final private int bottomConnect = 1; // 001
    // final private int topAndBottomConnect = 3; // 011

    // create N-by-N grid, with all sites blocked
    public Percolation(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("illegal N");
        }
        gLength = N;
        int gSize = gLength * gLength;
        quickF = new WeightedQuickUnionUF(gSize);
        opendGrid = new boolean[gSize];
        backwash = new byte[gSize];
    }

    // open site (row i, column j) if it is not open already
    public void open(final int i, final int j) {
        int current1dIndex = xyTo1D(i, j);
        byte connectionStatus = 0;
        // check top or bottom
        if (i == 1) {
            connectionStatus = 2;
        }
        if (i == gLength) {
            connectionStatus = (byte) (connectionStatus | 1);
        }
        // neighbors
        if (j > 1 && isOpen(i, j - 1)) {
            // get neighbor root connection status
            connectionStatus = (byte) (connectionStatus | backwash[quickF.find(xyTo1D(i, j - 1))]);
            // and connect
            quickF.union(current1dIndex, xyTo1D(i, j - 1));
        }
        // same for others
        if (j < gLength && isOpen(i, j + 1)) {
            connectionStatus = (byte) (connectionStatus | backwash[quickF.find(xyTo1D(i, j + 1))]);
            quickF.union(current1dIndex, xyTo1D(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            connectionStatus = (byte) (connectionStatus | backwash[quickF.find(xyTo1D(i - 1, j))]);
            quickF.union(current1dIndex, xyTo1D(i - 1, j));
        }
        if (i < gLength && isOpen(i + 1, j)) {
            connectionStatus = (byte) (connectionStatus | backwash[quickF.find(xyTo1D(i + 1, j))]);
            quickF.union(current1dIndex, xyTo1D(i + 1, j));
        }
        opendGrid[current1dIndex] = true;
        // set the newRoot status
        backwash[quickF.find(current1dIndex)] = connectionStatus;
        if (connectionStatus == 3) {
            perc = true;
        }

    }

    // is site (row i, column j) open?
    public boolean isOpen(final int i, final int j) {
        checkXY(i, j);
        return opendGrid[xyTo1D(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(final int i, final int j) {
        checkXY(i, j);
        // Check > is root connected to top
        int checkRoot = backwash[quickF.find(xyTo1D(i, j))];
        return (checkRoot > 1) ? true : false;
    }

    // does the system percolate?
    public boolean percolates() {
        return perc;
    }

    private int xyTo1D(final int x, final int y) {
        // return ((x - 1) * n + y) - 1;
        return (gLength * (x - 1) + y) - 1;
    }

    private void checkXY(final int x, final int y) {
        if (x <= 0 || gLength < x) {
            throw new IndexOutOfBoundsException("x not between 1 & " + gLength);
        }
        if (y <= 0 || gLength < y) {
            throw new IndexOutOfBoundsException("y not between 1 & " + gLength);
        }
    }

}
