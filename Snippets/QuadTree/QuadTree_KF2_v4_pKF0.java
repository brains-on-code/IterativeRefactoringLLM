package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

class Point {
    public final double x;
    public final double y;

    Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
}

class BoundingBox {
    public final Point center;
    public final double halfWidth;

    BoundingBox(final Point center, final double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    private double minX() {
        return center.x - halfWidth;
    }

    private double maxX() {
        return center.x + halfWidth;
    }

    private double minY() {
        return center.y - halfWidth;
    }

    private double maxY() {
        return center.y + halfWidth;
    }

    public boolean containsPoint(final Point point) {
        if (point == null) {
            return false;
        }
        return point.x >= minX()
            && point.x <= maxX()
            && point.y >= minY()
            && point.y <= maxY();
    }

    public boolean intersectsBoundingBox(final BoundingBox other) {
        if (other == null) {
            return false;
        }
        return other.minX() <= maxX()
            && other.maxX() >= minX()
            && other.minY() <= maxY()
            && other.maxY() >= minY();
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

    public QuadTree(final BoundingBox boundary, final int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.divided = false;
    }

    public boolean insert(final Point point) {
        if (point == null || !boundary.containsPoint(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        ensureSubdivided();

        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    private void ensureSubdivided() {
        if (!divided) {
            subdivide();
        }
    }

    private void subdivide() {
        final double quadrantHalfWidth = boundary.halfWidth / 2;
        final double centerX = boundary.center.x;
        final double centerY = boundary.center.y;

        final BoundingBox nwBoundary = createQuadrant(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth, quadrantHalfWidth);
        final BoundingBox neBoundary = createQuadrant(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth, quadrantHalfWidth);
        final BoundingBox swBoundary = createQuadrant(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth, quadrantHalfWidth);
        final BoundingBox seBoundary = createQuadrant(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth, quadrantHalfWidth);

        northWest = new QuadTree(nwBoundary, capacity);
        northEast = new QuadTree(neBoundary, capacity);
        southWest = new QuadTree(swBoundary, capacity);
        southEast = new QuadTree(seBoundary, capacity);

        divided = true;
    }

    private BoundingBox createQuadrant(final double centerX, final double centerY, final double halfWidth) {
        return new BoundingBox(new Point(centerX, centerY), halfWidth);
    }

    public List<Point> query(final BoundingBox range) {
        final List<Point> foundPoints = new ArrayList<>();
        if (range == null || !boundary.intersectsBoundingBox(range)) {
            return foundPoints;
        }

        for (final Point point : points) {
            if (range.containsPoint(point)) {
                foundPoints.add(point);
            }
        }

        if (divided) {
            queryChildren(range, foundPoints);
        }

        return foundPoints;
    }

    private void queryChildren(final BoundingBox range, final List<Point> foundPoints) {
        foundPoints.addAll(northWest.query(range));
        foundPoints.addAll(northEast.query(range));
        foundPoints.addAll(southWest.query(range));
        foundPoints.addAll(southEast.query(range));
    }
}