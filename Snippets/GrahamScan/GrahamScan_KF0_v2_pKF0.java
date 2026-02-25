package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * A Java program that computes the convex hull using the Graham Scan algorithm.
 * The time complexity is O(n) in the best case and O(n log(n)) in the worst case.
 * The space complexity is O(n).
 * This algorithm is applicable only to integral coordinates.
 *
 * References:
 * https://github.com/TheAlgorithms/C-Plus-Plus/blob/master/geometry/graham_scan_algorithm.cpp
 * https://github.com/TheAlgorithms/C-Plus-Plus/blob/master/geometry/graham_scan_functions.hpp
 * https://algs4.cs.princeton.edu/99hull/GrahamScan.java.html
 */
public class GrahamScan {

    private final Deque<Point> hull = new ArrayDeque<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        preprocessPoints(points);

        hull.push(points[0]);

        int firstNonEqualIndex = findFirstNonEqualIndex(points);
        if (firstNonEqualIndex == points.length) {
            return; // all points are equal
        }

        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstNonEqualIndex);
        hull.push(points[firstNonCollinearIndex - 1]);

        buildHull(points, firstNonCollinearIndex);
    }

    private void preprocessPoints(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private int findFirstNonEqualIndex(Point[] points) {
        int index = 1;
        while (index < points.length && points[0].equals(points[index])) {
            index++;
        }
        return index;
    }

    private int findFirstNonCollinearIndex(Point[] points, int firstNonEqualIndex) {
        int index = firstNonEqualIndex + 1;
        while (index < points.length
                && Point.orientation(points[0], points[firstNonEqualIndex], points[index]) == 0) {
            index++;
        }
        return index;
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
     * @return An iterable collection of points representing the convex hull.
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}