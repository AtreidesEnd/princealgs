/**
 * Created by Jared Meek on 18/05/2017.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
        // perform trials independent experiments on an n-by-n grid
        private double mean;
        private double stddev;
        private double confLo;
        private double confHi;

        public PercolationStats(int n, int trials) {
            if (n <= 0 || trials <= 0) {
                throw new IllegalArgumentException("N and Trials must be greater than zero");
            }
            double[] percOpen = new double[trials];
            Percolation perc;
            int r;
            int c;
            for (int i = 0; i<trials; i++) {
                perc = new Percolation(n);
                while (!perc.percolates()) {
                    r = (int) (Math.floor(Math.random()*n)+1);
                    c = (int) (Math.floor(Math.random()*n)+1);
                    perc.open(r,c);
                }
                percOpen[i] = (1.0*perc.numberOfOpenSites() / (n*n));
            }
            mean = StdStats.mean(percOpen);
            stddev = StdStats.stddev(percOpen);
            confLo = mean - (1.0/Math.sqrt(trials))*1.96*stddev;
            confHi = mean + (1.0/Math.sqrt(trials))*1.96*stddev;
        }
        public double mean() {return mean;}                      // sample mean of percolation threshold
        public double stddev() {return stddev;}                  // sample standard deviation of percolation threshold
        public double confidenceLo() {return confLo;}            // low endpoint of 95% confidence interval
        public double confidenceHi() {return confHi;}            // high endpoint of 95% confidence interval

        public static void main(String[] args){  // test client (described below)
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats percStats = new PercolationStats(n, trials);
            StdOut.println("mean                    = " + percStats.mean());
            StdOut.println("stddev                  = " + percStats.stddev());
            StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() +"]");
        }
}

