package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class LiangBarsky {

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public LiangBarsky(double minX, double minY, double maxX, double maxY) {
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

        double[] clippedParameters = computeClippingParameters(pValues, qValues);

        if (clippedParameters == null) {
            return null;
        }

        return createClippedLine(line, clippedParameters[0], clippedParameters[1], deltaX, deltaY);
    }

    private double[] computeClippingParameters(double[] pValues, double[] qValues) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double p = pValues[i];
            double q = qValues[i];

            if (p == 0 && q < 0) {
                return null;
            }

            if (p != 0) {
                double t = q / p;

                if (p < 0) {
                    if (t > tLeave) {
                        return null;
                    }
                    if (t > tEnter) {
                        tEnter = t;
                    }
                } else { // p > 0
                    if (t < tEnter) {
                        return null;
                    }
                    if (t < tLeave) {
                        tLeave = t;
                    }
                }
            }
        }

        return new double[] {tEnter, tLeave};
    }

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