import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

class PercolationStats {
    private final int size;
    private final int trials;
    private final double[] results;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("size and trials must be greater than 0");

        this.trials = trials;
        size = n;
        results = new double[trials];
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        stats.run();

        StdOut.print("mean                    = " + stats.mean() + "\n");
        StdOut.print("stddev                  = " + stats.stddev() + "\n");
        StdOut.print("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]\n");
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    private void run() {
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(size);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(size) + 1;
                int col = StdRandom.uniform(size) + 1;

                percolation.open(row, col);
            }

            results[i] = (double) percolation.numberOfOpenSites() / (size * size);
        }
    }
}
