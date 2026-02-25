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
    private final double windowMinX;
    private final double windowMinY;
    private final double windowMaxX;
    private final double windowMaxY;

    public CohenSutherlandClipper(double windowMinX, double windowMinY, double windowMaxX, double windowMaxY) {
        this.windowMinX = windowMinX;
        this.windowMinY = windowMinY;
        this.windowMaxX = windowMaxX;
        this.windowMaxY = windowMaxY;
    }

    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < windowMinX) {
            regionCode |= REGION_LEFT;
        } else if (x > windowMaxX) {
            regionCode |= REGION_RIGHT;
        }

        if (y < windowMinY) {
            regionCode |= REGION_BOTTOM;
        } else if (y > windowMaxY) {
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
        boolean lineAccepted = false;

        while (true) {
            if (startRegionCode == REGION_INSIDE && endRegionCode == REGION_INSIDE) {
                lineAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                break;
            } else {
                double intersectionX = 0;
                double intersectionY = 0;

                int outsideRegionCode = (startRegionCode != REGION_INSIDE) ? startRegionCode : endRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionX = startX + (endX - startX) * (windowMaxY - startY) / (endY - startY);
                    intersectionY = windowMaxY;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionX = startX + (endX - startX) * (windowMinY - startY) / (endY - startY);
                    intersectionY = windowMinY;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionY = startY + (endY - startY) * (windowMaxX - startX) / (endX - startX);
                    intersectionX = windowMaxX;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionY = startY + (endY - startY) * (windowMinX - startX) / (endX - startX);
                    intersectionX = windowMinX;
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

        if (lineAccepted) {
            return new Line(new Point(startX, startY), new Point(endX, endY));
        } else {
            return null;
        }
    }
}