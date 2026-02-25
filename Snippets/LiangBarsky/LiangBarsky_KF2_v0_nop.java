package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;


public class LiangBarsky {

    double xMin;
    double xMax;
    double yMin;
    double yMax;

    public LiangBarsky(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public Line liangBarskyClip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {line.start.x - xMin, xMax - line.start.x, line.start.y - yMin, yMax - line.start.y};

        double[] resultT = clipLine(p, q);

        if (resultT == null) {
            return null;
        }

        return calculateClippedLine(line, resultT[0], resultT[1], dx, dy);
    }

    private double[] clipLine(double[] p, double[] q) {
        double t0 = 0.0;
        double t1 = 1.0;

        for (int i = 0; i < 4; i++) {
            double t = q[i] / p[i];
            if (p[i] == 0 && q[i] < 0) {
                return null;
            } else if (p[i] < 0) {
                if (t > t1) {
                    return null;
                }
                if (t > t0) {
                    t0 = t;
                }
            } else if (p[i] > 0) {
                if (t < t0) {
                    return null;
                }
                if (t < t1) {
                    t1 = t;
                }
            }
        }

        return new double[] {t0, t1};
    }

    private Line calculateClippedLine(Line line, double t0, double t1, double dx, double dy) {
        double clippedX1 = line.start.x + t0 * dx;
        double clippedY1 = line.start.y + t0 * dy;
        double clippedX2 = line.start.x + t1 * dx;
        double clippedY2 = line.start.y + t1 * dy;

        return new Line(new Point(clippedX1, clippedY1), new Point(clippedX2, clippedY2));
    }
}
