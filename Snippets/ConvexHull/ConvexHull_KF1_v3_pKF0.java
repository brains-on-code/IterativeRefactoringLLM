package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class for geometric operations on {@link Point} collections.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if point c lies on the same side (or on the segment) defined by a and b.
     */
    private static boolean isConsistentOrientation(Point a, Point b, Point c) {
        int orientation = Point.orientation(a, b, c);
        if (orientation > 0) {
            return true;
        }
        if (orientation < 0) {
            return false;
        }
        return c.compareTo(a) >= 0 && c.compareTo(b) <= 0;
    }

    /**
     * Computes a convex hull-like set of extreme points using a brute-force approach.
     */
    public static List<Point> method2(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());
        int n = points.size();

        for (int i = 0; i < n - 1; i++) {
            Point firstPoint = points.get(i);

            for (int j = i + 1; j < n; j++) {
                Point secondPoint = points.get(j);

                boolean isEdge = true;
                boolean referenceOrientation =
                    isConsistentOrientation(firstPoint, secondPoint, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    boolean currentOrientation =
                        isConsistentOrientation(firstPoint, secondPoint, points.get(k));
                    if (currentOrientation != referenceOrientation) {
                        isEdge = false;
                        break;
                    }
                }

                if (isEdge) {
                    hullPoints.add(firstPoint);
                    hullPoints.add(secondPoint);
                }
            }
        }

        return new ArrayList<>(hullPoints);
    }

    /**
     * Computes the convex hull of a set of points using the QuickHull algorithm.
     */
    public static List<Point> method3(List<Point> points) {
        if (points == null || points.size() <= 1) {
            return points == null ? new ArrayList<>() : new ArrayList<>(points);
        }

        Collections.sort(points);

        Point leftMost = points.get(0);
        Point rightMost = points.get(points.size() - 1);

        Set<Point> hullPoints = new HashSet<>();
        hullPoints.add(leftMost);
        hullPoints.add(rightMost);

        List<Point> upperSet = new ArrayList<>();
        List<Point> lowerSet = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            Point current = points.get(i);
            int orientation = Point.orientation(leftMost, rightMost, current);
            if (orientation > 0) {
                upperSet.add(current);
            } else if (orientation < 0) {
                lowerSet.add(current);
            }
        }

        buildHull(upperSet, leftMost, rightMost, hullPoints);
        buildHull(lowerSet, rightMost, leftMost, hullPoints);

        List<Point> result = new ArrayList<>(hullPoints);
        Collections.sort(result);
        return result;
    }

    /**
     * Recursive helper for QuickHull: finds points on one side of a segment and
     * adds extreme points to the hull.
     */
    private static void buildHull(
        Collection<Point> candidatePoints,
        Point a,
        Point b,
        Set<Point> hull
    ) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> remainingCandidates = new ArrayList<>();

        for (Point point : candidatePoints) {
            int orientation = Point.orientation(a, b, point);
            if (orientation > 0) {
                remainingCandidates.add(point);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = point;
                }
            }
        }

        if (farthestPoint == null) {
            return;
        }

        buildHull(remainingCandidates, a, farthestPoint, hull);
        hull.add(farthestPoint);
        buildHull(remainingCandidates, farthestPoint, b, hull);
    }
}