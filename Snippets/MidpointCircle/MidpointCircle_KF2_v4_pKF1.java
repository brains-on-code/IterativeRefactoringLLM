package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MidpointCircle {

    private MidpointCircle() {
    }

    public static List<int[]> generateCirclePoints(int centerX, int centerY, int radius) {
        List<int[]> circlePoints = new ArrayList<>();

        if (radius == 0) {
            circlePoints.add(new int[] {centerX, centerY});
            return circlePoints;
        }

        int x = radius;
        int y = 0;

        int decision = 1 - radius;

        addSymmetricPoints(circlePoints, centerX, centerY, x, y);

        while (x > y) {
            y++;

            if (decision <= 0) {
                decision = decision + 2 * y + 1;
            } else {
                x--;
                decision = decision + 2 * y - 2 * x + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, x, y);
        }

        return circlePoints;
    }

    private static void addSymmetricPoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int xOffset,
        int yOffset
    ) {
        points.add(new int[] {centerX + xOffset, centerY + yOffset});
        points.add(new int[] {centerX - xOffset, centerY + yOffset});
        points.add(new int[] {centerX + xOffset, centerY - yOffset});
        points.add(new int[] {centerX - xOffset, centerY - yOffset});
        points.add(new int[] {centerX + yOffset, centerY + xOffset});
        points.add(new int[] {centerX - yOffset, centerY + xOffset});
        points.add(new int[] {centerX + yOffset, centerY - xOffset});
        points.add(new int[] {centerX - yOffset, centerY - xOffset});
    }
}