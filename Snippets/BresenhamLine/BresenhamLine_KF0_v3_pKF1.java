package com.thealgorithms.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BresenhamLine} class implements Bresenham's line algorithm,
 * which is an efficient way to determine the points of a straight line
 * between two given points in a 2D space.
 *
 * <p>This algorithm uses integer arithmetic to calculate the points,
 * making it suitable for rasterization in computer graphics.</p>
 *
 * For more information, please visit {@link https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm}
 */
public final class BresenhamLine {

    private BresenhamLine() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Finds the list of points that form a straight line between two endpoints.
     *
     * @param startX the x-coordinate of the starting point
     * @param startY the y-coordinate of the starting point
     * @param endX   the x-coordinate of the ending point
     * @param endY   the y-coordinate of the ending point
     * @return a {@code List<Point>} containing all points on the line
     */
    public static List<Point> findLine(int startX, int startY, int endX, int endY) {
        List<Point> linePoints = new ArrayList<>();

        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        int stepX = (startX < endX) ? 1 : -1;
        int stepY = (startY < endY) ? 1 : -1;

        int error = deltaX - deltaY;

        int currentX = startX;
        int currentY = startY;

        while (true) {
            linePoints.add(new Point(currentX, currentY));

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

        return linePoints;
    }
}