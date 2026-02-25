package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class GrahamScan {

    private final Stack<Point> hull = new Stack<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        sortPointsByPolarAngle(sortedPoints);

        hull.push(sortedPoints[0]);

        int firstDistinctIndex = findFirstDistinctPointIndex(sortedPoints);
        if (firstDistinctIndex == sortedPoints.length) {
            return;
        }

        int firstNonCollinearIndex = findFirstNonCollinearPointIndex(sortedPoints, firstDistinctIndex);
        hull.push(sortedPoints[firstNonCollinearIndex - 1]);

        buildHull(sortedPoints, firstNonCollinearIndex);
    }

    private void sortPointsByPolarAngle(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());
    }

    private int findFirstDistinctPointIndex(Point[] points) {
        Point referencePoint = points[0];
        for (int i = 1; i < points.length; i++) {
            if (!referencePoint.equals(points[i])) {
                return i;
            }
        }
        return points.length;
    }

    private int findFirstNonCollinearPointIndex(Point[] points, int firstDistinctIndex) {
        Point basePoint = points[0];
        Point comparisonPoint = points[firstDistinctIndex];

        for (int i = firstDistinctIndex + 1; i < points.length; i++) {
            if (Point.orientation(basePoint, comparisonPoint, points[i]) != 0) {
                return i;
            }
        }
        return points.length;
    }

    private void buildHull(Point[] points, int startIndex) {
        for (int i = startIndex; i < points.length; i++) {
            Point currentPoint = points[i];
            Point lastPoint = hull.pop();

            while (!hull.isEmpty() && Point.orientation(hull.peek(), lastPoint, currentPoint) <= 0) {
                lastPoint = hull.pop();
            }

            hull.push(lastPoint);
            hull.push(currentPoint);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}