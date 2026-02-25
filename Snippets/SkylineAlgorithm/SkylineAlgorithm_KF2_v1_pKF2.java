package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SkylineAlgorithm {

    private final List<Point> points;

    public SkylineAlgorithm() {
        this.points = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> produceSubSkyLines(List<Point> list) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }

        if (size == 2) {
            Point first = list.get(0);
            Point second = list.get(1);

            if (first.dominates(second)) {
                list.remove(1);
            } else if (second.dominates(first)) {
                list.remove(0);
            }
            return list;
        }

        int mid = size / 2;
        List<Point> leftHalf = new ArrayList<>(list.subList(0, mid));
        List<Point> rightHalf = new ArrayList<>(list.subList(mid, size));

        List<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        List<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    public List<Point> produceFinalSkyLine(List<Point> left, List<Point> right) {
        removeRedundantPointsWithSameX(left);

        int minY = findMinimumY(left);

        removePointsWithYGreaterOrEqual(right, minY);

        left.addAll(right);
        return left;
    }

    private void removeRedundantPointsWithSameX(List<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1);

            if (current.x == next.x && current.y > next.y) {
                points.remove(i);
                i--;
            }
        }
    }

    private int findMinimumY(List<Point> points) {
        int minY = points.get(0).y;

        for (int i = 1; i < points.size(); i++) {
            int currentY = points.get(i).y;
            if (currentY < minY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        return minY;
    }

    private void removePointsWithYGreaterOrEqual(List<Point> points, int thresholdY) {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).y >= thresholdY) {
                points.remove(i);
                i--;
            }
        }
    }

    public static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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

    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.x, b.x);
        }
    }
}