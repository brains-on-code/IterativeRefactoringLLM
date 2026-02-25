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

        int mid = size / 2;
        List<Point> leftHalf = new ArrayList<>(points.subList(0, mid));
        List<Point> rightHalf = new ArrayList<>(points.subList(mid, size));

        List<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        List<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    private List<Point> handleTwoPointCase(List<Point> points) {
        Point first = points.get(0);
        Point second = points.get(1);

        if (first.dominates(second)) {
            points.remove(1);
        } else if (second.dominates(first)) {
            points.remove(0);
        }
        return points;
    }

    public List<Point> produceFinalSkyLine(List<Point> left, List<Point> right) {
        removeRedundantLeftPoints(left);
        int minY = findMinimumY(left);
        filterRightPointsByMinY(right, minY);

        left.addAll(right);
        return left;
    }

    private void removeRedundantLeftPoints(List<Point> left) {
        if (left.size() < 2) {
            return;
        }

        Iterator<Point> iterator = left.iterator();
        Point previous = iterator.next();

        while (iterator.hasNext()) {
            Point current = iterator.next();
            if (previous.getX() == current.getX() && previous.getY() > current.getY()) {
                iterator.remove();
            } else {
                previous = current;
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
        Iterator<Point> iterator = right.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (point.getY() >= minY) {
                iterator.remove();
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
            boolean dominatesByX = this.x < other.x && this.y <= other.y;
            boolean dominatesByY = this.x <= other.x && this.y < other.y;
            return dominatesByX || dominatesByY;
        }
    }

    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.getX(), b.getX());
        }
    }
}