import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Jared Meek on 21/05/2017.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        In in = new In(args[1]); // input file
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!in.isEmpty()) {
            rq.enqueue(in.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
