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

        int cxPlusX = centerX + x;
        int cxMinusX = centerX - x;
        int cxPlusY = centerX + y;
        int cxMinusY = centerX - y;

        int cyPlusY = centerY + y;
        int cyMinusY = centerY - y;
        int cyPlusX = centerY + x;
        int cyMinusX = centerY - x;

        points.add(new int[] {cxPlusX, cyPlusY});
        points.add(new int[] {cxMinusX, cyPlusY});
        points.add(new int[] {cxPlusX, cyMinusY});
        points.add(new int[] {cxMinusX, cyMinusY});
        points.add(new int[] {cxPlusY, cyPlusX});
        points.add(new int[] {cxMinusY, cyPlusX});
        points.add(new int[] {cxPlusY, cyMinusX});
        points.add(new int[] {cxMinusY, cyMinusX});
    }
}