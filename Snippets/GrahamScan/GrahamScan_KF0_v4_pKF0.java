package com.thealgorithms.geometry;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

/**
 * Computes the convex hull using the Graham Scan algorithm.
 * Time complexity: O(n log n) in the worst case.
 * Space complexity: O(n).
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

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        sortPoints(sortedPoints);

        hull.push(sortedPoints[0]);

        int firstDistinctIndex = findFirstDistinctPointIndex(sortedPoints);
        if (firstDistinctIndex == sortedPoints.length) {
            return; // all points are identical
        }

        int firstNonCollinearIndex = findFirstNonCollinearPointIndex(sortedPoints, firstDistinctIndex);
        hull.push(sortedPoints[firstNonCollinearIndex - 1]);

        constructHull(sortedPoints, firstNonCollinearIndex);
    }

    private void sortPoints(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private int findFirstDistinctPointIndex(Point[] points) {
        int index = 1;
        while (index < points.length && points[0].equals(points[index])) {
            index++;
        }
        return index;
    }

    private int findFirstNonCollinearPointIndex(Point[] points, int firstDistinctIndex) {
        int index = firstDistinctIndex + 1;
        while (index < points.length
                && Point.orientation(points[0], points[firstDistinctIndex], points[index]) == 0) {
            index++;
        }
        return index;
    }

    private void constructHull(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            pushPointToHull(points[i]);
        }
    }

    private void pushPointToHull(Point point) {
        Point top = hull.pop();
        while (!hull.isEmpty() && Point.orientation(hull.peek(), top, point) <= 0) {
            top = hull.pop();
        }
        hull.push(top);
        hull.push(point);
    }

    /**
     * @return An iterable collection of points representing the convex hull.
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}