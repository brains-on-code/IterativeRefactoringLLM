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
        List<int[]> ellipsePoints = new ArrayList<>();

        if (radiusX == 0 && radiusY == 0) {
            ellipsePoints.add(new int[] {centerX, centerY});
            return ellipsePoints;
        }

        if (radiusX == 0) {
            for (int y = centerY - radiusY; y <= centerY + radiusY; y++) {
                ellipsePoints.add(new int[] {centerX, y});
            }
            return ellipsePoints;
        }

        if (radiusY == 0) {
            for (int x = centerX - radiusX; x <= centerX + radiusX; x++) {
                ellipsePoints.add(new int[] {x, centerY});
            }
            return ellipsePoints;
        }

        computeMidpointEllipse(ellipsePoints, centerX, centerY, radiusX, radiusY);

        return ellipsePoints;
    }

    /**
     * Midpoint ellipse algorithm implementation.
     */
    private static void computeMidpointEllipse(
            Collection<int[]> ellipsePoints, int centerX, int centerY, int radiusX, int radiusY) {

        int currentX = 0;
        int currentY = radiusY;

        int radiusXSquared = radiusX * radiusX;
        int radiusYSquared = radiusY * radiusY;

        double decisionParameterRegion1 =
                radiusYSquared
                        - (radiusXSquared * radiusY)
                        + (0.25 * radiusXSquared);
        double deltaX = 2.0 * radiusYSquared * currentX;
        double deltaY = 2.0 * radiusXSquared * currentY;

        while (deltaX < deltaY) {
            addSymmetricPoints(ellipsePoints, centerX, centerY, currentX, currentY);

            if (decisionParameterRegion1 < 0) {
                currentX++;
                deltaX += 2 * radiusYSquared;
                decisionParameterRegion1 += deltaX + radiusYSquared;
            } else {
                currentX++;
                currentY--;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion1 += deltaX - deltaY + radiusYSquared;
            }
        }

        double decisionParameterRegion2 =
                radiusYSquared * (currentX + 0.5) * (currentX + 0.5)
                        + radiusXSquared * (currentY - 1) * (currentY - 1)
                        - radiusXSquared * radiusYSquared;

        while (currentY >= 0) {
            addSymmetricPoints(ellipsePoints, centerX, centerY, currentX, currentY);

            if (decisionParameterRegion2 > 0) {
                currentY--;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion2 += radiusXSquared - deltaY;
            } else {
                currentY--;
                currentX++;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion2 += deltaX - deltaY + radiusXSquared;
            }
        }
    }

    /**
     * Adds the four symmetric points of (offsetX, offsetY) around the center.
     */
    private static void addSymmetricPoints(
            Collection<int[]> ellipsePoints, int centerX, int centerY, int offsetX, int offsetY) {

        ellipsePoints.add(new int[] {centerX + offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX + offsetX, centerY - offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}