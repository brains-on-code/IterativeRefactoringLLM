package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping algorithm implementation.
 *
 * Clips a line segment to a rectangular clipping window.
 */
public class Class1 {

    // Region codes for Cohen–Sutherland
    private static final int INSIDE = 0;  // 0000
    private static final int LEFT = 1;    // 0001
    private static final int RIGHT = 2;   // 0010
    private static final int BOTTOM = 4;  // 0100
    private static final int TOP = 8;     // 1000

    // Clipping window boundaries
    double xMin;
    double yMin;
    double xMax;
    double yMax;

    public Class1(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Compute the Cohen–Sutherland region code for a point (x, y).
     */
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

    /**
     * Clip the given line to the clipping window.
     *
     * @param line the line to be clipped
     * @return the clipped line, or null if the line lies completely outside
     */
    public Line method2(Line line) {
        double x0 = line.start.var5;
        double y0 = line.start.var6;
        double x1 = line.end.var5;
        double y1 = line.end.var6;

        int outCode0 = computeOutCode(x0, y0);
        int outCode1 = computeOutCode(x1, y1);
        boolean accept = false;

        while (true) {
            if ((outCode0 == 0) && (outCode1 == 0)) {
                // Both endpoints lie inside the window
                accept = true;
                break;
            } else if ((outCode0 & outCode1) != 0) {
                // Logical AND is not 0: line is outside the window
                break;
            } else {
                // At least one endpoint is outside the window; clip it
                double x = 0;
                double y = 0;

                // Choose an endpoint that is outside the window
                int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

                // Find intersection point
                if ((outCodeOut & TOP) != 0) {
                    // Point is above the clip window
                    x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                    y = yMax;
                } else if ((outCodeOut & BOTTOM) != 0) {
                    // Point is below the clip window
                    x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                    y = yMin;
                } else if ((outCodeOut & RIGHT) != 0) {
                    // Point is to the right of clip window
                    y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                    x = xMax;
                } else if ((outCodeOut & LEFT) != 0) {
                    // Point is to the left of clip window
                    y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                    x = xMin;
                }

                // Replace the outside point with intersection point and update code
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
        } else {
            return null;
        }
    }
}