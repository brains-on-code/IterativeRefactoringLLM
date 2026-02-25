package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The MidpointEllipse class implements the Midpoint Ellipse Drawing Algorithm.
 * This algorithm efficiently computes the points on an ellipse by dividing it into two regions
 * and using decision parameters to determine the next point to plot.
 */
public final class MidpointEllipse {

    private MidpointEllipse() {
        // Private constructor to prevent instantiation
    }

    /**
     * Draws an ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param centerX the x-coordinate of the center of the ellipse
     * @param centerY the y-coordinate of the center of the ellipse
     * @param a       the length of the semi-major axis (horizontal radius)
     * @param b       the length of the semi-minor axis (vertical radius)
     * @return a list of points (represented as int arrays) that form the ellipse
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (a == 0) {
            addVerticalLine(points, centerX, centerY, b);
            return points;
        }

        if (b == 0) {
            addHorizontalLine(points, centerX, centerY, a);
            return points;
        }

        computeEllipsePoints(points, centerX, centerY, a, b);
        return points;
    }

    private static void addVerticalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        for (int y = centerY - halfLength; y <= centerY + halfLength; y++) {
            points.add(new int[] {centerX, y});
        }
    }

    private static void addHorizontalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        for (int x = centerX - halfLength; x <= centerX + halfLength; x++) {
            points.add(new int[] {x, centerY});
        }
    }

    /**
     * Computes points of a non-degenerate ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param points   the list to which points will be added
     * @param centerX  the x-coordinate of the center of the ellipse
     * @param centerY  the y-coordinate of the center of the ellipse
     * @param a        the length of the semi-major axis (horizontal radius)
     * @param b        the length of the semi-minor axis (vertical radius)
     */
    private static void computeEllipsePoints(Collection<int[]> points, int centerX, int centerY, int a, int b) {
        int x = 0;
        int y = b;

        int a2 = a * a;
        int b2 = b * b;

        double dx = 2.0 * b2 * x;
        double dy = 2.0 * a2 * y;

        double d1 = b2 - (a2 * b) + (0.25 * a2);

        // Region 1: slope < 1
        while (dx < dy) {
            addEllipseSymmetricPoints(points, centerX, centerY, x, y);

            if (d1 < 0) {
                x++;
                dx += 2 * b2;
                d1 += dx + b2;
            } else {
                x++;
                y--;
                dx += 2 * b2;
                dy -= 2 * a2;
                d1 += dx - dy + b2;
            }
        }

        // Region 2: slope >= 1
        double d2 =
            b2 * (x + 0.5) * (x + 0.5)
                + a2 * (y - 1) * (y - 1)
                - a2 * b2;

        while (y >= 0) {
            addEllipseSymmetricPoints(points, centerX, centerY, x, y);

            if (d2 > 0) {
                y--;
                dy -= 2 * a2;
                d2 += a2 - dy;
            } else {
                y--;
                x++;
                dx += 2 * b2;
                dy -= 2 * a2;
                d2 += dx - dy + a2;
            }
        }
    }

    /**
     * Adds points for all four quadrants of the ellipse based on symmetry.
     *
     * @param points   the list to which points will be added
     * @param centerX  the x-coordinate of the center of the ellipse
     * @param centerY  the y-coordinate of the center of the ellipse
     * @param x        the x-coordinate relative to the center
     * @param y        the y-coordinate relative to the center
     */
    private static void addEllipseSymmetricPoints(Collection<int[]> points, int centerX, int centerY, int x, int y) {
        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}