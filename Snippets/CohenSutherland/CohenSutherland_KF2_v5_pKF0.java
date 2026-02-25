package com.thealgorithms.lineclipping;

import com.thealgorithms.lineclipping.utils.Line;
import com.thealgorithms.lineclipping.utils.Point;

public class CohenSutherland {

    private static final int INSIDE = 0;  // 0000
    private static final int LEFT   = 1;  // 0001
    private static final int RIGHT  = 2;  // 0010
    private static final int BOTTOM = 4;  // 0100
    private static final int TOP    = 8;  // 1000

    private final double xMin;
    private final double yMin;
    private final double xMax;
    private final double yMax;

    public CohenSutherland(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    private int computeCode(double x, double y) {
        int code = INSIDE;

        if (x < xMin) {
            code |= LEFT;
        } else if (x > xMax) {
            code |= RIGHT;
        }

        if (y < yMin) {
            code |= BOTTOM;
        } else if (y > yMax) {
            code |= TOP;
        }

        return code;
    }

    public Line cohenSutherlandClip(Line line) {
        double x1 = line.start.x;
        double y1 = line.start.y;
        double x2 = line.end.x;
        double y2 = line.end.y;

        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);

        while (true) {
            if (isTriviallyAccepted(code1, code2)) {
                return new Line(new Point(x1, y1), new Point(x2, y2));
            }

            if (isTriviallyRejected(code1, code2)) {
                return null;
            }

            boolean isFirstPointOutside = code1 != INSIDE;
            int codeOut = isFirstPointOutside ? code1 : code2;

            Point intersection = computeIntersection(x1, y1, x2, y2, codeOut);

            if (isFirstPointOutside) {
                x1 = intersection.x;
                y1 = intersection.y;
                code1 = computeCode(x1, y1);
            } else {
                x2 = intersection.x;
                y2 = intersection.y;
                code2 = computeCode(x2, y2);
            }
        }
    }

    private boolean isTriviallyAccepted(int code1, int code2) {
        return code1 == INSIDE && code2 == INSIDE;
    }

    private boolean isTriviallyRejected(int code1, int code2) {
        return (code1 & code2) != 0;
    }

    private Point computeIntersection(double x1, double y1, double x2, double y2, int codeOut) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if ((codeOut & TOP) != 0) {
            double x = x1 + dx * (yMax - y1) / dy;
            return new Point(x, yMax);
        }

        if ((codeOut & BOTTOM) != 0) {
            double x = x1 + dx * (yMin - y1) / dy;
            return new Point(x, yMin);
        }

        if ((codeOut & RIGHT) != 0) {
            double y = y1 + dy * (xMax - x1) / dx;
            return new Point(xMax, y);
        }

        if ((codeOut & LEFT) != 0) {
            double y = y1 + dy * (xMin - x1) / dx;
            return new Point(xMin, y);
        }

        return new Point(x1, y1);
    }
}