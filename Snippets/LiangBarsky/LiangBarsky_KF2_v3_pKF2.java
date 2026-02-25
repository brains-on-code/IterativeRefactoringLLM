package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang–Barsky line clipping algorithm implementation.
 *
 * Clips a line segment against an axis-aligned rectangular clipping window
 * defined by (xMin, yMin) as the bottom-left corner and (xMax, yMax) as the
 * top-right corner.
 */
public class LiangBarsky {

    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;

    /**
     * Creates a new Liang–Barsky clipper for the given rectangular window.
     *
     * @param xMin minimum x of the clipping window
     * @param yMin minimum y of the clipping window
     * @param xMax maximum x of the clipping window
     * @param yMax maximum y of the clipping window
     */
    public LiangBarsky(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Clips the given line segment against the configured clipping window.
     *
     * @param line line segment to clip
     * @return the clipped line segment, or {@code null} if it lies completely
     *         outside the clipping window
     */
    public Line liangBarskyClip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        // p and q arrays encode the inequalities for each clipping boundary:
        //   p[i] * t <= q[i], for i in {0..3}
        // where t is the parametric value along the line segment.
        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin, // left
            xMax - line.start.x, // right
            line.start.y - yMin, // bottom
            yMax - line.start.y  // top
        };

        double[] clippedParams = computeClippingParameters(p, q);
        if (clippedParams == null) {
            return null;
        }

        double tEnter = clippedParams[0];
        double tLeave = clippedParams[1];

        return createClippedLine(line, tEnter, tLeave, dx, dy);
    }

    /**
     * Computes the entering and leaving parameter values (tEnter, tLeave)
     * for the line segment with respect to the clipping window.
     *
     * @param p coefficients for the parametric inequalities
     * @param q constants for the parametric inequalities
     * @return array {tEnter, tLeave}, or {@code null} if the line is fully outside
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double pi = p[i];
            double qi = q[i];

            // Line is parallel to this clipping boundary
            if (pi == 0) {
                // If the line is outside this boundary, reject it
                if (qi < 0) {
                    return null;
                }
                // Otherwise, it is parallel and inside or on the boundary; no update
                continue;
            }

            double t = qi / pi;

            if (pi < 0) {
                // Potential entering point
                if (t > tLeave) {
                    return null; // enters after it leaves: no intersection
                }
                if (t > tEnter) {
                    tEnter = t;
                }
            } else {
                // Potential leaving point
                if (t < tEnter) {
                    return null; // leaves before it enters: no intersection
                }
                if (t < tLeave) {
                    tLeave = t;
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

    /**
     * Constructs the clipped line segment from the original line and the
     * computed entering and leaving parameter values.
     *
     * @param line   original line segment
     * @param tEnter entering parameter value
     * @param tLeave leaving parameter value
     * @param dx     delta x of the original line
     * @param dy     delta y of the original line
     * @return clipped line segment
     */
    private Line createClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        Point start = line.start;

        double clippedX1 = start.x + tEnter * dx;
        double clippedY1 = start.y + tEnter * dy;
        double clippedX2 = start.x + tLeave * dx;
        double clippedY2 = start.y + tLeave * dy;

        return new Line(
            new Point(clippedX1, clippedY1),
            new Point(clippedX2, clippedY2)
        );
    }
}