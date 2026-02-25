package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class CohenSutherland {

    private static final int REGION_INSIDE = 0;
    private static final int REGION_LEFT = 1;
    private static final int REGION_RIGHT = 2;
    private static final int REGION_BOTTOM = 4;
    private static final int REGION_TOP = 8;

    private final double clipWindowMinX;
    private final double clipWindowMinY;
    private final double clipWindowMaxX;
    private final double clipWindowMaxY;

    public CohenSutherland(double clipWindowMinX, double clipWindowMinY, double clipWindowMaxX, double clipWindowMaxY) {
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

    public Line clip(Line lineToClip) {
        double startPointX = lineToClip.start.x;
        double startPointY = lineToClip.start.y;
        double endPointX = lineToClip.end.x;
        double endPointY = lineToClip.end.y;

        int startPointRegionCode = computeRegionCode(startPointX, startPointY);
        int endPointRegionCode = computeRegionCode(endPointX, endPointY);
        boolean isLineAccepted = false;

        while (true) {
            if (startPointRegionCode == REGION_INSIDE && endPointRegionCode == REGION_INSIDE) {
                isLineAccepted = true;
                break;
            } else if ((startPointRegionCode & endPointRegionCode) != 0) {
                break;
            } else {
                double intersectionPointX = 0;
                double intersectionPointY = 0;

                int outsideRegionCode = (startPointRegionCode != REGION_INSIDE) ? startPointRegionCode : endPointRegionCode;

                if ((outsideRegionCode & REGION_TOP) != 0) {
                    intersectionPointX = startPointX + (endPointX - startPointX) * (clipWindowMaxY - startPointY) / (endPointY - startPointY);
                    intersectionPointY = clipWindowMaxY;
                } else if ((outsideRegionCode & REGION_BOTTOM) != 0) {
                    intersectionPointX = startPointX + (endPointX - startPointX) * (clipWindowMinY - startPointY) / (endPointY - startPointY);
                    intersectionPointY = clipWindowMinY;
                } else if ((outsideRegionCode & REGION_RIGHT) != 0) {
                    intersectionPointY = startPointY + (endPointY - startPointY) * (clipWindowMaxX - startPointX) / (endPointX - startPointX);
                    intersectionPointX = clipWindowMaxX;
                } else if ((outsideRegionCode & REGION_LEFT) != 0) {
                    intersectionPointY = startPointY + (endPointY - startPointY) * (clipWindowMinX - startPointX) / (endPointX - startPointX);
                    intersectionPointX = clipWindowMinX;
                }

                if (outsideRegionCode == startPointRegionCode) {
                    startPointX = intersectionPointX;
                    startPointY = intersectionPointY;
                    startPointRegionCode = computeRegionCode(startPointX, startPointY);
                } else {
                    endPointX = intersectionPointX;
                    endPointY = intersectionPointY;
                    endPointRegionCode = computeRegionCode(endPointX, endPointY);
                }
            }
        }

        if (isLineAccepted) {
            return new Line(new Point(startPointX, startPointY), new Point(endPointX, endPointY));
        } else {
            return null;
        }
    }
}