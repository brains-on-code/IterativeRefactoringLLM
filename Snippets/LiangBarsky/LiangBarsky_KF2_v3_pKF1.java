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

        double[] p = {-deltaX, deltaX, -deltaY, deltaY};
        double[] q = {
            line.start.x - windowMinX,
            windowMaxX - line.start.x,
            line.start.y - windowMinY,
            windowMaxY - line.start.y
        };

        double[] clippingParameters = computeClippingParameters(p, q);

        if (clippingParameters == null) {
            return null;
        }

        double tEnter = clippingParameters[0];
        double tLeave = clippingParameters[1];

        return createClippedLine(line, tEnter, tLeave, deltaX, deltaY);
    }

    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            double direction = p[i];
            double distanceToBoundary = q[i];

            if (direction == 0 && distanceToBoundary < 0) {
                return null;
            }

            if (direction != 0) {
                double tIntersection = distanceToBoundary / direction;

                if (direction < 0) {
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