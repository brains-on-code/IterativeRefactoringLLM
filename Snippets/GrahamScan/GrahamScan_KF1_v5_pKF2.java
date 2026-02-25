package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Computes the convex hull of a set of points using the Graham scan algorithm.
 *
 * <p>The input array of points is modified by this class (it is sorted in-place).
 */
public class ConvexHullGrahamScan {

    /** Points on the convex hull in counterclockwise order. */
    private final Stack<Point> hull = new Stack<>();

    /**
     * Constructs the convex hull from the given array of points.
     *
     * @param points the array of points for which to compute the convex hull
     */
    public ConvexHullGrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        Arrays.sort(points);
        Point anchor = points[0];

        Arrays.sort(points, 1, points.length, anchor.polarOrder());

        hull.push(anchor);

        int firstDistinctIndex = findFirstDistinctPointIndex(points, anchor);
        if (firstDistinctIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearPointIndex(points, anchor, firstDistinctIndex);

        hull.push(points[firstNonCollinearIndex - 1]);

        buildHull(points, firstNonCollinearIndex);
    }

    private int findFirstDistinctPointIndex(Point[] points, Point anchor) {
        int index = 1;
        while (index < points.length && anchor.equals(points[index])) {
            index++;
        }
        return index;
    }

    private int findFirstNonCollinearPointIndex(Point[] points, Point anchor, int firstDistinctIndex) {
        int index = firstDistinctIndex + 1;
        while (
            index < points.length
                && Point.orientation(anchor, points[firstDistinctIndex], points[index]) == 0
        ) {
            index++;
        }
        return index;
    }

    private void buildHull(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            Point candidate = points[i];
            Point top = hull.pop();

            while (Point.orientation(hull.peek(), top, candidate) <= 0) {
                top = hull.pop();
            }

            hull.push(top);
            hull.push(candidate);
        }
    }

    /**
     * Returns the points on the convex hull in counterclockwise order.
     *
     * @return an iterable over the points on the convex hull
     */
    public Iterable<Point> getHull() {
        return new ArrayList<>(hull);
    }
}