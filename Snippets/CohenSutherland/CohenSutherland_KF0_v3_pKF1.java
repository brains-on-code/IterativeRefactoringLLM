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
    private static final int REGION_INSIDE = 0;  // 0000
    private static final int REGION_LEFT = 1;    // 0001
    private static final int REGION_RIGHT = 2;   // 0010
    private static final int REGION_BOTTOM = 4;  // 0100
    private static final int REGION_TOP = 8;     // 1000

    // Define the clipping window
    private final double windowXMin;
    private final double windowYMin;
    private final double windowXMax;
    private final double windowYMax;

    public CohenSutherland(double windowXMin, double windowYMin, double windowXMax, double windowYMax) {
        this.windowXMin = windowXMin;
        this.windowYMin = windowYMin;
        this.windowXMax = windowXMax;
        this.windowYMax = windowYMax;
    }

    // Compute the region code for a point (x, y)
    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < windowXMin) {
            regionCode |= REGION_LEFT;
        } else if (x > windowXMax) {
            regionCode |= REGION_RIGHT;
        }

        if (y < windowYMin) {
            regionCode |= REGION_BOTTOM;
        } else if (y > windowYMax) {
            regionCode |= REGION_TOP;
        }

        return regionCode;
    }

    // Cohen-Sutherland algorithm to return the clipped line
    public Line clip(Line line) {
        double startX = line.start.x;
        double startY = line.start.y;
        double endX = line.end.x;
        double endY = line.end.y;

        int startRegionCode = computeRegionCode(startX, startY);
        int endRegionCode = computeRegionCode(endX, endY);
        boolean isLineAccepted = false;

        while (true) {
            if (startRegionCode == REGION_INSIDE && endRegionCode == REGION_INSIDE) {
                // Both points are inside the rectangle
                isLineAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                // Both points are outside the rectangle in the same region
                break;
            } else {
                // Some segment of the line is inside the rectangle
                double intersectionX = 0;
                double intersectionY = 0;

                // Pick an endpoint that is outside the rectangle
                int outsideRegionCode = (startRegionCode != REGION_INSIDE) ? startRegionCode : endRegionCode;

                // Find the intersection point using the line equation
                if ((outsideRegionCode & REGION_TOP) != 0) {
                    // Point is above the rectangle
                    intersectionX = startX + (endX - startX) * (windowYMax - startY) / (endY - startY);
                    intersectionY = windowYMax;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    // Point is below the rectangle
                    intersectionX = startX + (endX - startX) * (windowYMin - startY) / (endY - startY);
                    intersectionY = windowYMin;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    // Point is to the right of the rectangle
                    intersectionY = startY + (endY - startY) * (windowXMax - startX) / (endX - startX);
                    intersectionX = windowXMax;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    // Point is to the left of the rectangle
                    intersectionY = startY + (endY - startY) * (windowXMin - startX) / (endX - startX);
                    intersectionX = windowXMin;
                }

                // Replace the point outside the rectangle with the intersection point
                if (outsideRegionCode == startRegionCode) {
                    startX = intersectionX;
                    startY = intersectionY;
                    startRegionCode = computeRegionCode(startX, startY);
                } else {
                    endX = intersectionX;
                    endY = intersectionY;
                    endRegionCode = computeRegionCode(endX, endY);
                }
            }
        }

        if (isLineAccepted) {
            return new Line(new Point(startX, startY), new Point(endX, endY));
        }

        return null; // The line is fully rejected
    }
}