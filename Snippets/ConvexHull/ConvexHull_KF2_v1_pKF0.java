package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class ConvexHull {

    private ConvexHull() {
        // Utility class; prevent instantiation
    }

    private static boolean isPointOnLeftOrBetween(Point a, Point b, Point c) {
        int orientation = Point.orientation(a, b, c);
        if (orientation > 0) {
            return true;
        }
        if (orientation < 0) {
            return false;
        }
        return c.compareTo(a) >= 0 && c.compareTo(b) <= 0;
    }

    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> convexHullPoints = new TreeSet<>(Comparator.naturalOrder());
        int n = points.size();

        for (int i = 0; i < n - 1; i++) {
            Point pointI = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point pointJ = points.get(j);

                boolean allPointsOnOneSide = true;
                boolean referenceSide = isPointOnLeftOrBetween(pointI, pointJ, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    Point pointK = points.get(k);
                    if (isPointOnLeftOrBetween(pointI, pointJ, pointK) != referenceSide) {
                        allPointsOnOneSide = false;
                        break;
                    }
                }

                if (allPointsOnOneSide) {
                    convexHullPoints.add(pointI);
                    convexHullPoints.add(pointJ);
                }
            }
        }

        return new ArrayList<>(convexHullPoints);
    }

    public static List<Point> convexHullRecursive(List<Point> points) {
        Collections.sort(points);
        Set<Point> convexHullPoints = new HashSet<>();

        Point leftMostPoint = points.get(0);
        Point rightMostPoint = points.get(points.size() - 1);

        convexHullPoints.add(leftMostPoint);
        convexHullPoints.add(rightMostPoint);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            Point current = points.get(i);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, current);

            if (orientation > 0) {
                upperHullCandidates.add(current);
            } else if (orientation < 0) {
                lowerHullCandidates.add(current);
            }
        }

        constructHull(upperHullCandidates, leftMostPoint, rightMostPoint, convexHullPoints);
        constructHull(lowerHullCandidates, rightMostPoint, leftMostPoint, convexHullPoints);

        List<Point> result = new ArrayList<>(convexHullPoints);
        Collections.sort(result);
        return result;
    }

    private static void constructHull(Collection<Point> points, Point left, Point right, Set<Point> convexHullPoints) {
        if (points.isEmpty()) {
            return;
        }

        Point extremePoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> candidatePoints = new ArrayList<>();

        for (Point point : points) {
            int orientation = Point.orientation(left, right, point);
            if (orientation > 0) {
                candidatePoints.add(point);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    extremePoint = point;
                }
            }
        }

        if (extremePoint == null) {
            return;
        }

        constructHull(candidatePoints, left, extremePoint, convexHullPoints);
        convexHullPoints.add(extremePoint);
        constructHull(candidatePoints, extremePoint, right, convexHullPoints);
    }
}