import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        root = insert(root, p, new RectHV(0, 0, 1, 1), true);
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }
        if (node.point.equals(p)) {
            return node;
        }
        double cmp = isVertical ? p.x() - node.point.x() : p.y() - node.point.y();
        RectHV nextRect = null;
        if (isVertical) {
            nextRect = cmp < 0 ? new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax())
                               : new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        else {
            nextRect = cmp < 0 ? new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y())
                               : new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
        }
        if (cmp < 0) {
            node.left = insert(node.left, p, nextRect, !isVertical);
        }
        else {
            node.right = insert(node.right, p, nextRect, !isVertical);
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(p)) {
            return true;
        }
        double cmp = isVertical ? p.x() - node.point.x() : p.y() - node.point.y();
        if (cmp < 0) {
            return contains(node.left, p, !isVertical);
        }
        else {
            return contains(node.right, p, !isVertical);
        }
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        StdDraw.setPenRadius();
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.left, !isVertical);
        draw(node.right, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
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
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D closest, boolean isVertical) {
        if (node == null) {
            return closest;
        }
        double distToClosest = closest.distanceSquaredTo(p);
        if (node.rect.distanceSquaredTo(p) < distToClosest) {
            double distToPoint = node.point.distanceSquaredTo(p);
            if (distToPoint < distToClosest) {
                closest = node.point;
            }
            if ((isVertical && p.x() < node.point.x()) || (!isVertical && p.y() < node.point.y())) {
                closest = nearest(node.left, p, closest, !isVertical);
                closest = nearest(node.right, p, closest, !isVertical);
            }
            else {
                closest = nearest(node.right, p, closest, !isVertical);
                closest = nearest(node.left, p, closest, !isVertical);
            }
        }
        return closest;
    }
}
