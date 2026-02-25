package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;


class Point {
    public double x;
    public double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
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
        return point.x >= center.x - halfWidth && point.x <= center.x + halfWidth && point.y >= center.y - halfWidth && point.y <= center.y + halfWidth;
    }


    public boolean intersectsBoundingBox(BoundingBox otherBoundingBox) {
        return otherBoundingBox.center.x - otherBoundingBox.halfWidth <= center.x + halfWidth && otherBoundingBox.center.x + otherBoundingBox.halfWidth >= center.x - halfWidth && otherBoundingBox.center.y - otherBoundingBox.halfWidth <= center.y + halfWidth
            && otherBoundingBox.center.y + otherBoundingBox.halfWidth >= center.y - halfWidth;
    }
}


public class QuadTree {
    private final BoundingBox boundary;
    private final int capacity;

    private List<Point> pointList;
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

        if (pointList.size() < capacity) {
            pointList.add(point);
            return true;
        }

        if (!divided) {
            subDivide();
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


    private void subDivide() {
        double quadrantHalfWidth = boundary.halfWidth / 2;

        northWest = new QuadTree(new BoundingBox(new Point(boundary.center.x - quadrantHalfWidth, boundary.center.y + quadrantHalfWidth), quadrantHalfWidth), this.capacity);
        northEast = new QuadTree(new BoundingBox(new Point(boundary.center.x + quadrantHalfWidth, boundary.center.y + quadrantHalfWidth), quadrantHalfWidth), this.capacity);
        southWest = new QuadTree(new BoundingBox(new Point(boundary.center.x - quadrantHalfWidth, boundary.center.y - quadrantHalfWidth), quadrantHalfWidth), this.capacity);
        southEast = new QuadTree(new BoundingBox(new Point(boundary.center.x + quadrantHalfWidth, boundary.center.y - quadrantHalfWidth), quadrantHalfWidth), this.capacity);
        divided = true;
    }


    public List<Point> query(BoundingBox otherBoundingBox) {
        List<Point> points = new ArrayList<>();

        if (!boundary.intersectsBoundingBox(otherBoundingBox)) {
            return points;
        }

        points.addAll(pointList.stream().filter(otherBoundingBox::containsPoint).toList());

        if (divided) {
            points.addAll(northWest.query(otherBoundingBox));
            points.addAll(northEast.query(otherBoundingBox));
            points.addAll(southWest.query(otherBoundingBox));
            points.addAll(southEast.query(otherBoundingBox));
        }

        return points;
    }
}
