package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Implements the Liang–Barsky line clipping algorithm for a rectangular window.
 */
public class LiangBarskyClipper {

    /** Left boundary of the clipping window. */
    private final double xMin;

    /** Bottom boundary of the clipping window. */
    private final double yMin;

    /** Right boundary of the clipping window. */
    private final double xMax;

    /** Top boundary of the clipping window. */
    private final double yMax;

    /**
     * Creates a new Liang–Barsky clipper for the given rectangular window.
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

        return buildClippedLine(line, parameters[0], parameters[1], dx, dy);
    }

    /**
     * Computes the entering and leaving parameter values (t0, t1) for the
     * Liang–Barsky algorithm.
     *
     * @param p array of p values (direction-related)
     * @param q array of q values (boundary-related)
     * @return array {t0, t1} if the line intersects the window, otherwise {@code null}
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
                // Potentially entering the window
                if (r > tLeave) {
                    return null;
                }
                if (r > tEnter) {
                    tEnter = r;
                }
            } else if (p[i] > 0) {
                // Potentially leaving the window
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
     * Constructs the clipped line segment from the parameter values.
     *
     * @param line original line
     * @param tEnter entering parameter
     * @param tLeave leaving parameter
     * @param dx delta x of the original line
     * @param dy delta y of the original line
     * @return the clipped line segment
     */
    private Line buildClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        double x1 = line.start.x + tEnter * dx;
        double y1 = line.start.y + tEnter * dy;
        double x2 = line.start.x + tLeave * dx;
        double y2 = line.start.y + tLeave * dy;

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
}