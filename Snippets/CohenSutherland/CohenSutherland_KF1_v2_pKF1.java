package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping implementation.
 */
public class CohenSutherlandClipper {

    // Region codes
    private static final int INSIDE = 0;  // 0000
    private static final int LEFT = 1;    // 0001
    private static final int RIGHT = 2;   // 0010
    private static final int BOTTOM = 4;  // 0100
    private static final int TOP = 8;     // 1000

    // Clipping window boundaries
    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    public CohenSutherlandClipper(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    private int computeRegionCode(double x, double y) {
        int code = INSIDE;

        if (x < xMin) {
            code |= LEFT;
        } else if (x > xMax) {
            code |= RIGHT;
        }

        if (y < yMin) {
            code |= BOTTOM;
        } else if (y > yMax) {
            code |= TOP;
        }

        return code;
    }

    public Line clip(Line line) {
        double x0 = line.start.x;
        double y0 = line.start.y;
        double x1 = line.end.x;
        double y1 = line.end.y;

        int regionCodeStart = computeRegionCode(x0, y0);
        int regionCodeEnd = computeRegionCode(x1, y1);
        boolean accept = false;

        while (true) {
            if ((regionCodeStart == 0) && (regionCodeEnd == 0)) {
                accept = true;
                break;
            } else if ((regionCodeStart & regionCodeEnd) != 0) {
                break;
            } else {
                double x = 0;
                double y = 0;

                int regionCodeOut = (regionCodeStart != 0) ? regionCodeStart : regionCodeEnd;

                if ((regionCodeOut & TOP) != 0) {
                    x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                    y = yMax;
                } else if ((regionCodeOut & BOTTOM) != 0) {
                    x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                    y = yMin;
                } else if ((regionCodeOut & RIGHT) != 0) {
                    y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                    x = xMax;
                } else if ((regionCodeOut & LEFT) != 0) {
                    y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                    x = xMin;
                }

                if (regionCodeOut == regionCodeStart) {
                    x0 = x;
                    y0 = y;
                    regionCodeStart = computeRegionCode(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    regionCodeEnd = computeRegionCode(x1, y1);
                }
            }
        }

        if (accept) {
            return new Line(new Point(x0, y0), new Point(x1, y1));
        } else {
            return null;
        }
    }
}