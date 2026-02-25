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

        int xPlus = centerX + x;
        int xMinus = centerX - x;
        int yPlus = centerY + y;
        int yMinus = centerY - y;

        int xPlusY = centerY + x;
        int xMinusY = centerY - x;
        int yPlusX = centerX + y;
        int yMinusX = centerX - y;

        addPoint(points, xPlus, yPlus);
        addPoint(points, xMinus, yPlus);
        addPoint(points, xPlus, yMinus);
        addPoint(points, xMinus, yMinus);
        addPoint(points, yPlusX, xPlusY);
        addPoint(points, yMinusX, xPlusY);
        addPoint(points, yPlusX, xMinusY);
        addPoint(points, yMinusX, xMinusY);
    }

    private static void addPoint(Collection<int[]> points, int x, int y) {
        points.add(new int[] {x, y});
    }
}