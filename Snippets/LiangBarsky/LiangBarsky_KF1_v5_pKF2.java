package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Implements the Liang–Barsky line clipping algorithm for an
 * axis-aligned rectangular clipping window.
 */
public class LiangBarskyClipper {

    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    /**
     * Constructs a clipper for a rectangular window.
     *
     * @param xMin left boundary of the clipping window
     * @param yMin bottom boundary of the clipping window
     * @param xMax right boundary of the clipping window
     * @param yMax top boundary of the clipping window
     */
    public LiangBarskyClipper(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Clips the given line segment against the rectangular clipping window.
     *
     * @param line the line segment to be clipped
     * @return the clipped line segment, or {@code null} if it lies completely outside
     */
    public Line clip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        // p[i] and q[i] correspond to each boundary:
        // 0: left, 1: right, 2: bottom, 3: top
        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] parameters = computeClippingParameters(p, q);
        if (parameters == null) {
            return null;
        }

        double tEnter = parameters[0];
        double tLeave = parameters[1];

        return buildClippedLine(line, tEnter, tLeave, dx, dy);
    }

    /**
     * Computes the entering and leaving parameter values (tEnter, tLeave)
     * for the parametric line representation:
     * P(t) = P0 + t * (P1 - P0), 0 ≤ t ≤ 1.
     *
     * @param p direction-related values for each boundary
     * @param q boundary-related values for each boundary
     * @return array {tEnter, tLeave} if the line intersects the window,
     *         otherwise {@code null}
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) {
                // Line is parallel to this boundary and outside the window
                return null;
            }

            double r = q[i] / p[i];

            if (p[i] < 0) {
                // Potential entering point
                if (r > tLeave) {
                    return null;
                }
                if (r > tEnter) {
                    tEnter = r;
                }
            } else if (p[i] > 0) {
                // Potential leaving point
                if (r < tEnter) {
                    return null;
                }
                if (r < tLeave) {
                    tLeave = r;
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

    /**
     * Builds the clipped line segment from the parameter values.
     *
     * @param line   original line segment
     * @param tEnter entering parameter
     * @param tLeave leaving parameter
     * @param dx     delta x of the original line
     * @param dy     delta y of the original line
     * @return clipped line segment
     */
    private Line buildClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        double x1 = line.start.x + tEnter * dx;
        double y1 = line.start.y + tEnter * dy;
        double x2 = line.start.x + tLeave * dx;
        double y2 = line.start.y + tLeave * dy;

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
}