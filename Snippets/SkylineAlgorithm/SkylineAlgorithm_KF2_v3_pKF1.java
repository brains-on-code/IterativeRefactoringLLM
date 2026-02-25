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
        int totalPoints = points.size();

        if (totalPoints == 1) {
            return points;
        } else if (totalPoints == 2) {
            Point firstPoint = points.get(0);
            Point secondPoint = points.get(1);

            if (firstPoint.dominates(secondPoint)) {
                points.remove(1);
            } else if (secondPoint.dominates(firstPoint)) {
                points.remove(0);
            }
            return points;
        }

        int midIndex = totalPoints / 2;
        ArrayList<Point> leftPoints = new ArrayList<>();
        ArrayList<Point> rightPoints = new ArrayList<>();

        for (int i = 0; i < totalPoints; i++) {
            if (i < midIndex) {
                leftPoints.add(points.get(i));
            } else {
                rightPoints.add(points.get(i));
            }
        }

        ArrayList<Point> leftSkyline = computeSkyline(leftPoints);
        ArrayList<Point> rightSkyline = computeSkyline(rightPoints);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public ArrayList<Point> mergeSkylines(ArrayList<Point> leftSkyline, ArrayList<Point> rightSkyline) {
        for (int i = 0; i < leftSkyline.size() - 1; i++) {
            Point currentPoint = leftSkyline.get(i);
            Point nextPoint = leftSkyline.get(i + 1);

            if (currentPoint.getX() == nextPoint.getX() && currentPoint.getY() > nextPoint.getY()) {
                leftSkyline.remove(i);
                i--;
            }
        }

        int minY = leftSkyline.get(0).getY();
        for (int i = 1; i < leftSkyline.size(); i++) {
            int currentY = leftSkyline.get(i).getY();
            if (minY > currentY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        for (int i = 0; i < rightSkyline.size(); i++) {
            if (rightSkyline.get(i).getY() >= minY) {
                rightSkyline.remove(i);
                i--;
            }
        }

        leftSkyline.addAll(rightSkyline);
        return leftSkyline;
    }

    public static class Point {

        private final int x;
        private final int y;

        public Point(int xCoordinate, int yCoordinate) {
            this.x = xCoordinate;
            this.y = yCoordinate;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean dominates(Point other) {
            boolean strictlySmallerX = this.x < other.x;
            boolean strictlySmallerY = this.y < other.y;
            boolean smallerOrEqualX = this.x <= other.x;
            boolean smallerOrEqualY = this.y <= other.y;

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