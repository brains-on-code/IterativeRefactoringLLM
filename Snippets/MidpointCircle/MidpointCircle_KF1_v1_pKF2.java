package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for generating the integer points of a circle
 * using the Midpoint Circle Algorithm.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the integer points on a circle centered at (centerX, centerY)
     * with the given radius using the Midpoint Circle Algorithm.
     *
     * @param centerX the x-coordinate of the circle center
     * @param centerY the y-coordinate of the circle center
     * @param radius  the radius of the circle (non-negative)
     * @return a list of points on the circle; each point is an int[2] = {x, y}
     */
    public static List<int[]> method1(int centerX, int centerY, int radius) {
        List<int[]> points = new ArrayList<>();

        // Degenerate case: radius 0 -> only the center point.
        if (radius == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        int x = radius;
        int y = 0;

        // Decision parameter for the Midpoint Circle Algorithm.
        int decision = 1 - radius;

        // Add the initial set of symmetric points.
        addSymmetricPoints(points, centerX, centerY, x, y);

        // Iterate over the first octant and mirror points to other octants.
        while (x > y) {
            y++;

            if (decision <= 0) {
                // Move vertically.
                decision = decision + 2 * y + 1;
            } else {
                // Move diagonally.
                x--;
                decision = decision + 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    /**
     * Adds the eight symmetric points of (offsetX, offsetY) around the center
     * (centerX, centerY) for a circle.
     *
     * @param points  collection to which the points are added
     * @param centerX x-coordinate of the circle center
     * @param centerY y-coordinate of the circle center
     * @param offsetX x-offset from the center
     * @param offsetY y-offset from the center
     */
    private static void addSymmetricPoints(
            Collection<int[]> points,
            int centerX,
            int centerY,
            int offsetX,
            int offsetY
    ) {
        points.add(new int[] {centerX + offsetX, centerY + offsetY});
        points.add(new int[] {centerX - offsetX, centerY + offsetY});
        points.add(new int[] {centerX + offsetX, centerY - offsetY});
        points.add(new int[] {centerX - offsetX, centerY - offsetY});
        points.add(new int[] {centerX + offsetY, centerY + offsetX});
        points.add(new int[] {centerX - offsetY, centerY + offsetX});
        points.add(new int[] {centerX + offsetY, centerY - offsetX});
        points.add(new int[] {centerX - offsetY, centerY - offsetX});
    }
}