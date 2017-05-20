/**
 * Created by Jared Meek on 18/05/2017.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wqu1;
    private WeightedQuickUnionUF wqu2;
    private boolean[][] openMap;
    private int initSize;
    private int countOpen;
    private int topSite;
    private int botSite;

    public Percolation(int n) {// create n-by-n grid, with all sites blocked
        // we'll use 0 to represent the top virtual site, and n^2+1 to represent the bottom virtual site
        if (n < 1) {throw new IllegalArgumentException("n must be greater than 0");}
        wqu1 = new WeightedQuickUnionUF((n*n)+2); // UF to keep track of links with top and bottom virtual sites
        wqu2 = new WeightedQuickUnionUF((n*n)+1); // UF to keep track of links with only top virtual sites
        openMap = new boolean[n+1][n+1];
        initSize = n;
        topSite = 0;
        botSite = n*n+1;
        countOpen = 0;

        // connect top and bottom virtual sites
        for (int i = 1; i <= n; i++) {
            wqu1.union(xyToID(1,i),topSite);
            wqu1.union(xyToID(n,i),botSite);
            wqu2.union(xyToID(1,i),topSite);
        }
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        if (isOpen(row,col)) {return;}
        else {
            openMap[row][col] = true;
            countOpen++;
            int newSite = xyToID(row,col);
            // need to check: [row-1, col], [row+1,col], [row,col-1], [row,col+1]
            if (row > 1 && isOpen(row-1, col)) {
                wqu1.union(newSite,xyToID(row-1,col));
                wqu2.union(newSite,xyToID(row-1,col));
            }
            if (row < this.initSize && isOpen(row+1,col)) {
                wqu1.union(newSite,xyToID(row+1,col));
                wqu2.union(newSite,xyToID(row+1,col));
            }
            if (col > 1 && isOpen(row, col-1)) {
                wqu1.union(newSite,xyToID(row,col-1));
                wqu2.union(newSite,xyToID(row,col-1));
            }
            if (col < this.initSize && isOpen(row, col+1)) {
                wqu1.union(newSite,xyToID(row,col+1));
                wqu2.union(newSite,xyToID(row,col+1));
            }
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        checkException(row,col);
        return openMap[row][col];
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        checkException(row,col);
        return (wqu2.connected(xyToID(row,col),topSite) && openMap[row][col]);
    }

    private void checkException(int row, int col) {
        if (row <= 0 || row > this.initSize || col <= 0 || col > this.initSize) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
    }

    private int xyToID(int row, int col) {
        checkException(row,col);
        return (this.initSize*(row-1) + col);
    }

    public int numberOfOpenSites() { // number of open sites
        return countOpen;
    }
    public boolean percolates() { // does the system percolate?
        return (countOpen > 0 && wqu1.connected(topSite,botSite));
    }
}