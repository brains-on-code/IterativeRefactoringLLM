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

    public GrahamScan(Point[] inputPoints) {
        if (inputPoints == null || inputPoints.length == 0) {
            return;
        }

        // Sort by y-coordinate, then by polar order with respect to the first point
        Arrays.sort(inputPoints);
        Arrays.sort(inputPoints, 1, inputPoints.length, inputPoints[0].polarOrder());

        convexHull.push(inputPoints[0]);

        // Find the first point not equal to inputPoints[0]
        int firstDistinctPointIndex;
        for (firstDistinctPointIndex = 1;
             firstDistinctPointIndex < inputPoints.length;
             firstDistinctPointIndex++) {

            if (!inputPoints[0].equals(inputPoints[firstDistinctPointIndex])) {
                break;
            }
        }

        // All points are equal
        if (firstDistinctPointIndex == inputPoints.length) {
            return;
        }

        // Find the first point that is not collinear with inputPoints[0] and inputPoints[firstDistinctPointIndex]
        int firstNonCollinearPointIndex;
        for (firstNonCollinearPointIndex = firstDistinctPointIndex + 1;
             firstNonCollinearPointIndex < inputPoints.length;
             firstNonCollinearPointIndex++) {

            if (Point.orientation(
                    inputPoints[0],
                    inputPoints[firstDistinctPointIndex],
                    inputPoints[firstNonCollinearPointIndex]) != 0) {
                break;
            }
        }

        convexHull.push(inputPoints[firstNonCollinearPointIndex - 1]);

        // Process the remaining points and update the convex hull
        for (int currentPointIndex = firstNonCollinearPointIndex;
             currentPointIndex < inputPoints.length;
             currentPointIndex++) {

            Point lastHullPoint = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastHullPoint, inputPoints[currentPointIndex]) <= 0) {
                lastHullPoint = convexHull.pop();
            }
            convexHull.push(lastHullPoint);
            convexHull.push(inputPoints[currentPointIndex]);
        }
    }

    /**
     * @return An iterable collection of points representing the convex hull.
     */
    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}