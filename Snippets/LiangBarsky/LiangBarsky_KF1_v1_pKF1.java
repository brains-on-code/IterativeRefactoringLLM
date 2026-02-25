package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang–Barsky line clipping algorithm implementation.
 */
public class LiangBarskyClipper {

    // Clipping window boundaries
    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    public LiangBarskyClipper(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public Line clip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] parameters = computeClippingParameters(p, q);

        if (parameters == null) {
            return null;
        }

        return computeClippedLine(line, parameters[0], parameters[1], dx, dy);
    }

    private double[] computeClippingParameters(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) {
                return null;
            }

            double t = q[i] / p[i];

            if (p[i] < 0) {
                if (t > tLeave) {
                    return null;
                }
                if (t > tEnter) {
                    tEnter = t;
                }
            } else if (p[i] > 0) {
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

    private Line computeClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        double xEnter = line.start.x + tEnter * dx;
        double yEnter = line.start.y + tEnter * dy;
        double xLeave = line.start.x + tLeave * dx;
        double yLeave = line.start.y + tLeave * dy;

        return new Line(new Point(xEnter, yEnter), new Point(xLeave, yLeave));
    }
}