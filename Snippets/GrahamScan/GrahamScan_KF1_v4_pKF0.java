package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        sortPointsForGrahamScan(sortedPoints);
        buildHull(sortedPoints);
    }

    private void sortPointsForGrahamScan(Point[] points) {
        // First sort by y, then x (as defined in Point.compareTo)
        Arrays.sort(points);
        // Then sort remaining points by polar angle with respect to points[0]
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private void buildHull(Point[] points) {
        hull.push(points[0]);

        int firstDistinctIndex = findFirstDistinctIndex(points);
        if (firstDistinctIndex == points.length) {
            // All points are identical
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearIndex(points, firstDistinctIndex);

        // Add the last point that is collinear with the lowest point
        hull.push(points[firstNonCollinearIndex - 1]);

        processRemainingPoints(points, firstNonCollinearIndex);
    }

    private int findFirstDistinctIndex(Point[] points) {
        Point reference = points[0];
        for (int i = 1; i < points.length; i++) {
            if (!reference.equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    private int findFirstNonCollinearIndex(Point[] points, int firstDistinctIndex) {
        int index = firstDistinctIndex + 1;
        Point reference = points[0];
        Point firstDistinct = points[firstDistinctIndex];

        while (index < points.length
                && Point.orientation(reference, firstDistinct, points[index]) == 0) {
            index++;
        }
        return index;
    }

    private void processRemainingPoints(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            Point currentPoint = points[i];
            Point lastPoint = hull.pop();

            while (!hull.isEmpty()
                    && Point.orientation(hull.peek(), lastPoint, currentPoint) <= 0) {
                lastPoint = hull.pop();
            }

            hull.push(lastPoint);
            hull.push(currentPoint);
        }
    }

    /**
     * Returns the points on the convex hull in counterclockwise order.
     */
    public Iterable<Point> getHull() {
        return new ArrayList<>(hull);
    }
}