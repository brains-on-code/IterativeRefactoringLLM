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
    private final double clipWindowMinX;
    private final double clipWindowMinY;
    private final double clipWindowMaxX;
    private final double clipWindowMaxY;

    public CohenSutherlandClipper(double clipWindowMinX, double clipWindowMinY, double clipWindowMaxX, double clipWindowMaxY) {
        this.clipWindowMinX = clipWindowMinX;
        this.clipWindowMinY = clipWindowMinY;
        this.clipWindowMaxX = clipWindowMaxX;
        this.clipWindowMaxY = clipWindowMaxY;
    }

    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < clipWindowMinX) {
            regionCode |= REGION_LEFT;
        } else if (x > clipWindowMaxX) {
            regionCode |= REGION_RIGHT;
        }

        if (y < clipWindowMinY) {
            regionCode |= REGION_BOTTOM;
        } else if (y > clipWindowMaxY) {
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
        boolean isLineAccepted = false;

        while (true) {
            if (startRegionCode == 0 && endRegionCode == 0) {
                isLineAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                break;
            } else {
                double intersectionX = 0;
                double intersectionY = 0;

                int outsideRegionCode = (startRegionCode != 0) ? startRegionCode : endRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionX = startX + (endX - startX) * (clipWindowMaxY - startY) / (endY - startY);
                    intersectionY = clipWindowMaxY;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionX = startX + (endX - startX) * (clipWindowMinY - startY) / (endY - startY);
                    intersectionY = clipWindowMinY;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionY = startY + (endY - startY) * (clipWindowMaxX - startX) / (endX - startX);
                    intersectionX = clipWindowMaxX;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionY = startY + (endY - startY) * (clipWindowMinX - startX) / (endX - startX);
                    intersectionX = clipWindowMinX;
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

        if (isLineAccepted) {
            return new Line(new Point(startX, startY), new Point(endX, endY));
        } else {
            return null;
        }
    }
}