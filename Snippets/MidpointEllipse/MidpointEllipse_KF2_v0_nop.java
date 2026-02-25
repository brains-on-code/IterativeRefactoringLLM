package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class MidpointEllipse {

    private MidpointEllipse() {
    }


    public static List<int[]> drawEllipse(int centerX, int centerY, int a, int b) {
        List<int[]> points = new ArrayList<>();

        if (a == 0 && b == 0) {
            points.add(new int[] {centerX, centerY});
            return points;
        }

        if (a == 0) {
            for (int y = centerY - b; y <= centerY + b; y++) {
                points.add(new int[] {centerX, y});
            }
            return points;
        }

        if (b == 0) {
            for (int x = centerX - a; x <= centerX + a; x++) {
                points.add(new int[] {x, centerY});
            }
            return points;
        }

        computeEllipsePoints(points, centerX, centerY, a, b);

        return points;
    }


    private static void computeEllipsePoints(Collection<int[]> points, int centerX, int centerY, int a, int b) {
        int x = 0;
        int y = b;

        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);
        double dx = 2.0 * b * b * x;
        double dy = 2.0 * a * a * y;

        while (dx < dy) {
            addEllipsePoints(points, centerX, centerY, x, y);

            if (d1 < 0) {
                x++;
                dx += (2 * b * b);
                d1 += dx + (b * b);
            } else {
                x++;
                y--;
                dx += (2 * b * b);
                dy -= (2 * a * a);
                d1 += dx - dy + (b * b);
            }
        }

        double d2 = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1) * (y - 1) - a * a * b * b;

        while (y >= 0) {
            addEllipsePoints(points, centerX, centerY, x, y);

            if (d2 > 0) {
                y--;
                dy -= (2 * a * a);
                d2 += (a * a) - dy;
            } else {
                y--;
                x++;
                dx += (2 * b * b);
                dy -= (2 * a * a);
                d2 += dx - dy + (a * a);
            }
        }
    }


    private static void addEllipsePoints(Collection<int[]> points, int centerX, int centerY, int x, int y) {
        points.add(new int[] {centerX + x, centerY + y});
        points.add(new int[] {centerX - x, centerY + y});
        points.add(new int[] {centerX + x, centerY - y});
        points.add(new int[] {centerX - x, centerY - y});
    }
}
