package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Computes the convex hull of a set of points using the Graham scan algorithm.
 */
public class Class1 {

    private final Stack<Point> hull = new Stack<>();

    public Class1(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        // Sort points by y-coordinate, breaking ties by x-coordinate
        Arrays.sort(points);

        // Sort remaining points by polar angle with respect to points[0]
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        // Find the first point that is not equal to points[0]
        int firstDistinctIndex;
        for (firstDistinctIndex = 1; firstDistinctIndex < points.length; firstDistinctIndex++) {
            if (!points[0].equals(points[firstDistinctIndex])) {
                break;
            }
        }

        // All points are equal
        if (firstDistinctIndex == points.length) {
            return;
        }

        // Find the first point that does not lie collinear with points[0] and points[firstDistinctIndex]
        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstDistinctIndex + 1; firstNonCollinearIndex < points.length; firstNonCollinearIndex++) {
            if (Point.orientation(points[0], points[firstDistinctIndex], points[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        // Add the last collinear point with the lowest point to the hull
        hull.push(points[firstNonCollinearIndex - 1]);

        // Process remaining points
        for (int i = firstNonCollinearIndex; i < points.length; i++) {
            Point top = hull.pop();
            while (!hull.isEmpty() && Point.orientation(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }

    /**
     * Returns the points on the convex hull in counterclockwise order.
     */
    public Iterable<Point> getHull() {
        return new ArrayList<>(hull);
    }
}