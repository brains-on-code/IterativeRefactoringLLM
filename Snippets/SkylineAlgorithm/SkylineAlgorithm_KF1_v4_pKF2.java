package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A divide-and-conquer utility operating on {@link Point} objects.
 */
public class Class1 {

    private final List<Point> items;

    public Class1() {
        this.items = new ArrayList<>();
    }

    public List<Point> getItems() {
        return items;
    }

    /**
     * Recursively processes a list of {@link Point} items using a
     * divide-and-conquer strategy and returns the processed list.
     */
    public List<Point> processList(List<Point> points) {
        int size = points.size();

        if (size <= 1) {
            return points;
        }

        if (size == 2) {
            Point first = points.get(0);
            Point second = points.get(1);

            if (first.dominates(second)) {
                points.remove(1);
            } else if (second.dominates(first)) {
                points.remove(0);
            }
            return points;
        }

        int mid = size / 2;
        List<Point> leftHalf = new ArrayList<>(points.subList(0, mid));
        List<Point> rightHalf = new ArrayList<>(points.subList(mid, size));

        List<Point> processedLeft = processList(leftHalf);
        List<Point> processedRight = processList(rightHalf);

        return merge(processedLeft, processedRight);
    }

    /**
     * Merges two processed lists of {@link Point} items into a single list.
     */
    public List<Point> merge(List<Point> left, List<Point> right) {
        removeDominatedNeighbors(left);
        int minY = findMinY(left);
        filterRightByMinY(right, minY);

        left.addAll(right);
        return left;
    }

    /**
     * Removes dominated neighboring elements in the given list.
     * Two neighbors are compared by x; if x is equal, the one with larger y is removed.
     */
    private void removeDominatedNeighbors(List<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1);

            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                points.remove(i);
                i--;
            }
        }
    }

    /**
     * Returns the minimum y value in the given list.
     */
    private int findMinY(List<Point> points) {
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

    /**
     * Removes elements from the right list whose y value is not better than {@code minY}.
     */
    private void filterRightByMinY(List<Point> right, int minY) {
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).getY() >= minY) {
                right.remove(i);
                i--;
            }
        }
    }

    /**
     * Immutable 2D point with a dominance relation.
     */
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

        /**
         * Returns {@code true} if this point dominates the given point.
         * A point (x1, y1) dominates (x2, y2) if:
         *  - x1 < x2 and y1 <= y2, or
         *  - x1 <= x2 and y1 < y2.
         */
        public boolean dominates(Point other) {
            boolean dominatesByX = this.x < other.x && this.y <= other.y;
            boolean dominatesByY = this.x <= other.x && this.y < other.y;
            return dominatesByX || dominatesByY;
        }
    }

    /**
     * Comparator for {@link Point} based on the x value.
     */
    class PointXComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.getX(), b.getX());
        }
    }
}