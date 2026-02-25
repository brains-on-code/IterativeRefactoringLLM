package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SkylineAlgorithm {

    private final List<Point> points;

    public SkylineAlgorithm() {
        this.points = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> produceSubSkyLines(List<Point> points) {
        int size = points.size();
        if (size <= 1) {
            return points;
        }

        if (size == 2) {
            return handleTwoPointCase(points);
        }

        int midIndex = size / 2;
        List<Point> leftHalf = new ArrayList<>(points.subList(0, midIndex));
        List<Point> rightHalf = new ArrayList<>(points.subList(midIndex, size));

        List<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        List<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    private List<Point> handleTwoPointCase(List<Point> points) {
        Point firstPoint = points.get(0);
        Point secondPoint = points.get(1);

        if (firstPoint.dominates(secondPoint)) {
            points.remove(1);
        } else if (secondPoint.dominates(firstPoint)) {
            points.remove(0);
        }
        return points;
    }

    public List<Point> produceFinalSkyLine(List<Point> leftSkyline, List<Point> rightSkyline) {
        removeRedundantLeftPoints(leftSkyline);
        int minimumY = findMinimumY(leftSkyline);
        filterRightPointsByMinY(rightSkyline, minimumY);

        leftSkyline.addAll(rightSkyline);
        return leftSkyline;
    }

    private void removeRedundantLeftPoints(List<Point> leftSkyline) {
        if (leftSkyline.size() < 2) {
            return;
        }

        Iterator<Point> iterator = leftSkyline.iterator();
        Point previousPoint = iterator.next();

        while (iterator.hasNext()) {
            Point currentPoint = iterator.next();
            boolean sameXCoordinate = previousPoint.getX() == currentPoint.getX();
            boolean previousHigherY = previousPoint.getY() > currentPoint.getY();

            if (sameXCoordinate && previousHigherY) {
                iterator.remove();
            } else {
                previousPoint = currentPoint;
            }
        }
    }

    private int findMinimumY(List<Point> points) {
        int minimumY = points.get(0).getY();
        for (int i = 1; i < points.size(); i++) {
            int currentY = points.get(i).getY();
            if (currentY < minimumY) {
                minimumY = currentY;
                if (minimumY == 1) {
                    break;
                }
            }
        }
        return minimumY;
    }

    private void filterRightPointsByMinY(List<Point> rightSkyline, int minimumY) {
        Iterator<Point> iterator = rightSkyline.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (point.getY() >= minimumY) {
                iterator.remove();
            }
        }
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
            boolean dominatesByX = this.x < other.x && this.y <= other.y;
            boolean dominatesByY = this.x <= other.x && this.y < other.y;
            return dominatesByX || dominatesByY;
        }
    }

    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point firstPoint, Point secondPoint) {
            return Integer.compare(firstPoint.getX(), secondPoint.getX());
        }
    }
}