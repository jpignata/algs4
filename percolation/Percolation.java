import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;

    private final int bottom;
    private final boolean[] open;
    private final WeightedQuickUnionUF topToBottom;
    private final WeightedQuickUnionUF topToSites;
    private final int size;

    private int openCount;

    public Percolation(int n) {
        if (n < 1)
          throw new IllegalArgumentException("size must be greater than 0");

        int totalSites = n * n;

        bottom = totalSites + 1;
        open = new boolean[totalSites + 1];
        openCount = 0;
        topToBottom = new WeightedQuickUnionUF(totalSites + 2);
        topToSites = new WeightedQuickUnionUF(totalSites + 1);
        size = n;
    }

    public void open(int row, int col) {
        if (!valid(row, col))
          throw new IllegalArgumentException("invalid site");

        int site = site(row, col);

        if (!open[site]) {
          int above = site(row - 1, col);
          int below = site(row + 1, col);
          int left = site(row, col - 1);
          int right = site(row, col + 1);

          open[site] = true;
          openCount++;

          // If site is on the top row, union the top virtual site
          if (row == 1) union(TOP, site);

          // If site is on the bottom row, union the bottom virtual site
          if (row == size) topToBottom.union(site, bottom);

          // Union to above if open
          if (row > 1 && open[above]) union(site, above);

          // Union to below if open
          if (row != size && open[below]) union(site, below);

          // Union to left if open
          if (col > 1 && open[left]) union(site, left);

          // Union to right if open
          if (col != size && open[right]) union(site, right);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException("invalid site");

        return open[site(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException("invalid site");

        return topToSites.connected(TOP, site(row, col));
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return topToBottom.connected(TOP, bottom);
    }

    private int site(int row, int col) {
        return (row - 1) * size + col;
    }

    private void union(int site1, int site2) {
        topToBottom.union(site1, site2);
        topToSites.union(site1, site2);
    }

    private boolean valid(int row, int col) {
      return row > 0 && col > 0 && row <= size && col <= size;
    }
}
