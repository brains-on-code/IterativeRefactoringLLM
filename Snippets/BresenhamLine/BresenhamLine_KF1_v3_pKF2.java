package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for geometry-related algorithms.
 *
 * <p>Currently provides an implementation of Bresenham's line algorithm to
 * compute all integer points on a line between two coordinates.
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Prevent instantiation
    }

    /**
     * Computes the list of integer points on the line between (x1, y1) and (x2, y2)
     * using Bresenham's line algorithm.
     *
     * @param x1 start point x-coordinate
     * @param y1 start point y-coordinate
     * @param x2 end point x-coordinate
     * @param y2 end point y-coordinate
     * @return list of points representing the line from (x1, y1) to (x2, y2)
     */
    public static List<Point> computeLinePoints(int x1, int y1, int x2, int y2) {
        List<Point> pointsOnLine = new ArrayList<>();

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);

        int stepX = (x1 < x2) ? 1 : -1;
        int stepY = (y1 < y2) ? 1 : -1;

        int error = deltaX - deltaY;

        while (true) {
            pointsOnLine.add(new Point(x1, y1));

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int doubleError = error * 2;

            if (doubleError > -deltaY) {
                error -= deltaY;
                x1 += stepX;
            }

            if (doubleError < deltaX) {
                error += deltaX;
                y1 += stepY;
            }
        }

        return pointsOnLine;
    }
}