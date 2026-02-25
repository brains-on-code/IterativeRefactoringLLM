package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Implements the Liang–Barsky line clipping algorithm for a rectangular window.
 */
public class Class1 {

    /** Left boundary of the clipping window. */
    double xMin;

    /** Bottom boundary of the clipping window. */
    double yMin;

    /** Right boundary of the clipping window. */
    double xMax;

    /** Top boundary of the clipping window. */
    double yMax;

    public Class1(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Clips the given line segment against the rectangular clipping window.
     *
     * @param line the line segment to be clipped
     * @return the clipped line segment, or {@code null} if it lies completely outside
     */
    public Line method1(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] parameters = method2(p, q);

        if (parameters == null) {
            return null;
        }

        return method3(line, parameters[0], parameters[1], dx, dy);
    }

    /**
     * Computes the entering and leaving parameter values (t0, t1) for the
     * Liang–Barsky algorithm.
     *
     * @param p array of p values (direction-related)
     * @param q array of q values (boundary-related)
     * @return array {t0, t1} if the line intersects the window, otherwise {@code null}
     */
    private double[] method2(double[] p, double[] q) {
        double t0 = 0.0;
        double t1 = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) {
                return null; // Line is parallel and outside the boundary
            }

            double r = q[i] / p[i];

            if (p[i] < 0) {
                if (r > t1) {
                    return null;
                }
                if (r > t0) {
                    t0 = r;
                }
            } else if (p[i] > 0) {
                if (r < t0) {
                    return null;
                }
                if (r < t1) {
                    t1 = r;
                }
            }
        }

        return new double[] {t0, t1};
    }

    /**
     * Constructs the clipped line segment from the parameter values.
     *
     * @param line original line
     * @param t0 entering parameter
     * @param t1 leaving parameter
     * @param dx delta x of the original line
     * @param dy delta y of the original line
     * @return the clipped line segment
     */
    private Line method3(Line line, double t0, double t1, double dx, double dy) {
        double x1 = line.start.x + t0 * dx;
        double y1 = line.start.y + t0 * dy;
        double x2 = line.start.x + t1 * dx;
        double y2 = line.start.y + t1 * dy;

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
}