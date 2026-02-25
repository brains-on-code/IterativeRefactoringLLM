package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for ellipse point generation.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Generates integer points on an ellipse centered at (centerX, centerY)
     * with radii radiusX and radiusY using the midpoint ellipse algorithm.
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param radiusX radius along the x-axis
     * @param radiusY radius along the y-axis
     * @return list of integer coordinate pairs on the ellipse
     */
    public static List<int[]> method1(int centerX, int centerY, int radiusX, int radiusY) {
        List<int[]> points = new ArrayList<>();

        if (radiusX == 0 && radiusY == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (radiusX == 0) {
            for (int y = centerY - radiusY; y <= centerY + radiusY; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        if (radiusY == 0) {
            for (int x = centerX - radiusX; x <= centerX + radiusX; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        generateEllipsePoints(points, centerX, centerY, radiusX, radiusY);
        return points;
    }

    /**
     * Midpoint ellipse algorithm implementation.
     */
    private static void generateEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {

        int x = 0;
        int y = radiusY;

        double dx = 2.0 * radiusY * radiusY * x;
        double dy = 2.0 * radiusX * radiusX * y;
        double decisionParam =
                (radiusY * radiusY)
                        - (radiusX * radiusX * radiusY)
                        + (0.25 * radiusX * radiusX);

        // Region 1
        while (dx < dy) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParam < 0) {
                x++;
                dx += 2 * radiusY * radiusY;
                decisionParam += dx + (radiusY * radiusY);
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                decisionParam += dx - dy + (radiusY * radiusY);
            }
        }

        // Region 2
        decisionParam =
                radiusY * radiusY * (x + 0.5) * (x + 0.5)
                        + radiusX * radiusX * (y - 1) * (y - 1)
                        - radiusX * radiusX * radiusY * radiusY;

        while (y >= 0) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParam > 0) {
                y--;
                dy -= 2 * radiusX * radiusX;
                decisionParam += (radiusX * radiusX) - dy;
            } else {
                y--;
                x++;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                decisionParam += dx - dy + (radiusX * radiusX);
            }
        }
    }

    /**
     * Adds the four symmetric points of (centerX ± x, centerY ± y).
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}