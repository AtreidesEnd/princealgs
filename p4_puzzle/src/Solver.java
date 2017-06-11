import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Jared Meek on 04/06/2017.
 * dummysave
 */
public class Solver {          // find a solution to the initial board (using the A* algorithm)
    private boolean solvable;
    private SearchNode goalNode;
    //private int counter;

    private class SearchNode implements Comparable<SearchNode> {
        public Board board;
        public SearchNode previous;
        public int movesSoFar;

        public SearchNode(Board b, SearchNode p, int m) {
            this.board = b;
            this.previous = p;
            this.movesSoFar = m;
        }

        public int compareTo(SearchNode orig) {
            int comp = (board.manhattan() + this.movesSoFar) - (orig.board.manhattan() + orig.movesSoFar);
            // if the above is zero, need to compare just manhattans to tie break
            if (comp == 0) return board.manhattan() - orig.board.manhattan();
            return comp;
        }

    }

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        MinPQ<SearchNode> queue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();
        queue.insert(new SearchNode(initial,null,0));
        twinQueue.insert(new SearchNode(initial.twin(),null,0));
       // counter=0;

        SearchNode current;
        SearchNode twinCurrent;


        while (true) {
            // examine the next step
            current = queue.delMin();
            if (current.board.isGoal()) {
                solvable = true;
                goalNode = current;
                break;
            }

            twinCurrent = twinQueue.delMin();
            if (twinCurrent.board.isGoal()) {
                solvable = false;
                break;
            }

            // if not at goal, get neighbors and setup next round
            for (Board b : current.board.neighbors()) {
                // first check if the neighbor is the same as the board we came from, ignore those
                if (current.previous == null || !b.equals(current.previous.board)) {
                    queue.insert(new SearchNode(b, current, current.movesSoFar + 1));
                }
            }

            for (Board b : twinCurrent.board.neighbors()) {
                // first check if the neighbor is the same as the board we came from, ignore those
                if (twinCurrent.previous == null || !b.equals(twinCurrent.previous.board)) {
                    twinQueue.insert(new SearchNode(b, twinCurrent, twinCurrent.movesSoFar + 1));
                }
            }
            /*
            counter++;

            if (counter % 100000 == 0) {
                StdOut.println(counter);
                StdOut.println("Next board moves: " + queue.min().movesSoFar);
                StdOut.println("Next board ManhD: " + queue.min().board.manhattan());
                StdOut.println("Next board priority: " + (queue.min().board.manhattan()+queue.min().movesSoFar));
            }
            */

        }
    }

    public boolean isSolvable() {           // is the initial board solvable?
        return solvable;
    }

    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) {
            return goalNode.movesSoFar;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;

        Stack<Board> path = new Stack<>();
        SearchNode curr = goalNode;

        while (curr != null) {
            path.push(curr.board);
            curr = curr.previous;
        }
        return path;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)

    }
}
