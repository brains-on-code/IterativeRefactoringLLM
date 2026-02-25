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
        // Private Constructor to prevent instantiation.
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
        List<int[]> circlePoints = new ArrayList<>();

        if (radius == 0) {
            circlePoints.add(new int[] {centerX, centerY});
            return circlePoints;
        }

        int currentX = radius;
        int currentY = 0;

        int decisionParameter = 1 - radius;

        addSymmetricPoints(circlePoints, centerX, centerY, currentX, currentY);

        while (currentX > currentY) {
            currentY++;

            if (decisionParameter <= 0) {
                decisionParameter = decisionParameter + 2 * currentY + 1;
            } else {
                currentX--;
                decisionParameter = decisionParameter + 2 * currentY - 2 * currentX + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, currentX, currentY);
        }

        return circlePoints;
    }

    /**
     * Adds the symmetric points in all octants of the circle based on the current x and y offsets.
     *
     * @param points   The collection to which symmetric points will be added.
     * @param centerX  The x-coordinate of the circle's center.
     * @param centerY  The y-coordinate of the circle's center.
     * @param xOffset  The current x-offset from the center on the circumference.
     * @param yOffset  The current y-offset from the center on the circumference.
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int xOffset, int yOffset) {

        points.add(new int[] {centerX + xOffset, centerY + yOffset});
        points.add(new int[] {centerX - xOffset, centerY + yOffset});
        points.add(new int[] {centerX + xOffset, centerY - yOffset});
        points.add(new int[] {centerX - xOffset, centerY - yOffset});
        points.add(new int[] {centerX + yOffset, centerY + xOffset});
        points.add(new int[] {centerX - yOffset, centerY + xOffset});
        points.add(new int[] {centerX + yOffset, centerY - xOffset});
        points.add(new int[] {centerX - yOffset, centerY - xOffset});
    }
}