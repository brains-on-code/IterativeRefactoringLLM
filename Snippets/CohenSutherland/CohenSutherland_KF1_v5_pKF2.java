package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping algorithm implementation.
 *
 * Clips a line segment to a rectangular clipping window.
 */
public class Class1 {

    /** Region code: point is inside the clipping window. */
    private static final int INSIDE = 0;  // 0000

    /** Region code bit: point is to the left of the clipping window. */
    private static final int LEFT = 1;    // 0001

    /** Region code bit: point is to the right of the clipping window. */
    private static final int RIGHT = 2;   // 0010

    /** Region code bit: point is below the clipping window. */
    private static final int BOTTOM = 4;  // 0100

    /** Region code bit: point is above the clipping window. */
    private static final int TOP = 8;     // 1000

    /** Minimum x-coordinate of the clipping window. */
    private final double xMin;

    /** Minimum y-coordinate of the clipping window. */
    private final double yMin;

    /** Maximum x-coordinate of the clipping window. */
    private final double xMax;

    /** Maximum y-coordinate of the clipping window. */
    private final double yMax;

    public Class1(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Computes the Cohen–Sutherland region code for a point (x, y).
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return region code indicating the point's position relative to the window
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
     * Clips the given line to the clipping window using the Cohen–Sutherland algorithm.
     *
     * @param line the line to be clipped
     * @return the clipped line, or {@code null} if the line lies completely outside
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
            // Trivial accept: both endpoints lie inside the window.
            if (outCode0 == INSIDE && outCode1 == INSIDE) {
                accept = true;
                break;
            }

            // Trivial reject: both endpoints share an outside region.
            if ((outCode0 & outCode1) != 0) {
                break;
            }

            // At least one endpoint is outside the window; clip it.
            double x = 0;
            double y = 0;

            // Choose an endpoint that is outside the window.
            int outCodeOut = (outCode0 != INSIDE) ? outCode0 : outCode1;

            // Find intersection point with the appropriate window boundary.
            if ((outCodeOut & TOP) != 0) {
                // Above the window.
                x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                y = yMax;
            } else if ((outCodeOut & BOTTOM) != 0) {
                // Below the window.
                x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                y = yMin;
            } else if ((outCodeOut & RIGHT) != 0) {
                // To the right of the window.
                y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                x = xMax;
            } else if ((outCodeOut & LEFT) != 0) {
                // To the left of the window.
                y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                x = xMin;
            }

            // Replace the outside point with the intersection point and update its code.
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

        if (accept) {
            return new Line(new Point(x0, y0), new Point(x1, y1));
        }
        return null;
    }
}