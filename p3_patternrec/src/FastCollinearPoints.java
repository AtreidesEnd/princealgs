import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jared Meek on 23/05/2017.
 */
public class FastCollinearPoints {
    private int nSeg = 0;
    private ArrayList<PotentialSegment> segs = new ArrayList<>();

    private class PotentialSegment {
        private Point p;
        private Point q;

        public PotentialSegment(Point p, Point q) {
            if (p == null || q == null) {
                throw new NullPointerException("argument is null");
            }
            this.p = p;
            this.q = q;
        }

        public Point getP1() {
            return p;
        }

        public Point getP2() {
            return q;
        }
    }


    public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
        // checking for null conditions
        // there's probably a way to do this during the below loop, but for now let's just get it over with
        if (points == null ) throw new NullPointerException();
        for (int a = 0; a < points.length; a++ ) {
            if (points[a] == null) throw new NullPointerException();
        }

        Point[] sortedPoints = points.clone();
        ArrayList<Point> tempSeg = new ArrayList<>();

        for (int i = 0; i<points.length; i++) {
            Arrays.sort(sortedPoints, points[i].slopeOrder());
            // at the end of this, points[i] will always be at array 0, and i can proceed from there
            int start = 1;
            int end = 2;
            boolean unique = true;

            while (end < points.length-1) {
                tempSeg.add(points[i]);
                tempSeg.add(sortedPoints[start]);
                while ((points[i].slopeTo(sortedPoints[start]) == points[i].slopeTo(sortedPoints[end]))) {
                    tempSeg.add(sortedPoints[end++]);
                    if (end >= points.length) break; // if we increment past the array, we're done
                }
                if (tempSeg.size() >= 4) { // consider this a segment if it's 4 or bigger
                    // now we need to establish the 'maximal' version of this line
                    Point minPoint = tempSeg.get(0);
                    Point maxPoint = tempSeg.get(0);

                    for (Point p : tempSeg) { // establish min and max point
                        if (p.compareTo(minPoint) < 0) {
                            minPoint = p;
                        } else if (p.compareTo(maxPoint) > 0) {
                            maxPoint = p;
                        }
                    }
                    // now we have a segment from [minPoint to maxPoint], see if it's unique
                    for (PotentialSegment seg : segs) {
                        if (seg.getP1()==minPoint && seg.getP2()==maxPoint) unique = false;
                    }

                    if (unique) { // if unique, add to segments
                        segs.add(new PotentialSegment(minPoint,maxPoint));
                        nSeg++;
                    }
                } // if tempSeg isn't bigger than 4, then we start from the next point
                tempSeg.clear();
                start = end;
               end = start+1;
            } // after the while loop, we start over for next (i)
        }
    }

    public int numberOfSegments() { // number of line segments
        return nSeg;
    }

    public LineSegment[] segments() { // the actual line segments identified
        LineSegment[] tempLS = new LineSegment[nSeg];
        for (int i = 0; i < nSeg; i++) {
            tempLS[i] = new LineSegment(segs.get(i).getP1(),segs.get(i).getP2());
        }
        return tempLS;
    }
}