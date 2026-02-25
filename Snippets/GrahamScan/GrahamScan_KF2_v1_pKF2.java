package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GrahamScan {

    private final Stack<Point> hull = new Stack<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        // Sort points by natural order (e.g., by y-coordinate, then x-coordinate)
        Arrays.sort(points);

        // Sort remaining points by polar angle with respect to the first point
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        int firstNonEqualIndex = findFirstNonEqualIndex(points);
        if (firstNonEqualIndex == points.length) {
            // All points are identical; convex hull is just the single point
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstNonEqualIndex);

        // Add the last point that is collinear with points[0] and points[firstNonEqualIndex]
        hull.push(points[firstNonCollinearIndex - 1]);

        // Process remaining points to build the convex hull
        for (int i = firstNonCollinearIndex; i < points.length; i++) {
            Point top = hull.pop();
            while (!hull.isEmpty() && Point.orientation(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
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
        for (int i = firstNonEqualIndex + 1; i < points.length; i++) {
            if (Point.orientation(points[0], points[firstNonEqualIndex], points[i]) != 0) {
                return i;
            }
        }
        return points.length;
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}