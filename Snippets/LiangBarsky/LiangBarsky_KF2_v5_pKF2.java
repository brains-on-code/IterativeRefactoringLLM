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

    private static final int BOUNDARY_COUNT = 4;

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

    /**
     * Creates the array of p coefficients used in the Liang–Barsky inequalities.
     *
     * p represents the direction of the line relative to each clipping boundary.
     */
    private double[] createPArray(double dx, double dy) {
        return new double[] {
            -dx, // left boundary
            dx,  // right boundary
            -dy, // bottom boundary
            dy   // top boundary
        };
    }

    /**
     * Creates the array of q constants used in the Liang–Barsky inequalities.
     *
     * q represents the distance from the line's starting point to each boundary.
     */
    private double[] createQArray(Line line) {
        return new double[] {
            line.start.x - xMin, // left boundary
            xMax - line.start.x, // right boundary
            line.start.y - yMin, // bottom boundary
            yMax - line.start.y  // top boundary
        };
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

        for (int i = 0; i < BOUNDARY_COUNT; i++) {
            double pi = p[i];
            double qi = q[i];

            // Line is parallel to this boundary
            if (pi == 0) {
                // If outside and parallel, the line is completely outside
                if (qi < 0) {
                    return null;
                }
                // Otherwise, it is inside or on the boundary; no update needed
                continue;
            }

            double t = qi / pi;

            if (pi < 0) {
                // Potential entering point
                tEnter = updateEnteringParameter(tEnter, tLeave, t);
                if (tEnter < 0) {
                    return null;
                }
            } else {
                // Potential leaving point
                tLeave = updateLeavingParameter(tEnter, tLeave, t);
                if (tLeave < 0) {
                    return null;
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

    /**
     * Updates the entering parameter tEnter.
     *
     * @param currentEnter current tEnter value
     * @param currentLeave current tLeave value
     * @param candidate    candidate t value for entering
     * @return updated tEnter, or -1.0 if the segment is outside
     */
    private double updateEnteringParameter(double currentEnter, double currentLeave, double candidate) {
        if (candidate > currentLeave) {
            // Entering point is after the leaving point: no visible segment
            return -1.0;
        }
        return Math.max(currentEnter, candidate);
    }

    /**
     * Updates the leaving parameter tLeave.
     *
     * @param currentEnter current tEnter value
     * @param currentLeave current tLeave value
     * @param candidate    candidate t value for leaving
     * @return updated tLeave, or -1.0 if the segment is outside
     */
    private double updateLeavingParameter(double currentEnter, double currentLeave, double candidate) {
        if (candidate < currentEnter) {
            // Leaving point is before the entering point: no visible segment
            return -1.0;
        }
        return Math.min(currentLeave, candidate);
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