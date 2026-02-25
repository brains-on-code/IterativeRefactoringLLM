package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class GrahamScan {

    private final Stack<Point> hull = new Stack<>();

    public GrahamScan(Point[] points) {
        Arrays.sort(points);
        Arrays.sort(points, 1, points.length, points[0].polarOrder());

        hull.push(points[0]);

        int firstNonEqualIndex;
        for (firstNonEqualIndex = 1; firstNonEqualIndex < points.length; firstNonEqualIndex++) {
            if (!points[0].equals(points[firstNonEqualIndex])) {
                break;
            }
        }

        if (firstNonEqualIndex == points.length) {
            return;
        }

        int firstNonCollinearIndex;
        for (firstNonCollinearIndex = firstNonEqualIndex + 1; firstNonCollinearIndex < points.length; firstNonCollinearIndex++) {
            if (Point.orientation(points[0], points[firstNonEqualIndex], points[firstNonCollinearIndex]) != 0) {
                break;
            }
        }

        hull.push(points[firstNonCollinearIndex - 1]);

        for (int i = firstNonCollinearIndex; i < points.length; i++) {
            Point top = hull.pop();
            while (Point.orientation(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }


    public Iterable<Point> hull() {
        return new ArrayList<>(hull);
    }
}
