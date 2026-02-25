package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GrahamScan {

    private final Stack<Point> convexHull = new Stack<>();

    public GrahamScan(Point[] inputPoints) {
        if (inputPoints == null || inputPoints.length == 0) {
            return;
        }

        Point[] sortedPoints = Arrays.copyOf(inputPoints, inputPoints.length);

        Arrays.sort(sortedPoints);
        Arrays.sort(sortedPoints, 1, sortedPoints.length, sortedPoints[0].polarOrder());

        convexHull.push(sortedPoints[0]);

        int firstDistinctIndex;
        for (firstDistinctIndex = 1; firstDistinctIndex < sortedPoints.length; firstDistinctIndex++) {
            if (!sortedPoints[0].equals(sortedPoints[firstDistinctIndex])) {
                break;
            }
        }

        if (firstDistinctIndex == sortedPoints.length) {
            return;
        }

        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstDistinctIndex + 1;
             firstNonCollinearIndex < sortedPoints.length;
             firstNonCollinearIndex++) {

            if (Point.orientation(
                    sortedPoints[0],
                    sortedPoints[firstDistinctIndex],
                    sortedPoints[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        convexHull.push(sortedPoints[firstNonCollinearIndex - 1]);

        for (int pointIndex = firstNonCollinearIndex; pointIndex < sortedPoints.length; pointIndex++) {
            Point lastHullPoint = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastHullPoint, sortedPoints[pointIndex]) <= 0) {
                lastHullPoint = convexHull.pop();
            }
            convexHull.push(lastHullPoint);
            convexHull.push(sortedPoints[pointIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}