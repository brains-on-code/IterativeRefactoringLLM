package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

public class SkylineAlgorithm {

    private final ArrayList<Point> skylinePoints;

    public SkylineAlgorithm() {
        this.skylinePoints = new ArrayList<>();
    }

    public ArrayList<Point> getSkylinePoints() {
        return skylinePoints;
    }

    public ArrayList<Point> computeSkyline(ArrayList<Point> points) {
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

        int middleIndex = numberOfPoints / 2;
        ArrayList<Point> leftPartition = new ArrayList<>();
        ArrayList<Point> rightPartition = new ArrayList<>();

        for (int index = 0; index < numberOfPoints; index++) {
            if (index < middleIndex) {
                leftPartition.add(points.get(index));
            } else {
                rightPartition.add(points.get(index));
            }
        }

        ArrayList<Point> leftSkyline = computeSkyline(leftPartition);
        ArrayList<Point> rightSkyline = computeSkyline(rightPartition);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public ArrayList<Point> mergeSkylines(ArrayList<Point> leftSkyline, ArrayList<Point> rightSkyline) {
        for (int index = 0; index < leftSkyline.size() - 1; index++) {
            Point currentPoint = leftSkyline.get(index);
            Point nextPoint = leftSkyline.get(index + 1);

            if (currentPoint.getX() == nextPoint.getX() && currentPoint.getY() > nextPoint.getY()) {
                leftSkyline.remove(index);
                index--;
            }
        }

        int minimumY = leftSkyline.get(0).getY();
        for (int index = 1; index < leftSkyline.size(); index++) {
            int currentY = leftSkyline.get(index).getY();
            if (minimumY > currentY) {
                minimumY = currentY;
                if (minimumY == 1) {
                    break;
                }
            }
        }

        for (int index = 0; index < rightSkyline.size(); index++) {
            if (rightSkyline.get(index).getY() >= minimumY) {
                rightSkyline.remove(index);
                index--;
            }
        }

        leftSkyline.addAll(rightSkyline);
        return leftSkyline;
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
            boolean strictlySmallerX = this.xCoordinate < otherPoint.xCoordinate;
            boolean strictlySmallerY = this.yCoordinate < otherPoint.yCoordinate;
            boolean smallerOrEqualX = this.xCoordinate <= otherPoint.xCoordinate;
            boolean smallerOrEqualY = this.yCoordinate <= otherPoint.yCoordinate;

            return (strictlySmallerX && smallerOrEqualY) || (smallerOrEqualX && strictlySmallerY);
        }
    }

    class XCoordinateComparator implements Comparator<Point> {

        @Override
        public int compare(Point firstPoint, Point secondPoint) {
            return Integer.compare(firstPoint.getX(), secondPoint.getX());
        }
    }
}