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

        int cxPlusX = centerX + x;
        int cxMinusX = centerX - x;
        int cxPlusY = centerX + y;
        int cxMinusY = centerX - y;

        int cyPlusY = centerY + y;
        int cyMinusY = centerY - y;
        int cyPlusX = centerY + x;
        int cyMinusX = centerY - x;

        points.add(new int[] {cxPlusX, cyPlusY});
        points.add(new int[] {cxMinusX, cyPlusY});
        points.add(new int[] {cxPlusX, cyMinusY});
        points.add(new int[] {cxMinusX, cyMinusY});
        points.add(new int[] {cxPlusY, cyPlusX});
        points.add(new int[] {cxMinusY, cyPlusX});
        points.add(new int[] {cxPlusY, cyMinusX});
        points.add(new int[] {cxMinusY, cyMinusX});
    }
}