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

        sortPoints(points);
        initializeHull(points);

        int firstNonEqualIndex = findFirstNonEqualIndex(points);
        if (firstNonEqualIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstNonEqualIndex);
        hull.push(points[firstNonCollinearIndex - 1]);

        buildHull(points, firstNonCollinearIndex);
    }

    private void sortPoints(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private void initializeHull(Point[] points) {
        hull.push(points[0]);
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

    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}