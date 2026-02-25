package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for circle-related geometry operations.
 */
public final class CirclePointsGenerator {

    private CirclePointsGenerator() {
        // Utility class; prevent instantiation.
    }

    /**
     * Generates the integer-coordinate points on the circumference of a circle
     * using the midpoint circle algorithm.
     *
     * @param centerX the x-coordinate of the circle center
     * @param centerY the y-coordinate of the circle center
     * @param radius  the radius of the circle
     * @return a list of points on the circle, each represented as an int[2] = {x, y}
     */
    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> circlePoints = new ArrayList<>();

        if (radius == 0) {
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
                decisionParameter = decisionParameter + 2 * y + 1;
            } else {
                x--;
                decisionParameter = decisionParameter + 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, x, y);
        }

        return circlePoints;
    }

    /**
     * Adds the eight symmetric points of (x, y) around the circle center.
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