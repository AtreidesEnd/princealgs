import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by Jared Meek on 21/05/2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {} // construct an empty deque

    public boolean isEmpty() { // is the deque empty?
        return size == 0;
    }

    public int size() { return size; } // return the number of items on the deque

    public void addFirst(Item item) { // add the item to the front
        if (item == null) { throw new NullPointerException(); }

        Node newFirst = new Node();

        if (isEmpty()) {
            first = newFirst;
            first.item = item;
            last = first;
        } else {
            Node oldFirst = first;
            first = newFirst;
            first.item = item;
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        size++;
    }

    public void addLast(Item item) { // add the item to the end
        if (item == null) { throw new NullPointerException(); }

        Node newLast = new Node();

        if (isEmpty()) {
            last = newLast;
            last.item = item;
            first = last;
        } else {
            Node oldLast = last;
            last = newLast;
            last.item = item;
            oldLast.next = last;
            last.prev = oldLast;
        }
        size++;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (isEmpty()) { throw new NoSuchElementException(); }
        Item item = first.item;
        first = first.next;
        first.prev = null;
        size--;
        return item;
    }

    public Item removeLast() { // remove and return the item from the end
        if (isEmpty()) { throw new NoSuchElementException(); }
        Item item = last.item;
        last = last.prev;
        size--;
        return item;
    }
    public Iterator<Item> iterator() { return new DequeIterator(); } // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; } // note, easier to think of this has "hasCurrent()"... as the next operation returns current.item, and then shifts pointer forward one
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) { // unit testing (optional)
        Deque<Integer> testDeque = new Deque<Integer>();

        testDeque.addLast(null);

        testDeque.addFirst(1);
        testDeque.addFirst(2);
        testDeque.addFirst(3);
        testDeque.addFirst(4);
        testDeque.addFirst(5);
        testDeque.addLast(15);

        for (int s : testDeque) {
            StdOut.println(s);
        }

        StdOut.println("Size: " + testDeque.size());
    }
}
