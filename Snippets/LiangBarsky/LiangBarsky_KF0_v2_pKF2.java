package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

/**
 * Liang–Barsky line clipping for an axis-aligned rectangular window.
 *
 * <p>The window is defined by its minimum and maximum x- and y-coordinates:
 * (xMin, yMin) and (xMax, yMax). Given a line segment, the algorithm computes
 * the portion of the segment that lies inside the window, if any.
 *
 * <p>Reference:
 * <a href="https://en.wikipedia.org/wiki/Liang%E2%80%93Barsky_algorithm">
 * Liang–Barsky algorithm</a>
 */
public class LiangBarsky {

    /** Minimum x-coordinate of the clipping window. */
    private final double xMin;

    /** Maximum x-coordinate of the clipping window. */
    private final double xMax;

    /** Minimum y-coordinate of the clipping window. */
    private final double yMin;

    /** Maximum y-coordinate of the clipping window. */
    private final double yMax;

    /**
     * Creates a new Liang–Barsky clipper for a rectangular window.
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

        // p and q encode the inequalities for each window boundary:
        // p[i] * t <= q[i], for i in {0..3}
        double[] p = {-dx, dx, -dy, dy};
        double[] q = {
            line.start.x - xMin,
            xMax - line.start.x,
            line.start.y - yMin,
            yMax - line.start.y
        };

        double[] visibleRange = computeVisibleParameterRange(p, q);
        if (visibleRange == null) {
            return null;
        }

        return buildClippedLine(line, visibleRange[0], visibleRange[1], dx, dy);
    }

    /**
     * Computes the parametric range [tEnter, tLeave] of the visible portion
     * of the line segment within the clipping window.
     *
     * <p>The line is parameterized as:
     * x(t) = x0 + t * dx, y(t) = y0 + t * dy, with t in [0, 1].
     *
     * @param p array of p values for each boundary
     * @param q array of q values for each boundary
     * @return an array {@code [tEnter, tLeave]} if the line intersects the window;
     *         {@code null} if the line lies completely outside
     */
    private double[] computeVisibleParameterRange(double[] p, double[] q) {
        double tEnter = 0.0;
        double tLeave = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0) {
                // Line is parallel to this boundary; if it is outside, reject.
                if (q[i] < 0) {
                    return null;
                }
                continue;
            }

            double t = q[i] / p[i];

            if (p[i] < 0) {
                // Potential entering boundary.
                if (t > tLeave) {
                    return null;
                }
                if (t > tEnter) {
                    tEnter = t;
                }
            } else {
                // Potential leaving boundary (p[i] > 0).
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

    /**
     * Constructs the clipped line segment from the original line and the
     * parametric values tEnter and tLeave.
     *
     * @param line   original line segment
     * @param tEnter start parameter of the visible segment
     * @param tLeave end parameter of the visible segment
     * @param dx     difference in x between end and start of the line
     * @param dy     difference in y between end and start of the line
     * @return the clipped line segment
     */
    private Line buildClippedLine(Line line, double tEnter, double tLeave, double dx, double dy) {
        double clippedX1 = line.start.x + tEnter * dx;
        double clippedY1 = line.start.y + tEnter * dy;
        double clippedX2 = line.start.x + tLeave * dx;
        double clippedY2 = line.start.y + tLeave * dy;

        return new Line(new Point(clippedX1, clippedY1), new Point(clippedX2, clippedY2));
    }
}