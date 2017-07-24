/**
 * Created by jmeek on 24/07/2017.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Iterator;

public class PointSET {

    private SET<Point2D> set = new SET<Point2D>();


    public PointSET()  {} // construct an empty set of points

    public boolean isEmpty() { // is the set empty?
        return set.isEmpty();
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (!set.contains(p)) set.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        return set.contains(p);
    }

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
    /*
    public static void main(String[] args) { // unit testing of the methods (optional)

    }
    */
}
