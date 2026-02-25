package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping algorithm implementation.
 *
 * Clips a line segment to a rectangular clipping window.
 */
public class Class1 {

    // Region codes
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

    public Class1(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /** Compute region code for a point (x, y). */
    private int computeOutCode(double x, double y) {
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

    /** Clip the given line to the clipping window. Returns null if fully outside. */
    public Line method2(Line line) {
        double x0 = line.start.var5;
        double y0 = line.start.var6;
        double x1 = line.end.var5;
        double y1 = line.end.var6;

        int outCode0 = computeOutCode(x0, y0);
        int outCode1 = computeOutCode(x1, y1);
        boolean accept = false;

        while (true) {
            if (outCode0 == INSIDE && outCode1 == INSIDE) {
                // Both endpoints inside: trivially accept
                accept = true;
                break;
            } else if ((outCode0 & outCode1) != 0) {
                // Logical AND is not 0: trivially reject
                break;
            } else {
                // At least one endpoint is outside the clipping rectangle
                double x = 0;
                double y = 0;

                // Pick the endpoint that is outside
                int outCodeOut = (outCode0 != INSIDE) ? outCode0 : outCode1;

                if ((outCodeOut & TOP) != 0) {
                    // Point is above the clip rectangle
                    x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                    y = yMax;
                } else if ((outCodeOut & BOTTOM) != 0) {
                    // Point is below the clip rectangle
                    x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                    y = yMin;
                } else if ((outCodeOut & RIGHT) != 0) {
                    // Point is to the right of clip rectangle
                    y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                    x = xMax;
                } else if ((outCodeOut & LEFT) != 0) {
                    // Point is to the left of clip rectangle
                    y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                    x = xMin;
                }

                // Replace the outside point with intersection point and recompute code
                if (outCodeOut == outCode0) {
                    x0 = x;
                    y0 = y;
                    outCode0 = computeOutCode(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    outCode1 = computeOutCode(x1, y1);
                }
            }
        }

        if (accept) {
            return new Line(new Point(x0, y0), new Point(x1, y1));
        }
        return null;
    }
}