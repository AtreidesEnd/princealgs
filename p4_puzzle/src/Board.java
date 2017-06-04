import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by jmeek on 29/05/2017.
 */
public class Board {
    private int[][] blocks;
    private int[][] twin;
    private int n;
    private int empRow;
    private int empCol;
    private int hammingD;
    private int manhattanD;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new int[this.n][this.n];
        this.twin = new int[this.n][this.n];
        this.hammingD = 0;
        this.manhattanD = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                this.blocks[i][j] = blocks[i][j];
                this.twin[i][j] = blocks[i][j];
                if (this.blocks[i][j] == 0) {
                    empRow = i;
                    empCol = j;
                }
            }
        }
        // making the twin a 'twin'
        this.twin[0][0] = this.blocks[0][1];
        this.twin[0][1] = this.blocks[0][0];

        // calculate Hamming and Manhattan distances now and store them
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                int currentBlock = blocks[i][j];
                if (currentBlock != 0 && currentBlock != (i * n + j + 1)) {
                    this.hammingD++; // current block is out of place
                    int correctRow = Math.floorDiv(currentBlock - 1, this.n);
                    int correctCol = (currentBlock - 1) % this.n;
                    this.manhattanD += (Math.abs(correctRow - i) + Math.abs(correctCol - j));
                }
            }
        }
    }

    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        return hammingD;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        return manhattanD;
    }

    public boolean isGoal() { // is this board the goal board?
        return (hammingD == 0);
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        return new Board(this.twin);
    }

    public boolean equals(Object y) { // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board other = (Board) y;

        if (other.dimension() != this.dimension() ||
                other.manhattanD != this.manhattanD ||
                other.hammingD != this.hammingD) return false;

        return other.toString().equals(this.toString());
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        Stack<Board> neighbors = new Stack<Board>();
        for (int i = empRow - 1; i <= empRow + 1; i++) {
            for (int j = empCol - 1; j <= empCol + 1; j++) {
                if ((i == empRow ^ j == empCol) && (i < n && i >= 0) && (j < n && j >= 0)) { // check only top,left,right,bottom, only if in bounds
                    int toSwap = blocks[i][j];
                    blocks[i][j] = 0; // performm the swap
                    blocks[empRow][empCol] = toSwap;
                    neighbors.push(new Board(blocks));
                    blocks[i][j] = toSwap; // undo the swap
                    blocks[empRow][empCol] = 0;
                }
            }
        }
        return neighbors;
    }

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded) }
        int[][] test1 = new int[3][3];
        test1[0][0] = 1;
        test1[0][1] = 2;
        test1[0][2] = 3;
        test1[1][0] = 4;
        test1[1][1] = 5;
        test1[1][2] = 6;
        test1[2][0] = 7;
        test1[2][1] = 8;
        test1[2][2] = 0;

        Board testBoard = new Board(test1);

        StdOut.print(testBoard.toString());
        StdOut.println(testBoard.dimension());
        StdOut.println(testBoard.hamming());
        StdOut.println(testBoard.manhattan());
        StdOut.println(testBoard.isGoal());
        StdOut.println(testBoard.neighbors());
        StdOut.println(testBoard.twin());

    }

}