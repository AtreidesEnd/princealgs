import javax.sound.sampled.Line;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jared Meek on 23/05/2017.
 */
public class BruteCollinearPoints {
    private int nSeg = 0;
    private ArrayList<LineSegment> segs = new ArrayList<>();


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

        ArrayList<Point> tempSeg = new ArrayList<>();
        boolean unique = true;
        for (int i = 0; i < points.length-3; i++ ) {
            for (int j = i+1; j < points.length-2; j++ ) {
                for (int k = j+1; k < points.length-1; k++ ) {
                    for (int l = k+1; l < points.length; l++ ) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]) ) {
                            tempSeg.add(points[i]); tempSeg.add(points[j]);
                            tempSeg.add(points[k]); tempSeg.add(points[l]);
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
                            for (LineSegment seg : segs) {
                                if (seg.toString().equals(minPoint + " -> " + maxPoint)) unique = false;
                            }

                            if (unique) { // if unique, add to segments
                                segs.add(new LineSegment(minPoint,maxPoint));
                                nSeg++;
                            }
                        }
                        tempSeg.clear(); // if the 4 don't match, try again with the next 4!
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // number of line segments
        return nSeg;
    }

    public LineSegment[] segments() { // the actual line segments identified
        return segs.toArray(new LineSegment[0]);
    }
}
