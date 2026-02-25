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

        Arrays.sort(points); // sort by y, then x
        Arrays.sort(points, 1, points.length, points[0].polarOrder()); // sort by polar angle

        buildHull(points);
    }

    private void buildHull(Point[] points) {
        hull.push(points[0]);

        int firstDistinctIndex = findFirstDistinctIndex(points);
        if (firstDistinctIndex == points.length) {
            return; // all points are equal
        }

        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstDistinctIndex);
        hull.push(points[firstNonCollinearIndex - 1]); // last collinear with lowest point

        processRemainingPoints(points, firstNonCollinearIndex);
    }

    private int findFirstDistinctIndex(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (!points[0].equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    private int findFirstNonCollinearIndex(Point[] points, int firstDistinctIndex) {
        int i = firstDistinctIndex + 1;
        while (i < points.length
                && Point.orientation(points[0], points[firstDistinctIndex], points[i]) == 0) {
            i++;
        }
        return i;
    }

    private void processRemainingPoints(Point[] points, int startIndex) {
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
     * Returns the points on the convex hull in counterclockwise order.
     */
    public Iterable<Point> getHull() {
        return new ArrayList<>(hull);
    }
}