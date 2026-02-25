package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class CohenSutherland {

    // Region codes for the Cohen–Sutherland algorithm
    private static final int INSIDE = 0;  // 0000
    private static final int LEFT   = 1;  // 0001
    private static final int RIGHT  = 2;  // 0010
    private static final int BOTTOM = 4;  // 0100
    private static final int TOP    = 8;  // 1000

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

    /**
     * Compute the region code for a point (x, y) using the clipping rectangle.
     */
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

    /**
     * Clip the given line to the rectangle defined in the constructor.
     * Returns a new clipped Line, or null if the line lies completely outside.
     */
    public Line cohenSutherlandClip(Line line) {
        double x1 = line.start.x;
        double y1 = line.start.y;
        double x2 = line.end.x;
        double y2 = line.end.y;

        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);
        boolean accept = false;

        while (true) {
            // Trivial accept: both endpoints inside
            if (code1 == 0 && code2 == 0) {
                accept = true;
                break;
            }
            // Trivial reject: logical AND is not 0, line is outside
            if ((code1 & code2) != 0) {
                break;
            }

            // At least one endpoint is outside; pick it.
            int codeOut = (code1 != 0) ? code1 : code2;
            double x = 0;
            double y = 0;

            // Find intersection point with the clipping boundary
            if ((codeOut & TOP) != 0) {
                // Point is above the clip rectangle
                x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                y = yMax;
            } else if ((codeOut & BOTTOM) != 0) {
                // Point is below the clip rectangle
                x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                y = yMin;
            } else if ((codeOut & RIGHT) != 0) {
                // Point is to the right of clip rectangle
                y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                x = xMax;
            } else if ((codeOut & LEFT) != 0) {
                // Point is to the left of clip rectangle
                y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                x = xMin;
            }

            // Replace the outside point with the intersection point
            if (codeOut == code1) {
                x1 = x;
                y1 = y;
                code1 = computeCode(x1, y1);
            } else {
                x2 = x;
                y2 = y;
                code2 = computeCode(x2, y2);
            }
        }

        if (!accept) {
            return null;
        }

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
}