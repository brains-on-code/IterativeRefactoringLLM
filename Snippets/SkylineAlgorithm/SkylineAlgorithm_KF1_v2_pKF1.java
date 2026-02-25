package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParetoFrontier {

    private final List<Point> points;

    public ParetoFrontier() {
        this.points = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> mergeDominatedPoints(List<Point> inputPoints) {
        int size = inputPoints.size();
        if (size == 1) {
            return inputPoints;
        } else if (size == 2) {
            if (inputPoints.get(0).dominates(inputPoints.get(1))) {
                inputPoints.remove(1);
            } else if (inputPoints.get(1).dominates(inputPoints.get(0))) {
                inputPoints.remove(0);
            }
            return inputPoints;
        }

        List<Point> leftHalf = new ArrayList<>();
        List<Point> rightHalf = new ArrayList<>();
        int midpoint = inputPoints.size() / 2;

        for (int index = 0; index < inputPoints.size(); index++) {
            if (index < midpoint) {
                leftHalf.add(inputPoints.get(index));
            } else {
                rightHalf.add(inputPoints.get(index));
            }
        }

        List<Point> mergedLeft = mergeDominatedPoints(leftHalf);
        List<Point> mergedRight = mergeDominatedPoints(rightHalf);

        return mergeFrontiers(mergedLeft, mergedRight);
    }

    public List<Point> mergeFrontiers(List<Point> leftFrontier, List<Point> rightFrontier) {
        for (int index = 0; index < leftFrontier.size() - 1; index++) {
            Point current = leftFrontier.get(index);
            Point next = leftFrontier.get(index + 1);
            if (current.getX() == next.getX() && current.getY() > next.getY()) {
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