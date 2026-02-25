package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

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

    private final Stack<Point> convexHull = new Stack<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        // Sort by y-coordinate, then by polar order with respect to the first point
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        convexHull.push(points[0]);

        // Find the first point not equal to points[0]
        int firstDistinctIndex;
        for (firstDistinctIndex = 1;
             firstDistinctIndex < points.length;
             firstDistinctIndex++) {

            if (!points[0].equals(points[firstDistinctIndex])) {
                break;
            }
        }

        // All points are equal
        if (firstDistinctIndex == points.length) {
            return;
        }

        // Find the first point that is not collinear with points[0] and points[firstDistinctIndex]
        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstDistinctIndex + 1;
             firstNonCollinearIndex < points.length;
             firstNonCollinearIndex++) {

            if (Point.orientation(
                    points[0],
                    points[firstDistinctIndex],
                    points[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        convexHull.push(points[firstNonCollinearIndex - 1]);

        // Process the remaining points and update the convex hull
        for (int currentIndex = firstNonCollinearIndex;
             currentIndex < points.length;
             currentIndex++) {

            Point lastHullPoint = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastHullPoint, points[currentIndex]) <= 0) {
                lastHullPoint = convexHull.pop();
            }
            convexHull.push(lastHullPoint);
            convexHull.push(points[currentIndex]);
        }
    }

    /**
     * @return An iterable collection of points representing the convex hull.
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}