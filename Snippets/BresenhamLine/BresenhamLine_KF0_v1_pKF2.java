package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements Bresenham's line algorithm to compute all integer points on a
 * straight line between two points in 2D space.
 *
 * <p>The algorithm uses only integer arithmetic, making it suitable for
 * rasterization in computer graphics.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm">
 *      Bresenham's line algorithm (Wikipedia)</a>
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns all integer points on the line segment from (x0, y0) to (x1, y1),
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
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            line.add(new Point(x0, y0));

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int e2 = err * 2;

            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        return line;
    }
}