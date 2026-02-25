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

        int firstDistinctIndex;
        for (firstDistinctIndex = 1; firstDistinctIndex < points.length; firstDistinctIndex++) {
            if (!points[0].equals(points[firstDistinctIndex])) {
                break;
            }
        }

        if (firstDistinctIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstDistinctIndex + 1;
             firstNonCollinearIndex < points.length;
             firstNonCollinearIndex++) {
            if (Point.orientation(points[0], points[firstDistinctIndex], points[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        hull.push(points[firstNonCollinearIndex - 1]);

        for (int i = firstNonCollinearIndex; i < points.length; i++) {
            Point top = hull.pop();
            while (Point.orientation(hull.peek(), top, points[i]) <= 0) {
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