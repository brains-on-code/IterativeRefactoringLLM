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

    /** Creates an empty skyline instance. */
    public SkylineAlgorithm() {
        this.points = new ArrayList<>();
    }

    /** Returns the list of points. */
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
     * @param list the list of points
     * @return the skyline of {@code list}
     */
    public ArrayList<Point> produceSubSkyLines(ArrayList<Point> list) {
        int size = list.size();
        if (size == 1) {
            return list;
        }
        if (size == 2) {
            if (list.get(0).dominates(list.get(1))) {
                list.remove(1);
            } else if (list.get(1).dominates(list.get(0))) {
                list.remove(0);
            }
            return list;
        }

        int mid = size / 2;
        ArrayList<Point> leftHalf = new ArrayList<>(mid);
        ArrayList<Point> rightHalf = new ArrayList<>(size - mid);

        for (int i = 0; i < size; i++) {
            if (i < mid) {
                leftHalf.add(list.get(i));
            } else {
                rightHalf.add(list.get(i));
            }
        }

        ArrayList<Point> leftSubSkyLine = produceSubSkyLines(leftHalf);
        ArrayList<Point> rightSubSkyLine = produceSubSkyLines(rightHalf);

        return produceFinalSkyLine(leftSubSkyLine, rightSubSkyLine);
    }

    /**
     * Merges two skylines into a final skyline.
     *
     * <p>Steps:
     * <ol>
     *   <li>Remove dominated points from the left skyline that share the same x
     *       but have a higher y.</li>
     *   <li>Find the minimum y in the left skyline.</li>
     *   <li>Remove points from the right skyline whose y is greater than or
     *       equal to that minimum (they are dominated).</li>
     *   <li>Append the cleaned right skyline to the cleaned left skyline.</li>
     * </ol>
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
     * Removes dominated points in the left skyline that have the same x but a higher y.
     */
    private void removeDominatedWithSameX(ArrayList<Point> left) {
        for (int i = 0; i < left.size() - 1; i++) {
            Point current = left.get(i);
            Point next = left.get(i + 1);
            if (current.x == next.x && current.y > next.y) {
                left.remove(i);
                i--;
            }
        }
    }

    /**
     * Finds the minimum y-coordinate in the given skyline.
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
     * Removes points from the right skyline whose y is greater than or equal to minY.
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

        /** Returns the x-coordinate. */
        public int getX() {
            return x;
        }

        /** Returns the y-coordinate. */
        public int getY() {
            return y;
        }

        /**
         * Returns {@code true} if this point dominates {@code p1} according to
         * skyline dominance:
         *
         * <p>{@code this} dominates {@code p1} if:
         * <ul>
         *   <li>{@code this.x < p1.x} and {@code this.y <= p1.y}, or</li>
         *   <li>{@code this.x <= p1.x} and {@code this.y < p1.y}</li>
         * </ul>
         *
         * @param p1 the point to compare against
         * @return {@code true} if this point dominates {@code p1}, {@code false} otherwise
         */
        public boolean dominates(Point p1) {
            return (this.x < p1.x && this.y <= p1.y)
                || (this.x <= p1.x && this.y < p1.y);
        }
    }

    /** Comparator that orders points by their x-coordinate. */
    class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return Integer.compare(a.x, b.x);
        }
    }
}