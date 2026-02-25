package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for generating points on an ellipse using the midpoint ellipse algorithm.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Generates integer points on the boundary of an axis-aligned ellipse centered at (centerX, centerY)
     * with horizontal radius radiusX and vertical radius radiusY.
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param radiusX horizontal radius (semi-major or semi-minor axis)
     * @param radiusY vertical radius (semi-major or semi-minor axis)
     * @return list of integer coordinate pairs lying on the ellipse boundary
     */
    public static List<int[]> method1(int centerX, int centerY, int radiusX, int radiusY) {
        List<int[]> points = new ArrayList<>();

        // Degenerate case: single point
        if (radiusX == 0 && radiusY == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        // Degenerate case: vertical line segment
        if (radiusX == 0) {
            for (int y = centerY - radiusY; y <= centerY + radiusY; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        // Degenerate case: horizontal line segment
        if (radiusY == 0) {
            for (int x = centerX - radiusX; x <= centerX + radiusX; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        // General ellipse
        method2(points, centerX, centerY, radiusX, radiusY);

        return points;
    }

    /**
     * Midpoint ellipse algorithm implementation.
     *
     * @param points  collection to store generated points
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param radiusX horizontal radius
     * @param radiusY vertical radius
     */
    private static void method2(Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {
        int x = 0;
        int y = radiusY;

        // Region 1 decision parameters
        double decisionParamRegion1 =
                (radiusY * radiusY)
                        - (radiusX * radiusX * radiusY)
                        + (0.25 * radiusX * radiusX);
        double dx = 2.0 * radiusY * radiusY * x;
        double dy = 2.0 * radiusX * radiusX * y;

        // Region 1: slope magnitude > 1
        while (dx < dy) {
            method3(points, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                dx += 2 * radiusY * radiusY;
                decisionParamRegion1 += dx + (radiusY * radiusY);
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                decisionParamRegion1 += dx - dy + (radiusY * radiusY);
            }
        }

        // Region 2 decision parameter
        double decisionParamRegion2 =
                radiusY * radiusY * (x + 0.5) * (x + 0.5)
                        + radiusX * radiusX * (y - 1) * (y - 1)
                        - radiusX * radiusX * radiusY * radiusY;

        // Region 2: slope magnitude <= 1
        while (y >= 0) {
            method3(points, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                dy -= 2 * radiusX * radiusX;
                decisionParamRegion2 += (radiusX * radiusX) - dy;
            } else {
                y--;
                x++;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                decisionParamRegion2 += dx - dy + (radiusX * radiusX);
            }
        }
    }

    /**
     * Adds the four symmetric points of (offsetX, offsetY) around the center.
     *
     * @param points  collection to store generated points
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param offsetX x-offset from the center
     * @param offsetY y-offset from the center
     */
    private static void method3(Collection<int[]> points, int centerX, int centerY, int offsetX, int offsetY) {
        points.add(new int[] {centerX + offsetX, centerY + offsetY});
        points.add(new int[] {centerX - offsetX, centerY + offsetY});
        points.add(new int[] {centerX + offsetX, centerY - offsetY});
        points.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}