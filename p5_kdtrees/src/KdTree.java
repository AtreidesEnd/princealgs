import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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

        if (x.p.equals(p)) {
            size--;
            return x;
        }

        if ((level % 2 == 0 && p.x() < x.p.x()) || (level % 2 == 1 && p.y() < x.p.y())) {
            x.lb = insert(x.lb, x, p, level+1);
        } else {
            x.rt = insert(x.rt, x, p, level+1);
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
            return contains(x.lb, p, level+1);
        } else {
            return contains(x.rt, p, level+1);
        }
    }

    public void draw() { // draw all points to standard draw
        draw(root,0);
    }

    private void draw(Node x, int level) {
        int lvl = level % 2;

        StdDraw.setPenRadius(.001);
        if (lvl == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(),x.rect.ymin(),x.p.x(),x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(),x.p.y(),x.rect.xmax(),x.p.y());
        }

        StdDraw.setPenRadius(.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();

        if (x.lb != null) draw(x.lb, level+1);
        if (x.rt != null) draw(x.rt, level+1);

    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle
        Stack<Point2D> st = new Stack<Point2D>();
        range(root, rect, st);
        return st;
    }

    private void range(Node x, RectHV r, Stack<Point2D> st) {
        if (x == null) return;
        if (r.contains(x.p)) st.push(x.p);
        if (x.lb != null && x.lb.rect.intersects(r)) range(x.lb, r, st);
        if (x.rt != null && x.rt.rect.intersects(r)) range(x.rt, r, st);
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (isEmpty()) return null;
        return nearest(p,null,root,0);
    }

    private Point2D nearest(Point2D p, Point2D champ, Node x, int level) {
        if (champ == null || x.p.distanceTo(p) < champ.distanceTo(p)) champ = x.p;

        if ((level % 2 == 0 && p.x() < x.p.x()) || (level % 2 == 1 && p.y() < x.p.y())) {
            if (x.lb != null) champ = nearest(p, champ, x.lb,level+1); // go left/down
            if (x.rt != null && (x.rt.rect.contains(p) || x.rt.rect.distanceTo(p) < champ.distanceTo(p))) {
                champ = nearest(p, champ, x.rt,level+1); // if still valid, go right also
            }
        } else {
            if (x.rt != null) champ = nearest(p, champ, x.rt,level+1); // go right/up
            if (x.lb != null && (x.lb.rect.contains(p) || x.lb.rect.distanceTo(p) < champ.distanceTo(p))) {
                champ = nearest(p, champ, x.lb,level+1); // if still valid, go left also
            }
        }

        return champ;
    }


    public static void main(String[] args) { // unit testing of the methods (optional)
        KdTree tree = new KdTree();
        StdOut.println(tree.isEmpty());
        tree.insert(new Point2D(.7,.2));
        tree.insert(new Point2D(.5,.4));
        tree.insert(new Point2D(.2,.3));
        tree.insert(new Point2D(.4,.7));
        tree.insert(new Point2D(.9,.6));
        StdOut.println(tree.size());

        StdOut.println(tree.contains(new Point2D(.9,.6)));
        StdOut.println(tree.nearest(new Point2D(.95,.45)));

    }
}
