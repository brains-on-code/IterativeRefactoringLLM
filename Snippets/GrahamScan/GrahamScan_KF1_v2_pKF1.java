package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Implementation of the Graham scan algorithm for computing the convex hull
 * of a set of points in the plane.
 */
public class ConvexHull {

    private final Stack<Point> convexHullStack = new Stack<>();

    public ConvexHull(Point[] inputPoints) {
        if (inputPoints == null || inputPoints.length == 0) {
            return;
        }

        Arrays.sort(inputPoints);
        Arrays.sort(inputPoints, 1, inputPoints.length, inputPoints[0].polarOrder());

        convexHullStack.push(inputPoints[0]);

        int firstDistinctPointIndex;
        for (firstDistinctPointIndex = 1; firstDistinctPointIndex < inputPoints.length; firstDistinctPointIndex++) {
            if (!inputPoints[0].equals(inputPoints[firstDistinctPointIndex])) {
                break;
            }
        }

        if (firstDistinctPointIndex == inputPoints.length) {
            return;
        }

        int firstNonCollinearPointIndex;
        for (firstNonCollinearPointIndex = firstDistinctPointIndex + 1;
             firstNonCollinearPointIndex < inputPoints.length;
             firstNonCollinearPointIndex++) {
            if (Point.orientation(inputPoints[0], inputPoints[firstDistinctPointIndex],
                inputPoints[firstNonCollinearPointIndex]) != 0) {
                break;
            }
        }

        convexHullStack.push(inputPoints[firstNonCollinearPointIndex - 1]);

        for (int currentPointIndex = firstNonCollinearPointIndex; currentPointIndex < inputPoints.length; currentPointIndex++) {
            Point lastPointOnHull = convexHullStack.pop();
            while (Point.orientation(convexHullStack.peek(), lastPointOnHull, inputPoints[currentPointIndex]) <= 0) {
                lastPointOnHull = convexHullStack.pop();
            }
            convexHullStack.push(lastPointOnHull);
            convexHullStack.push(inputPoints[currentPointIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(convexHullStack);
    }
}