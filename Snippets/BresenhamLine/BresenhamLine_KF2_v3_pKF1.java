package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class BresenhamLine {

    private BresenhamLine() {}

    public static List<Point> findLine(int startX, int startY, int endX, int endY) {
        List<Point> linePoints = new ArrayList<>();

        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        int stepDirectionX = (startX < endX) ? 1 : -1;
        int stepDirectionY = (startY < endY) ? 1 : -1;
        int errorDifference = deltaX - deltaY;

        int currentX = startX;
        int currentY = startY;

        while (true) {
            linePoints.add(new Point(currentX, currentY));

            if (currentX == endX && currentY == endY) {
                break;
            }

            final int doubledErrorDifference = errorDifference * 2;

            if (doubledErrorDifference > -deltaY) {
                errorDifference -= deltaY;
                currentX += stepDirectionX;
            }

            if (doubledErrorDifference < deltaX) {
                errorDifference += deltaX;
                currentY += stepDirectionY;
            }
        }

        return linePoints;
    }
}