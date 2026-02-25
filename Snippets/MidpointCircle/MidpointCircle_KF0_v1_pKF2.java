package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implements the Midpoint Circle Algorithm.
 * This algorithm calculates integer-coordinate points on the circumference
 * of a circle using only integer arithmetic.
 */
public final class MidpointCircle {

    private MidpointCircle() {
        // Prevent instantiation
    }

    /**
     * Generates integer-coordinate points on the circumference of a circle
     * using the midpoint circle algorithm.
     *
     * @param centerX x-coordinate of the circle's center
     * @param centerY y-coordinate of the circle's center
     * @param radius  radius of the circle (non-negative)
     * @return list of points on the circle; each point is an int[2] = {x, y}
     */
    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> points = new ArrayList<>();

        if (radius == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        int x = radius;
        int y = 0;

        // Initial decision parameter
        int decision = 1 - radius;

        addSymmetricPoints(points, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decision <= 0) {
                // Midpoint is inside or on the circle
                decision += 2 * y + 1;
            } else {
                // Midpoint is outside the circle
                x--;
                decision += 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    /**
     * Adds the eight symmetric points corresponding to (x, y) for a circle
     * centered at (centerX, centerY).
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
        points.add(new int[] {centerX + y, centerY + x});
        points.add(new int[] {centerX - y, centerY + x});
        points.add(new int[] {centerX + y, centerY - x});
        points.add(new int[] {centerX - y, centerY - x});
    }
}