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

        // Step 1: sort all points to find the anchor (lowest y, then lowest x).
        Arrays.sort(points);
        Point anchor = points[0];

        // Step 2: sort remaining points by polar angle with respect to the anchor.
        Arrays.sort(points, 1, points.length, anchor.polarOrder());

        // Step 3: push the anchor point onto the hull.
        hull.push(anchor);

        // Step 4: skip all points identical to the anchor.
        int firstDistinct = 1;
        while (firstDistinct < points.length && anchor.equals(points[firstDistinct])) {
            firstDistinct++;
        }

        // If all points are identical, the hull consists of the anchor only.
        if (firstDistinct == points.length) {
            return;
        }

        // Step 5: find the first point that is not collinear with (anchor, firstDistinct).
        int firstNonCollinear = firstDistinct + 1;
        while (
            firstNonCollinear < points.length
                && Point.orientation(anchor, points[firstDistinct], points[firstNonCollinear]) == 0
        ) {
            firstNonCollinear++;
        }

        // Push the last collinear point (furthest along the smallest polar angle direction).
        hull.push(points[firstNonCollinear - 1]);

        // Step 6: process remaining points, maintaining a counterclockwise hull.
        for (int i = firstNonCollinear; i < points.length; i++) {
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