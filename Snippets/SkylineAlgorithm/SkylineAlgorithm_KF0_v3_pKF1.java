package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Space complexity: O(n)
 * Time complexity: O(n log n), because it is a divide and conquer algorithm.
 */
public class SkylineAlgorithm {

    private final ArrayList<Point> skylinePoints;

    /**
     * Main constructor of the application. ArrayList skylinePoints gets created,
     * which represents the sum of all edges.
     */
    public SkylineAlgorithm() {
        skylinePoints = new ArrayList<>();
    }

    /**
     * @return skylinePoints, the ArrayList that includes all points.
     */
    public ArrayList<Point> getPoints() {
        return skylinePoints;
    }

    /**
     * The main divide and conquer, and also recursive algorithm. It gets an
     * ArrayList full of points as an argument. If the size of that ArrayList is
     * 1 or 2, the ArrayList is returned as it is, or with one less point (if
     * the initial size is 2 and one of its points is dominated by the other
     * one). On the other hand, if the ArrayList's size is bigger than 2, the
     * function is called again, twice, with arguments the corresponding half of
     * the initial ArrayList each time. Once the recursion has ended, the
     * function mergeSkyLines gets called, in order to produce the final
     * skyline, and return it.
     *
     * @param points the initial list of points
     * @return mergedSkyLine, the combination of first half's and second half's
     * skyline
     * @see Point
     */
    public ArrayList<Point> computeSkyline(ArrayList<Point> points) {
        int size = points.size();
        if (size == 1) {
            return points;
        } else if (size == 2) {
            Point firstPoint = points.get(0);
            Point secondPoint = points.get(1);

            if (firstPoint.dominates(secondPoint)) {
                points.remove(1);
            } else if (secondPoint.dominates(firstPoint)) {
                points.remove(0);
            }
            return points;
        }

        ArrayList<Point> leftPoints = new ArrayList<>();
        ArrayList<Point> rightPoints = new ArrayList<>();
        int middleIndex = size / 2;

        for (int index = 0; index < size; index++) {
            if (index < middleIndex) {
                leftPoints.add(points.get(index));
            } else {
                rightPoints.add(points.get(index));
            }
        }

        ArrayList<Point> leftSkyline = computeSkyline(leftPoints);
        ArrayList<Point> rightSkyline = computeSkyline(rightPoints);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    /**
     * The first half's skyline gets cleared from some points that are not part
     * of the final skyline (Points with same x-value and different y-values.
     * The point with the smallest y-value is kept). Then, the minimum y-value
     * of the points of first half's skyline is found. That helps us to clear
     * the second half's skyline, because, the points of second half's skyline
     * that have greater y-value of the minimum y-value that we found before,
     * are dominated, so they are not part of the final skyline. Finally, the
     * "cleaned" first half's and second half's skylines, are combined,
     * producing the final skyline, which is returned.
     *
     * @param leftSkyline  the skyline of the left part of points
     * @param rightSkyline the skyline of the right part of points
     * @return finalSkyline the final skyline
     */
    public ArrayList<Point> mergeSkylines(ArrayList<Point> leftSkyline, ArrayList<Point> rightSkyline) {
        // Remove dominated points from leftSkyline (same x, keep smaller y)
        for (int index = 0; index < leftSkyline.size() - 1; index++) {
            Point currentPoint = leftSkyline.get(index);
            Point nextPoint = leftSkyline.get(index + 1);
            if (currentPoint.x == nextPoint.x && currentPoint.y > nextPoint.y) {
                leftSkyline.remove(index);
                index--;
            }
        }

        // Find minimum y-value in leftSkyline
        int minY = leftSkyline.get(0).y;
        for (int index = 1; index < leftSkyline.size(); index++) {
            int currentY = leftSkyline.get(index).y;
            if (minY > currentY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        // Remove dominated points from rightSkyline (y >= minY)
        for (int index = 0; index < rightSkyline.size(); index++) {
            if (rightSkyline.get(index).y >= minY) {
                rightSkyline.remove(index);
                index--;
            }
        }

        leftSkyline.addAll(rightSkyline);
        return leftSkyline;
    }

    public static class Point {

        private final int x;
        private final int y;

        /**
         * The main constructor of Point Class, used to represent the 2
         * Dimension points.
         *
         * @param x the point's x-value.
         * @param y the point's y-value.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return x, the x-value
         */
        public int getX() {
            return x;
        }

        /**
         * @return y, the y-value
         */
        public int getY() {
            return y;
        }

        /**
         * Based on the skyline theory, it checks if the point that calls the
         * function dominates the argument point.
         *
         * @param otherPoint the point that is compared
         * @return true if the point which calls the function dominates otherPoint,
         * false otherwise.
         */
        public boolean dominates(Point otherPoint) {
            return ((this.x < otherPoint.x && this.y <= otherPoint.y)
                || (this.x <= otherPoint.x && this.y < otherPoint.y));
        }
    }

    /**
     * It is used to compare the 2 Dimension points, based on their x-values, in
     * order to get sorted later.
     */
    class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point firstPoint, Point secondPoint) {
            return Integer.compare(firstPoint.x, secondPoint.x);
        }
    }
}