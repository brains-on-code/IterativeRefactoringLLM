package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable 2D point.
 */
class Point {
    public final double x;
    public final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * Axis-aligned square bounding box defined by its center and half-width.
 */
class BoundingBox {
    public final Point center;
    public final double halfWidth;

    BoundingBox(Point center, double halfWidth) {
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

    /**
     * Returns {@code true} if the given point lies inside or on the boundary of this box.
     *
     * @param point the point to test
     */
    public boolean containsPoint(Point point) {
        return point.x >= minX()
            && point.x <= maxX()
            && point.y >= minY()
            && point.y <= maxY();
    }

    /**
     * Returns {@code true} if this box intersects the given box.
     *
     * @param other the other bounding box
     */
    public boolean intersectsBoundingBox(BoundingBox other) {
        return other.minX() <= this.maxX()
            && other.maxX() >= this.minX()
            && other.minY() <= this.maxY()
            && other.maxY() >= this.minY();
    }
}

/**
 * QuadTree for storing 2D points in a square region.
 */
public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private final List<Point> points;
    private boolean divided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    /**
     * Creates a QuadTree with the given bounding box and node capacity.
     *
     * @param boundary region covered by this node
     * @param capacity maximum number of points before subdivision
     */
    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.divided = false;
    }

    /**
     * Inserts a point into the tree if it lies within the boundary.
     *
     * @param point point to insert
     * @return {@code true} if the point was inserted; {@code false} otherwise
     */
    public boolean insert(Point point) {
        if (point == null || !boundary.containsPoint(point)) {
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

    /**
     * Subdivides this node into four child quadrants.
     */
    private void subdivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        BoundingBox nwBoundary = new BoundingBox(
            new Point(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth),
            quadrantHalfWidth
        );
        BoundingBox neBoundary = new BoundingBox(
            new Point(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth),
            quadrantHalfWidth
        );
        BoundingBox swBoundary = new BoundingBox(
            new Point(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth),
            quadrantHalfWidth
        );
        BoundingBox seBoundary = new BoundingBox(
            new Point(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth),
            quadrantHalfWidth
        );

        northWest = new QuadTree(nwBoundary, capacity);
        northEast = new QuadTree(neBoundary, capacity);
        southWest = new QuadTree(swBoundary, capacity);
        southEast = new QuadTree(seBoundary, capacity);

        divided = true;
    }

    /**
     * Returns all points within the given range.
     *
     * @param range query bounding box
     */
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