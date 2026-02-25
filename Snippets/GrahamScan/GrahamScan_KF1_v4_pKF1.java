package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Implementation of the Graham scan algorithm for computing the convex hull
 * of a set of points in the plane.
 */
public class ConvexHull {

    private final Stack<Point> hull = new Stack<>();

    public ConvexHull(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        int firstDistinctPointIndex;
        for (firstDistinctPointIndex = 1; firstDistinctPointIndex < points.length; firstDistinctPointIndex++) {
            if (!points[0].equals(points[firstDistinctPointIndex])) {
                break;
            }
        }

        if (firstDistinctPointIndex == points.length) {
            return;
        }

        int firstNonCollinearPointIndex;
        for (firstNonCollinearPointIndex = firstDistinctPointIndex + 1;
             firstNonCollinearPointIndex < points.length;
             firstNonCollinearPointIndex++) {
            if (Point.orientation(points[0], points[firstDistinctPointIndex],
                points[firstNonCollinearPointIndex]) != 0) {
                break;
            }
        }

        hull.push(points[firstNonCollinearPointIndex - 1]);

        for (int pointIndex = firstNonCollinearPointIndex; pointIndex < points.length; pointIndex++) {
            Point lastPointOnHull = hull.pop();
            while (Point.orientation(hull.peek(), lastPointOnHull, points[pointIndex]) <= 0) {
                lastPointOnHull = hull.pop();
            }
            hull.push(lastPointOnHull);
            hull.push(points[pointIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}