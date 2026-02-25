package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

class Point {
    public double x;
    public double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class BoundingBox {
    public Point center;
    public double halfSize;

    BoundingBox(Point center, double halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    public boolean contains(Point point) {
        return point.x >= center.x - halfSize
            && point.x <= center.x + halfSize
            && point.y >= center.y - halfSize
            && point.y <= center.y + halfSize;
    }

    public boolean intersects(BoundingBox other) {
        return other.center.x - other.halfSize <= center.x + halfSize
            && other.center.x + other.halfSize >= center.x - halfSize
            && other.center.y - other.halfSize <= center.y + halfSize
            && other.center.y + other.halfSize >= center.y - halfSize;
    }
}

public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private List<Point> points;
    private boolean subdivided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;

        this.points = new ArrayList<>();
        this.subdivided = false;
        this.northWest = null;
        this.northEast = null;
        this.southWest = null;
        this.southEast = null;
    }

    public boolean insert(Point point) {
        if (point == null) {
            return false;
        }

        if (!boundary.contains(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        if (!subdivided) {
            subdivide();
        }

        if (northWest.insert(point)) {
            return true;
        }

        if (northEast.insert(point)) {
            return true;
        }

        if (southWest.insert(point)) {
            return true;
        }

        if (southEast.insert(point)) {
            return true;
        }

        return false;
    }

    private void subdivide() {
        double childHalfSize = boundary.halfSize / 2;

        northWest =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x - childHalfSize,
                        boundary.center.y + childHalfSize),
                    childHalfSize),
                this.capacity);

        northEast =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x + childHalfSize,
                        boundary.center.y + childHalfSize),
                    childHalfSize),
                this.capacity);

        southWest =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x - childHalfSize,
                        boundary.center.y - childHalfSize),
                    childHalfSize),
                this.capacity);

        southEast =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x + childHalfSize,
                        boundary.center.y - childHalfSize),
                    childHalfSize),
                this.capacity);

        subdivided = true;
    }

    public List<Point> query(BoundingBox range) {
        List<Point> pointsInRange = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return pointsInRange;
        }

        pointsInRange.addAll(points.stream().filter(range::contains).toList());

        if (subdivided) {
            pointsInRange.addAll(northWest.query(range));
            pointsInRange.addAll(northEast.query(range));
            pointsInRange.addAll(southWest.query(range));
            pointsInRange.addAll(southEast.query(range));
        }

        return pointsInRange;
    }
}