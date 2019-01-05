import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {
    private static int top = 0;
    private final int size;
    private final int bottom;
    private final WeightedQuickUnionUF quickUnionUF;
    private final boolean[] opened;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("size must be greater than 0");

        size = n;
        bottom = size * size + 1;
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n * n + 2];
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException("invalid site");

        int site = ((row - 1) * size) + col;

        // Open the site
        opened[site] = true;

        // If site is on the top row, union the top hidden site
        if (row == 1) union(top, site);

        // If site is on the bottom row, union the bottom hidden site
        if (row == size) union(site, bottom);

        // Find adjacent sites
        int above = ((row - 2) * size) + col;
        int below = (row * size) + col;
        int left = ((row - 1) * size) + col - 1;
        int right = ((row - 1) * size) + col + 1;

        // Union to above if open
        if (row > 1 && opened[above]) union(site, above);

        // Union to below if open
        if (row != size && opened[below]) union(site, below);

        // Union to left if open
        if (col > 1 && opened[left]) union(site, left);

        // Union to right if open
        if (col != size && opened[right]) union(site, right);
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException("invalid site");

        return opened[(row * size) + col];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException("invalid site");

        return quickUnionUF.connected(0, (row * size) + col);
    }

    public int numberOfOpenSites() {
        int count = 0;

        for (int i = 0; i < opened.length; i++) {
            if (opened[i]) count++;
        }

        return count;
    }

    public boolean percolates() {
        return quickUnionUF.connected(top, bottom);
    }

    private void union(int site1, int site2) {
        quickUnionUF.union(site1, site2);
    }
}
