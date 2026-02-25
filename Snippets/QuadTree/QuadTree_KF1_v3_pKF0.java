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

    private double minX() {
        return center.x - halfSize;
    }

    private double maxX() {
        return center.x + halfSize;
    }

    private double minY() {
        return center.y - halfSize;
    }

    private double maxY() {
        return center.y + halfSize;
    }

    public boolean contains(final Point point) {
        return point.x >= minX()
            && point.x <= maxX()
            && point.y >= minY()
            && point.y <= maxY();
    }

    public boolean intersects(final BoundingBox other) {
        return other.minX() <= this.maxX()
            && other.maxX() >= this.minX()
            && other.minY() <= this.maxY()
            && other.maxY() >= this.minY();
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

        northWest = createChild(centerX - newHalfSize, centerY + newHalfSize, newHalfSize);
        northEast = createChild(centerX + newHalfSize, centerY + newHalfSize, newHalfSize);
        southWest = createChild(centerX - newHalfSize, centerY - newHalfSize, newHalfSize);
        southEast = createChild(centerX + newHalfSize, centerY - newHalfSize, newHalfSize);

        subdivided = true;
    }

    private QuadTree createChild(double centerX, double centerY, double halfSize) {
        BoundingBox childBoundary = new BoundingBox(new Point(centerX, centerY), halfSize);
        return new QuadTree(childBoundary, capacity);
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