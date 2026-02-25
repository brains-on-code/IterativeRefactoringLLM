package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointEllipse {

    private MidpointEllipse() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the set of integer points that approximate an ellipse using the
     * midpoint ellipse algorithm.
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param a       semi-major axis length (horizontal radius)
     * @param b       semi-minor axis length (vertical radius)
     * @return list of points on the ellipse, each as an int[2] = {x, y}
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        // Degenerate case: ellipse reduces to a single point
        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        // Degenerate case: ellipse reduces to a vertical line segment
        if (a == 0) {
            for (int y = centerY - b; y <= centerY + b; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        // Degenerate case: ellipse reduces to a horizontal line segment
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
     * Uses the midpoint ellipse algorithm to compute points in all four
     * quadrants of the ellipse centered at (centerX, centerY).
     */
    private static void computeEllipsePoints(Collection<int[]> points, int centerX, int centerY, int a, int b) {
        int x = 0;
        int y = b;

        // Region 1 initial decision parameter and derivatives
        double decisionParamRegion1 = (b * b) - (a * a * b) + (0.25 * a * a);
        double dx = 2.0 * b * b * x;
        double dy = 2.0 * a * a * y;

        // Region 1: slope magnitude < 1
        while (dx < dy) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                dx += 2 * b * b;
                decisionParamRegion1 += dx + (b * b);
            } else {
                x++;
                y--;
                dx += 2 * b * b;
                dy -= 2 * a * a;
                decisionParamRegion1 += dx - dy + (b * b);
            }
        }

        // Region 2 initial decision parameter
        double decisionParamRegion2 =
            b * b * (x + 0.5) * (x + 0.5)
                + a * a * (y - 1) * (y - 1)
                - a * a * b * b;

        // Region 2: slope magnitude >= 1
        while (y >= 0) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                dy -= 2 * a * a;
                decisionParamRegion2 += (a * a) - dy;
            } else {
                y--;
                x++;
                dx += 2 * b * b;
                dy -= 2 * a * a;
                decisionParamRegion2 += dx - dy + (a * a);
            }
        }
    }

    /**
     * Adds the four symmetric points of (centerX ± x, centerY ± y) to the
     * collection, exploiting ellipse symmetry across both axes.
     */
    private static void addSymmetricEllipsePoints(Collection<int[]> points, int centerX, int centerY, int x, int y) {
        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}