package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class LiangBarsky {

    private final double clipMinX;
    private final double clipMaxX;
    private final double clipMinY;
    private final double clipMaxY;

    public LiangBarsky(double clipMinX, double clipMinY, double clipMaxX, double clipMaxY) {
        this.clipMinX = clipMinX;
        this.clipMinY = clipMinY;
        this.clipMaxX = clipMaxX;
        this.clipMaxY = clipMaxY;
    }

    public Line clip(Line line) {
        double deltaX = line.end.x - line.start.x;
        double deltaY = line.end.y - line.start.y;

        double[] directionCoefficients = {-deltaX, deltaX, -deltaY, deltaY};
        double[] boundaryDistances = {
            line.start.x - clipMinX,
            clipMaxX - line.start.x,
            line.start.y - clipMinY,
            clipMaxY - line.start.y
        };

        double[] clippingParameters = computeClippingParameters(directionCoefficients, boundaryDistances);

        if (clippingParameters == null) {
            return null;
        }

        double tEnter = clippingParameters[0];
        double tLeave = clippingParameters[1];

        return createClippedLine(line, tEnter, tLeave, deltaX, deltaY);
    }

    private double[] computeClippingParameters(double[] directionCoefficients, double[] boundaryDistances) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double directionCoefficient = directionCoefficients[i];
            double boundaryDistance = boundaryDistances[i];

            if (directionCoefficient == 0 && boundaryDistance < 0) {
                return null;
            }

            if (directionCoefficient != 0) {
                double tIntersection = boundaryDistance / directionCoefficient;

                if (directionCoefficient < 0) {
                    if (tIntersection > tLeave) {
                        return null;
                    }
                    if (tIntersection > tEnter) {
                        tEnter = tIntersection;
                    }
                } else {
                    if (tIntersection < tEnter) {
                        return null;
                    }
                    if (tIntersection < tLeave) {
                        tLeave = tIntersection;
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

        Point clippedStartPoint = new Point(clippedStartX, clippedStartY);
        Point clippedEndPoint = new Point(clippedEndX, clippedEndY);

        return new Line(clippedStartPoint, clippedEndPoint);
    }
}