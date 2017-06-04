/**
 * Created by jmeek on 29/05/2017.
 */
public class Board {
    private int[][] blocks;
    private int hammingD;
    private int manhattanD;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++ ) {
            for (int j = 0; j < blocks[0].length; j++ ) {
                this.blocks[i][j] = blocks[i][j];
            }
        }

        // calculate Hamming and Manhattan distances now and store them
        int count = 0;
        for (int i = 0; i < blocks.length; i++ ) {
            for (int j = 0; j < blocks[0].length; j++ ) {
                if (blocks[])
            }
        }

    }

    public int dimension() { // board dimension n
        return blocks.length;
    }

    public int hamming() { // number of blocks out of place

    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal

    }

    public boolean isGoal() { // is this board the goal board?

    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks

    }

    public boolean equals(Object y) { // does this board equal y?

    }

    public Iterable<Board> neighbors() { // all neighboring boards

    }

    public String toString() { // string representation of this board (in the output format specified below)

    }
}

    public static void main(String[] args) // unit tests (not graded) }