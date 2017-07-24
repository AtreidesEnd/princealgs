import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by jmeek on 24/07/2017.
 */
public class KdTree {

    private int size = 0;
    private Node root;

    private static class Node  {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {} // construct an empty set of points

    public boolean isEmpty() { // is the set empty?
        return (size == 0);
    }

    public int size() { // number of points in the set
        return size;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        root = insert(root, null, p, 0);
        size++;
    }

    private Node insert(Node x, Node parent, Point2D p, int level) {
        if (x == null) {
            if (parent == null) return new Node(p, new RectHV(0,0,1,1));
            if (level % 2 == 0) {
                if (p.y() < parent.p.y()) {
                    return new Node(p, new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.rect.xmax(),parent.p.y()));
                } else {
                    return new Node(p, new RectHV(parent.rect.xmin(),parent.p.y(),parent.rect.xmax(),parent.rect.ymax()));
                }
            } else {
                if (p.x() < parent.p.x()) {
                    return new Node(p, new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.p.x(),parent.rect.ymax()));
                } else {
                    return new Node(p, new RectHV(parent.p.x(),parent.rect.ymin(),parent.rect.xmax(),parent.rect.ymax()));
                }
            }
        }

        if (x.p.equals(p)) return x;

        if ((level % 2 == 0 && p.x() < x.p.x()) || (level % 2 == 1 && p.y() < x.p.y())) {
            x.lb = insert(x.lb, x, p, ++level);
        } else {
            x.rt = insert(x.rt, x, p, ++level);
        }
        return x;
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int level) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        if ((level % 2 == 0 && p.x() < x.p.x()) || (level % 2 == 1 && p.y() < x.p.y())) {
            return contains(x.lb, p, ++level);
        } else {
            return contains(x.rt, p, ++level);
        }
    }
/*
    public void draw() { // draw all points to standard draw
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
        SET<Point2D> newSet = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) newSet.add(p);
        }
        return newSet;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        Point2D champ = null;
        for (Point2D pt : set) {
            if (champ == null || p.distanceTo(pt) < p.distanceTo(champ)) {
                champ = pt;
            }
        }
        return champ;
    }
    */


    public static void main(String[] args) { // unit testing of the methods (optional)
        KdTree tree = new KdTree();
        StdOut.println(tree.isEmpty());
        tree.insert(new Point2D(.7,.2));
        tree.insert(new Point2D(.5,.4));
        tree.insert(new Point2D(.2,.3));
        tree.insert(new Point2D(.4,.7));
        tree.insert(new Point2D(.9,.6));
        StdOut.println(tree.size());

    }
}
