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

        double[] directionCoefficients = {-deltaX, deltaX, -deltaY, deltaY};
        double[] boundaryDistances = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] clipParameters = computeClipParameters(directionCoefficients, boundaryDistances);
        if (clipParameters == null) {
            return null; // Line is outside the clipping window
        }

        double enteringParameter = clipParameters[0];
        double leavingParameter = clipParameters[1];

        return createClippedLine(line, enteringParameter, leavingParameter, deltaX, deltaY);
    }

    /**
     * Computes the entering and leaving parameters (tEnter, tLeave) for the line.
     *
     * @param directionCoefficients array of p values for each boundary
     * @param boundaryDistances     array of q values for each boundary
     * @return array containing {tEnter, tLeave}, or null if the line is outside
     */
    private double[] computeClipParameters(double[] directionCoefficients, double[] boundaryDistances) {
        double enteringParameter = 0.0;
        double leavingParameter = 1.0;

        for (int i = 0; i < 4; i++) {
            double directionCoefficient = directionCoefficients[i];
            double boundaryDistance = boundaryDistances[i];

            if (directionCoefficient == 0) {
                if (boundaryDistance < 0) {
                    return null; // Line is parallel and outside the boundary
                }
                continue; // Line is parallel and inside or on the boundary
            }

            double parameter = boundaryDistance / directionCoefficient;

            if (directionCoefficient < 0) {
                if (parameter > leavingParameter) {
                    return null; // Line is outside
                }
                if (parameter > enteringParameter) {
                    enteringParameter = parameter; // Update entering parameter
                }
            } else { // directionCoefficient > 0
                if (parameter < enteringParameter) {
                    return null; // Line is outside
                }
                if (parameter < leavingParameter) {
                    leavingParameter = parameter; // Update leaving parameter
                }
            }
        }

        return new double[] {enteringParameter, leavingParameter};
    }

    /**
     * Creates the clipped line segment based on entering and leaving parameters.
     *
     * @param originalLine      the original line
     * @param enteringParameter entering parameter
     * @param leavingParameter  leaving parameter
     * @param deltaX            change in x over the line
     * @param deltaY            change in y over the line
     * @return the clipped line segment
     */
    private Line createClippedLine(
        Line originalLine,
        double enteringParameter,
        double leavingParameter,
        double deltaX,
        double deltaY
    ) {
        double clippedStartX = originalLine.start.x + enteringParameter * deltaX;
        double clippedStartY = originalLine.start.y + enteringParameter * deltaY;
        double clippedEndX = originalLine.start.x + leavingParameter * deltaX;
        double clippedEndY = originalLine.start.y + leavingParameter * deltaY;

        return new Line(
            new Point(clippedStartX, clippedStartY),
            new Point(clippedEndX, clippedEndY)
        );
    }
}