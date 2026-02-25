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

        // 1. Sort by y-coordinate (and x-coordinate as a tiebreaker).
        Arrays.sort(points);

        // 2. Sort remaining points by polar angle with respect to points[0].
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        // 3. Find the first point that is not identical to points[0].
        int firstNonEqualIndex = findFirstNonEqualIndex(points);
        if (firstNonEqualIndex == points.length) {
            // All points are identical; hull is just points[0].
            return;
        }

        // 4. Find the first point that is not collinear with points[0] and points[firstNonEqualIndex].
        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstNonEqualIndex);

        // The last collinear point with the starting segment belongs to the hull.
        hull.push(points[firstNonCollinearIndex - 1]);

        // 5. Process remaining points and maintain a convex hull stack.
        buildHull(points, firstNonCollinearIndex);
    }

    private int findFirstNonEqualIndex(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (!points[0].equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    private int findFirstNonCollinearIndex(Point[] points, int firstNonEqualIndex) {
        int i = firstNonEqualIndex + 1;
        while (i < points.length
                && Point.orientation(points[0], points[firstNonEqualIndex], points[i]) == 0) {
            i++;
        }
        return i;
    }

    private void buildHull(Point[] points, int startIndex) {
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
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}