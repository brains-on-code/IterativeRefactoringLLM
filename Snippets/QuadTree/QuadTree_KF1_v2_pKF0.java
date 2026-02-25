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
    public final double halfSize;

    BoundingBox(final Point center, final double halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    public boolean contains(final Point point) {
        final double minX = center.x - halfSize;
        final double maxX = center.x + halfSize;
        final double minY = center.y - halfSize;
        final double maxY = center.y + halfSize;

        return point.x >= minX
            && point.x <= maxX
            && point.y >= minY
            && point.y <= maxY;
    }

    public boolean intersects(final BoundingBox other) {
        final double thisMinX = center.x - halfSize;
        final double thisMaxX = center.x + halfSize;
        final double thisMinY = center.y - halfSize;
        final double thisMaxY = center.y + halfSize;

        final double otherMinX = other.center.x - other.halfSize;
        final double otherMaxX = other.center.x + other.halfSize;
        final double otherMinY = other.center.y - other.halfSize;
        final double otherMaxY = other.center.y + other.halfSize;

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
    private boolean subdivided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(final BoundingBox boundary, final int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.subdivided = false;
    }

    public boolean insert(final Point point) {
        if (point == null || !boundary.contains(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        if (!subdivided) {
            subdivide();
        }

        return insertIntoChildren(point);
    }

    private boolean insertIntoChildren(final Point point) {
        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    private void subdivide() {
        final double newHalfSize = boundary.halfSize / 2;
        final double centerX = boundary.center.x;
        final double centerY = boundary.center.y;

        northWest = new QuadTree(
            new BoundingBox(
                new Point(centerX - newHalfSize, centerY + newHalfSize),
                newHalfSize
            ),
            capacity
        );

        northEast = new QuadTree(
            new BoundingBox(
                new Point(centerX + newHalfSize, centerY + newHalfSize),
                newHalfSize
            ),
            capacity
        );

        southWest = new QuadTree(
            new BoundingBox(
                new Point(centerX - newHalfSize, centerY - newHalfSize),
                newHalfSize
            ),
            capacity
        );

        southEast = new QuadTree(
            new BoundingBox(
                new Point(centerX + newHalfSize, centerY - newHalfSize),
                newHalfSize
            ),
            capacity
        );

        subdivided = true;
    }

    public List<Point> query(final BoundingBox range) {
        final List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return foundPoints;
        }

        for (Point point : points) {
            if (range.contains(point)) {
                foundPoints.add(point);
            }
        }

        if (subdivided) {
            foundPoints.addAll(northWest.query(range));
            foundPoints.addAll(northEast.query(range));
            foundPoints.addAll(southWest.query(range));
            foundPoints.addAll(southEast.query(range));
        }

        return foundPoints;
    }
}