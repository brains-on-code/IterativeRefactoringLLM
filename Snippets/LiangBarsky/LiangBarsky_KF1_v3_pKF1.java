package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang–Barsky line clipping algorithm implementation.
 */
public class LiangBarskyClipper {

    // Clipping window boundaries
    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;

    public LiangBarskyClipper(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Line clip(Line line) {
        double directionX = line.end.x - line.start.x;
        double directionY = line.end.y - line.start.y;

        double[] pCoefficients = {-directionX, directionX, -directionY, directionY};
        double[] qCoefficients = {
            line.start.x - minX,
            maxX - line.start.x,
            line.start.y - minY,
            maxY - line.start.y
        };

        double[] clippingParameters = computeClippingParameters(pCoefficients, qCoefficients);

        if (clippingParameters == null) {
            return null;
        }

        double enteringParameter = clippingParameters[0];
        double leavingParameter = clippingParameters[1];

        return computeClippedLine(line, enteringParameter, leavingParameter, directionX, directionY);
    }

    private double[] computeClippingParameters(double[] pCoefficients, double[] qCoefficients) {
        double enteringParameter = 0.0;
        double leavingParameter = 1.0;

        for (int i = 0; i < 4; i++) {
            double p = pCoefficients[i];
            double q = qCoefficients[i];

            if (p == 0 && q < 0) {
                return null;
            }

            double parameter = q / p;

            if (p < 0) {
                if (parameter > leavingParameter) {
                    return null;
                }
                if (parameter > enteringParameter) {
                    enteringParameter = parameter;
                }
            } else if (p > 0) {
                if (parameter < enteringParameter) {
                    return null;
                }
                if (parameter < leavingParameter) {
                    leavingParameter = parameter;
                }
            }
        }

        return new double[] {enteringParameter, leavingParameter};
    }

    private Line computeClippedLine(
        Line line,
        double enteringParameter,
        double leavingParameter,
        double directionX,
        double directionY
    ) {
        double xEnter = line.start.x + enteringParameter * directionX;
        double yEnter = line.start.y + enteringParameter * directionY;
        double xLeave = line.start.x + leavingParameter * directionX;
        double yLeave = line.start.y + leavingParameter * directionY;

        Point entryPoint = new Point(xEnter, yEnter);
        Point exitPoint = new Point(xLeave, yLeave);

        return new Line(entryPoint, exitPoint);
    }
}