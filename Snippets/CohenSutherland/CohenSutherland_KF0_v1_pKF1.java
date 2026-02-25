package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen-Sutherland Line Clipping Algorithm
 *
 * This algorithm is used to clip a line segment to a rectangular window.
 * It assigns a region code to each endpoint of the line segment, and
 * then efficiently determines whether the line segment is fully inside,
 * fully outside, or partially inside the window.
 *
 * Reference:
 * https://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm
 *
 * Clipping window boundaries are defined as (xMin, yMin) and (xMax, yMax).
 * The algorithm computes the clipped line segment if it's partially or
 * fully inside the clipping window.
 */
public class CohenSutherland {

    // Region codes for the 9 regions
    private static final int REGION_INSIDE = 0; // 0000
    private static final int REGION_LEFT = 1; // 0001
    private static final int REGION_RIGHT = 2; // 0010
    private static final int REGION_BOTTOM = 4; // 0100
    private static final int REGION_TOP = 8; // 1000

    // Define the clipping window
    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    public CohenSutherland(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    // Compute the region code for a point (x, y)
    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < xMin) {
            regionCode |= REGION_LEFT;
        } else if (x > xMax) {
            regionCode |= REGION_RIGHT;
        }

        if (y < yMin) {
            regionCode |= REGION_BOTTOM;
        } else if (y > yMax) {
            regionCode |= REGION_TOP;
        }

        return regionCode;
    }

    // Cohen-Sutherland algorithm to return the clipped line
    public Line clip(Line line) {
        double xStart = line.start.x;
        double yStart = line.start.y;
        double xEnd = line.end.x;
        double yEnd = line.end.y;

        int startRegionCode = computeRegionCode(xStart, yStart);
        int endRegionCode = computeRegionCode(xEnd, yEnd);
        boolean isAccepted = false;

        while (true) {
            if (startRegionCode == 0 && endRegionCode == 0) {
                // Both points are inside the rectangle
                isAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                // Both points are outside the rectangle in the same region
                break;
            } else {
                // Some segment of the line is inside the rectangle
                double intersectionX = 0;
                double intersectionY = 0;

                // Pick an endpoint that is outside the rectangle
                int outsideRegionCode = (startRegionCode != 0) ? startRegionCode : endRegionCode;

                // Find the intersection point using the line equation
                if ((outsideRegionCode & REGION_TOP) != 0) {
                    // Point is above the rectangle
                    intersectionX = xStart + (xEnd - xStart) * (yMax - yStart) / (yEnd - yStart);
                    intersectionY = yMax;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    // Point is below the rectangle
                    intersectionX = xStart + (xEnd - xStart) * (yMin - yStart) / (yEnd - yStart);
                    intersectionY = yMin;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    // Point is to the right of the rectangle
                    intersectionY = yStart + (yEnd - yStart) * (xMax - xStart) / (xEnd - xStart);
                    intersectionX = xMax;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    // Point is to the left of the rectangle
                    intersectionY = yStart + (yEnd - yStart) * (xMin - xStart) / (xEnd - xStart);
                    intersectionX = xMin;
                }

                // Replace the point outside the rectangle with the intersection point
                if (outsideRegionCode == startRegionCode) {
                    xStart = intersectionX;
                    yStart = intersectionY;
                    startRegionCode = computeRegionCode(xStart, yStart);
                } else {
                    xEnd = intersectionX;
                    yEnd = intersectionY;
                    endRegionCode = computeRegionCode(xEnd, yEnd);
                }
            }
        }

        if (isAccepted) {
            return new Line(new Point(xStart, yStart), new Point(xEnd, yEnd));
        }

        return null; // The line is fully rejected
    }
}