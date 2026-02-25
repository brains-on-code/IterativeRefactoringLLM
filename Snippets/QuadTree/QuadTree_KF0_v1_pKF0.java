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
    public final double x;
    public final double y;

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
     * Checks if the point is inside the bounding box
     *
     * @param point The point to check
     * @return true if the point is inside the bounding box, false otherwise
     */
    public boolean containsPoint(Point point) {
        double x = point.x;
        double y = point.y;
        return x >= minX() && x <= maxX() && y >= minY() && y <= maxY();
    }

    /**
     * Checks if the bounding box intersects with the other bounding box
     *
     * @param other The other bounding box
     * @return true if the bounding box intersects with the other bounding box, false otherwise
     */
    public boolean intersectsBoundingBox(BoundingBox other) {
        return other.minX() <= maxX()
            && other.maxX() >= minX()
            && other.minY() <= maxY()
            && other.maxY() >= minY();
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

    /**
     * Inserts a point into the tree
     *
     * @param point The point to insert
     * @return true if the point is successfully inserted, false otherwise
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
     * Create four children that fully divide this quad into four quads of equal area
     */
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

    /**
     * Queries all the points that intersect with the other bounding box
     *
     * @param range The other bounding box
     * @return List of points that intersect with the other bounding box
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