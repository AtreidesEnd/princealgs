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

        for (int i = 0; i < points.length-3; i++ ) {
            for (int j = i+1; j < points.length-2; j++ ) {
                for (int k = j+1; k < points.length-1; k++ ) {
                    for (int l = k+1; l < points.length; l++ ) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]) ) {
                            nSeg++;
                            segs.add(new LineSegment(points[i],points[l]));
                        }
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
