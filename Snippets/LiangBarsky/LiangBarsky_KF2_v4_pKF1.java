package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class LiangBarsky {

    private final double windowMinX;
    private final double windowMaxX;
    private final double windowMinY;
    private final double windowMaxY;

    public LiangBarsky(double windowMinX, double windowMinY, double windowMaxX, double windowMaxY) {
        this.windowMinX = windowMinX;
        this.windowMinY = windowMinY;
        this.windowMaxX = windowMaxX;
        this.windowMaxY = windowMaxY;
    }

    public Line clip(Line line) {
        double deltaX = line.end.x - line.start.x;
        double deltaY = line.end.y - line.start.y;

        double[] directionCoefficients = {-deltaX, deltaX, -deltaY, deltaY};
        double[] boundaryDistances = {
            line.start.x - windowMinX,
            windowMaxX - line.start.x,
            line.start.y - windowMinY,
            windowMaxY - line.start.y
        };

        double[] clippingParameters = computeClippingParameters(directionCoefficients, boundaryDistances);

        if (clippingParameters == null) {
            return null;
        }

        double enteringParameter = clippingParameters[0];
        double leavingParameter = clippingParameters[1];

        return createClippedLine(line, enteringParameter, leavingParameter, deltaX, deltaY);
    }

    private double[] computeClippingParameters(double[] directionCoefficients, double[] boundaryDistances) {
        double enteringParameter = 0.0;
        double leavingParameter = 1.0;

        for (int i = 0; i < 4; i++) {
            double direction = directionCoefficients[i];
            double distanceToBoundary = boundaryDistances[i];

            if (direction == 0 && distanceToBoundary < 0) {
                return null;
            }

            if (direction != 0) {
                double intersectionParameter = distanceToBoundary / direction;

                if (direction < 0) {
                    if (intersectionParameter > leavingParameter) {
                        return null;
                    }
                    if (intersectionParameter > enteringParameter) {
                        enteringParameter = intersectionParameter;
                    }
                } else {
                    if (intersectionParameter < enteringParameter) {
                        return null;
                    }
                    if (intersectionParameter < leavingParameter) {
                        leavingParameter = intersectionParameter;
                    }
                }
            }
        }

        return new double[] {enteringParameter, leavingParameter};
    }

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

        Point clippedStartPoint = new Point(clippedStartX, clippedStartY);
        Point clippedEndPoint = new Point(clippedEndX, clippedEndY);

        return new Line(clippedStartPoint, clippedEndPoint);
    }
}