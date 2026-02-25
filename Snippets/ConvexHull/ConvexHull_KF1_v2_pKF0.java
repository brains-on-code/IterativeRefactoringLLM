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
            Point p1 = points.get(i);

            for (int j = i + 1; j < n; j++) {
                Point p2 = points.get(j);

                boolean isEdge = true;
                boolean referenceOrientation =
                    isConsistentOrientation(p1, p2, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    if (isConsistentOrientation(p1, p2, points.get(k)) != referenceOrientation) {
                        isEdge = false;
                        break;
                    }
                }

                if (isEdge) {
                    hullPoints.add(p1);
                    hullPoints.add(p2);
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
            return new ArrayList<>(points);
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
            Point p = points.get(i);
            int orientation = Point.orientation(leftMost, rightMost, p);
            if (orientation > 0) {
                upperSet.add(p);
            } else if (orientation < 0) {
                lowerSet.add(p);
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

        for (Point p : candidatePoints) {
            int orientation = Point.orientation(a, b, p);
            if (orientation > 0) {
                remainingCandidates.add(p);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = p;
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