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
    public boolean contains(Point point) {
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
    public boolean intersects(BoundingBox otherBoundingBox) {
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
    private final int nodeCapacity;

    private List<Point> storedPoints;
    private boolean isSubdivided;
    private QuadTree northWestChild;
    private QuadTree northEastChild;
    private QuadTree southWestChild;
    private QuadTree southEastChild;

    public QuadTree(BoundingBox boundary, int nodeCapacity) {
        this.boundary = boundary;
        this.nodeCapacity = nodeCapacity;

        this.storedPoints = new ArrayList<>();
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

        if (!boundary.contains(point)) {
            return false;
        }

        if (storedPoints.size() < nodeCapacity) {
            storedPoints.add(point);
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
        double childHalfWidth = boundary.halfWidth / 2;

        northWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate - childHalfWidth,
                        boundary.centerPoint.yCoordinate + childHalfWidth),
                    childHalfWidth),
                this.nodeCapacity);

        northEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate + childHalfWidth,
                        boundary.centerPoint.yCoordinate + childHalfWidth),
                    childHalfWidth),
                this.nodeCapacity);

        southWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate - childHalfWidth,
                        boundary.centerPoint.yCoordinate - childHalfWidth),
                    childHalfWidth),
                this.nodeCapacity);

        southEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundary.centerPoint.xCoordinate + childHalfWidth,
                        boundary.centerPoint.yCoordinate - childHalfWidth),
                    childHalfWidth),
                this.nodeCapacity);

        isSubdivided = true;
    }

    /**
     * Queries all the points that intersect with the other bounding box
     *
     * @param queryRange The other bounding box
     * @return List of points that intersect with the other bounding box
     */
    public List<Point> query(BoundingBox queryRange) {
        List<Point> pointsWithinRange = new ArrayList<>();

        if (!boundary.intersects(queryRange)) {
            return pointsWithinRange;
        }

        pointsWithinRange.addAll(storedPoints.stream().filter(queryRange::contains).toList());

        if (isSubdivided) {
            pointsWithinRange.addAll(northWestChild.query(queryRange));
            pointsWithinRange.addAll(northEastChild.query(queryRange));
            pointsWithinRange.addAll(southWestChild.query(queryRange));
            pointsWithinRange.addAll(southEastChild.query(queryRange));
        }

        return pointsWithinRange;
    }
}