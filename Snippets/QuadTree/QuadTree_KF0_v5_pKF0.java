package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point in 2D space.
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
 * Represents an axis-aligned square bounding box in 2D space.
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
     * Checks if the point is inside the bounding box.
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
     * Checks if this bounding box intersects with another bounding box.
     *
     * @param other The other bounding box
     * @return true if the bounding boxes intersect, false otherwise
     */
    public boolean intersectsBoundingBox(BoundingBox other) {
        return other.minX() <= maxX()
            && other.maxX() >= minX()
            && other.minY() <= maxY()
            && other.maxY() >= minY();
    }
}

/**
 * QuadTree is a tree data structure used to store spatial information efficiently.
 *
 * This implementation is specific to Point QuadTrees.
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
     * Inserts a point into the tree.
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

        ensureSubdivided();
        return insertIntoChildren(point);
    }

    private boolean insertIntoChildren(Point point) {
        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    /**
     * Ensures that the current node is subdivided into four children.
     */
    private void ensureSubdivided() {
        if (!divided) {
            subdivide();
        }
    }

    /**
     * Creates four children that fully divide this quad into four quads of equal area.
     */
    private void subdivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        northWest = createChild(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth, quadrantHalfWidth);
        northEast = createChild(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth, quadrantHalfWidth);
        southWest = createChild(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth, quadrantHalfWidth);
        southEast = createChild(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth, quadrantHalfWidth);

        divided = true;
    }

    private QuadTree createChild(double centerX, double centerY, double halfWidth) {
        Point childCenter = new Point(centerX, centerY);
        BoundingBox childBoundary = new BoundingBox(childCenter, halfWidth);
        return new QuadTree(childBoundary, capacity);
    }

    /**
     * Queries all the points that intersect with the given bounding box.
     *
     * @param range The bounding box to query
     * @return List of points that intersect with the given bounding box
     */
    public List<Point> query(BoundingBox range) {
        List<Point> foundPoints = new ArrayList<>();
        query(range, foundPoints);
        return foundPoints;
    }

    /**
     * Helper method to accumulate query results into the provided list.
     *
     * @param range       The bounding box to query
     * @param foundPoints The list to accumulate found points into
     */
    private void query(BoundingBox range, List<Point> foundPoints) {
        if (!boundary.intersectsBoundingBox(range)) {
            return;
        }

        addPointsInRange(range, foundPoints);

        if (!divided) {
            return;
        }

        queryChildren(range, foundPoints);
    }

    private void addPointsInRange(BoundingBox range, List<Point> foundPoints) {
        for (Point point : points) {
            if (range.containsPoint(point)) {
                foundPoints.add(point);
            }
        }
    }

    private void queryChildren(BoundingBox range, List<Point> foundPoints) {
        northWest.query(range, foundPoints);
        northEast.query(range, foundPoints);
        southWest.query(range, foundPoints);
        southEast.query(range, foundPoints);
    }
}