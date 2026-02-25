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

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] clippedParams = computeClippingParameters(p, q);
        if (clippedParams == null) {
            return null;
        }

        double t0 = clippedParams[0];
        double t1 = clippedParams[1];

        return createClippedLine(line, t0, t1, dx, dy);
    }

    /**
     * Computes the valid parameter range [t0, t1] for which the line
     * lies inside the clipping window.
     *
     * @param p array of p values for each boundary
     * @param q array of q values for each boundary
     * @return array containing {t0, t1}, or {@code null} if the line is outside
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double t0 = 0.0;
        double t1 = 1.0;

        for (int i = 0; i < 4; i++) {
            double pi = p[i];
            double qi = q[i];

            if (pi == 0) {
                if (qi < 0) {
                    return null; // Line is parallel and outside this boundary
                }
                continue; // Line is parallel and inside or on this boundary
            }

            double t = qi / pi;

            if (pi < 0) {
                if (t > t1) {
                    return null; // Line is outside
                }
                if (t > t0) {
                    t0 = t; // Update lower bound
                }
            } else { // pi > 0
                if (t < t0) {
                    return null; // Line is outside
                }
                if (t < t1) {
                    t1 = t; // Update upper bound
                }
            }
        }

        return new double[] {t0, t1};
    }

    /**
     * Constructs the clipped line segment using the given parameters.
     *
     * @param line original line
     * @param t0   lower parameter bound
     * @param t1   upper parameter bound
     * @param dx   delta x of the original line
     * @param dy   delta y of the original line
     * @return the clipped line segment
     */
    private Line createClippedLine(Line line, double t0, double t1, double dx, double dy) {
        double clippedX1 = line.start.x + t0 * dx;
        double clippedY1 = line.start.y + t0 * dy;
        double clippedX2 = line.start.x + t1 * dx;
        double clippedY2 = line.start.y + t1 * dy;

        Point start = new Point(clippedX1, clippedY1);
        Point end = new Point(clippedX2, clippedY2);

        return new Line(start, end);
    }
}