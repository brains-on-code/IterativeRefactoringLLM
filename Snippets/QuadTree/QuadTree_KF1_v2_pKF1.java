package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;

class Point {
    public double xCoordinate;
    public double yCoordinate;

    Point(double xCoordinate, double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}

class BoundingBox {
    public Point centerPoint;
    public double halfDimension;

    BoundingBox(Point centerPoint, double halfDimension) {
        this.centerPoint = centerPoint;
        this.halfDimension = halfDimension;
    }

    public boolean contains(Point point) {
        return point.xCoordinate >= centerPoint.xCoordinate - halfDimension
            && point.xCoordinate <= centerPoint.xCoordinate + halfDimension
            && point.yCoordinate >= centerPoint.yCoordinate - halfDimension
            && point.yCoordinate <= centerPoint.yCoordinate + halfDimension;
    }

    public boolean intersects(BoundingBox otherBoundingBox) {
        return otherBoundingBox.centerPoint.xCoordinate - otherBoundingBox.halfDimension <= centerPoint.xCoordinate + halfDimension
            && otherBoundingBox.centerPoint.xCoordinate + otherBoundingBox.halfDimension >= centerPoint.xCoordinate - halfDimension
            && otherBoundingBox.centerPoint.yCoordinate - otherBoundingBox.halfDimension <= centerPoint.yCoordinate + halfDimension
            && otherBoundingBox.centerPoint.yCoordinate + otherBoundingBox.halfDimension >= centerPoint.yCoordinate - halfDimension;
    }
}

public class QuadTree {
    private final BoundingBox boundaryBox;
    private final int nodeCapacity;

    private List<Point> storedPoints;
    private boolean isSubdivided;
    private QuadTree northWestChild;
    private QuadTree northEastChild;
    private QuadTree southWestChild;
    private QuadTree southEastChild;

    public QuadTree(BoundingBox boundaryBox, int nodeCapacity) {
        this.boundaryBox = boundaryBox;
        this.nodeCapacity = nodeCapacity;

        this.storedPoints = new ArrayList<>();
        this.isSubdivided = false;
        this.northWestChild = null;
        this.northEastChild = null;
        this.southWestChild = null;
        this.southEastChild = null;
    }

    public boolean insert(Point pointToInsert) {
        if (pointToInsert == null) {
            return false;
        }

        if (!boundaryBox.contains(pointToInsert)) {
            return false;
        }

        if (storedPoints.size() < nodeCapacity) {
            storedPoints.add(pointToInsert);
            return true;
        }

        if (!isSubdivided) {
            subdivide();
        }

        if (northWestChild.insert(pointToInsert)) {
            return true;
        }

        if (northEastChild.insert(pointToInsert)) {
            return true;
        }

        if (southWestChild.insert(pointToInsert)) {
            return true;
        }

        if (southEastChild.insert(pointToInsert)) {
            return true;
        }

        return false;
    }

    private void subdivide() {
        double childHalfDimension = boundaryBox.halfDimension / 2;

        northWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundaryBox.centerPoint.xCoordinate - childHalfDimension,
                        boundaryBox.centerPoint.yCoordinate + childHalfDimension),
                    childHalfDimension),
                this.nodeCapacity);

        northEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundaryBox.centerPoint.xCoordinate + childHalfDimension,
                        boundaryBox.centerPoint.yCoordinate + childHalfDimension),
                    childHalfDimension),
                this.nodeCapacity);

        southWestChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundaryBox.centerPoint.xCoordinate - childHalfDimension,
                        boundaryBox.centerPoint.yCoordinate - childHalfDimension),
                    childHalfDimension),
                this.nodeCapacity);

        southEastChild =
            new QuadTree(
                new BoundingBox(
                    new Point(
                        boundaryBox.centerPoint.xCoordinate + childHalfDimension,
                        boundaryBox.centerPoint.yCoordinate - childHalfDimension),
                    childHalfDimension),
                this.nodeCapacity);

        isSubdivided = true;
    }

    public List<Point> query(BoundingBox queryRange) {
        List<Point> pointsInRange = new ArrayList<>();

        if (!boundaryBox.intersects(queryRange)) {
            return pointsInRange;
        }

        pointsInRange.addAll(storedPoints.stream().filter(queryRange::contains).toList());

        if (isSubdivided) {
            pointsInRange.addAll(northWestChild.query(queryRange));
            pointsInRange.addAll(northEastChild.query(queryRange));
            pointsInRange.addAll(southWestChild.query(queryRange));
            pointsInRange.addAll(southEastChild.query(queryRange));
        }

        return pointsInRange;
    }
}