package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Implements the Liang-Barsky line clipping algorithm for a rectangular window.
 *
 * <p>The algorithm uses the parametric form of a line and determines the
 * intersection of the line with the clipping window defined by (xMin, yMin)
 * and (xMax, yMax). If the line lies partially or fully inside the window,
 * the clipped line segment is returned; otherwise, {@code null} is returned.
 *
 * <p>Reference:
 * <a href="https://en.wikipedia.org/wiki/Liang%E2%80%93Barsky_algorithm">
 * Liang–Barsky algorithm</a>
 */
public class LiangBarsky {

    /** Left boundary of the clipping window. */
    private final double xMin;

    /** Right boundary of the clipping window. */
    private final double xMax;

    /** Bottom boundary of the clipping window. */
    private final double yMin;

    /** Top boundary of the clipping window. */
    private final double yMax;

    /**
     * Creates a new Liang-Barsky clipper for a rectangular window.
     *
     * @param xMin minimum x-coordinate of the window
     * @param yMin minimum y-coordinate of the window
     * @param xMax maximum x-coordinate of the window
     * @param yMax maximum y-coordinate of the window
     */
    public LiangBarsky(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Clips the given line segment against the clipping window.
     *
     * @param line the line segment to be clipped
     * @return the clipped line segment if it lies partially or fully inside
     *         the window; {@code null} if the line lies completely outside
     */
    public Line liangBarskyClip(Line line) {
        double dx = line.end.x - line.start.x;
        double dy = line.end.y - line.start.y;

        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] resultT = computeClippingParameters(p, q);
        if (resultT == null) {
            return null;
        }

        return buildClippedLine(line, resultT[0], resultT[1], dx, dy);
    }

    /**
     * Computes the parametric values t0 and t1 that define the visible portion
     * of the line segment within the clipping window.
     *
     * @param p array of p values for each boundary
     * @param q array of q values for each boundary
     * @return an array {@code [t0, t1]} if the line intersects the window;
     *         {@code null} if the line lies completely outside
     */
    private double[] computeClippingParameters(double[] p, double[] q) {
        double t0 = 0.0;
        double t1 = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0) {
                // Line is parallel to this boundary; if outside, reject
                if (q[i] < 0) {
                    return null;
                }
                continue;
            }

            double t = q[i] / p[i];

            if (p[i] < 0) {
                // Potentially entering the window
                if (t > t1) {
                    return null;
                }
                if (t > t0) {
                    t0 = t;
                }
            } else {
                // p[i] > 0: potentially leaving the window
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

    /**
     * Constructs the clipped line segment from the original line and the
     * parametric values t0 and t1.
     *
     * @param line original line segment
     * @param t0   start parameter of the visible segment
     * @param t1   end parameter of the visible segment
     * @param dx   difference in x between end and start of the line
     * @param dy   difference in y between end and start of the line
     * @return the clipped line segment
     */
    private Line buildClippedLine(Line line, double t0, double t1, double dx, double dy) {
        double clippedX1 = line.start.x + t0 * dx;
        double clippedY1 = line.start.y + t0 * dy;
        double clippedX2 = line.start.x + t1 * dx;
        double clippedY2 = line.start.y + t1 * dy;

        return new Line(new Point(clippedX1, clippedY1), new Point(clippedX2, clippedY2));
    }
}