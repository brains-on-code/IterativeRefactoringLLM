package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point in 2D space.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Point_(geometry)">Point</a>
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
 * Represents an axis-aligned square bounding box in 2D space, defined by its
 * center and half the side length.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bounding_box">Bounding Box</a>
 */
class BoundingBox {
    public Point center;
    public double halfWidth;

    BoundingBox(Point center, double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    /**
     * Returns {@code true} if the given point lies inside (or on the edge of)
     * this bounding box.
     */
    public boolean containsPoint(Point point) {
        double minX = center.x - halfWidth;
        double maxX = center.x + halfWidth;
        double minY = center.y - halfWidth;
        double maxY = center.y + halfWidth;

        return point.x >= minX
            && point.x <= maxX
            && point.y >= minY
            && point.y <= maxY;
    }

    /**
     * Returns {@code true} if this bounding box intersects the given bounding box.
     */
    public boolean intersectsBoundingBox(BoundingBox other) {
        double thisMinX = center.x - halfWidth;
        double thisMaxX = center.x + halfWidth;
        double thisMinY = center.y - halfWidth;
        double thisMaxY = center.y + halfWidth;

        double otherMinX = other.center.x - other.halfWidth;
        double otherMaxX = other.center.x + other.halfWidth;
        double otherMinY = other.center.y - other.halfWidth;
        double otherMaxY = other.center.y + other.halfWidth;

        boolean xOverlap = otherMinX <= thisMaxX && otherMaxX >= thisMinX;
        boolean yOverlap = otherMinY <= thisMaxY && otherMaxY >= thisMinY;

        return xOverlap && yOverlap;
    }
}

/**
 * Point QuadTree implementation for efficient storage and querying of 2D points.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadtree">Quad Tree</a>
 */
public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private final List<Point> pointList;
    private boolean divided;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;

        this.pointList = new ArrayList<>();
        this.divided = false;
    }

    /**
     * Inserts a point into this quadtree.
     *
     * @param point the point to insert
     * @return {@code true} if the point was inserted, {@code false} otherwise
     */
    public boolean insert(Point point) {
        if (point == null) {
            return false;
        }

        // Ignore points that do not lie within this node's boundary.
        if (!boundary.containsPoint(point)) {
            return false;
        }

        // If there is still capacity, store the point in this node.
        if (pointList.size() < capacity) {
            pointList.add(point);
            return true;
        }

        // Otherwise, ensure this node is subdivided and delegate insertion.
        if (!divided) {
            subDivide();
        }

        return northWest.insert(point)
            || northEast.insert(point)
            || southWest.insert(point)
            || southEast.insert(point);
    }

    /**
     * Subdivides this node into four child quadrants of equal area.
     */
    private void subDivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        BoundingBox nwBox =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox neBox =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox swBox =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox seBox =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);

        northWest = new QuadTree(nwBox, capacity);
        northEast = new QuadTree(neBox, capacity);
        southWest = new QuadTree(swBox, capacity);
        southEast = new QuadTree(seBox, capacity);

        divided = true;
    }

    /**
     * Returns all points contained in this quadtree that lie within the given
     * bounding box.
     *
     * @param range the query bounding box
     * @return list of points within {@code range}
     */
    public List<Point> query(BoundingBox range) {
        List<Point> points = new ArrayList<>();

        // If the query range does not intersect this node's boundary, there can
        // be no points to return from this subtree.
        if (!boundary.intersectsBoundingBox(range)) {
            return points;
        }

        // Collect points stored at this node that lie within the query range.
        for (Point p : pointList) {
            if (range.containsPoint(p)) {
                points.add(p);
            }
        }

        // Recursively query child quadrants if this node is subdivided.
        if (divided) {
            points.addAll(northWest.query(range));
            points.addAll(northEast.query(range));
            points.addAll(southWest.query(range));
            points.addAll(southEast.query(range));
        }

        return points;
    }
}