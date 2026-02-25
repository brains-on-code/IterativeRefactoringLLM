package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen-Sutherland Line Clipping Algorithm.
 *
 * Clips a line segment to a rectangular window defined by (xMin, yMin) and (xMax, yMax).
 * Each endpoint is assigned a region code, and the algorithm determines whether the
 * line is fully inside, fully outside, or partially inside the window.
 *
 * Reference:
 * https://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm
 */
public class CohenSutherland {

    /** Region code: inside the clipping window (0000). */
    private static final int INSIDE = 0;

    /** Region code: left of the clipping window (0001). */
    private static final int LEFT = 1;

    /** Region code: right of the clipping window (0010). */
    private static final int RIGHT = 2;

    /** Region code: below the clipping window (0100). */
    private static final int BOTTOM = 4;

    /** Region code: above the clipping window (1000). */
    private static final int TOP = 8;

    /** Minimum x-coordinate of the clipping window. */
    private final double xMin;

    /** Minimum y-coordinate of the clipping window. */
    private final double yMin;

    /** Maximum x-coordinate of the clipping window. */
    private final double xMax;

    /** Maximum y-coordinate of the clipping window. */
    private final double yMax;

    public CohenSutherland(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Computes the region code for a point (x, y) relative to the clipping window.
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
     * Applies the Cohen-Sutherland algorithm to clip the given line to the
     * clipping window. Returns the clipped line, or {@code null} if the line
     * lies completely outside the window.
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
            // Trivial accept: both endpoints are inside.
            if (code1 == 0 && code2 == 0) {
                accept = true;
                break;
            }

            // Trivial reject: both endpoints share an outside region.
            if ((code1 & code2) != 0) {
                break;
            }

            // At least one endpoint is outside; find intersection.
            double x = 0;
            double y = 0;

            // Choose an endpoint that is outside the window.
            int codeOut = (code1 != 0) ? code1 : code2;

            // Compute intersection point with the appropriate window boundary.
            if ((codeOut & TOP) != 0) {
                // Above the window.
                x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                y = yMax;
            } else if ((codeOut & BOTTOM) != 0) {
                // Below the window.
                x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                y = yMin;
            } else if ((codeOut & RIGHT) != 0) {
                // Right of the window.
                y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                x = xMax;
            } else if ((codeOut & LEFT) != 0) {
                // Left of the window.
                y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                x = xMin;
            }

            // Replace the outside endpoint with the intersection point and recompute its code.
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

        if (accept) {
            return new Line(new Point(x1, y1), new Point(x2, y2));
        }

        // Line is completely outside the clipping window.
        return null;
    }
}