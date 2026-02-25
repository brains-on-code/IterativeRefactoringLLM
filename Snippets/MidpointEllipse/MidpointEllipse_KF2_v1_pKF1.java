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

        int x = 0;
        int y = radiusY;

        double decisionParamRegion1 =
                (radiusY * radiusY) - (radiusX * radiusX * radiusY) + (0.25 * radiusX * radiusX);
        double deltaX = 2.0 * radiusY * radiusY * x;
        double deltaY = 2.0 * radiusX * radiusX * y;

        while (deltaX < deltaY) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                deltaX += 2 * radiusY * radiusY;
                decisionParamRegion1 += deltaX + (radiusY * radiusY);
            } else {
                x++;
                y--;
                deltaX += 2 * radiusY * radiusY;
                deltaY -= 2 * radiusX * radiusX;
                decisionParamRegion1 += deltaX - deltaY + (radiusY * radiusY);
            }
        }

        double decisionParamRegion2 =
                radiusY * radiusY * (x + 0.5) * (x + 0.5)
                        + radiusX * radiusX * (y - 1) * (y - 1)
                        - radiusX * radiusX * radiusY * radiusY;

        while (y >= 0) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                deltaY -= 2 * radiusX * radiusX;
                decisionParamRegion2 += (radiusX * radiusX) - deltaY;
            } else {
                y--;
                x++;
                deltaX += 2 * radiusY * radiusY;
                deltaY -= 2 * radiusX * radiusX;
                decisionParamRegion2 += deltaX - deltaY + (radiusX * radiusX);
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