package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for geometry-related operations.
 */
public final class CirclePointsGenerator {

    private CirclePointsGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates the integer points of a circle using the midpoint circle algorithm.
     *
     * @param centerX the x-coordinate of the circle center
     * @param centerY the y-coordinate of the circle center
     * @param radius  the radius of the circle
     * @return a list of points on the circle, each represented as an int[2] = {x, y}
     */
    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> circlePoints = new ArrayList<>();

        if (radius <= 0) {
            circlePoints.add(new int[] {centerX, centerY});
            return circlePoints;
        }

        int x = radius;
        int y = 0;
        int decisionParameter = 1 - radius;

        addSymmetricPoints(circlePoints, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decisionParameter <= 0) {
                decisionParameter += 2 * y + 1;
            } else {
                x--;
                decisionParameter += 2 * (y - x) + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, x, y);
        }

        return circlePoints;
    }

    /**
     * Adds all 8 symmetric points of (x, y) around the center (centerX, centerY).
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        int centerPlusX = centerX + x;
        int centerMinusX = centerX - x;
        int centerPlusY = centerX + y;
        int centerMinusY = centerX - y;

        int centerYPlusY = centerY + y;
        int centerYMinusY = centerY - y;
        int centerYPlusX = centerY + x;
        int centerYMinusX = centerY - x;

        points.add(new int[] {centerPlusX, centerYPlusY});
        points.add(new int[] {centerMinusX, centerYPlusY});
        points.add(new int[] {centerPlusX, centerYMinusY});
        points.add(new int[] {centerMinusX, centerYMinusY});
        points.add(new int[] {centerPlusY, centerYPlusX});
        points.add(new int[] {centerMinusY, centerYPlusX});
        points.add(new int[] {centerPlusY, centerYMinusX});
        points.add(new int[] {centerMinusY, centerYMinusX});
    }
}