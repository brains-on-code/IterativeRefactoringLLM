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

        // 1. Sort by y-coordinate (then x-coordinate) to find the anchor point at index 0.
        Arrays.sort(points);

        // 2. Sort remaining points by polar angle with respect to the anchor.
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        // 3. Push the anchor point.
        hull.push(points[0]);

        // 4. Skip all points identical to the anchor.
        int firstDistinct = 1;
        while (firstDistinct < points.length && points[0].equals(points[firstDistinct])) {
            firstDistinct++;
        }

        // If all points are identical, the hull is just the anchor.
        if (firstDistinct == points.length) {
            return;
        }

        // 5. Find the first point that is not collinear with (anchor, firstDistinct).
        int firstNonCollinear = firstDistinct + 1;
        while (
            firstNonCollinear < points.length
                && Point.orientation(points[0], points[firstDistinct], points[firstNonCollinear]) == 0
        ) {
            firstNonCollinear++;
        }

        // Push the last collinear point (furthest in the direction of the smallest polar angle).
        hull.push(points[firstNonCollinear - 1]);

        // 6. Process remaining points, maintaining a counterclockwise hull.
        for (int i = firstNonCollinear; i < points.length; i++) {
            Point top = hull.pop();
            while (Point.orientation(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
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