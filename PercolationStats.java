/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] stats;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n out of bounds");
        stats = new double[trials];
        int[] order = new int[n * n];
        for (int i = 0; i < n * n; i++) order[i] = i;

        // StdRandom.shuffle(order);

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            StdRandom.shuffle(order);
            int j = 0;
            while (!percolation.percolates()) {
                int row = order[j] / n + 1;
                int col = order[j] % n + 1;
                percolation.open(row, col);
                j++;
            }
            double size = n * n;
            stats[i] = j / size;
        }

    }   // perform trials independent experiments on an n-by-n grid

    public double mean() {
        mean = StdStats.mean(stats);
        return mean;
    }                         // sample mean of percolation threshold

    public double stddev() {
        stddev = StdStats.stddev(stats);
        return stddev;
    }                       // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean - 1.96 * stddev / (Math.sqrt(stats.length));
    }                 // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean + 1.96 * stddev / (Math.sqrt(stats.length));
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.valueOf(args[0]),
                                                                 Integer.valueOf(args[1]));
        System.out.printf("mean\t\t\t= %f\n", percolationStats.mean());
        System.out.printf("stddev\t\t\t= %f\n", percolationStats.stddev());
        System.out
                .printf("95%% confidence interval = [%f, %f]\n", percolationStats.confidenceLo(),
                        percolationStats.confidenceHi());


    }       // test client (described below)
}
