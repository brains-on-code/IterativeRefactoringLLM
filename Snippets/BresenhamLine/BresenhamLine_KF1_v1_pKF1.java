package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for geometry-related algorithms.
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Prevent instantiation
    }

    /**
     * Computes the points of a line between two coordinates using Bresenham's line algorithm.
     *
     * @param x0 starting x-coordinate
     * @param y0 starting y-coordinate
     * @param x1 ending x-coordinate
     * @param y1 ending y-coordinate
     * @return list of points representing the line from (x0, y0) to (x1, y1)
     */
    public static List<Point> computeLine(int x0, int y0, int x1, int y1) {
        List<Point> pointsOnLine = new ArrayList<>();

        int deltaX = Math.abs(x1 - x0);
        int deltaY = Math.abs(y1 - y0);
        int stepX = (x0 < x1) ? 1 : -1;
        int stepY = (y0 < y1) ? 1 : -1;
        int error = deltaX - deltaY;

        while (true) {
            pointsOnLine.add(new Point(x0, y0));

            if (x0 == x1 && y0 == y1) {
                break;
            }

            final int doubleError = error * 2;

            if (doubleError > -deltaY) {
                error -= deltaY;
                x0 += stepX;
            }

            if (doubleError < deltaX) {
                error += deltaX;
                y0 += stepY;
            }
        }

        return pointsOnLine;
    }
}