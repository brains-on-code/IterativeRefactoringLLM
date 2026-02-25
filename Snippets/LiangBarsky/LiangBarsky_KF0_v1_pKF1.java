package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * @author shikarisohan
 * @since 10/5/24
 *
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

    // Liang-Barsky algorithm to return the clipped line
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

        double[] clippingParameters = computeClippingParameters(pValues, qValues);

        if (clippingParameters == null) {
            return null; // Line is outside the clipping window
        }

        double tEnter = clippingParameters[0];
        double tLeave = clippingParameters[1];

        return createClippedLine(line, tEnter, tLeave, deltaX, deltaY);
    }

    // Compute the entering and leaving parameters (tEnter, tLeave) for the line
    private double[] computeClippingParameters(double[] pValues, double[] qValues) {
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

    // Create the clipped line segment based on tEnter and tLeave
    private Line createClippedLine(Line originalLine, double tEnter, double tLeave, double deltaX, double deltaY) {
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