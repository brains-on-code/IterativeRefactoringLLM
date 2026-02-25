package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for generating integer points on a circle
 * using the Midpoint Circle Algorithm.
 */
public final class CirclePointsGenerator {

    private CirclePointsGenerator() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates integer points on a circle centered at (centerX, centerY)
     * with the given radius using the Midpoint Circle Algorithm.
     *
     * @param centerX the x-coordinate of the circle center
     * @param centerY the y-coordinate of the circle center
     * @param radius  the radius of the circle (non-negative)
     * @return a list of points on the circle; each point is an int[2] = {x, y}
     */
    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> points = new ArrayList<>();

        if (radius == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        int x = radius;
        int y = 0;
        int decision = 1 - radius;

        addSymmetricPoints(points, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decision <= 0) {
                decision += 2 * y + 1;
            } else {
                x--;
                decision += 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    /**
     * Adds the eight points that are symmetric to (offsetX, offsetY)
     * around the center (centerX, centerY).
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
        int xPlusOffsetX = centerX + offsetX;
        int xMinusOffsetX = centerX - offsetX;
        int yPlusOffsetY = centerY + offsetY;
        int yMinusOffsetY = centerY - offsetY;

        int xPlusOffsetY = centerX + offsetY;
        int xMinusOffsetY = centerX - offsetY;
        int yPlusOffsetX = centerY + offsetX;
        int yMinusOffsetX = centerY - offsetX;

        points.add(new int[] {xPlusOffsetX, yPlusOffsetY});   // ( +x, +y)
        points.add(new int[] {xMinusOffsetX, yPlusOffsetY});  // ( -x, +y)
        points.add(new int[] {xPlusOffsetX, yMinusOffsetY});  // ( +x, -y)
        points.add(new int[] {xMinusOffsetX, yMinusOffsetY}); // ( -x, -y)

        points.add(new int[] {xPlusOffsetY, yPlusOffsetX});   // ( +y, +x)
        points.add(new int[] {xMinusOffsetY, yPlusOffsetX});  // ( -y, +x)
        points.add(new int[] {xPlusOffsetY, yMinusOffsetX});  // ( +y, -x)
        points.add(new int[] {xMinusOffsetY, yMinusOffsetX}); // ( -y, -x)
    }
}