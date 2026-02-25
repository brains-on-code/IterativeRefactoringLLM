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

    /** Stack of points forming the convex hull in counterclockwise order. */
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

        // Sort points by y-coordinate, breaking ties by x-coordinate.
        Arrays.sort(points);

        // Sort remaining points by polar angle with respect to points[0].
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        // Push the point with the lowest y-coordinate (and leftmost if tie).
        hull.push(points[0]);

        // Find the first point that is not equal to points[0].
        int firstDistinct = 1;
        while (firstDistinct < points.length && points[0].equals(points[firstDistinct])) {
            firstDistinct++;
        }

        // If all points are equal, the hull consists of a single point.
        if (firstDistinct == points.length) {
            return;
        }

        // Find the first point that is not collinear with points[0] and points[firstDistinct].
        int firstNonCollinear = firstDistinct + 1;
        while (
            firstNonCollinear < points.length
                && Point.orientation(points[0], points[firstDistinct], points[firstNonCollinear]) == 0
        ) {
            firstNonCollinear++;
        }

        // Push the last collinear point with the smallest polar angle.
        hull.push(points[firstNonCollinear - 1]);

        // Process remaining points and maintain a counterclockwise hull.
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