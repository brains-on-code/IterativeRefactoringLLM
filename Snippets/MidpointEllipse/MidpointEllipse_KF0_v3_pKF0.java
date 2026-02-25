package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The MidpointEllipse class implements the Midpoint Ellipse Drawing Algorithm.
 * This algorithm efficiently computes the points on an ellipse by dividing it into two regions
 * and using decision parameters to determine the next point to plot.
 */
public final class MidpointEllipse {

    private MidpointEllipse() {
        // Prevent instantiation
    }

    /**
     * Draws an ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param centerX the x-coordinate of the center of the ellipse
     * @param centerY the y-coordinate of the center of the ellipse
     * @param a       the length of the semi-major axis (horizontal radius)
     * @param b       the length of the semi-minor axis (vertical radius)
     * @return a list of points (represented as int arrays) that form the ellipse
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        if (a == 0 && b == 0) {
            points.add(newPoint(centerX, centerY));
            return points;
        }

        if (a == 0) {
            addVerticalLine(points, centerX, centerY, b);
            return points;
        }

        if (b == 0) {
            addHorizontalLine(points, centerX, centerY, a);
            return points;
        }

        computeEllipsePoints(points, centerX, centerY, a, b);
        return points;
    }

    private static int[] newPoint(int x, int y) {
        return new int[] {x, y};
    }

    private static void addVerticalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        int startY = centerY - halfLength;
        int endY = centerY + halfLength;
        for (int y = startY; y <= endY; y++) {
            points.add(newPoint(centerX, y));
        }
    }

    private static void addHorizontalLine(Collection<int[]> points, int centerX, int centerY, int halfLength) {
        int startX = centerX - halfLength;
        int endX = centerX + halfLength;
        for (int x = startX; x <= endX; x++) {
            points.add(newPoint(x, centerY));
        }
    }

    /**
     * Computes points of a non-degenerate ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param points  the collection to which points will be added
     * @param centerX the x-coordinate of the center of the ellipse
     * @param centerY the y-coordinate of the center of the ellipse
     * @param a       the length of the semi-major axis (horizontal radius)
     * @param b       the length of the semi-minor axis (vertical radius)
     */
    private static void computeEllipsePoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int a,
        int b
    ) {
        int x = 0;
        int y = b;

        int a2 = a * a;
        int b2 = b * b;

        double dx = 0.0;
        double dy = 2.0 * a2 * y;

        double d1 = b2 - (a2 * b) + (0.25 * a2);

        computeRegion1(points, centerX, centerY, a2, b2, x, y, dx, dy, d1);
        computeRegion2(points, centerX, centerY, a2, b2, x, y, dx, dy);
    }

    /**
     * Region 1 of the Midpoint Ellipse Algorithm where slope < 1.
     */
    private static void computeRegion1(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int a2,
        int b2,
        int xStart,
        int yStart,
        double dxStart,
        double dyStart,
        double d1Start
    ) {
        int x = xStart;
        int y = yStart;
        double dx = dxStart;
        double dy = dyStart;
        double d1 = d1Start;

        while (dx < dy) {
            addEllipseSymmetricPoints(points, centerX, centerY, x, y);

            if (d1 < 0) {
                x++;
                dx += 2.0 * b2;
                d1 += dx + b2;
            } else {
                x++;
                y--;
                dx += 2.0 * b2;
                dy -= 2.0 * a2;
                d1 += dx - dy + b2;
            }
        }
    }

    /**
     * Region 2 of the Midpoint Ellipse Algorithm where slope >= 1.
     */
    private static void computeRegion2(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int a2,
        int b2,
        int xStart,
        int yStart,
        double dxStart,
        double dyStart
    ) {
        int x = xStart;
        int y = yStart;
        double dx = dxStart;
        double dy = dyStart;

        double xMid = x + 0.5;
        double yPrev = y - 1.0;
        double d2 = b2 * xMid * xMid + a2 * yPrev * yPrev - a2 * b2;

        while (y >= 0) {
            addEllipseSymmetricPoints(points, centerX, centerY, x, y);

            if (d2 > 0) {
                y--;
                dy -= 2.0 * a2;
                d2 += a2 - dy;
            } else {
                y--;
                x++;
                dx += 2.0 * b2;
                dy -= 2.0 * a2;
                d2 += dx - dy + a2;
            }
        }
    }

    /**
     * Adds points for all four quadrants of the ellipse based on symmetry.
     *
     * @param points  the collection to which points will be added
     * @param centerX the x-coordinate of the center of the ellipse
     * @param centerY the y-coordinate of the center of the ellipse
     * @param x       the x-coordinate relative to the center
     * @param y       the y-coordinate relative to the center
     */
    private static void addEllipseSymmetricPoints(
        Collection<int[]> points,
        int centerX,
        int centerY,
        int x,
        int y
    ) {
        int xPos = centerX + x;
        int xNeg = centerX - x;
        int yPos = centerY + y;
        int yNeg = centerY - y;

        points.add(newPoint(xPos, yPos));
        points.add(newPoint(xNeg, yPos));
        points.add(newPoint(xPos, yNeg));
        points.add(newPoint(xNeg, yNeg));
    }
}