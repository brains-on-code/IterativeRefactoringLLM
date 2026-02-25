package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * The Liang-Barsky line clipping algorithm is an efficient algorithm for
 * line clipping against a rectangular window. It is based on the parametric
 * equation of a line and checks the intersections of the line with the
 * window boundaries. This algorithm calculates the intersection points,
 * if any, and returns the clipped line that lies inside the window.
 *
 * Reference:
 * https://en.wikipedia.org/wiki/Liang%E2%80%93Barsky_algorithm
 *
 * Clipping window boundaries are defined as (xMin, yMin) and (xMax, yMax).
 * The algorithm computes the clipped line segment if it's partially or
 * fully inside the clipping window.
 */
public class LiangBarsky {

    private static final int BOUNDARY_COUNT = 4;

    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;

    public LiangBarsky(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Applies the Liang-Barsky algorithm to clip the given line
     * against the rectangular clipping window.
     *
     * @param line the line to be clipped
     * @return the clipped line, or {@code null} if the line lies completely outside
     */
    public Line liangBarskyClip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = createPArray(dx, dy);
        double[] q = createQArray(line);

        double[] clippedParams = computeClippingParameters(p, q);
        if (clippedParams == null) {
            return null;
        }

        double tEnter = clippedParams[0];
        double tLeave = clippedParams[1];

        return createClippedLine(line, tEnter, tLeave, dx, dy);
    }

    private double[] createPArray(double dx, double dy) {
        return new double[] { -dx, dx, -dy, dy };
    }

    private double[] createQArray(Line line) {
        return new double[] {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };
    }

    /**
     * Computes the valid parameter range [tEnter, tLeave] for which the line
     * lies inside the clipping window.
     *
     * @param p array of p values for each boundary
     * @param q array of q values for each boundary
     * @return array containing {tEnter, tLeave}, or {@code null} if the line is outside
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < BOUNDARY_COUNT; i++) {
            double pi = p[i];
            double qi = q[i];

            if (pi == 0) {
                if (qi < 0) {
                    return null;
                }
                continue;
            }

            double t = qi / pi;

            if (pi < 0) {
                if (!updateEnteringParameter(t, tLeave)) {
                    return null;
                }
                tEnter = Math.max(tEnter, t);
            } else {
                if (!updateLeavingParameter(t, tEnter)) {
                    return null;
                }
                tLeave = Math.min(tLeave, t);
            }
        }

        return new double[] { tEnter, tLeave };
    }

    private boolean updateEnteringParameter(double t, double tLeave) {
        return t <= tLeave;
    }

    private boolean updateLeavingParameter(double t, double tEnter) {
        return t >= tEnter;
    }

    /**
     * Constructs the clipped line segment using the given parameters.
     *
     * @param line original line
     * @param tEnter lower parameter bound
     * @param tLeave upper parameter bound
     * @param dx   delta x of the original line
     * @param dy   delta y of the original line
     * @return the clipped line segment
     */
    private Line createClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        Point start = new Point(
            line.start.x + tEnter * dx,
            line.start.y + tEnter * dy
        );
        Point end = new Point(
            line.start.x + tLeave * dx,
            line.start.y + tLeave * dy
        );

        return new Line(start, end);
    }
}