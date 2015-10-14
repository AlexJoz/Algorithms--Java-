
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import static java.lang.Math.*;

public class PercolationStats {
    private double[] fraction;
    private int expTimes;
    private int gSize;
    private double jMean;
    private double jStdDev;
    private double jLo, jHi;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Incorrect input parameters");
        }
        expTimes = T;
        fraction = new double[expTimes];
        gSize = N * N;
        // make the experiment for T times
        for (int k = 0; k < expTimes; k++) {
            int c = 0;
            Percolation tPerc = new Percolation(N);
            // until it percolates open random sites
            while (!tPerc.percolates()) {
                int i = 1 + StdRandom.uniform(N);
                int j = 1 + StdRandom.uniform(N);
                if (!tPerc.isOpen(i, j)) {
                    tPerc.open(i, j);
                    c++;
                    fraction[k] = (double) c / gSize;
                }
            }
        }

        jMean = StdStats.mean(fraction);
        jStdDev = StdStats.stddev(fraction);
        jLo = jMean - 1.96 * jStdDev / sqrt(expTimes);
        jHi = jMean + 1.96 * jStdDev / sqrt(expTimes);

    }

    public double mean() {
        return jMean;
    }

    public double stddev() {
        return jStdDev;
    }

    public double confidenceLo() {
        return jLo;
    }

    public double confidenceHi() {
        return jHi;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats pStats = new PercolationStats(n, t);
        System.out.println("mean                    =" + pStats.mean());
        System.out.println("stddev                  =" + pStats.stddev());
        System.out.println("95% confidence interval =" + pStats.confidenceLo() + ", " + pStats.confidenceHi());

    }

}
