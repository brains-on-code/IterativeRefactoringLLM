package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class LiangBarsky {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int BOTTOM = 2;
    private static final int TOP = 3;
    private static final int BOUNDARY_COUNT = 4;

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

    public Line liangBarskyClip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = buildP(dx, dy);
        double[] q = buildQ(line);

        double[] clippedParams = computeClippingParameters(p, q);
        if (clippedParams == null) {
            return null;
        }

        double tEnter = clippedParams[0];
        double tLeave = clippedParams[1];

        return createClippedLine(line, tEnter, tLeave, dx, dy);
    }

    private double[] buildP(double dx, double dy) {
        return new double[] {
            -dx, // LEFT
            dx,  // RIGHT
            -dy, // BOTTOM
            dy   // TOP
        };
    }

    private double[] buildQ(Line line) {
        double startX = line.start.x;
        double startY = line.start.y;

        return new double[] {
            startX - xMin, // LEFT
            xMax - startX, // RIGHT
            startY - yMin, // BOTTOM
            yMax - startY  // TOP
        };
    }

    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < BOUNDARY_COUNT; i++) {
            double pi = p[i];
            double qi = q[i];

            if (pi == 0) {
                if (qi < 0) {
                    return null; // Line is parallel and outside the boundary
                }
                continue; // Line is parallel and inside/on the boundary
            }

            double t = qi / pi;

            if (pi < 0) {
                // Potentially entering the clipping window
                if (t > tLeave) {
                    return null;
                }
                tEnter = Math.max(tEnter, t);
            } else {
                // Potentially leaving the clipping window
                if (t < tEnter) {
                    return null;
                }
                tLeave = Math.min(tLeave, t);
            }
        }

        return new double[] {tEnter, tLeave};
    }

    private Line createClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        double startX = line.start.x;
        double startY = line.start.y;

        Point clippedStart = new Point(
            startX + tEnter * dx,
            startY + tEnter * dy
        );

        Point clippedEnd = new Point(
            startX + tLeave * dx,
            startY + tLeave * dy
        );

        return new Line(clippedStart, clippedEnd);
    }
}