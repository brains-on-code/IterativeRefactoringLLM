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

    BoundingBox(Point centerPoint, double halfWidth) {
        this.center = centerPoint;
        this.halfWidth = halfWidth;
    }

    public boolean containsPoint(Point point) {
        boolean withinXBounds = point.x >= center.x - halfWidth && point.x <= center.x + halfWidth;
        boolean withinYBounds = point.y >= center.y - halfWidth && point.y <= center.y + halfWidth;
        return withinXBounds && withinYBounds;
    }

    public boolean intersectsBoundingBox(BoundingBox otherBoundingBox) {
        boolean intersectsX =
                otherBoundingBox.center.x - otherBoundingBox.halfWidth <= center.x + halfWidth
                        && otherBoundingBox.center.x + otherBoundingBox.halfWidth >= center.x - halfWidth;

        boolean intersectsY =
                otherBoundingBox.center.y - otherBoundingBox.halfWidth <= center.y + halfWidth
                        && otherBoundingBox.center.y + otherBoundingBox.halfWidth >= center.y - halfWidth;

        return intersectsX && intersectsY;
    }
}

public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private List<Point> points;
    private boolean isSubdivided;
    private QuadTree northWestQuadrant;
    private QuadTree northEastQuadrant;
    private QuadTree southWestQuadrant;
    private QuadTree southEastQuadrant;

    public QuadTree(BoundingBox boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;

        this.points = new ArrayList<>();
        this.isSubdivided = false;
        this.northWestQuadrant = null;
        this.northEastQuadrant = null;
        this.southWestQuadrant = null;
        this.southEastQuadrant = null;
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

        if (!isSubdivided) {
            subdivide();
        }

        if (northWestQuadrant.insert(point)) {
            return true;
        }

        if (northEastQuadrant.insert(point)) {
            return true;
        }

        if (southWestQuadrant.insert(point)) {
            return true;
        }

        if (southEastQuadrant.insert(point)) {
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

        northWestQuadrant =
                new QuadTree(new BoundingBox(northWestCenter, childHalfWidth), this.capacity);
        northEastQuadrant =
                new QuadTree(new BoundingBox(northEastCenter, childHalfWidth), this.capacity);
        southWestQuadrant =
                new QuadTree(new BoundingBox(southWestCenter, childHalfWidth), this.capacity);
        southEastQuadrant =
                new QuadTree(new BoundingBox(southEastCenter, childHalfWidth), this.capacity);

        isSubdivided = true;
    }

    public List<Point> query(BoundingBox searchRegion) {
        List<Point> foundPoints = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(searchRegion)) {
            return foundPoints;
        }

        foundPoints.addAll(points.stream().filter(searchRegion::containsPoint).toList());

        if (isSubdivided) {
            foundPoints.addAll(northWestQuadrant.query(searchRegion));
            foundPoints.addAll(northEastQuadrant.query(searchRegion));
            foundPoints.addAll(southWestQuadrant.query(searchRegion));
            foundPoints.addAll(southEastQuadrant.query(searchRegion));
        }

        return foundPoints;
    }
}