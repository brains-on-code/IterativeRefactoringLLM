package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class CohenSutherland {

    private static final int REGION_INSIDE = 0;
    private static final int REGION_LEFT = 1;
    private static final int REGION_RIGHT = 2;
    private static final int REGION_BOTTOM = 4;
    private static final int REGION_TOP = 8;

    private final double clipXMin;
    private final double clipYMin;
    private final double clipXMax;
    private final double clipYMax;

    public CohenSutherland(double clipXMin, double clipYMin, double clipXMax, double clipYMax) {
        this.clipXMin = clipXMin;
        this.clipYMin = clipYMin;
        this.clipXMax = clipXMax;
        this.clipYMax = clipYMax;
    }

    private int computeRegionCode(double x, double y) {
        int regionCode = REGION_INSIDE;

        if (x < clipXMin) {
            regionCode |= REGION_LEFT;
        } else if (x > clipXMax) {
            regionCode |= REGION_RIGHT;
        }

        if (y < clipYMin) {
            regionCode |= REGION_BOTTOM;
        } else if (y > clipYMax) {
            regionCode |= REGION_TOP;
        }

        return regionCode;
    }

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
                isAccepted = true;
                break;
            } else if ((startRegionCode & endRegionCode) != 0) {
                break;
            } else {
                double intersectionX = 0;
                double intersectionY = 0;

                int outsideRegionCode = (startRegionCode != 0) ? startRegionCode : endRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionX = xStart + (xEnd - xStart) * (clipYMax - yStart) / (yEnd - yStart);
                    intersectionY = clipYMax;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionX = xStart + (xEnd - xStart) * (clipYMin - yStart) / (yEnd - yStart);
                    intersectionY = clipYMin;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionY = yStart + (yEnd - yStart) * (clipXMax - xStart) / (xEnd - xStart);
                    intersectionX = clipXMax;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionY = yStart + (yEnd - yStart) * (clipXMin - xStart) / (xEnd - xStart);
                    intersectionX = clipXMin;
                }

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
        } else {
            return null;
        }
    }
}