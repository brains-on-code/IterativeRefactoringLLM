package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to represent the Midpoint Circle Algorithm.
 * This algorithm calculates points on the circumference of a circle
 * using integer arithmetic for efficient computation.
 */
public final class MidpointCircle {

    private MidpointCircle() {
        // Prevent instantiation
    }

    /**
     * Generates points on the circumference of a circle using the midpoint circle algorithm.
     *
     * @param centerX The x-coordinate of the circle's center.
     * @param centerY The y-coordinate of the circle's center.
     * @param radius  The radius of the circle.
     * @return A list of points on the circle, each represented as an int[] with 2 elements [x, y].
     */
    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> points = new ArrayList<>();

        if (radius == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        int x = radius;
        int y = 0;
        int decisionParam = 1 - radius;

        addSymmetricPoints(points, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decisionParam <= 0) {
                decisionParam += 2 * y + 1;
            } else {
                x--;
                decisionParam += 2 * (y - x) + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    /**
     * Adds the symmetric points in all octants of the circle based on the current x and y values.
     *
     * @param points  The list to which symmetric points will be added.
     * @param centerX The x-coordinate of the circle's center.
     * @param centerY The y-coordinate of the circle's center.
     * @param x       The current x-coordinate on the circumference.
     * @param y       The current y-coordinate on the circumference.
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        int xPlusCenterX = centerX + x;
        int xMinusCenterX = centerX - x;
        int yPlusCenterY = centerY + y;
        int yMinusCenterY = centerY - y;

        int yPlusCenterX = centerX + y;
        int yMinusCenterX = centerX - y;
        int xPlusCenterY = centerY + x;
        int xMinusCenterY = centerY - x;

        points.add(new int[] {xPlusCenterX, yPlusCenterY});
        points.add(new int[] {xMinusCenterX, yPlusCenterY});
        points.add(new int[] {xPlusCenterX, yMinusCenterY});
        points.add(new int[] {xMinusCenterX, yMinusCenterY});
        points.add(new int[] {yPlusCenterX, xPlusCenterY});
        points.add(new int[] {yMinusCenterX, xPlusCenterY});
        points.add(new int[] {yPlusCenterX, xMinusCenterY});
        points.add(new int[] {yMinusCenterX, xMinusCenterY});
    }
}