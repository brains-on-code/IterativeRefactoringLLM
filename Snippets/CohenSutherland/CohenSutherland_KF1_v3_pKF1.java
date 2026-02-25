package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping implementation.
 */
public class CohenSutherlandClipper {

    // Region codes
    private static final int REGION_INSIDE = 0;  // 0000
    private static final int REGION_LEFT = 1;    // 0001
    private static final int REGION_RIGHT = 2;   // 0010
    private static final int REGION_BOTTOM = 4;  // 0100
    private static final int REGION_TOP = 8;     // 1000

    // Clipping window boundaries
    private final double windowXMin;
    private final double windowYMin;
    private final double windowXMax;
    private final double windowYMax;

    public CohenSutherlandClipper(double windowXMin, double windowYMin, double windowXMax, double windowYMax) {
        this.windowXMin = windowXMin;
        this.windowYMin = windowYMin;
        this.windowXMax = windowXMax;
        this.windowYMax = windowYMax;
    }

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

    public Line clip(Line line) {
        double startX = line.start.x;
        double startY = line.start.y;
        double endX = line.end.x;
        double endY = line.end.y;

        int startRegionCode = computeRegionCode(startX, startY);
        int endRegionCode = computeRegionCode(endX, endY);
        boolean isAccepted = false;

        while (true) {
            if ((startRegionCode == 0) && (endRegionCode == 0)) {
                isAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                break;
            } else {
                double intersectionX = 0;
                double intersectionY = 0;

                int outsideRegionCode = (startRegionCode != 0) ? startRegionCode : endRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionX = startX + (endX - startX) * (windowYMax - startY) / (endY - startY);
                    intersectionY = windowYMax;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionX = startX + (endX - startX) * (windowYMin - startY) / (endY - startY);
                    intersectionY = windowYMin;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionY = startY + (endY - startY) * (windowXMax - startX) / (endX - startX);
                    intersectionX = windowXMax;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionY = startY + (endY - startY) * (windowXMin - startX) / (endX - startX);
                    intersectionX = windowXMin;
                }

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

        if (isAccepted) {
            return new Line(new Point(startX, startY), new Point(endX, endY));
        } else {
            return null;
        }
    }
}