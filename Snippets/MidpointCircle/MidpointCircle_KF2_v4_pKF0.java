package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointCircle {

    private MidpointCircle() {
        // Utility class; prevent instantiation
    }

    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> points = new ArrayList<>();

        if (radius == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        int x = radius;
        int y = 0;
        int decisionParam = 1 - radius;

        addSymmetricPoints(points, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decisionParam <= 0) {
                decisionParam += 2 * y + 1;
            } else {
                x--;
                decisionParam += 2 * (y - x) + 1;
            }

            addSymmetricPoints(points, centerX, centerY, x, y);
        }

        return points;
    }

    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int x, int y) {

        int xPositive = centerX + x;
        int xNegative = centerX - x;
        int yPositive = centerY + y;
        int yNegative = centerY - y;

        int swappedXPositive = centerX + y;
        int swappedXNegative = centerX - y;
        int swappedYPositive = centerY + x;
        int swappedYNegative = centerY - x;

        points.add(new int[] {xPositive, yPositive});
        points.add(new int[] {xNegative, yPositive});
        points.add(new int[] {xPositive, yNegative});
        points.add(new int[] {xNegative, yNegative});
        points.add(new int[] {swappedXPositive, swappedYPositive});
        points.add(new int[] {swappedXNegative, swappedYPositive});
        points.add(new int[] {swappedXPositive, swappedYNegative});
        points.add(new int[] {swappedXNegative, swappedYNegative});
    }
}