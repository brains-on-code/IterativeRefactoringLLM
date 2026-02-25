package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

class Point {
    public final double x;
    public final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class BoundingBox {
    public final Point center;
    public final double halfWidth;

    BoundingBox(Point center, double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    public boolean containsPoint(Point point) {
        double minX = center.x - halfWidth;
        double maxX = center.x + halfWidth;
        double minY = center.y - halfWidth;
        double maxY = center.y + halfWidth;

        return point.x >= minX
            && point.x <= maxX
            && point.y >= minY
            && point.y <= maxY;
    }

    public boolean intersectsBoundingBox(BoundingBox other) {
        double thisMinX = center.x - halfWidth;
        double thisMaxX = center.x + halfWidth;
        double thisMinY = center.y - halfWidth;
        double thisMaxY = center.y + halfWidth;

        double otherMinX = other.center.x - other.halfWidth;
        double otherMaxX = other.center.x + other.halfWidth;
        double otherMinY = other.center.y - other.halfWidth;
        double otherMaxY = other.center.y + other.halfWidth;

        return otherMinX <= thisMaxX
            && otherMaxX >= thisMinX
            && otherMinY <= thisMaxY
            && otherMaxY >= thisMinY;
    }
}

public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private final List<Point> points;
    private boolean divided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.divided = false;
    }

    public boolean insert(Point point) {
        if (point == null) {
            return false;
        }

        if (!boundary.containsPoint(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        if (!divided) {
            subdivide();
        }

        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    private void subdivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        BoundingBox nwBoundary =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox neBoundary =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox swBoundary =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox seBoundary =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);

        northWest = new QuadTree(nwBoundary, capacity);
        northEast = new QuadTree(neBoundary, capacity);
        southWest = new QuadTree(swBoundary, capacity);
        southEast = new QuadTree(seBoundary, capacity);

        divided = true;
    }

    public List<Point> query(BoundingBox range) {
        List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(range)) {
            return foundPoints;
        }

        for (Point point : points) {
            if (range.containsPoint(point)) {
                foundPoints.add(point);
            }
        }

        if (divided) {
            foundPoints.addAll(northWest.query(range));
            foundPoints.addAll(northEast.query(range));
            foundPoints.addAll(southWest.query(range));
            foundPoints.addAll(southEast.query(range));
        }

        return foundPoints;
    }
}