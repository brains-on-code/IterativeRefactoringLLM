package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointEllipse {

    private MidpointEllipse() {
        // Utility class; prevent instantiation
    }

    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (a == 0) {
            addVerticalLine(points, centerX, centerY, b);
            return points;
        }

        if (b == 0) {
            addHorizontalLine(points, centerX, centerY, a);
            return points;
        }

        computeEllipsePoints(points, centerX, centerY, a, b);
        return points;
    }

    private static void addVerticalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        for (int y = centerY - halfLength; y <= centerY + halfLength; y++) {
            points.add(new int[] {centerX, y});
        }
    }

    private static void addHorizontalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        for (int x = centerX - halfLength; x <= centerX + halfLength; x++) {
            points.add(new int[] {x, centerY});
        }
    }

    private static void computeEllipsePoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int a,
        int b
    ) {
        int x = 0;
        int y = b;

        int a2 = a * a;
        int b2 = b * b;

        double decisionParamRegion1 = b2 - (a2 * b) + (0.25 * a2);
        double dx = 2.0 * b2 * x;
        double dy = 2.0 * a2 * y;

        // Region 1
        while (dx < dy) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                dx += 2 * b2;
                decisionParamRegion1 += dx + b2;
            } else {
                x++;
                y--;
                dx += 2 * b2;
                dy -= 2 * a2;
                decisionParamRegion1 += dx - dy + b2;
            }
        }

        // Region 2
        double decisionParamRegion2 =
            b2 * (x + 0.5) * (x + 0.5) +
            a2 * (y - 1) * (y - 1) -
            a2 * b2;

        while (y >= 0) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                dy -= 2 * a2;
                decisionParamRegion2 += a2 - dy;
            } else {
                y--;
                x++;
                dx += 2 * b2;
                dy -= 2 * a2;
                decisionParamRegion2 += dx - dy + a2;
            }
        }
    }

    private static void addSymmetricEllipsePoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int x,
        int y
    ) {
        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}