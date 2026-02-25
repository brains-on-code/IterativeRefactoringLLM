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
        int decision = 1 - radius;

        addSymmetricPoints(circlePoints, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decision <= 0) {
                decision += 2 * y + 1;
            } else {
                x--;
                decision += 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, x, y);
        }

        return circlePoints;
    }

    /**
     * Adds the eight symmetric points of (offsetX, offsetY) around the circle center.
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int offsetX, int offsetY) {

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