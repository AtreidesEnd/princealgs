import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by Jared Meek on 21/05/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a; // array of items for the queue
    private int n;    // number of items in the queue

    public RandomizedQueue() { // construct an empty randomized queue
        a = (Item[]) new Object[2]; // ignore the stupid java error
        n = 0;
    }

    // resize the underlying array holding the elements, from course libs
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public boolean isEmpty() { // is the queue empty?
        return n == 0;
    }

    public int size() { // return the number of items on the queue
        return n;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) throw new NullPointerException();
        if (n == a.length) {
//            StdOut.println("Resize down called: n = " + n + ", a.length = " + a.length + " | Resizing to : " + 2*a.length);
            resize(2*a.length);
        }
        a[n++] = item;
    }

    public Item dequeue() { // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException();
        if (n == 1) {
            Item deq = a[--n];
            a[n] = null;
            return deq;
        } else {
            int iDequeue = StdRandom.uniform(n);
            Item deq = a[iDequeue];
            a[iDequeue] = a[--n];
            if (n <= a.length / 4) {
//                StdOut.println("Resize down called: n = " + n + ", a.length = " + a.length + " | Resizing to : " + Math.max(a.length / 2, 2));
                resize(Math.max(a.length / 2, 2));
            }
            return deq;
        }
    }

    public Item sample() { // return (but do not remove) a random item
        if (isEmpty()) throw new NoSuchElementException();
        if (n == 1) { return a[0]; }
        int rand = StdRandom.uniform(n);
        return a[rand];
    }

    public Iterator<Item> iterator() { return new RandDequeIter(); }        // return an independent iterator over items in random order

    private class RandDequeIter implements Iterator<Item> {

        private int[] shuff = new int[n];
        private int index = 0;

        private RandDequeIter() {
            for (int i=0; i<n; i++) {
                shuff[i] = i;
            }
            for (int i=0; i<n; i++) {
                int rand = StdRandom.uniform(i,n);
                int swap = shuff[rand];
                shuff[rand] = shuff[i];
                shuff[i] = swap;
            }
        }

        public boolean hasNext() { return index<n; } // note, easier to think of this has "hasCurrent()"... as the next operation returns current.item, and then shifts pointer forward one
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            return a[shuff[index++]];
        }
    }


    public static void main(String[] args) { // unit testing (optional)
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

        for (int i=0; i<20; i++) {
            test.enqueue(StdRandom.uniform(50));
        }
        StdOut.println("Size: " + test.size());
        while (!test.isEmpty()) {
            StdOut.println(test.dequeue());
            StdOut.println("Size: " + test.size());
        }
        StdOut.println(test.size());
//        test.enqueue(3);
//        test.dequeue();
//        test.dequeue();
//        test.dequeue();
//        test.enqueue(4);
//
//        test.enqueue(5);
//        test.enqueue(6);
//
//        for (int s : test) {
//            StdOut.println(s);
//            for (int y : test) {
//                StdOut.println(y);
//            }
//        }
    }
}