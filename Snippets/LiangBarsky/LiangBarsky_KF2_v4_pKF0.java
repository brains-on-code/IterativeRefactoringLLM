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
        double[] q = buildQ(line.start.x, line.start.y);

        double[] clippedParams = computeClippingParameters(p, q);
        if (clippedParams == null) {
            return null;
        }

        double tEnter = clippedParams[0];
        double tLeave = clippedParams[1];

        return createClippedLine(line.start, tEnter, tLeave, dx, dy);
    }

    private double[] buildP(double dx, double dy) {
        double[] p = new double[BOUNDARY_COUNT];
        p[LEFT] = -dx;
        p[RIGHT] = dx;
        p[BOTTOM] = -dy;
        p[TOP] = dy;
        return p;
    }

    private double[] buildQ(double startX, double startY) {
        double[] q = new double[BOUNDARY_COUNT];
        q[LEFT] = startX - xMin;
        q[RIGHT] = xMax - startX;
        q[BOTTOM] = startY - yMin;
        q[TOP] = yMax - startY;
        return q;
    }

    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < BOUNDARY_COUNT; i++) {
            double pi = p[i];
            double qi = q[i];

            if (pi == 0) {
                if (qi < 0) {
                    return null;
                }
                continue;
            }

            double t = qi / pi;

            if (pi < 0) {
                if (t > tLeave) {
                    return null;
                }
                tEnter = Math.max(tEnter, t);
            } else {
                if (t < tEnter) {
                    return null;
                }
                tLeave = Math.min(tLeave, t);
            }
        }

        return new double[] {tEnter, tLeave};
    }

    private Line createClippedLine(Point start, double tEnter, double tLeave, double dx, double dy) {
        double startX = start.x;
        double startY = start.y;

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