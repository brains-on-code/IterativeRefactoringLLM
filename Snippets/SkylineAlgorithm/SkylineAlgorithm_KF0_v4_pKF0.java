package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Space complexity: O(n)
 * Time complexity: O(n log n), because it is a divide and conquer algorithm.
 */
public class SkylineAlgorithm {

    private final List<Point> points;

    /**
     * Main constructor of the application. The list of points represents the sum of all edges.
     */
    public SkylineAlgorithm() {
        this.points = new ArrayList<>();
    }

    /**
     * @return the list that includes all points.
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * Main divide-and-conquer recursive algorithm.
     *
     * If the list size is 1, it is returned as is.
     * If the list size is 2, the dominated point (if any) is removed.
     * If the list size is greater than 2, the list is split in half, the method
     * is called recursively on each half, and then the final skyline is produced
     * by merging the two sub-skylines.
     *
     * @param points the initial list of points
     * @return the combined skyline of the first and second halves
     */
    public List<Point> produceSubSkyLines(List<Point> points) {
        int size = points.size();
        if (size <= 1) {
            return new ArrayList<>(points);
        }

        if (size == 2) {
            return handleTwoPoints(points);
        }

        int mid = size / 2;
        List<Point> leftHalf = new ArrayList<>(points.subList(0, mid));
        List<Point> rightHalf = new ArrayList<>(points.subList(mid, size));

        List<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        List<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    private List<Point> handleTwoPoints(List<Point> points) {
        Point first = points.get(0);
        Point second = points.get(1);

        List<Point> result = new ArrayList<>(2);
        if (first.dominates(second)) {
            result.add(first);
        } else if (second.dominates(first)) {
            result.add(second);
        } else {
            result.add(first);
            result.add(second);
        }
        return result;
    }

    /**
     * Produces the final skyline from the skylines of the left and right parts.
     *
     * 1. Removes dominated points from the left skyline where points share the same x
     *    and the one with the larger y is removed.
     * 2. Finds the minimum y-value in the left skyline.
     * 3. Removes dominated points from the right skyline whose y is greater than or
     *    equal to that minimum y.
     * 4. Combines the cleaned left and right skylines and returns the result.
     *
     * @param left  the skyline of the left part of points
     * @param right the skyline of the right part of points
     * @return the final skyline
     */
    public List<Point> produceFinalSkyLine(List<Point> left, List<Point> right) {
        List<Point> cleanedLeft = removeDominatedInLeft(new ArrayList<>(left));
        int minY = findMinY(cleanedLeft);
        List<Point> cleanedRight = removeDominatedInRight(new ArrayList<>(right), minY);

        cleanedLeft.addAll(cleanedRight);
        return cleanedLeft;
    }

    private List<Point> removeDominatedInLeft(List<Point> left) {
        if (left.isEmpty()) {
            return left;
        }

        for (int i = 0; i < left.size() - 1; i++) {
            Point current = left.get(i);
            Point next = left.get(i + 1);
            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                left.remove(i);
                i--;
            }
        }
        return left;
    }

    private int findMinY(List<Point> points) {
        if (points.isEmpty()) {
            return Integer.MAX_VALUE;
        }

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

    private List<Point> removeDominatedInRight(List<Point> right, int minY) {
        if (right.isEmpty()) {
            return right;
        }

        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).getY() >= minY) {
                right.remove(i);
                i--;
            }
        }
        return right;
    }

    public static class Point {

        private final int x;
        private final int y;

        /**
         * Main constructor of Point class, used to represent 2D points.
         *
         * @param x the point's x-value
         * @param y the point's y-value
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x-value
         */
        public int getX() {
            return x;
        }

        /**
         * @return the y-value
         */
        public int getY() {
            return y;
        }

        /**
         * Based on the skyline theory, checks if this point dominates the given point.
         *
         * @param other the point to compare against
         * @return true if this point dominates {@code other}, false otherwise
         */
        public boolean dominates(Point other) {
            return (this.x < other.x && this.y <= other.y)
                || (this.x <= other.x && this.y < other.y);
        }
    }

    /**
     * Comparator used to compare 2D points based on their x-values, in order to sort them.
     */
    static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.getX(), b.getX());
        }
    }
}