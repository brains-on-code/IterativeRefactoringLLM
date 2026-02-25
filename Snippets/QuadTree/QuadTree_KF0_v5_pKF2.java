package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable point in 2D space.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Point_(geometry)">Point</a>
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
 * Axis-aligned square bounding box in 2D space, defined by its center and half
 * the side length.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bounding_box">Bounding Box</a>
 */
class BoundingBox {
    public final Point center;
    public final double halfWidth;

    BoundingBox(Point center, double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    /**
     * Returns {@code true} if the given point lies inside or on the edge of this
     * bounding box.
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
 * Point QuadTree for efficient storage and querying of 2D points.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadtree">Quad Tree</a>
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
     * Inserts a point into this quadtree.
     *
     * @param point the point to insert
     * @return {@code true} if the point was inserted; {@code false} otherwise
     */
    public boolean insert(Point point) {
        if (point == null) {
            return false;
        }

        if (!boundary.containsPoint(point)) {
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
     * Subdivides this node into four child quadrants of equal area.
     */
    private void subdivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2.0;
        double centerX = boundary.center.x;
        double centerY = boundary.center.y;

        BoundingBox northWestBox =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox northEastBox =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY + quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox southWestBox =
            new BoundingBox(new Point(centerX - quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);
        BoundingBox southEastBox =
            new BoundingBox(new Point(centerX + quadrantHalfWidth, centerY - quadrantHalfWidth), quadrantHalfWidth);

        northWest = new QuadTree(northWestBox, capacity);
        northEast = new QuadTree(northEastBox, capacity);
        southWest = new QuadTree(southWestBox, capacity);
        southEast = new QuadTree(southEastBox, capacity);

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
        List<Point> result = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(range)) {
            return result;
        }

        for (Point point : points) {
            if (range.containsPoint(point)) {
                result.add(point);
            }
        }

        if (divided) {
            result.addAll(northWest.query(range));
            result.addAll(northEast.query(range));
            result.addAll(southWest.query(range));
            result.addAll(southEast.query(range));
        }

        return result;
    }
}