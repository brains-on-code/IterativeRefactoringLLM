package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for generating points on an ellipse using the midpoint ellipse algorithm.
 */
public final class EllipseMidpoint {

    private EllipseMidpoint() {
        // Prevent instantiation
    }

    /**
     * Generates integer points on the boundary of an axis-aligned ellipse centered at
     * (centerX, centerY) with horizontal radius radiusX and vertical radius radiusY.
     *
     * Degenerate cases:
     * <ul>
     *     <li>radiusX == 0 && radiusY == 0 → single point at the center</li>
     *     <li>radiusX == 0 → vertical line segment</li>
     *     <li>radiusY == 0 → horizontal line segment</li>
     * </ul>
     *
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param radiusX horizontal radius
     * @param radiusY vertical radius
     * @return list of integer coordinate pairs lying on the ellipse boundary
     */
    public static List<int[]> generateEllipsePoints(int centerX, int centerY, int radiusX, int radiusY) {
        List<int[]> points = new ArrayList<>();

        if (radiusX == 0 && radiusY == 0) {
            // Single point
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (radiusX == 0) {
            // Vertical line segment
            for (int y = centerY - radiusY; y <= centerY + radiusY; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        if (radiusY == 0) {
            // Horizontal line segment
            for (int x = centerX - radiusX; x <= centerX + radiusX; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        computeMidpointEllipse(points, centerX, centerY, radiusX, radiusY);
        return points;
    }

    /**
     * Midpoint ellipse algorithm.
     *
     * The first quadrant is split into two regions:
     * <ul>
     *     <li>Region 1: |slope| &gt; 1 (dx &lt; dy)</li>
     *     <li>Region 2: |slope| ≤ 1 (dx ≥ dy)</li>
     * </ul>
     *
     * Points are generated in the first quadrant and mirrored to all four quadrants.
     *
     * @param points  collection to store generated points
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param radiusX horizontal radius
     * @param radiusY vertical radius
     */
    private static void computeMidpointEllipse(
            Collection<int[]> points, int centerX, int centerY, int radiusX, int radiusY) {

        int x = 0;
        int y = radiusY;

        int radiusXSq = radiusX * radiusX;
        int radiusYSq = radiusY * radiusY;

        // Region 1 decision parameters (start at (0, radiusY))
        double decisionParamRegion1 =
                radiusYSq - (radiusXSq * radiusY) + (0.25 * radiusXSq);
        double dx = 2.0 * radiusYSq * x;
        double dy = 2.0 * radiusXSq * y;

        // Region 1: |slope| > 1 (dx < dy)
        while (dx < dy) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParamRegion1 < 0) {
                x++;
                dx += 2 * radiusYSq;
                decisionParamRegion1 += dx + radiusYSq;
            } else {
                x++;
                y--;
                dx += 2 * radiusYSq;
                dy -= 2 * radiusXSq;
                decisionParamRegion1 += dx - dy + radiusYSq;
            }
        }

        // Region 2 decision parameter (start from last (x, y) of region 1)
        double decisionParamRegion2 =
                radiusYSq * (x + 0.5) * (x + 0.5)
                        + radiusXSq * (y - 1) * (y - 1)
                        - radiusXSq * radiusYSq;

        // Region 2: |slope| ≤ 1 (dx ≥ dy)
        while (y >= 0) {
            addSymmetricPoints(points, centerX, centerY, x, y);

            if (decisionParamRegion2 > 0) {
                y--;
                dy -= 2 * radiusXSq;
                decisionParamRegion2 += radiusXSq - dy;
            } else {
                y--;
                x++;
                dx += 2 * radiusYSq;
                dy -= 2 * radiusXSq;
                decisionParamRegion2 += dx - dy + radiusXSq;
            }
        }
    }

    /**
     * Adds the four symmetric points of (offsetX, offsetY) around the center.
     *
     * @param points  collection to store generated points
     * @param centerX x-coordinate of the ellipse center
     * @param centerY y-coordinate of the ellipse center
     * @param offsetX x-offset from the center
     * @param offsetY y-offset from the center
     */
    private static void addSymmetricPoints(
            Collection<int[]> points, int centerX, int centerY, int offsetX, int offsetY) {

        points.add(new int[] {centerX + offsetX, centerY + offsetY});
        points.add(new int[] {centerX - offsetX, centerY + offsetY});
        points.add(new int[] {centerX + offsetX, centerY - offsetY});
        points.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}