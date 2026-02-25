package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParetoFrontier {

    private final List<Point> frontierPoints;

    public ParetoFrontier() {
        this.frontierPoints = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return frontierPoints;
    }

    public List<Point> mergeDominatedPoints(List<Point> points) {
        int numberOfPoints = points.size();
        if (numberOfPoints == 1) {
            return points;
        } else if (numberOfPoints == 2) {
            Point firstPoint = points.get(0);
            Point secondPoint = points.get(1);

            if (firstPoint.dominates(secondPoint)) {
                points.remove(1);
            } else if (secondPoint.dominates(firstPoint)) {
                points.remove(0);
            }
            return points;
        }

        List<Point> leftSubList = new ArrayList<>();
        List<Point> rightSubList = new ArrayList<>();
        int middleIndex = points.size() / 2;

        for (int index = 0; index < points.size(); index++) {
            if (index < middleIndex) {
                leftSubList.add(points.get(index));
            } else {
                rightSubList.add(points.get(index));
            }
        }

        List<Point> leftFrontier = mergeDominatedPoints(leftSubList);
        List<Point> rightFrontier = mergeDominatedPoints(rightSubList);

        return mergeFrontiers(leftFrontier, rightFrontier);
    }

    public List<Point> mergeFrontiers(List<Point> leftFrontier, List<Point> rightFrontier) {
        for (int index = 0; index < leftFrontier.size() - 1; index++) {
            Point currentPoint = leftFrontier.get(index);
            Point nextPoint = leftFrontier.get(index + 1);
            if (currentPoint.getX() == nextPoint.getX() && currentPoint.getY() > nextPoint.getY()) {
                leftFrontier.remove(index);
                index--;
            }
        }

        int minimumY = leftFrontier.get(0).getY();
        for (int index = 1; index < leftFrontier.size(); index++) {
            int currentY = leftFrontier.get(index).getY();
            if (minimumY > currentY) {
                minimumY = currentY;
                if (minimumY == 1) {
                    break;
                }
            }
        }

        for (int index = 0; index < rightFrontier.size(); index++) {
            if (rightFrontier.get(index).getY() >= minimumY) {
                rightFrontier.remove(index);
                index--;
            }
        }

        leftFrontier.addAll(rightFrontier);
        return leftFrontier;
    }

    public static class Point {

        private final int xCoordinate;
        private final int yCoordinate;

        public Point(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        public int getX() {
            return xCoordinate;
        }

        public int getY() {
            return yCoordinate;
        }

        public boolean dominates(Point otherPoint) {
            return (this.xCoordinate < otherPoint.xCoordinate && this.yCoordinate <= otherPoint.yCoordinate)
                    || (this.xCoordinate <= otherPoint.xCoordinate && this.yCoordinate < otherPoint.yCoordinate);
        }
    }

    class PointXComparator implements Comparator<Point> {

        @Override
        public int compare(Point firstPoint, Point secondPoint) {
            return Integer.compare(firstPoint.getX(), secondPoint.getX());
        }
    }
}