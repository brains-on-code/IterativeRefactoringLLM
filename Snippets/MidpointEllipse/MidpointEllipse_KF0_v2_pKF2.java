package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Midpoint Ellipse Drawing Algorithm.
 *
 * <p>Computes integer points approximating an ellipse centered at
 * ({@code centerX}, {@code centerY}) with horizontal radius {@code a}
 * and vertical radius {@code b}. Degenerate cases (point or line) are
 * handled explicitly.
 */
public final class MidpointEllipse {

    private MidpointEllipse() {
        // Prevent instantiation.
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

        // Fully degenerate: single point.
        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        // Degenerate: vertical line.
        if (a == 0) {
            for (int y = centerY - b; y <= centerY + b; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        // Degenerate: horizontal line.
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
     * <p>The algorithm is evaluated in two regions:
     * <ul>
     *   <li>Region 1: slope &lt; 1 (iterate while {@code dx < dy})</li>
     *   <li>Region 2: slope ≥ 1 (iterate while {@code y >= 0})</li>
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

        // Decision parameter for Region 1.
        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);

        // Region 1: slope < 1.
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

        // Decision parameter for Region 2.
        double d2 =
                b * b * (x + 0.5) * (x + 0.5)
                        + a * a * (y - 1) * (y - 1)
                        - a * a * b * b;

        // Region 2: slope ≥ 1.
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
     * Adds the four symmetric points:
     * ({@code centerX ± x}, {@code centerY ± y}).
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