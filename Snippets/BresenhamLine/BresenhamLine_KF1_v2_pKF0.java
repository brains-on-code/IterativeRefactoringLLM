package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for geometry-related algorithms.
 */
public final class LineAlgorithms {

    private LineAlgorithms() {
        // Prevent instantiation
    }

    /**
     * Computes the set of points on a line between two coordinates using
     * Bresenham's line algorithm.
     *
     * @param startX starting x-coordinate
     * @param startY starting y-coordinate
     * @param endX   ending x-coordinate
     * @param endY   ending y-coordinate
     * @return list of points representing the line from (startX, startY) to (endX, endY)
     */
    public static List<Point> getLinePoints(int startX, int startY, int endX, int endY) {
        List<Point> pointsOnLine = new ArrayList<>();

        int currentX = startX;
        int currentY = startY;

        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        int stepX = (startX < endX) ? 1 : -1;
        int stepY = (startY < endY) ? 1 : -1;

        int error = deltaX - deltaY;

        while (true) {
            pointsOnLine.add(new Point(currentX, currentY));

            if (currentX == endX && currentY == endY) {
                break;
            }

            int doubleError = error * 2;

            if (doubleError > -deltaY) {
                error -= deltaY;
                currentX += stepX;
            }

            if (doubleError < deltaX) {
                error += deltaX;
                currentY += stepY;
            }
        }

        return pointsOnLine;
    }
}