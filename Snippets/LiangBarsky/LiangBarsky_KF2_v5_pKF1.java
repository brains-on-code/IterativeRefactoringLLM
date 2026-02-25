package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class LiangBarsky {

    private final double minWindowX;
    private final double maxWindowX;
    private final double minWindowY;
    private final double maxWindowY;

    public LiangBarsky(double minWindowX, double minWindowY, double maxWindowX, double maxWindowY) {
        this.minWindowX = minWindowX;
        this.minWindowY = minWindowY;
        this.maxWindowX = maxWindowX;
        this.maxWindowY = maxWindowY;
    }

    public Line clip(Line line) {
        double deltaX = line.end.x - line.start.x;
        double deltaY = line.end.y - line.start.y;

        double[] p = {-deltaX, deltaX, -deltaY, deltaY};
        double[] q = {
            line.start.x - minWindowX,
            maxWindowX - line.start.x,
            line.start.y - minWindowY,
            maxWindowY - line.start.y
        };

        double[] clippedParameters = computeClippingParameters(p, q);

        if (clippedParameters == null) {
            return null;
        }

        double enteringT = clippedParameters[0];
        double leavingT = clippedParameters[1];

        return createClippedLine(line, enteringT, leavingT, deltaX, deltaY);
    }

    private double[] computeClippingParameters(double[] p, double[] q) {
        double enteringT = 0.0;
        double leavingT = 1.0;

        for (int i = 0; i < 4; i++) {
            double directionCoefficient = p[i];
            double distanceToBoundary = q[i];

            if (directionCoefficient == 0 && distanceToBoundary < 0) {
                return null;
            }

            if (directionCoefficient != 0) {
                double t = distanceToBoundary / directionCoefficient;

                if (directionCoefficient < 0) {
                    if (t > leavingT) {
                        return null;
                    }
                    if (t > enteringT) {
                        enteringT = t;
                    }
                } else {
                    if (t < enteringT) {
                        return null;
                    }
                    if (t < leavingT) {
                        leavingT = t;
                    }
                }
            }
        }

        return new double[] {enteringT, leavingT};
    }

    private Line createClippedLine(
        Line originalLine,
        double enteringT,
        double leavingT,
        double deltaX,
        double deltaY
    ) {
        double clippedStartX = originalLine.start.x + enteringT * deltaX;
        double clippedStartY = originalLine.start.y + enteringT * deltaY;
        double clippedEndX = originalLine.start.x + leavingT * deltaX;
        double clippedEndY = originalLine.start.y + leavingT * deltaY;

        Point clippedStartPoint = new Point(clippedStartX, clippedStartY);
        Point clippedEndPoint = new Point(clippedEndX, clippedEndY);

        return new Line(clippedStartPoint, clippedEndPoint);
    }
}