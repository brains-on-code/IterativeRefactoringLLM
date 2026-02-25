package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class implementing Bresenham's line algorithm.
 * Produces a list of integer points approximating a straight line
 * between two coordinates on a grid.
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Prevent instantiation
    }

    /**
     * Computes the points of a line between (x0, y0) and (x1, y1)
     * using Bresenham's line algorithm.
     *
     * @param x0 starting x-coordinate
     * @param y0 starting y-coordinate
     * @param x1 ending x-coordinate
     * @param y1 ending y-coordinate
     * @return list of points representing the line
     */
    public static List<Point> findLine(int x0, int y0, int x1, int y1) {
        List<Point> line = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int stepX = x0 < x1 ? 1 : -1;
        int stepY = y0 < y1 ? 1 : -1;

        int error = dx - dy;

        while (true) {
            line.add(new Point(x0, y0));

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int doubledError = error << 1;

            if (doubledError > -dy) {
                error -= dy;
                x0 += stepX;
            }

            if (doubledError < dx) {
                error += dx;
                y0 += stepY;
            }
        }

        return line;
    }
}