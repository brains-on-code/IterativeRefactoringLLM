package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointCircle {

    private MidpointCircle() {
        // Prevent instantiation
    }

    /**
     * Generates integer points on the circumference of a circle using the
     * midpoint circle algorithm.
     *
     * @param centerX x-coordinate of the circle center
     * @param centerY y-coordinate of the circle center
     * @param radius  radius of the circle (non-negative)
     * @return list of points on the circle as [x, y] integer pairs
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
        int decisionParam = 1 - radius;

        addSymmetricPoints(points, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decisionParam <= 0) {
                decisionParam += 2 * y + 1;
            } else {
                x--;
                decisionParam += 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    /**
     * Adds the eight points symmetric to (x, y) around the circle center.
     *
     * @param points  collection to which the points are added
     * @param centerX x-coordinate of the circle center
     * @param centerY y-coordinate of the circle center
     * @param x       x-offset from the center
     * @param y       y-offset from the center
     */
    private static void addSymmetricPoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int x,
        int y
    ) {
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