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

        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        int firstDistinctIndex = findFirstDistinctPointIndex(points);
        if (firstDistinctIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearPointIndex(points, firstDistinctIndex);
        hull.push(points[firstNonCollinearIndex - 1]);

        constructHull(points, firstNonCollinearIndex);
    }

    private int findFirstDistinctPointIndex(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (!points[0].equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    private int findFirstNonCollinearPointIndex(Point[] points, int firstDistinctIndex) {
        for (int i = firstDistinctIndex + 1; i < points.length; i++) {
            if (Point.orientation(points[0], points[firstDistinctIndex], points[i]) != 0) {
                return i;
            }
        }
        return points.length;
    }

    private void constructHull(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            Point lastPoint = hull.pop();
            while (!hull.isEmpty() && Point.orientation(hull.peek(), lastPoint, points[i]) <= 0) {
                lastPoint = hull.pop();
            }
            hull.push(lastPoint);
            hull.push(points[i]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}