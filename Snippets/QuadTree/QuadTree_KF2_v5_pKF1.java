package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

class Point {
    public double x;
    public double y;

    Point(double xCoordinate, double yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }
}

class BoundingBox {
    public Point center;
    public double halfWidth;

    BoundingBox(Point center, double halfWidth) {
        this.center = center;
        this.halfWidth = halfWidth;
    }

    public boolean containsPoint(Point point) {
        boolean isWithinXBounds = point.x >= center.x - halfWidth && point.x <= center.x + halfWidth;
        boolean isWithinYBounds = point.y >= center.y - halfWidth && point.y <= center.y + halfWidth;
        return isWithinXBounds && isWithinYBounds;
    }

    public boolean intersectsBoundingBox(BoundingBox other) {
        boolean intersectsOnXAxis =
                other.center.x - other.halfWidth <= center.x + halfWidth
                        && other.center.x + other.halfWidth >= center.x - halfWidth;

        boolean intersectsOnYAxis =
                other.center.y - other.halfWidth <= center.y + halfWidth
                        && other.center.y + other.halfWidth >= center.y - halfWidth;

        return intersectsOnXAxis && intersectsOnYAxis;
    }
}

public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private final List<Point> points;
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

    private void subdivide() {
        double childHalfWidth = boundary.halfWidth / 2;

        Point northWestCenter =
                new Point(boundary.center.x - childHalfWidth, boundary.center.y + childHalfWidth);
        Point northEastCenter =
                new Point(boundary.center.x + childHalfWidth, boundary.center.y + childHalfWidth);
        Point southWestCenter =
                new Point(boundary.center.x - childHalfWidth, boundary.center.y - childHalfWidth);
        Point southEastCenter =
                new Point(boundary.center.x + childHalfWidth, boundary.center.y - childHalfWidth);

        northWest = new QuadTree(new BoundingBox(northWestCenter, childHalfWidth), this.capacity);
        northEast = new QuadTree(new BoundingBox(northEastCenter, childHalfWidth), this.capacity);
        southWest = new QuadTree(new BoundingBox(southWestCenter, childHalfWidth), this.capacity);
        southEast = new QuadTree(new BoundingBox(southEastCenter, childHalfWidth), this.capacity);

        subdivided = true;
    }

    public List<Point> query(BoundingBox searchRegion) {
        List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(searchRegion)) {
            return foundPoints;
        }

        foundPoints.addAll(points.stream().filter(searchRegion::containsPoint).toList());

        if (subdivided) {
            foundPoints.addAll(northWest.query(searchRegion));
            foundPoints.addAll(northEast.query(searchRegion));
            foundPoints.addAll(southWest.query(searchRegion));
            foundPoints.addAll(southEast.query(searchRegion));
        }

        return foundPoints;
    }
}