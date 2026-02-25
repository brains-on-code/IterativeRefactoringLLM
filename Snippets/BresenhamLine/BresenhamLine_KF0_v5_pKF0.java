package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BresenhamLine} class implements Bresenham's line algorithm,
 * which efficiently determines the points of a straight line between two
 * points in 2D space using integer arithmetic.
 *
 * For more information, see
 * <a href="https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm">
 * Bresenham's line algorithm</a>.
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Prevent instantiation
    }

    /**
     * Computes the list of points that form a straight line between two endpoints
     * using Bresenham's line algorithm.
     *
     * @param x0 the x-coordinate of the starting point
     * @param y0 the y-coordinate of the starting point
     * @param x1 the x-coordinate of the ending point
     * @param y1 the y-coordinate of the ending point
     * @return a list containing all points on the line, including endpoints
     */
    public static List<Point> findLine(int x0, int y0, int x1, int y1) {
        List<Point> linePoints = new ArrayList<>();

        int deltaX = Math.abs(x1 - x0);
        int deltaY = Math.abs(y1 - y0);

        int stepX = (x0 < x1) ? 1 : -1;
        int stepY = (y0 < y1) ? 1 : -1;

        int error = deltaX - deltaY;

        while (true) {
            linePoints.add(new Point(x0, y0));

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int doubledError = error * 2;

            if (doubledError > -deltaY) {
                error -= deltaY;
                x0 += stepX;
            }

            if (doubledError < deltaX) {
                error += deltaX;
                y0 += stepY;
            }
        }

        return linePoints;
    }
}