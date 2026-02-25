package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class CohenSutherland {

    private static final int REGION_INSIDE = 0;
    private static final int REGION_LEFT = 1;
    private static final int REGION_RIGHT = 2;
    private static final int REGION_BOTTOM = 4;
    private static final int REGION_TOP = 8;

    private final double clipMinX;
    private final double clipMinY;
    private final double clipMaxX;
    private final double clipMaxY;

    public CohenSutherland(double clipMinX, double clipMinY, double clipMaxX, double clipMaxY) {
        this.clipMinX = clipMinX;
        this.clipMinY = clipMinY;
        this.clipMaxX = clipMaxX;
        this.clipMaxY = clipMaxY;
    }

    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < clipMinX) {
            regionCode |= REGION_LEFT;
        } else if (x > clipMaxX) {
            regionCode |= REGION_RIGHT;
        }

        if (y < clipMinY) {
            regionCode |= REGION_BOTTOM;
        } else if (y > clipMaxY) {
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
            if (startRegionCode == REGION_INSIDE && endRegionCode == REGION_INSIDE) {
                isAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                break;
            } else {
                double intersectionX = 0;
                double intersectionY = 0;

                int outsideRegionCode = (startRegionCode != REGION_INSIDE) ? startRegionCode : endRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionX = startX + (endX - startX) * (clipMaxY - startY) / (endY - startY);
                    intersectionY = clipMaxY;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionX = startX + (endX - startX) * (clipMinY - startY) / (endY - startY);
                    intersectionY = clipMinY;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionY = startY + (endY - startY) * (clipMaxX - startX) / (endX - startX);
                    intersectionX = clipMaxX;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionY = startY + (endY - startY) * (clipMinX - startX) / (endX - startX);
                    intersectionX = clipMinX;
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