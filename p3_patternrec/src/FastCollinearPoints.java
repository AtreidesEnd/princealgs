import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jared Meek on 23/05/2017.
 */
public class FastCollinearPoints {
    private int nSeg = 0;
    private ArrayList<LineSegment> segs = new ArrayList<>();


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
                tempSeg.add(points[0]);
                tempSeg.add(sortedPoints[start]);
                while (points[0].slopeTo(sortedPoints[start]) == points[0].slopeTo(sortedPoints[end])) {
                    tempSeg.add(sortedPoints[end]);
                    end++;
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
                    for (LineSegment l : segs) {
                        if (l.toString().equals(minPoint + " -> " + maxPoint)) unique = false;
                    }

                    if (unique) { // if unique, add to segments
                        segs.add(new LineSegment(minPoint,maxPoint));
                        nSeg++;
                    }
                } // if tempSeg isn't bigger than 4, then we start from the next point
                tempSeg.clear();
                start = end+1;
                end = start+1;
            } // after the while loop, we start over for next (i)
        }
    }

    public int numberOfSegments() { // number of line segments
        return nSeg;
    }

    public LineSegment[] segments() { // the actual line segments identified
        return segs.toArray(new LineSegment[0]);
    }
}