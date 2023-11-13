import java.util.ArrayList;
import java.util.Arrays;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class BruteCollinearPoints {
    private LineSegment[] totalSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // Check null
        if (points == null) throw new IllegalArgumentException("Null array");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null element found");
            }
        }

        // Check Duplicate
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate found");
        }

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        Point[] clonedArray = points.clone();
        Arrays.sort(clonedArray);
        for (int p = 0; p < clonedArray.length - 3; p++) {
            for (int q = p + 1; q < clonedArray.length - 2; q++) {
                for (int r = q + 1; r < clonedArray.length - 1; r++) {
                    for (int s = r + 1; s < clonedArray.length; s++) {
                        if (clonedArray[p].slopeTo(clonedArray[q]) == clonedArray[p].slopeTo(
                                clonedArray[r]) &&
                                clonedArray[p].slopeTo(clonedArray[q]) == clonedArray[p].slopeTo(
                                        clonedArray[s])) {
                            lineSegments.add(new LineSegment(clonedArray[p],
                                                             clonedArray[s]));              // here
                        }
                    }
                }
            }
        }
        totalSegments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return totalSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return totalSegments.clone();
    }
}
