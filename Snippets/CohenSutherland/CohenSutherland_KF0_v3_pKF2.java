package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Cohen–Sutherland line clipping algorithm.
 *
 * Clips a line segment to an axis-aligned rectangular window defined by
 * (xMin, yMin) as the bottom-left corner and (xMax, yMax) as the top-right corner.
 *
 * Reference:
 * https://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm
 */
public class CohenSutherland {

    /** Region code: point is inside the clipping window (0000). */
    private static final int INSIDE = 0;

    /** Region code bit: point is to the left of the window (0001). */
    private static final int LEFT = 1;

    /** Region code bit: point is to the right of the window (0010). */
    private static final int RIGHT = 2;

    /** Region code bit: point is below the window (0100). */
    private static final int BOTTOM = 4;

    /** Region code bit: point is above the window (1000). */
    private static final int TOP = 8;

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
     * Computes the Cohen–Sutherland region code for a point (x, y)
     * relative to the current clipping window.
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
     * Clips the given line segment to the clipping window using the
     * Cohen–Sutherland algorithm.
     *
     * @param line the line segment to clip
     * @return the clipped line segment, or {@code null} if it lies completely outside
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
            if (code1 == INSIDE && code2 == INSIDE) {
                accept = true;
                break;
            }

            if ((code1 & code2) != 0) {
                break;
            }

            double x = 0;
            double y = 0;

            int codeOut = (code1 != INSIDE) ? code1 : code2;

            if ((codeOut & TOP) != 0) {
                x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                y = yMax;
            } else if ((codeOut & BOTTOM) != 0) {
                x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                y = yMin;
            } else if ((codeOut & RIGHT) != 0) {
                y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                x = xMax;
            } else if ((codeOut & LEFT) != 0) {
                y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                x = xMin;
            }

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

        return null;
    }
}