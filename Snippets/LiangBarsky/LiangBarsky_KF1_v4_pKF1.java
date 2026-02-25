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
        double deltaX = line.end.x - line.start.x;
        double deltaY = line.end.y - line.start.y;

        double[] pValues = {-deltaX, deltaX, -deltaY, deltaY};
        double[] qValues = {
            line.start.x - minX,
            maxX - line.start.x,
            line.start.y - minY,
            maxY - line.start.y
        };

        double[] clipParameters = computeClipParameters(pValues, qValues);

        if (clipParameters == null) {
            return null;
        }

        double tEnter = clipParameters[0];
        double tLeave = clipParameters[1];

        return computeClippedLine(line, tEnter, tLeave, deltaX, deltaY);
    }

    private double[] computeClipParameters(double[] pValues, double[] qValues) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double p = pValues[i];
            double q = qValues[i];

            if (p == 0 && q < 0) {
                return null;
            }

            double t = q / p;

            if (p < 0) {
                if (t > tLeave) {
                    return null;
                }
                if (t > tEnter) {
                    tEnter = t;
                }
            } else if (p > 0) {
                if (t < tEnter) {
                    return null;
                }
                if (t < tLeave) {
                    tLeave = t;
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

    private Line computeClippedLine(
        Line line,
        double tEnter,
        double tLeave,
        double deltaX,
        double deltaY
    ) {
        double xEnter = line.start.x + tEnter * deltaX;
        double yEnter = line.start.y + tEnter * deltaY;
        double xLeave = line.start.x + tLeave * deltaX;
        double yLeave = line.start.y + tLeave * deltaY;

        Point entryPoint = new Point(xEnter, yEnter);
        Point exitPoint = new Point(xLeave, yLeave);

        return new Line(entryPoint, exitPoint);
    }
}