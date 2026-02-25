package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang-Barsky line clipping algorithm implementation.
 *
 * Clipping window boundaries are defined as (xMin, yMin) and (xMax, yMax).
 * The algorithm computes the clipped line segment if it's partially or
 * fully inside the clipping window.
 */
public class LiangBarsky {

    // Clipping window boundaries
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
     * Clips the given line against the rectangular clipping window.
     *
     * @param line the line to be clipped
     * @return the clipped line segment, or null if the line lies completely outside
     */
    public Line clip(Line line) {
        double deltaX = line.end.x - line.start.x;
        double deltaY = line.end.y - line.start.y;

        double[] pValues = {-deltaX, deltaX, -deltaY, deltaY};
        double[] qValues = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] clipParameters = computeClipParameters(pValues, qValues);
        if (clipParameters == null) {
            return null; // Line is outside the clipping window
        }

        double tEnter = clipParameters[0];
        double tLeave = clipParameters[1];

        return createClippedLine(line, tEnter, tLeave, deltaX, deltaY);
    }

    /**
     * Computes the entering and leaving parameters (tEnter, tLeave) for the line.
     *
     * @param pValues array of p values for each boundary
     * @param qValues array of q values for each boundary
     * @return array containing {tEnter, tLeave}, or null if the line is outside
     */
    private double[] computeClipParameters(double[] pValues, double[] qValues) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double p = pValues[i];
            double q = qValues[i];

            if (p == 0) {
                if (q < 0) {
                    return null; // Line is parallel and outside the boundary
                }
                continue; // Line is parallel and inside or on the boundary
            }

            double t = q / p;

            if (p < 0) {
                if (t > tLeave) {
                    return null; // Line is outside
                }
                if (t > tEnter) {
                    tEnter = t; // Update entering parameter
                }
            } else { // p > 0
                if (t < tEnter) {
                    return null; // Line is outside
                }
                if (t < tLeave) {
                    tLeave = t; // Update leaving parameter
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

    /**
     * Creates the clipped line segment based on entering and leaving parameters.
     *
     * @param originalLine the original line
     * @param tEnter       entering parameter
     * @param tLeave       leaving parameter
     * @param deltaX       change in x over the line
     * @param deltaY       change in y over the line
     * @return the clipped line segment
     */
    private Line createClippedLine(
        Line originalLine,
        double tEnter,
        double tLeave,
        double deltaX,
        double deltaY
    ) {
        double clippedStartX = originalLine.start.x + tEnter * deltaX;
        double clippedStartY = originalLine.start.y + tEnter * deltaY;
        double clippedEndX = originalLine.start.x + tLeave * deltaX;
        double clippedEndY = originalLine.start.y + tLeave * deltaY;

        return new Line(
            new Point(clippedStartX, clippedStartY),
            new Point(clippedEndX, clippedEndY)
        );
    }
}