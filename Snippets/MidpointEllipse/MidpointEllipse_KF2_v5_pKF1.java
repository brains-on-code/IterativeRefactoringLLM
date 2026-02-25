package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointEllipse {

    private MidpointEllipse() {
    }

    public static List<int[]> drawEllipse(int centerX, int centerY, int radiusX, int radiusY) {
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

        computeEllipsePoints(points, centerX, centerY, radiusX, radiusY);

        return points;
    }

    private static void computeEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {

        int x = 0;
        int y = radiusY;

        int radiusXSquared = radiusX * radiusX;
        int radiusYSquared = radiusY * radiusY;

        double decisionParameterRegion1 =
                radiusYSquared - (radiusXSquared * radiusY) + (0.25 * radiusXSquared);
        double deltaX = 2.0 * radiusYSquared * x;
        double deltaY = 2.0 * radiusXSquared * y;

        while (deltaX < deltaY) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParameterRegion1 < 0) {
                x++;
                deltaX += 2 * radiusYSquared;
                decisionParameterRegion1 += deltaX + radiusYSquared;
            } else {
                x++;
                y--;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion1 += deltaX - deltaY + radiusYSquared;
            }
        }

        double decisionParameterRegion2 =
                radiusYSquared * (x + 0.5) * (x + 0.5)
                        + radiusXSquared * (y - 1) * (y - 1)
                        - radiusXSquared * radiusYSquared;

        while (y >= 0) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParameterRegion2 > 0) {
                y--;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion2 += radiusXSquared - deltaY;
            } else {
                y--;
                x++;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                decisionParameterRegion2 += deltaX - deltaY + radiusXSquared;
            }
        }
    }

    private static void addSymmetricEllipsePoints(
            Collection<int[]> points, int centerX, int centerY, int offsetX, int offsetY) {

        points.add(new int[] {centerX + offsetX, centerY + offsetY});
        points.add(new int[] {centerX - offsetX, centerY + offsetY});
        points.add(new int[] {centerX + offsetX, centerY - offsetY});
        points.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}