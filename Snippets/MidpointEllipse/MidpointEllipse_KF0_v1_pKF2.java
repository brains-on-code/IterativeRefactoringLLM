package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implements the Midpoint Ellipse Drawing Algorithm.
 *
 * <p>The algorithm computes the set of integer points that approximate an ellipse
 * centered at (centerX, centerY) with horizontal radius {@code a} and vertical
 * radius {@code b}. Degenerate cases (lines and a single point) are handled
 * explicitly.
 */
public final class MidpointEllipse {

    private MidpointEllipse() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns the points of an ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param a       horizontal radius (semi-major axis)
     * @param b       vertical radius (semi-minor axis)
     * @return list of points, each represented as {@code int[]{x, y}}
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        // Single point (fully degenerate).
        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        // Vertical line (a == 0).
        if (a == 0) {
            for (int y = centerY - b; y <= centerY + b; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        // Horizontal line (b == 0).
        if (b == 0) {
            for (int x = centerX - a; x <= centerX + a; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        // Proper ellipse.
        computeEllipsePoints(points, centerX, centerY, a, b);
        return points;
    }

    /**
     * Computes points of a non-degenerate ellipse using the Midpoint Ellipse Algorithm.
     *
     * <p>The algorithm is split into two regions:
     * <ul>
     *   <li>Region 1: slope &lt; 1</li>
     *   <li>Region 2: slope ≥ 1</li>
     * </ul>
     *
     * @param points   collection to which computed points are added
     * @param centerX  x-coordinate of the ellipse center
     * @param centerY  y-coordinate of the ellipse center
     * @param a        horizontal radius
     * @param b        vertical radius
     */
    private static void computeEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int a, int b) {

        int x = 0;
        int y = b;

        double dx = 2.0 * b * b * x;
        double dy = 2.0 * a * a * y;

        // Initial decision parameter for Region 1 (slope < 1).
        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);

        // Region 1: iterate while slope < 1 (dx < dy).
        while (dx < dy) {
            addEllipsePoints(points, centerX, centerY, x, y);

            if (d1 < 0) {
                x++;
                dx += 2.0 * b * b;
                d1 += dx + (b * b);
            } else {
                x++;
                y--;
                dx += 2.0 * b * b;
                dy -= 2.0 * a * a;
                d1 += dx - dy + (b * b);
            }
        }

        // Initial decision parameter for Region 2 (slope ≥ 1).
        double d2 =
                b * b * (x + 0.5) * (x + 0.5)
                        + a * a * (y - 1) * (y - 1)
                        - a * a * b * b;

        // Region 2: iterate while y ≥ 0.
        while (y >= 0) {
            addEllipsePoints(points, centerX, centerY, x, y);

            if (d2 > 0) {
                y--;
                dy -= 2.0 * a * a;
                d2 += (a * a) - dy;
            } else {
                y--;
                x++;
                dx += 2.0 * b * b;
                dy -= 2.0 * a * a;
                d2 += dx - dy + (a * a);
            }
        }
    }

    /**
     * Adds the four symmetric points of (centerX ± x, centerY ± y).
     *
     * @param points   collection to which points are added
     * @param centerX  x-coordinate of the ellipse center
     * @param centerY  y-coordinate of the ellipse center
     * @param x        x-offset from the center
     * @param y        y-offset from the center
     */
    private static void addEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}