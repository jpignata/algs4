import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CRITICAL_VALUE = 1.96;

    private final int trials;
    private final double[] results;

    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("size and trials must be greater than 0");

        this.trials = trials;
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                percolation.open(row, col);
            }

            results[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.print("mean                    = " + stats.mean() + "\n");
        StdOut.print("stddev                  = " + stats.stddev() + "\n");
        StdOut.print("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]\n");
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - CRITICAL_VALUE * stddev / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + CRITICAL_VALUE * stddev / Math.sqrt(trials);
    }
}
