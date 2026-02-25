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

        Arrays.sort(inputPoints);
        Arrays.sort(inputPoints, 1, inputPoints.length, inputPoints[0].polarOrder());

        convexHull.push(inputPoints[0]);

        int firstDistinctIndex;
        for (firstDistinctIndex = 1; firstDistinctIndex < inputPoints.length; firstDistinctIndex++) {
            if (!inputPoints[0].equals(inputPoints[firstDistinctIndex])) {
                break;
            }
        }

        if (firstDistinctIndex == inputPoints.length) {
            return;
        }

        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstDistinctIndex + 1;
             firstNonCollinearIndex < inputPoints.length;
             firstNonCollinearIndex++) {

            if (Point.orientation(inputPoints[0],
                                  inputPoints[firstDistinctIndex],
                                  inputPoints[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        convexHull.push(inputPoints[firstNonCollinearIndex - 1]);

        for (int i = firstNonCollinearIndex; i < inputPoints.length; i++) {
            Point lastPoint = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastPoint, inputPoints[i]) <= 0) {
                lastPoint = convexHull.pop();
            }
            convexHull.push(lastPoint);
            convexHull.push(inputPoints[i]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}