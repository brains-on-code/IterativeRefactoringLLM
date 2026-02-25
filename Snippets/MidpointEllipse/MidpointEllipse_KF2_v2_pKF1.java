package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointEllipse {

    private MidpointEllipse() {
    }

    public static List<int[]> drawEllipse(int centerX, int centerY, int radiusX, int radiusY) {
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

        computeEllipsePoints(ellipsePoints, centerX, centerY, radiusX, radiusY);

        return ellipsePoints;
    }

    private static void computeEllipsePoints(
            Collection<int[]> ellipsePoints, int centerX, int centerY, int radiusX, int radiusY) {

        int currentX = 0;
        int currentY = radiusY;

        double decisionParameterRegion1 =
                (radiusY * radiusY) - (radiusX * radiusX * radiusY) + (0.25 * radiusX * radiusX);
        double deltaX = 2.0 * radiusY * radiusY * currentX;
        double deltaY = 2.0 * radiusX * radiusX * currentY;

        while (deltaX < deltaY) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, currentX, currentY);

            if (decisionParameterRegion1 < 0) {
                currentX++;
                deltaX += 2 * radiusY * radiusY;
                decisionParameterRegion1 += deltaX + (radiusY * radiusY);
            } else {
                currentX++;
                currentY--;
                deltaX += 2 * radiusY * radiusY;
                deltaY -= 2 * radiusX * radiusX;
                decisionParameterRegion1 += deltaX - deltaY + (radiusY * radiusY);
            }
        }

        double decisionParameterRegion2 =
                radiusY * radiusY * (currentX + 0.5) * (currentX + 0.5)
                        + radiusX * radiusX * (currentY - 1) * (currentY - 1)
                        - radiusX * radiusX * radiusY * radiusY;

        while (currentY >= 0) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, currentX, currentY);

            if (decisionParameterRegion2 > 0) {
                currentY--;
                deltaY -= 2 * radiusX * radiusX;
                decisionParameterRegion2 += (radiusX * radiusX) - deltaY;
            } else {
                currentY--;
                currentX++;
                deltaX += 2 * radiusY * radiusY;
                deltaY -= 2 * radiusX * radiusX;
                decisionParameterRegion2 += deltaX - deltaY + (radiusX * radiusX);
            }
        }
    }

    private static void addSymmetricEllipsePoints(
            Collection<int[]> ellipsePoints, int centerX, int centerY, int offsetX, int offsetY) {

        ellipsePoints.add(new int[] {centerX + offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX + offsetX, centerY - offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}