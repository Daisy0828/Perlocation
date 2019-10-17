/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n out of bounds");
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
        // create n-by-n grid, with all sites blocked
    }

    public void open(int row, int col) {
        if (row <= 0 || row > grid.length)
            throw new IllegalArgumentException("row index i out of bounds");
        if (col <= 0 || col > grid.length)
            throw new IllegalArgumentException("col index i out of bounds");
        if (!isOpen(row, col)) {
            int[] needtounion = { 1, 1, 1, 1 };
            if (row == 1) needtounion[0] = 0;
            if (row == grid.length) needtounion[1] = 0;
            if (col == 1) needtounion[2] = 0;
            if (col == grid.length) needtounion[3] = 0;
            if (needtounion[0] == 1 && isOpen(row - 1, col)) weightedQuickUnionUF
                    .union((row - 1) * grid.length + col - 1, (row - 2) * grid.length + col - 1);
            if (needtounion[1] == 1 && isOpen(row + 1, col))
                weightedQuickUnionUF
                        .union((row - 1) * grid.length + col - 1, row * grid.length + col - 1);
            if (needtounion[2] == 1 && isOpen(row, col - 1)) weightedQuickUnionUF
                    .union((row - 1) * grid.length + col - 1, (row - 1) * grid.length + col - 2);
            if (needtounion[3] == 1 && isOpen(row, col + 1))
                weightedQuickUnionUF
                        .union((row - 1) * grid.length + col - 1, (row - 1) * grid.length + col);
            grid[row - 1][col - 1] = 1;
        }
    }   // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > grid.length)
            throw new IllegalArgumentException("row index i out of bounds");
        if (col <= 0 || col > grid.length)
            throw new IllegalArgumentException("col index i out of bounds");

        return grid[row - 1][col - 1] == 1;
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > grid.length)
            throw new IllegalArgumentException("row index i out of bounds");
        if (col <= 0 || col > grid.length)
            throw new IllegalArgumentException("col index i out of bounds");

        if (!isOpen(row, col)) return false;

        for (int i = 0; i < grid.length; i++) {
            if (weightedQuickUnionUF.connected((row - 1) * grid.length + col - 1, i)) return true;
        }
        return false;
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 1) result++;
            }
        }
        return result;
    }      // number of open sites

    public boolean percolates() {
        for (int i = 0; i < grid.length; i++) {
            if (isFull(grid.length, i + 1)) return true;
        }
        return false;
    }             // does the system percolate?

    public static void main(String[] args) {

    }  // test client (optional)
}
