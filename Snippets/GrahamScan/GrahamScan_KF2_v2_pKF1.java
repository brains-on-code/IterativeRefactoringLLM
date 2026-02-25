package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GrahamScan {

    private final Stack<Point> convexHull = new Stack<>();

    public GrahamScan(Point[] points) {
        if (points == null || points.length == 0) {
            return;
        }

        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        convexHull.push(points[0]);

        int firstUniquePointIndex;
        for (firstUniquePointIndex = 1; firstUniquePointIndex < points.length; firstUniquePointIndex++) {
            if (!points[0].equals(points[firstUniquePointIndex])) {
                break;
            }
        }

        if (firstUniquePointIndex == points.length) {
            return;
        }

        int firstNonCollinearPointIndex;
        for (firstNonCollinearPointIndex = firstUniquePointIndex + 1;
             firstNonCollinearPointIndex < points.length;
             firstNonCollinearPointIndex++) {

            if (Point.orientation(points[0],
                                  points[firstUniquePointIndex],
                                  points[firstNonCollinearPointIndex]) != 0) {
                break;
            }
        }

        convexHull.push(points[firstNonCollinearPointIndex - 1]);

        for (int currentPointIndex = firstNonCollinearPointIndex; currentPointIndex < points.length; currentPointIndex++) {
            Point lastHullPoint = convexHull.pop();
            while (Point.orientation(convexHull.peek(), lastHullPoint, points[currentPointIndex]) <= 0) {
                lastHullPoint = convexHull.pop();
            }
            convexHull.push(lastHullPoint);
            convexHull.push(points[currentPointIndex]);
        }
    }

    public Iterable<Point> hull() {
        return new ArrayList<>(convexHull);
    }
}