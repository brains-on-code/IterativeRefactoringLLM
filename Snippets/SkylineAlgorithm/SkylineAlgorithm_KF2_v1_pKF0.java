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
        removeRedundantLeftPoints(left);
        int minY = findMinimumY(left);
        filterRightPointsByMinY(right, minY);

        left.addAll(right);
        return left;
    }

    private void removeRedundantLeftPoints(List<Point> left) {
        for (int i = 0; i < left.size() - 1; i++) {
            Point current = left.get(i);
            Point next = left.get(i + 1);

            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                left.remove(i);
                i--;
            }
        }
    }

    private int findMinimumY(List<Point> points) {
        int minY = points.get(0).getY();
        for (int i = 1; i < points.size(); i++) {
            int currentY = points.get(i).getY();
            if (currentY < minY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }
        return minY;
    }

    private void filterRightPointsByMinY(List<Point> right, int minY) {
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).getY() >= minY) {
                right.remove(i);
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
            boolean strictlySmallerX = this.x < other.x && this.y <= other.y;
            boolean strictlySmallerY = this.x <= other.x && this.y < other.y;
            return strictlySmallerX || strictlySmallerY;
        }
    }

    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.getX(), b.getX());
        }
    }
}