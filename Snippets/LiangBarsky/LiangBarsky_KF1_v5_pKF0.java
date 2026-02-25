package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang–Barsky line clipping algorithm implementation.
 *
 * Clips a line segment against an axis-aligned rectangular clipping window.
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

    public LiangBarskyClipper(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Clips the given line segment to the rectangular window.
     *
     * @param line the line segment to clip
     * @return the clipped line segment, or {@code null} if it lies completely outside
     */
    public Line clip(Line line) {
        Point start = line.start;
        Point end = line.end;

        double dx = end.x - start.x;
        double dy = end.y - start.y;

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            start.x - xMin,
            xMax - start.x,
            start.y - yMin,
            yMax - start.y
        };

        double[] parameters = computeClippingParameters(p, q);
        if (parameters == null) {
            return null;
        }

        double tEnter = parameters[0];
        double tLeave = parameters[1];

        return buildClippedLine(start, dx, dy, tEnter, tLeave);
    }

    /**
     * Computes the entering and leaving parameters (tEnter, tLeave) for the line segment
     * using the Liang–Barsky inequalities.
     *
     * @param p array of p values (direction-related)
     * @param q array of q values (boundary-related)
     * @return array {tEnter, tLeave} if the line intersects the window, otherwise {@code null}
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double pi = p[i];
            double qi = q[i];

            if (pi == 0) {
                if (qi < 0) {
                    return null;
                }
                continue;
            }

            double r = qi / pi;

            if (pi < 0) {
                if (r > tLeave) {
                    return null;
                }
                tEnter = Math.max(tEnter, r);
            } else {
                if (r < tEnter) {
                    return null;
                }
                tLeave = Math.min(tLeave, r);
            }
        }

        return new double[] {tEnter, tLeave};
    }

    /**
     * Constructs the clipped line segment from the original line start point and the
     * entering/leaving parameters.
     *
     * @param start  start point of the original line
     * @param dx     delta x of the original line
     * @param dy     delta y of the original line
     * @param tEnter entering parameter
     * @param tLeave leaving parameter
     * @return the clipped line segment
     */
    private Line buildClippedLine(Point start, double dx, double dy, double tEnter, double tLeave) {
        Point clippedStart = new Point(
            start.x + tEnter * dx,
            start.y + tEnter * dy
        );
        Point clippedEnd = new Point(
            start.x + tLeave * dx,
            start.y + tLeave * dy
        );

        return new Line(clippedStart, clippedEnd);
    }
}