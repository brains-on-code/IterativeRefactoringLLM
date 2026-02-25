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

        int currentX = radius;
        int currentY = 0;

        int decisionParameter = 1 - radius;

        addSymmetricPoints(circlePoints, centerX, centerY, currentX, currentY);

        while (currentX > currentY) {
            currentY++;

            if (decisionParameter <= 0) {
                decisionParameter = decisionParameter + 2 * currentY + 1;
            } else {
                currentX--;
                decisionParameter = decisionParameter + 2 * currentY - 2 * currentX + 1;
            }

            addSymmetricPoints(circlePoints, centerX, centerY, currentX, currentY);
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