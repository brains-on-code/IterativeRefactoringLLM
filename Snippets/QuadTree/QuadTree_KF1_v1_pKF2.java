package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 2D point.
 */
class Point {
    public double x;
    public double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * Represents an axis-aligned square boundary in 2D space.
 * The boundary is defined by its center point and half-size (half the side length).
 */
class Boundary {
    public Point center;
    public double halfSize;

    Boundary(Point center, double halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    /**
     * Checks whether the given point lies within this boundary (inclusive).
     */
    public boolean contains(Point point) {
        return point.x >= center.x - halfSize
            && point.x <= center.x + halfSize
            && point.y >= center.y - halfSize
            && point.y <= center.y + halfSize;
    }

    /**
     * Checks whether this boundary intersects with another boundary.
     */
    public boolean intersects(Boundary other) {
        return other.center.x - other.halfSize <= center.x + halfSize
            && other.center.x + other.halfSize >= center.x - halfSize
            && other.center.y - other.halfSize <= center.y + halfSize
            && other.center.y + other.halfSize >= center.y - halfSize;
    }
}

/**
 * A simple point quadtree implementation for 2D spatial partitioning.
 */
public class QuadTree {
    /** Boundary of this quadtree node. */
    private final Boundary boundary;

    /** Maximum number of points this node can hold before subdividing. */
    private final int capacity;

    /** Points stored in this node (if not subdivided or not yet full). */
    private List<Point> points;

    /** Whether this node has been subdivided into four children. */
    private boolean subdivided;

    /** Child nodes: northwest, northeast, southwest, southeast. */
    private QuadTree northwest;
    private QuadTree northeast;
    private QuadTree southwest;
    private QuadTree southeast;

    public QuadTree(Boundary boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;

        this.points = new ArrayList<>();
        this.subdivided = false;
        this.northwest = null;
        this.northeast = null;
        this.southwest = null;
        this.southeast = null;
    }

    /**
     * Inserts a point into the quadtree.
     *
     * @param point the point to insert
     * @return true if the point was successfully inserted; false otherwise
     */
    public boolean insert(Point point) {
        if (point == null) {
            return false;
        }

        // Ignore points that do not belong in this node's boundary.
        if (!boundary.contains(point)) {
            return false;
        }

        // If there is still room in this node, add the point here.
        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        // Otherwise, subdivide if necessary and delegate to children.
        if (!subdivided) {
            subdivide();
        }

        if (northwest.insert(point)) {
            return true;
        }
        if (northeast.insert(point)) {
            return true;
        }
        if (southwest.insert(point)) {
            return true;
        }
        if (southeast.insert(point)) {
            return true;
        }

        return false;
    }

    /**
     * Subdivides this node into four child quadtrees.
     */
    private void subdivide() {
        double childHalfSize = boundary.halfSize / 2;

        northwest =
            new QuadTree(
                new Boundary(
                    new Point(boundary.center.x - childHalfSize, boundary.center.y + childHalfSize),
                    childHalfSize),
                this.capacity);

        northeast =
            new QuadTree(
                new Boundary(
                    new Point(boundary.center.x + childHalfSize, boundary.center.y + childHalfSize),
                    childHalfSize),
                this.capacity);

        southwest =
            new QuadTree(
                new Boundary(
                    new Point(boundary.center.x - childHalfSize, boundary.center.y - childHalfSize),
                    childHalfSize),
                this.capacity);

        southeast =
            new QuadTree(
                new Boundary(
                    new Point(boundary.center.x + childHalfSize, boundary.center.y - childHalfSize),
                    childHalfSize),
                this.capacity);

        subdivided = true;
    }

    /**
     * Queries all points within the given range.
     *
     * @param range the boundary to query
     * @return a list of points that lie within the given range
     */
    public List<Point> query(Boundary range) {
        List<Point> found = new ArrayList<>();

        // If the range does not intersect this node's boundary, return empty.
        if (!boundary.intersects(range)) {
            return found;
        }

        // Add all points in this node that are within the range.
        found.addAll(points.stream().filter(range::contains).toList());

        // If subdivided, query all children.
        if (subdivided) {
            found.addAll(northwest.query(range));
            found.addAll(northeast.query(range));
            found.addAll(southwest.query(range));
            found.addAll(southeast.query(range));
        }

        return found;
    }
}