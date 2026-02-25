package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for ellipse point generation.
 */
public final class EllipsePoints {

    private EllipsePoints() {
        // Utility class; prevent instantiation.
    }

    /**
     * Generates integer points on an ellipse centered at (centerX, centerY)
     * with radii radiusX and radiusY using the midpoint ellipse algorithm.
     */
    public static List<int[]> generateEllipsePoints(int centerX, int centerY, int radiusX, int radiusY) {
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

        computeMidpointEllipse(points, centerX, centerY, radiusX, radiusY);

        return points;
    }

    /**
     * Midpoint ellipse algorithm implementation.
     */
    private static void computeMidpointEllipse(
            Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {

        int x = 0;
        int y = radiusY;

        double decisionParamRegion1 =
                (radiusY * radiusY)
                        - (radiusX * radiusX * radiusY)
                        + (0.25 * radiusX * radiusX);
        double dx = 2.0 * radiusY * radiusY * x;
        double dy = 2.0 * radiusX * radiusX * y;

        while (dx < dy) {
            addSymmetricPoints(points, centerX, centerY, x, y);

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

        double decisionParamRegion2 =
                radiusY * radiusY * (x + 0.5) * (x + 0.5)
                        + radiusX * radiusX * (y - 1) * (y - 1)
                        - radiusX * radiusX * radiusY * radiusY;

        while (y >= 0) {
            addSymmetricPoints(points, centerX, centerY, x, y);

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
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int offsetX, int offsetY) {

        points.add(new int[] {centerX + offsetX, centerY + offsetY});
        points.add(new int[] {centerX - offsetX, centerY + offsetY});
        points.add(new int[] {centerX + offsetX, centerY - offsetY});
        points.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}