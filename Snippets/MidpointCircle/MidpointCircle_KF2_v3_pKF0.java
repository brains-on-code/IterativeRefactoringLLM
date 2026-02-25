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

        int xPos = centerX + x;
        int xNeg = centerX - x;
        int yPos = centerY + y;
        int yNeg = centerY - y;

        int yAsXPos = centerX + y;
        int yAsXNeg = centerX - y;
        int xAsYPos = centerY + x;
        int xAsYNeg = centerY - x;

        points.add(new int[] {xPos, yPos});
        points.add(new int[] {xNeg, yPos});
        points.add(new int[] {xPos, yNeg});
        points.add(new int[] {xNeg, yNeg});
        points.add(new int[] {yAsXPos, xAsYPos});
        points.add(new int[] {yAsXNeg, xAsYPos});
        points.add(new int[] {yAsXPos, xAsYNeg});
        points.add(new int[] {yAsXNeg, xAsYNeg});
    }
}