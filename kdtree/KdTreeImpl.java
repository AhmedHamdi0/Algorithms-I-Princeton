/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private Node left, right;
        private int level;           // odd or even
        private RectHV rect;

        public Node(Point2D p, RectHV rect, int level) {
            this.point = p;
            this.rect = rect;
            this.left = null;
            this.right = null;
            this.level = level;
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (contains(p))
            return;
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, p.x(), 1), 1);
            size++;
            return;
        }

        Node temp = root;
        while (true) {
            if (isVertical(temp)) {                       // Compare x axis
                if (p.x() < temp.point.x()) {
                    if (temp.left == null) {
                        temp.left = new Node(p, new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                           temp.rect.xmax(), p.y()), 2);
                        size++;
                        return;
                    }
                    temp = temp.left;
                }
                else {
                    if (temp.right == null) {
                        temp.right = new Node(p, new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                            temp.rect.xmax(), p.y()), 2);
                        size++;
                        return;
                    }
                    temp = temp.right;
                }
            }
            else if (isHorizontal(temp)) {                        // Compare y axis
                if (p.y() < temp.point.y()) {
                    if (temp.left == null) {
                        temp.left = new Node(p, new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                           p.x(), temp.rect.ymax()), 1);
                        size++;
                        return;
                    }
                    temp = temp.left;
                }
                else {
                    if (temp.right == null) {
                        temp.right = new Node(p, new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                            p.x(), temp.rect.ymax()), 1);
                        size++;
                        return;
                    }
                    temp = temp.right;
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        Node temp = root;
        while (temp != null) {
            if (temp.point.equals(p))
                return true;
            if (isVertical(temp)) {
                temp = p.x() < temp.point.x() ? temp.left : temp.right;
            }
            else if (isHorizontal(temp)) {
                temp = p.y() < temp.point.y() ? temp.left : temp.right;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node == null) {
            return;
        }

        // draw the point
        StdDraw.point(node.point.x(), node.point.y());

        // draw the splitting line
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        // recursively draw the left and right subtrees
        draw(node.left, !vertical);
        draw(node.right, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            points.add(node.point);
        }

        if (node.left != null && rect.intersects(node.left.rect)) {
            range(node.left, rect, points);
        }

        if (node.right != null && rect.intersects(node.right.rect)) {
            range(node.right, rect, points);
        }
    }

    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D nearestPoint) {
        if (node == null) {
            return nearestPoint;
        }

        double nearestDistance = nearestPoint.distanceSquaredTo(queryPoint);
        double currentDistance = node.point.distanceSquaredTo(queryPoint);

        if (currentDistance < nearestDistance) {
            nearestPoint = node.point;
        }

        Node first = node.left;
        Node second = node.right;

        if (first != null && second != null) {
            double firstDistance = first.rect.distanceSquaredTo(queryPoint);
            double secondDistance = second.rect.distanceSquaredTo(queryPoint);

            if (firstDistance < secondDistance) {
                nearestPoint = nearest(first, queryPoint, nearestPoint);
                nearestPoint = nearest(second, queryPoint, nearestPoint);
            }
            else {
                nearestPoint = nearest(second, queryPoint, nearestPoint);
                nearestPoint = nearest(first, queryPoint, nearestPoint);
            }
        }
        else if (first != null) {
            nearestPoint = nearest(first, queryPoint, nearestPoint);
        }
        else if (second != null) {
            nearestPoint = nearest(second, queryPoint, nearestPoint);
        }

        return nearestPoint;
    }

    private boolean isVertical(Node node) {
        return node.level % 2 != 0;
    }

    private boolean isHorizontal(Node node) {
        return node.level % 2 == 0;
    }
}
