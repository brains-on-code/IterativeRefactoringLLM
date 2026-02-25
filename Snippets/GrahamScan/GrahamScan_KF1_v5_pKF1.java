package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Implementation of the Graham scan algorithm for computing the convex hull
 * of a set of points in the plane.
 */
public class ConvexHull {

    private final Stack<Point> convexHull = new Stack<>();

    public ConvexHull(Point[] inputPoints) {
        if (inputPoints == null || inputPoints.length == 0) {
            return;
        }

        Point[] points = Arrays.copyOf(inputPoints, inputPoints.length);

        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        convexHull.push(points[0]);

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

        convexHull.push(points[firstNonCollinearPointIndex - 1]);

        for (int currentPointIndex = firstNonCollinearPointIndex; currentPointIndex < points.length; currentPointIndex++) {
            Point lastPointOnHull = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastPointOnHull, points[currentPointIndex]) <= 0) {
                lastPointOnHull = convexHull.pop();
            }
            convexHull.push(lastPointOnHull);
            convexHull.push(points[currentPointIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}