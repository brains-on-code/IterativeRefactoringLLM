package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Computes the convex hull of a set of points using the Graham Scan algorithm.
 *
 * Time complexity:
 * - Best case: O(n)
 * - Worst case: O(n log n)
 *
 * Space complexity: O(n)
 *
 * This implementation assumes integral coordinates.
 *
 * References:
 * https://github.com/TheAlgorithms/C-Plus-Plus/blob/master/geometry/graham_scan_algorithm.cpp
 * https://github.com/TheAlgorithms/C-Plus-Plus/blob/master/geometry/graham_scan_functions.hpp
 * https://algs4.cs.princeton.edu/99hull/GrahamScan.java.html
 */
public class GrahamScan {

    private final Stack<Point> hull = new Stack<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        sortByReferenceAndAngle(points);
        initializeHull(points);

        int firstDistinctIndex = findFirstDistinctPointIndex(points);
        if (firstDistinctIndex == points.length) {
            return; // all points are identical
        }

        int firstNonCollinearIndex = findFirstNonCollinearPointIndex(points, firstDistinctIndex);
        hull.push(points[firstNonCollinearIndex - 1]);

        constructHull(points, firstNonCollinearIndex);
    }

    /**
     * Sorts the array of points so that:
     * <ol>
     *   <li>{@code points[0]} is the reference point (lowest y, then lowest x)</li>
     *   <li>The remaining points are sorted by polar angle with respect to {@code points[0]}</li>
     * </ol>
     */
    private void sortByReferenceAndAngle(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private void initializeHull(Point[] points) {
        hull.push(points[0]);
    }

    /**
     * Finds the index of the first point that is not equal to {@code points[0]}.
     *
     * @param points the array of points
     * @return the index of the first distinct point, or {@code points.length} if all points are equal
     */
    private int findFirstDistinctPointIndex(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (!points[0].equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    /**
     * Finds the index of the first point that is not collinear with
     * {@code points[0]} and {@code points[firstDistinctIndex]}, scanning forward
     * from {@code firstDistinctIndex + 1}.
     *
     * @param points            the array of points
     * @param firstDistinctIndex index of the first point distinct from {@code points[0]}
     * @return the index of the first non-collinear point
     */
    private int findFirstNonCollinearPointIndex(Point[] points, int firstDistinctIndex) {
        int i = firstDistinctIndex + 1;
        while (i < points.length
                && Point.orientation(points[0], points[firstDistinctIndex], points[i]) == 0) {
            i++;
        }
        return i;
    }

    /**
     * Constructs the convex hull using the Graham Scan algorithm.
     *
     * @param points     the array of points
     * @param startIndex index from which to start processing points
     */
    private void constructHull(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            Point top = hull.pop();
            while (!hull.isEmpty()
                    && Point.orientation(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }

    /**
     * Returns the points on the convex hull in counterclockwise order.
     *
     * @return an iterable over the points on the hull
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}