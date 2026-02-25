package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Point is a simple class that represents a point in 2D space.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Point_(geometry)">Point</a>
 * author <a href="https://github.com/sailok">Sailok Chinta</a>
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
 * BoundingBox is a simple class that represents a bounding box in 2D space.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bounding_box">Bounding Box</a>
 * author <a href="https://github.com/sailok">Sailok Chinta</a>
 */
class BoundingBox {
    public Point center;
    public double halfWidth;

    BoundingBox(Point center, double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    /**
     * Checks if the point is inside the bounding box
     *
     * @param point The point to check
     * @return true if the point is inside the bounding box, false otherwise
     */
    public boolean contains(Point point) {
        return point.x >= center.x - halfWidth
            && point.x <= center.x + halfWidth
            && point.y >= center.y - halfWidth
            && point.y <= center.y + halfWidth;
    }

    /**
     * Checks if the bounding box intersects with the other bounding box
     *
     * @param other The other bounding box
     * @return true if the bounding box intersects with the other bounding box, false otherwise
     */
    public boolean intersects(BoundingBox other) {
        return other.center.x - other.halfWidth <= center.x + halfWidth
            && other.center.x + other.halfWidth >= center.x - halfWidth
            && other.center.y - other.halfWidth <= center.y + halfWidth
            && other.center.y + other.halfWidth >= center.y - halfWidth;
    }
}

/**
 * QuadTree is a tree data structure that is used to store spatial information
 * in an efficient way.
 *
 * This implementation is specific to Point QuadTrees
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadtree">Quad Tree</a>
 * author <a href="https://github.com/sailok">Sailok Chinta</a>
 */
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

    /**
     * Inserts a point into the tree
     *
     * @param point The point to insert
     * @return true if the point is successfully inserted, false otherwise
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

    /**
     * Create four children that fully divide this quad into four quads of equal area
     */
    private void subdivide() {
        double childHalfWidth = boundary.halfWidth / 2;

        northWest =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x - childHalfWidth,
                        boundary.center.y + childHalfWidth),
                    childHalfWidth),
                this.capacity);

        northEast =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x + childHalfWidth,
                        boundary.center.y + childHalfWidth),
                    childHalfWidth),
                this.capacity);

        southWest =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x - childHalfWidth,
                        boundary.center.y - childHalfWidth),
                    childHalfWidth),
                this.capacity);

        southEast =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.center.x + childHalfWidth,
                        boundary.center.y - childHalfWidth),
                    childHalfWidth),
                this.capacity);

        subdivided = true;
    }

    /**
     * Queries all the points that intersect with the other bounding box
     *
     * @param range The other bounding box
     * @return List of points that intersect with the other bounding box
     */
    public List<Point> query(BoundingBox range) {
        List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return foundPoints;
        }

        foundPoints.addAll(points.stream().filter(range::contains).toList());

        if (subdivided) {
            foundPoints.addAll(northWest.query(range));
            foundPoints.addAll(northEast.query(range));
            foundPoints.addAll(southWest.query(range));
            foundPoints.addAll(southEast.query(range));
        }

        return foundPoints;
    }
}