/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] totalSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicate(points);

        Point[] clonedArray = points.clone();
        List<LineSegment> lineSegments = new ArrayList<>();

        Arrays.sort(clonedArray);

        final int N = points.length;

        for (int i = 0; i < N; i++) {
            Point p = clonedArray[i];
            Point[] slopeSorted = clonedArray.clone();
            Arrays.sort(slopeSorted, p.slopeOrder());

            int j = 1;
            while (j < N) {
                LinkedList<Point> couldCollinear = new LinkedList<>();
                final Double matchSlope = p.slopeTo(slopeSorted[j]);
                do {
                    couldCollinear.add(slopeSorted[j]);
                    j++;
                }
                while (j < N && p.slopeTo(slopeSorted[j]) == matchSlope);

                if (couldCollinear.size() >= 3 && p.compareTo(couldCollinear.peek()) < 0) {
                    Point min = p;
                    Point max = couldCollinear.removeLast();
                    lineSegments.add(new LineSegment(min, max)); // here - add first to last
                }
            }
        }
        totalSegments = lineSegments.toArray(new LineSegment[0]); // here
    }

    public int numberOfSegments() {
        return totalSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return totalSegments.clone();
    }

    private void checkNull(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Null array");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Null element found");
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate found");
        }
    }
}
