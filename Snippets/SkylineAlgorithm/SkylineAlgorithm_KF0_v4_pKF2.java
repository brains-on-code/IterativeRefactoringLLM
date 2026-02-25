package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Divide-and-conquer skyline algorithm.
 *
 * <p>Space complexity: O(n) <br>
 * Time complexity: O(n log n)
 */
public class SkylineAlgorithm {

    private final ArrayList<Point> points;

    public SkylineAlgorithm() {
        this.points = new ArrayList<>();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Recursively computes the skyline of the given list of points.
     *
     * <p>Base cases:
     * <ul>
     *   <li>Size 1: already a skyline.</li>
     *   <li>Size 2: remove the dominated point, if any.</li>
     * </ul>
     *
     * <p>Recursive case:
     * <ul>
     *   <li>Split the list into two halves.</li>
     *   <li>Recursively compute the skyline of each half.</li>
     *   <li>Merge the two skylines.</li>
     * </ul>
     *
     * @param points the list of points
     * @return the skyline of {@code points}
     */
    public ArrayList<Point> produceSubSkyLines(ArrayList<Point> points) {
        int size = points.size();

        if (size == 1) {
            return points;
        }

        if (size == 2) {
            removeDominatedInPair(points);
            return points;
        }

        int mid = size / 2;
        ArrayList<Point> leftHalf = new ArrayList<>(points.subList(0, mid));
        ArrayList<Point> rightHalf = new ArrayList<>(points.subList(mid, size));

        ArrayList<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        ArrayList<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    /**
     * Removes the dominated point from a list of exactly two points, if any.
     *
     * @param points list containing exactly two points
     */
    private void removeDominatedInPair(ArrayList<Point> points) {
        Point first = points.get(0);
        Point second = points.get(1);

        if (first.dominates(second)) {
            points.remove(1);
        } else if (second.dominates(first)) {
            points.remove(0);
        }
    }

    /**
     * Merges two skylines into a final skyline.
     *
     * @param left  the skyline of the left subset of points
     * @param right the skyline of the right subset of points
     * @return the merged skyline
     */
    public ArrayList<Point> produceFinalSkyLine(ArrayList<Point> left, ArrayList<Point> right) {
        removeDominatedWithSameX(left);
        int minY = findMinY(left);
        removeDominatedFromRight(right, minY);

        left.addAll(right);
        return left;
    }

    /**
     * Removes dominated points in a skyline that share the same x-coordinate.
     * For points with the same x, only the one with the smallest y is kept.
     *
     * @param skyline the skyline to clean
     */
    private void removeDominatedWithSameX(ArrayList<Point> skyline) {
        for (int i = 0; i < skyline.size() - 1; i++) {
            Point current = skyline.get(i);
            Point next = skyline.get(i + 1);

            if (current.x == next.x && current.y > next.y) {
                skyline.remove(i);
                i--;
            }
        }
    }

    /**
     * Finds the minimum y-coordinate in a skyline.
     *
     * @param skyline the skyline
     * @return the minimum y value
     */
    private int findMinY(ArrayList<Point> skyline) {
        int minY = skyline.get(0).y;

        for (int i = 1; i < skyline.size(); i++) {
            int y = skyline.get(i).y;
            if (minY > y) {
                minY = y;
                if (minY == 1) {
                    break;
                }
            }
        }

        return minY;
    }

    /**
     * Removes points from the right skyline that are dominated by the minimum y
     * value found in the left skyline.
     *
     * @param right the right skyline
     * @param minY  the minimum y value from the left skyline
     */
    private void removeDominatedFromRight(ArrayList<Point> right, int minY) {
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).y >= minY) {
                right.remove(i);
                i--;
            }
        }
    }

    public static class Point {

        private final int x;
        private final int y;

        /**
         * Creates a 2D point.
         *
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
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
         * Returns {@code true} if this point dominates {@code other} according to
         * skyline dominance:
         *
         * <p>{@code this} dominates {@code other} if:
         * <ul>
         *   <li>{@code this.x < other.x} and {@code this.y <= other.y}, or</li>
         *   <li>{@code this.x <= other.x} and {@code this.y < other.y}</li>
         * </ul>
         *
         * @param other the point to compare against
         * @return {@code true} if this point dominates {@code other}, {@code false} otherwise
         */
        public boolean dominates(Point other) {
            return (this.x < other.x && this.y <= other.y)
                || (this.x <= other.x && this.y < other.y);
        }
    }

    class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.x, b.x);
        }
    }
}