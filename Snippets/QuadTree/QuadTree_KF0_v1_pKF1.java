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
    public double xCoordinate;
    public double yCoordinate;

    Point(double xCoordinate, double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}

/**
 * BoundingBox is a simple class that represents a bounding box in 2D space.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bounding_box">Bounding Box</a>
 * author <a href="https://github.com/sailok">Sailok Chinta</a>
 */
class BoundingBox {
    public Point centerPoint;
    public double halfWidth;

    BoundingBox(Point centerPoint, double halfWidth) {
        this.centerPoint = centerPoint;
        this.halfWidth = halfWidth;
    }

    /**
     * Checks if the point is inside the bounding box
     *
     * @param point The point to check
     * @return true if the point is inside the bounding box, false otherwise
     */
    public boolean containsPoint(Point point) {
        return point.xCoordinate >= centerPoint.xCoordinate - halfWidth
            && point.xCoordinate <= centerPoint.xCoordinate + halfWidth
            && point.yCoordinate >= centerPoint.yCoordinate - halfWidth
            && point.yCoordinate <= centerPoint.yCoordinate + halfWidth;
    }

    /**
     * Checks if the bounding box intersects with the other bounding box
     *
     * @param otherBoundingBox The other bounding box
     * @return true if the bounding box intersects with the other bounding box, false otherwise
     */
    public boolean intersectsBoundingBox(BoundingBox otherBoundingBox) {
        return otherBoundingBox.centerPoint.xCoordinate - otherBoundingBox.halfWidth <= centerPoint.xCoordinate + halfWidth
            && otherBoundingBox.centerPoint.xCoordinate + otherBoundingBox.halfWidth >= centerPoint.xCoordinate - halfWidth
            && otherBoundingBox.centerPoint.yCoordinate - otherBoundingBox.halfWidth <= centerPoint.yCoordinate + halfWidth
            && otherBoundingBox.centerPoint.yCoordinate + otherBoundingBox.halfWidth >= centerPoint.yCoordinate - halfWidth;
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
    private boolean isSubdivided;
    private QuadTree northWestChild;
    private QuadTree northEastChild;
    private QuadTree southWestChild;
    private QuadTree southEastChild;

    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;

        this.points = new ArrayList<>();
        this.isSubdivided = false;
        this.northWestChild = null;
        this.northEastChild = null;
        this.southWestChild = null;
        this.southEastChild = null;
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

        if (!boundary.containsPoint(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        if (!isSubdivided) {
            subdivide();
        }

        if (northWestChild.insert(point)) {
            return true;
        }

        if (northEastChild.insert(point)) {
            return true;
        }

        if (southWestChild.insert(point)) {
            return true;
        }

        if (southEastChild.insert(point)) {
            return true;
        }

        return false;
    }

    /**
     * Create four children that fully divide this quad into four quads of equal area
     */
    private void subdivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2;

        northWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate - quadrantHalfWidth,
                        boundary.centerPoint.yCoordinate + quadrantHalfWidth),
                    quadrantHalfWidth),
                this.capacity);

        northEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate + quadrantHalfWidth,
                        boundary.centerPoint.yCoordinate + quadrantHalfWidth),
                    quadrantHalfWidth),
                this.capacity);

        southWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate - quadrantHalfWidth,
                        boundary.centerPoint.yCoordinate - quadrantHalfWidth),
                    quadrantHalfWidth),
                this.capacity);

        southEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate + quadrantHalfWidth,
                        boundary.centerPoint.yCoordinate - quadrantHalfWidth),
                    quadrantHalfWidth),
                this.capacity);

        isSubdivided = true;
    }

    /**
     * Queries all the points that intersect with the other bounding box
     *
     * @param queryBoundingBox The other bounding box
     * @return List of points that intersect with the other bounding box
     */
    public List<Point> query(BoundingBox queryBoundingBox) {
        List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(queryBoundingBox)) {
            return foundPoints;
        }

        foundPoints.addAll(points.stream().filter(queryBoundingBox::containsPoint).toList());

        if (isSubdivided) {
            foundPoints.addAll(northWestChild.query(queryBoundingBox));
            foundPoints.addAll(northEastChild.query(queryBoundingBox));
            foundPoints.addAll(southWestChild.query(queryBoundingBox));
            foundPoints.addAll(southEastChild.query(queryBoundingBox));
        }

        return foundPoints;
    }
}