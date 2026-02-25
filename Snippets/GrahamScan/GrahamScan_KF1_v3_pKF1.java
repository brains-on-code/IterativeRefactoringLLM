package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Implementation of the Graham scan algorithm for computing the convex hull
 * of a set of points in the plane.
 */
public class ConvexHull {

    private final Stack<Point> hullPoints = new Stack<>();

    public ConvexHull(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hullPoints.push(points[0]);

        int firstUniqueIndex;
        for (firstUniqueIndex = 1; firstUniqueIndex < points.length; firstUniqueIndex++) {
            if (!points[0].equals(points[firstUniqueIndex])) {
                break;
            }
        }

        if (firstUniqueIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstUniqueIndex + 1;
             firstNonCollinearIndex < points.length;
             firstNonCollinearIndex++) {
            if (Point.orientation(points[0], points[firstUniqueIndex],
                points[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        hullPoints.push(points[firstNonCollinearIndex - 1]);

        for (int currentIndex = firstNonCollinearIndex; currentIndex < points.length; currentIndex++) {
            Point lastHullPoint = hullPoints.pop();
            while (Point.orientation(hullPoints.peek(), lastHullPoint, points[currentIndex]) <= 0) {
                lastHullPoint = hullPoints.pop();
            }
            hullPoints.push(lastHullPoint);
            hullPoints.push(points[currentIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(hullPoints);
    }
}