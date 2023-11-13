/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        this.set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (!contains(p))
            set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        SET<Point2D> tempSet = new SET<Point2D>();
        for (Point2D p : this.set) {
            if (p.x() >= rect.xmin() && p.y() >= rect.ymin() && p.x() <= rect.xmax()
                    && p.y() <= rect.ymax())
                tempSet.add(p);
        }
        return tempSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;

        double tempVal = set.min().distanceSquaredTo(p);
        Point2D tempPoint = set.min();
        for (Point2D point : set) {
            if (point.distanceSquaredTo(p) < tempVal) {
                tempVal = point.distanceSquaredTo(p);
                tempPoint = point;
            }
        }
        return tempPoint;
    }

    public static void main(String[] args) {

    }
}
