package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Implementation of the Cohen–Sutherland line clipping algorithm.
 * Clips a line segment to a rectangular clipping window.
 */
public class CohenSutherland {

    /** Outcode: point is inside the clipping window. */
    private static final int INSIDE = 0;  // 0000

    /** Outcode bit: point is to the left of the clipping window. */
    private static final int LEFT = 1;    // 0001

    /** Outcode bit: point is to the right of the clipping window. */
    private static final int RIGHT = 2;   // 0010

    /** Outcode bit: point is below the clipping window. */
    private static final int BOTTOM = 4;  // 0100

    /** Outcode bit: point is above the clipping window. */
    private static final int TOP = 8;     // 1000

    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    /**
     * Creates a new rectangular clipping window.
     *
     * @param xMin minimum x-coordinate of the window
     * @param yMin minimum y-coordinate of the window
     * @param xMax maximum x-coordinate of the window
     * @param yMax maximum y-coordinate of the window
     */
    public CohenSutherland(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Computes the Cohen–Sutherland outcode for a point relative to the
     * clipping window.
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return the outcode representing the point's position
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
     * Clips the given line segment to the clipping window.
     *
     * @param line the line segment to clip
     * @return the clipped line segment, or {@code null} if the line lies
     *         completely outside the clipping window
     */
    public Line cohenSutherlandClip(Line line) {
        double x1 = line.start.x;
        double y1 = line.start.y;
        double x2 = line.end.x;
        double y2 = line.end.y;

        int code1 = computeOutCode(x1, y1);
        int code2 = computeOutCode(x2, y2);
        boolean accept = false;

        while (true) {
            // Both endpoints are inside the window: trivially accept.
            if (code1 == INSIDE && code2 == INSIDE) {
                accept = true;
                break;
            }

            // Logical AND is non-zero: both endpoints share an outside region, trivially reject.
            if ((code1 & code2) != 0) {
                break;
            }

            // At least one endpoint is outside the window; select it.
            int codeOut = (code1 != INSIDE) ? code1 : code2;
            double x = 0;
            double y = 0;

            // Compute intersection point with the relevant clipping boundary.
            if ((codeOut & TOP) != 0) {
                // Above the window
                x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                y = yMax;
            } else if ((codeOut & BOTTOM) != 0) {
                // Below the window
                x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                y = yMin;
            } else if ((codeOut & RIGHT) != 0) {
                // To the right of the window
                y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                x = xMax;
            } else if ((codeOut & LEFT) != 0) {
                // To the left of the window
                y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                x = xMin;
            }

            // Replace the outside endpoint with the intersection point and recompute its outcode.
            if (codeOut == code1) {
                x1 = x;
                y1 = y;
                code1 = computeOutCode(x1, y1);
            } else {
                x2 = x;
                y2 = y;
                code2 = computeOutCode(x2, y2);
            }
        }

        if (!accept) {
            return null;
        }

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
}