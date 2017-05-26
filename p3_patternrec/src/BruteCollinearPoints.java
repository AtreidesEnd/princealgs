import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jared Meek on 23/05/2017.
 */
public class BruteCollinearPoints {
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

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
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

        Point[] pointsArr = points.clone(); // this makes this data type immutable
        Arrays.sort(pointsArr);

        ArrayList<Point> tempSeg = new ArrayList<>();
        boolean unique = true;
        for (int i = 0; i < pointsArr.length-3; i++ ) {
            for (int j = i+1; j < pointsArr.length-2; j++ ) {
                for (int k = j+1; k < pointsArr.length-1; k++ ) {
                    for (int l = k+1; l < pointsArr.length; l++ ) {
                        if (pointsArr[i].slopeTo(pointsArr[j]) == pointsArr[i].slopeTo(pointsArr[k]) &&
                                pointsArr[i].slopeTo(pointsArr[j]) == pointsArr[i].slopeTo(pointsArr[l]) ) {
                            tempSeg.add(pointsArr[i]); tempSeg.add(pointsArr[j]);
                            tempSeg.add(pointsArr[k]); tempSeg.add(pointsArr[l]);
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
                        }
                        tempSeg.clear(); // if the 4 don't match, try again with the next 4!
                        unique = true;
                    }
                }
            }
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
