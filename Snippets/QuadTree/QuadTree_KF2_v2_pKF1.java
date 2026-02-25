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
        boolean isWithinXBounds = point.x >= center.x - halfWidth && point.x <= center.x + halfWidth;
        boolean isWithinYBounds = point.y >= center.y - halfWidth && point.y <= center.y + halfWidth;
        return isWithinXBounds && isWithinYBounds;
    }

    public boolean intersectsBoundingBox(BoundingBox otherBoundingBox) {
        boolean intersectsOnXAxis =
                otherBoundingBox.center.x - otherBoundingBox.halfWidth <= center.x + halfWidth
                        && otherBoundingBox.center.x + otherBoundingBox.halfWidth >= center.x - halfWidth;

        boolean intersectsOnYAxis =
                otherBoundingBox.center.y - otherBoundingBox.halfWidth <= center.y + halfWidth
                        && otherBoundingBox.center.y + otherBoundingBox.halfWidth >= center.y - halfWidth;

        return intersectsOnXAxis && intersectsOnYAxis;
    }
}

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

        northWestChild =
                new QuadTree(new BoundingBox(northWestCenter, childHalfWidth), this.capacity);
        northEastChild =
                new QuadTree(new BoundingBox(northEastCenter, childHalfWidth), this.capacity);
        southWestChild =
                new QuadTree(new BoundingBox(southWestCenter, childHalfWidth), this.capacity);
        southEastChild =
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
            foundPoints.addAll(northWestChild.query(searchRegion));
            foundPoints.addAll(northEastChild.query(searchRegion));
            foundPoints.addAll(southWestChild.query(searchRegion));
            foundPoints.addAll(southEastChild.query(searchRegion));
        }

        return foundPoints;
    }
}