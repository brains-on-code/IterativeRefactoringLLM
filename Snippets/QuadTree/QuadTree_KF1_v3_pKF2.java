package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/** Immutable 2D point. */
class Point {
    public final double x;
    public final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * Axis-aligned square region in 2D space, defined by its center and half the side length.
 */
class Boundary {
    public final Point center;
    public final double halfSize;

    Boundary(Point center, double halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    /** Returns {@code true} if the given point lies within this boundary (inclusive). */
    public boolean contains(Point point) {
        double minX = center.x - halfSize;
        double maxX = center.x + halfSize;
        double minY = center.y - halfSize;
        double maxY = center.y + halfSize;

        return point.x >= minX
            && point.x <= maxX
            && point.y >= minY
            && point.y <= maxY;
    }

    /** Returns {@code true} if this boundary intersects the given boundary. */
    public boolean intersects(Boundary other) {
        double thisMinX = center.x - halfSize;
        double thisMaxX = center.x + halfSize;
        double thisMinY = center.y - halfSize;
        double thisMaxY = center.y + halfSize;

        double otherMinX = other.center.x - other.halfSize;
        double otherMaxX = other.center.x + other.halfSize;
        double otherMinY = other.center.y - other.halfSize;
        double otherMaxY = other.center.y + other.halfSize;

        return otherMinX <= thisMaxX
            && otherMaxX >= thisMinX
            && otherMinY <= thisMaxY
            && otherMaxY >= thisMinY;
    }
}

/** Point quadtree for 2D spatial partitioning. */
public class QuadTree {
    /** Spatial region covered by this node. */
    private final Boundary boundary;

    /** Maximum number of points this node can hold before subdividing. */
    private final int capacity;

    /** Points stored directly in this node. */
    private final List<Point> points;

    /** Child nodes (created lazily on subdivision). */
    private QuadTree northwest;
    private QuadTree northeast;
    private QuadTree southwest;
    private QuadTree southeast;

    /** Whether this node has been subdivided into children. */
    private boolean subdivided;

    public QuadTree(Boundary boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.subdivided = false;
    }

    /**
     * Inserts a point into the quadtree.
     *
     * @param point the point to insert
     * @return {@code true} if the point was inserted; {@code false} if the point is
     *         {@code null} or lies outside this node's boundary
     */
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

        return northwest.insert(point)
            || northeast.insert(point)
            || southwest.insert(point)
            || southeast.insert(point);
    }

    /** Subdivides this node into four child quadtrees, one for each quadrant. */
    private void subdivide() {
        double childHalfSize = boundary.halfSize / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        Boundary nwBoundary =
            new Boundary(new Point(centerX - childHalfSize, centerY + childHalfSize), childHalfSize);
        Boundary neBoundary =
            new Boundary(new Point(centerX + childHalfSize, centerY + childHalfSize), childHalfSize);
        Boundary swBoundary =
            new Boundary(new Point(centerX - childHalfSize, centerY - childHalfSize), childHalfSize);
        Boundary seBoundary =
            new Boundary(new Point(centerX + childHalfSize, centerY - childHalfSize), childHalfSize);

        northwest = new QuadTree(nwBoundary, capacity);
        northeast = new QuadTree(neBoundary, capacity);
        southwest = new QuadTree(swBoundary, capacity);
        southeast = new QuadTree(seBoundary, capacity);

        subdivided = true;
    }

    /**
     * Returns all points within the given query boundary.
     *
     * @param range the query boundary
     * @return list of points that lie within {@code range}
     */
    public List<Point> query(Boundary range) {
        List<Point> found = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return found;
        }

        for (Point point : points) {
            if (range.contains(point)) {
                found.add(point);
            }
        }

        if (subdivided) {
            found.addAll(northwest.query(range));
            found.addAll(northeast.query(range));
            found.addAll(southwest.query(range));
            found.addAll(southeast.query(range));
        }

        return found;
    }
}