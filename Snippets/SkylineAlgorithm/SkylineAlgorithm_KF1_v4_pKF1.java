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
        int size = points.size();
        if (size == 1) {
            return points;
        } else if (size == 2) {
            Point first = points.get(0);
            Point second = points.get(1);

            if (first.dominates(second)) {
                points.remove(1);
            } else if (second.dominates(first)) {
                points.remove(0);
            }
            return points;
        }

        List<Point> leftPoints = new ArrayList<>();
        List<Point> rightPoints = new ArrayList<>();
        int midIndex = points.size() / 2;

        for (int i = 0; i < points.size(); i++) {
            if (i < midIndex) {
                leftPoints.add(points.get(i));
            } else {
                rightPoints.add(points.get(i));
            }
        }

        List<Point> leftFrontier = mergeDominatedPoints(leftPoints);
        List<Point> rightFrontier = mergeDominatedPoints(rightPoints);

        return mergeFrontiers(leftFrontier, rightFrontier);
    }

    public List<Point> mergeFrontiers(List<Point> leftFrontier, List<Point> rightFrontier) {
        for (int i = 0; i < leftFrontier.size() - 1; i++) {
            Point current = leftFrontier.get(i);
            Point next = leftFrontier.get(i + 1);
            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                leftFrontier.remove(i);
                i--;
            }
        }

        int minY = leftFrontier.get(0).getY();
        for (int i = 1; i < leftFrontier.size(); i++) {
            int currentY = leftFrontier.get(i).getY();
            if (minY > currentY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        for (int i = 0; i < rightFrontier.size(); i++) {
            if (rightFrontier.get(i).getY() >= minY) {
                rightFrontier.remove(i);
                i--;
            }
        }

        leftFrontier.addAll(rightFrontier);
        return leftFrontier;
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
            return (this.x < other.x && this.y <= other.y)
                    || (this.x <= other.x && this.y < other.y);
        }
    }

    class PointXComparator implements Comparator<Point> {

        @Override
        public int compare(Point first, Point second) {
            return Integer.compare(first.getX(), second.getX());
        }
    }
}