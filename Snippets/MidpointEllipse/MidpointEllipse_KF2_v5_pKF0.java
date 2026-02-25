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
        int startY = centerY - halfLength;
        int endY = centerY + halfLength;
        for (int y = startY; y <= endY; y++) {
            points.add(new int[] {centerX, y});
        }
    }

    private static void addHorizontalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        int startX = centerX - halfLength;
        int endX = centerX + halfLength;
        for (int x = startX; x <= endX; x++) {
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

        int aSquared = a * a;
        int bSquared = b * b;

        double decisionParamRegion1 = bSquared - (aSquared * b) + (0.25 * aSquared);
        double dx = 2.0 * bSquared * x;
        double dy = 2.0 * aSquared * y;

        x = computeRegion1(points, centerX, centerY, aSquared, bSquared, x, y, dx, dy, decisionParamRegion1);
        computeRegion2(points, centerX, centerY, aSquared, bSquared, x, y);
    }

    private static int computeRegion1(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int aSquared,
        int bSquared,
        int startX,
        int startY,
        double startDx,
        double startDy,
        double decisionParam
    ) {
        int x = startX;
        int y = startY;
        double dx = startDx;
        double dy = startDy;

        while (dx < dy) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParam < 0) {
                x++;
                dx += 2 * bSquared;
                decisionParam += dx + bSquared;
            } else {
                x++;
                y--;
                dx += 2 * bSquared;
                dy -= 2 * aSquared;
                decisionParam += dx - dy + bSquared;
            }
        }
        return x;
    }

    private static void computeRegion2(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int aSquared,
        int bSquared,
        int startX,
        int startY
    ) {
        int x = startX;
        int y = startY;

        double dx = 2.0 * bSquared * x;
        double dy = 2.0 * aSquared * y;

        double decisionParam =
            bSquared * (x + 0.5) * (x + 0.5) +
            aSquared * (y - 1) * (y - 1) -
            aSquared * bSquared;

        while (y >= 0) {
            addSymmetricEllipsePoints(points, centerX, centerY, x, y);

            if (decisionParam > 0) {
                y--;
                dy -= 2 * aSquared;
                decisionParam += aSquared - dy;
            } else {
                y--;
                x++;
                dx += 2 * bSquared;
                dy -= 2 * aSquared;
                decisionParam += dx - dy + aSquared;
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