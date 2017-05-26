import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jared Meek on 23/05/2017.
 */
public class FastCollinearPoints {
    private int nSeg = 0;
    private ArrayList<PotentialSegment> segs = new ArrayList<>();
    private LineSegment[] segments;

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


    public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more pointsArr
        // checking for null conditions
        // there's probably a way to do this during the below loop, but for now let's just get it over with
        if (points == null ) throw new NullPointerException();
        for (int a = 0; a < points.length; a++ ) {
            if (points[a] == null) throw new NullPointerException();
            for (int b = 1; b < points.length; b++ ) {
                if (a != b && points[a].compareTo(points[b]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Point[] pointsArr = points.clone();
        Point[] sortedPoints = pointsArr.clone();
        ArrayList<Point> tempSeg = new ArrayList<>();

        for (int i = 0; i<pointsArr.length; i++) {
            Arrays.sort(sortedPoints, pointsArr[i].slopeOrder());
            // at the end of this, pointsArr[i] will always be at array 0, and i can proceed from there
            int start = 1;
            int end = 2;
            boolean unique = true;

            while (end < pointsArr.length-1) {
                tempSeg.add(pointsArr[i]);
                tempSeg.add(sortedPoints[start]);
                while ((pointsArr[i].slopeTo(sortedPoints[start]) == pointsArr[i].slopeTo(sortedPoints[end]))) {
                    tempSeg.add(sortedPoints[end++]);
                    if (end >= pointsArr.length) break; // if we increment past the array, we're done
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
                        if (seg.getP1()==minPoint && seg.getP2()==maxPoint) {
                            unique = false;
                        }
                    }

                    if (unique) { // if unique, add to segments
                        segs.add(new PotentialSegment(minPoint,maxPoint));
                        nSeg++;
                    }
                } // if tempSeg isn't bigger than 4, then we start from the next point
                tempSeg.clear();
                unique = true;
                start = end;
                end = start+1;
            } // after the while loop, we start over for next (i)
        }
        segments = new LineSegment[nSeg];
        for (int i = 0; i < nSeg; i++) {
            segments[i] = new LineSegment(segs.get(i).getP1(),segs.get(i).getP2());
        }

    }

    public int numberOfSegments() { // number of line segments
        return nSeg;
    }

    public LineSegment[] segments() { // the actual line segments identified
        return segments;
    }
}