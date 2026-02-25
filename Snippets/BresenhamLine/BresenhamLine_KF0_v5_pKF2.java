package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class implementing Bresenham's line algorithm.
 *
 * <p>Computes all integer points on a straight line between two points in 2D
 * space using only integer arithmetic, which is useful for rasterization in
 * computer graphics.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm">
 *      Bresenham's line algorithm (Wikipedia)</a>
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes all integer points on the line segment from (x0, y0) to (x1, y1),
     * including both endpoints.
     *
     * @param x0 the x-coordinate of the starting point
     * @param y0 the y-coordinate of the starting point
     * @param x1 the x-coordinate of the ending point
     * @param y1 the y-coordinate of the ending point
     * @return a list of points on the line segment
     */
    public static List<Point> findLine(int x0, int y0, int x1, int y1) {
        List<Point> line = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int stepX = (x0 < x1) ? 1 : -1;
        int stepY = (y0 < y1) ? 1 : -1;

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