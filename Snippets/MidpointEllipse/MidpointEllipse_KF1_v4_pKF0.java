package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for ellipse point generation.
 */
public final class EllipsePointGenerator {

    private EllipsePointGenerator() {
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
    public static List<int[]> generateEllipse(int centerX, int centerY, int radiusX, int radiusY) {
        List<int[]> points = new ArrayList<>();

        if (radiusX == 0 && radiusY == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (radiusX == 0) {
            addVerticalLine(points, centerX, centerY, radiusY);
            return points;
        }

        if (radiusY == 0) {
            addHorizontalLine(points, centerX, centerY, radiusX);
            return points;
        }

        generateEllipsePoints(points, centerX, centerY, radiusX, radiusY);
        return points;
    }

    private static void addVerticalLine(Collection<int[]> points, int centerX, int centerY, int radiusY) {
        int startY = centerY - radiusY;
        int endY = centerY + radiusY;
        for (int y = startY; y <= endY; y++) {
            points.add(new int[] {centerX, y});
        }
    }

    private static void addHorizontalLine(Collection<int[]> points, int centerX, int centerY, int radiusX) {
        int startX = centerX - radiusX;
        int endX = centerX + radiusX;
        for (int x = startX; x <= endX; x++) {
            points.add(new int[] {x, centerY});
        }
    }

    /**
     * Midpoint ellipse algorithm implementation.
     */
    private static void generateEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {

        int x = 0;
        int y = radiusY;

        int radiusXSq = radiusX * radiusX;
        int radiusYSq = radiusY * radiusY;

        double dx = 0.0;
        double dy = 2.0 * radiusXSq * y;
        double decisionParam =
                radiusYSq
                        - (radiusXSq * radiusY)
                        + (0.25 * radiusXSq);

        // Region 1
        while (dx < dy) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParam < 0) {
                x++;
                dx += 2.0 * radiusYSq;
                decisionParam += dx + radiusYSq;
            } else {
                x++;
                y--;
                dx += 2.0 * radiusYSq;
                dy -= 2.0 * radiusXSq;
                decisionParam += dx - dy + radiusYSq;
            }
        }

        // Region 2
        decisionParam =
                radiusYSq * (x + 0.5) * (x + 0.5)
                        + radiusXSq * (y - 1) * (y - 1)
                        - radiusXSq * radiusYSq;

        while (y >= 0) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParam > 0) {
                y--;
                dy -= 2.0 * radiusXSq;
                decisionParam += radiusXSq - dy;
            } else {
                y--;
                x++;
                dx += 2.0 * radiusYSq;
                dy -= 2.0 * radiusXSq;
                decisionParam += dx - dy + radiusXSq;
            }
        }
    }

    /**
     * Adds the four symmetric points of (centerX ± x, centerY ± y).
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        int xPlus = centerX + x;
        int xMinus = centerX - x;
        int yPlus = centerY + y;
        int yMinus = centerY - y;

        points.add(new int[] {xPlus, yPlus});
        points.add(new int[] {xMinus, yPlus});
        points.add(new int[] {xPlus, yMinus});
        points.add(new int[] {xMinus, yMinus});
    }
}