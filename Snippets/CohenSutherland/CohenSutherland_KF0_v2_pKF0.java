package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen-Sutherland Line Clipping Algorithm.
 *
 * Clips a line segment to a rectangular window defined by (xMin, yMin) and (xMax, yMax).
 */
public class CohenSutherland {

    // Region codes for the 9 regions
    private static final int INSIDE = 0;  // 0000
    private static final int LEFT   = 1;  // 0001
    private static final int RIGHT  = 2;  // 0010
    private static final int BOTTOM = 4;  // 0100
    private static final int TOP    = 8;  // 1000

    // Clipping window boundaries
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

    /** Compute the region code for a point (x, y). */
    private int computeCode(double x, double y) {
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

    /** Cohen-Sutherland algorithm to return the clipped line, or null if fully outside. */
    public Line cohenSutherlandClip(Line line) {
        double x1 = line.start.x;
        double y1 = line.start.y;
        double x2 = line.end.x;
        double y2 = line.end.y;

        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);

        while (true) {
            boolean isStartInside = code1 == INSIDE;
            boolean isEndInside = code2 == INSIDE;

            if (isStartInside && isEndInside) {
                return new Line(new Point(x1, y1), new Point(x2, y2));
            }

            if ((code1 & code2) != 0) {
                return null;
            }

            double xIntersection = 0;
            double yIntersection = 0;

            int outsideCode = (code1 != INSIDE) ? code1 : code2;

            if ((outsideCode & TOP) != 0) {
                xIntersection = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                yIntersection = yMax;
            } else if ((outsideCode & BOTTOM) != 0) {
                xIntersection = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                yIntersection = yMin;
            } else if ((outsideCode & RIGHT) != 0) {
                yIntersection = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                xIntersection = xMax;
            } else if ((outsideCode & LEFT) != 0) {
                yIntersection = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                xIntersection = xMin;
            }

            if (outsideCode == code1) {
                x1 = xIntersection;
                y1 = yIntersection;
                code1 = computeCode(x1, y1);
            } else {
                x2 = xIntersection;
                y2 = yIntersection;
                code2 = computeCode(x2, y2);
            }
        }
    }
}