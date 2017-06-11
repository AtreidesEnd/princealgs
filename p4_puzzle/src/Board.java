import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by jmeek on 29/05/2017.
 */
public class Board {
    private short[][] blocks;
    private short n;
    private short empRow;
    private short empCol;
    private int hammingD;
    private int manhattanD;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();

        this.n = (short) blocks.length;
        this.blocks = new short[this.n][this.n];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                this.blocks[i][j] = (short) blocks[i][j];
                if (this.blocks[i][j] == 0) {
                    empRow = (short) i;
                    empCol = (short) j;
                } else if (this.blocks[i][j] != (i * n + j + 1)) {
                    hammingD++; // current block is out of place
                    int correctRow = Math.floorDiv(this.blocks[i][j] - 1, this.n);
                    int correctCol = (this.blocks[i][j] - 1) % this.n;
                    manhattanD += (Math.abs(correctRow - i) + Math.abs(correctCol - j));
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

        int[][] twin = new int[n][n];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                twin[i][j] = blocks[i][j];
            }
        }

        if (blocks[0][0] == 0 || blocks[0][1] == 0) {
            twin[1][0] = blocks[1][1];
            twin[1][1] = blocks[1][0];
        } else {
            twin[0][0] = blocks[0][1];
            twin[0][1] = blocks[0][0];
        }

        return new Board(twin);
    }

    @Override
    public boolean equals(Object y) { // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board other = (Board) y;

        if (other.dimension() != this.dimension() ||
                other.manhattanD != this.manhattanD ||
                other.hammingD != this.hammingD) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        Stack<Board> neighbors = new Stack<Board>();
        for (int i = empRow - 1; i <= empRow + 1; i++) {
            for (int j = empCol - 1; j <= empCol + 1; j++) {
                // check only top,left,right,bottom, only if in bounds
                if ((i == empRow ^ j == empCol) && (i < n && i >= 0) && (j < n && j >= 0)) {
                    int[][] newBlocks = new int[n][n];
                    for (int k = 0; k < blocks.length; k++) {
                        for (int l = 0; l < blocks[0].length; l++) {
                            newBlocks[k][l] = blocks[k][l];
                        }
                    }
                    newBlocks[i][j] = 0; // perform the swap
                    newBlocks[empRow][empCol] = blocks[i][j];
                    neighbors.push(new Board(newBlocks));
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
        test1[0][1] = 4;
        test1[0][2] = 6;
        test1[1][0] = 2;
        test1[1][1] = 5;
        test1[1][2] = 3;
        test1[2][0] = 7;
        test1[2][1] = 0;
        test1[2][2] = 8;

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