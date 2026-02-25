package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointEllipse {

    private MidpointEllipse() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes integer points approximating an ellipse using the midpoint ellipse algorithm.
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param a       semi-major axis length (horizontal radius)
     * @param b       semi-minor axis length (vertical radius)
     * @return list of points on the ellipse, each as an int[2] = {x, y}
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        // Degenerate case: single point
        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        // Degenerate case: vertical line (radius only in y)
        if (a == 0) {
            for (int y = centerY - b; y <= centerY + b; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        // Degenerate case: horizontal line (radius only in x)
        if (b == 0) {
            for (int x = centerX - a; x <= centerX + a; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        computeEllipsePoints(points, centerX, centerY, a, b);
        return points;
    }

    /**
     * Computes ellipse points in all four quadrants using the midpoint ellipse algorithm.
     */
    private static void computeEllipsePoints(
        Collection<int[]> points, int centerX, int centerY, int a, int b
    ) {
        int x = 0;
        int y = b;

        double a2 = a * a;
        double b2 = b * b;

        double dx = 2.0 * b2 * x;
        double dy = 2.0 * a2 * y;

        // Initial decision parameter for Region 1 (|slope| < 1)
        double decisionParamRegion1 = b2 - (a2 * b) + (0.25 * a2);

        // Region 1: iterate while the slope magnitude is less than 1
        while (dx < dy) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                dx += 2 * b2;
                decisionParamRegion1 += dx + b2;
            } else {
                x++;
                y--;
                dx += 2 * b2;
                dy -= 2 * a2;
                decisionParamRegion1 += dx - dy + b2;
            }
        }

        // Initial decision parameter for Region 2 (|slope| >= 1)
        double decisionParamRegion2 =
            b2 * (x + 0.5) * (x + 0.5)
                + a2 * (y - 1) * (y - 1)
                - a2 * b2;

        // Region 2: iterate while y is non-negative
        while (y >= 0) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                dy -= 2 * a2;
                decisionParamRegion2 += a2 - dy;
            } else {
                y--;
                x++;
                dx += 2 * b2;
                dy -= 2 * a2;
                decisionParamRegion2 += dx - dy + a2;
            }
        }
    }

    /**
     * Adds the four symmetric points around the center:
     * (centerX ± x, centerY ± y).
     */
    private static void addSymmetricEllipsePoints(
        Collection<int[]> points, int centerX, int centerY, int x, int y
    ) {
        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}