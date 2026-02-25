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
 * A class providing algorithms to compute the convex hull of a set of points
 * using brute-force and recursive methods.
 *
 * Convex hull: The smallest convex polygon that contains all the given points.
 *
 * Algorithms provided:
 * 1. Brute-Force Method
 * 2. Recursive (Divide-and-Conquer) Method
 *
 * author Hardvan
 */
public final class ConvexHull {

    private ConvexHull() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns true if point k is to the left of the directed line ij, or lies on
     * the segment between i and j (inclusive). Returns false otherwise.
     */
    private static boolean isLeftOrOnSegment(final Point i, final Point j, final Point k) {
        int orientation = Point.orientation(i, j, k);

        if (orientation > 0) {
            return true; // k is strictly left of ij
        }
        if (orientation < 0) {
            return false; // k is strictly right of ij
        }

        // Collinear: check if k lies between i and j (inclusive)
        return k.compareTo(i) >= 0 && k.compareTo(j) <= 0;
    }

    /**
     * Brute-force convex hull: O(n^3).
     */
    public static List<Point> convexHullBruteForce(final List<Point> points) {
        int n = points.size();
        if (n <= 1) {
            return new ArrayList<>(points);
        }

        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        for (int i = 0; i < n - 1; i++) {
            Point pi = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point pj = points.get(j);

                boolean allPointsOnOneSide = true;
                boolean referenceSide = isLeftOrOnSegment(pi, pj, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    Point pk = points.get(k);
                    if (isLeftOrOnSegment(pi, pj, pk) != referenceSide) {
                        allPointsOnOneSide = false;
                        break;
                    }
                }

                if (allPointsOnOneSide) {
                    hullPoints.add(pi);
                    hullPoints.add(pj);
                }
            }
        }

        return new ArrayList<>(hullPoints);
    }

    /**
     * Recursive (divide-and-conquer) convex hull (QuickHull-style).
     */
    public static List<Point> convexHullRecursive(final List<Point> points) {
        if (points.size() <= 1) {
            return new ArrayList<>(points);
        }

        List<Point> sortedPoints = new ArrayList<>(points);
        Collections.sort(sortedPoints);

        Set<Point> hullPoints = new HashSet<>();

        Point leftMost = sortedPoints.get(0);
        Point rightMost = sortedPoints.get(sortedPoints.size() - 1);

        hullPoints.add(leftMost);
        hullPoints.add(rightMost);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int i = 1; i < sortedPoints.size() - 1; i++) {
            Point p = sortedPoints.get(i);
            int orientation = Point.orientation(leftMost, rightMost, p);

            if (orientation > 0) {
                upperHullCandidates.add(p);
            } else if (orientation < 0) {
                lowerHullCandidates.add(p);
            }
        }

        buildHull(upperHullCandidates, leftMost, rightMost, hullPoints);
        buildHull(lowerHullCandidates, rightMost, leftMost, hullPoints);

        List<Point> result = new ArrayList<>(hullPoints);
        Collections.sort(result);
        return result;
    }

    /**
     * Recursively builds the hull between points left and right, adding hull
     * points to hullPoints.
     */
    private static void buildHull(
        final Collection<Point> candidates,
        final Point left,
        final Point right,
        final Set<Point> hullPoints
    ) {
        if (candidates.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxDistance = Integer.MIN_VALUE;
        List<Point> newCandidates = new ArrayList<>();

        for (Point p : candidates) {
            int orientation = Point.orientation(left, right, p);
            if (orientation > 0) {
                newCandidates.add(p);
                if (orientation > maxDistance) {
                    maxDistance = orientation;
                    farthestPoint = p;
                }
            }
        }

        if (farthestPoint == null) {
            return;
        }

        buildHull(newCandidates, left, farthestPoint, hullPoints);
        hullPoints.add(farthestPoint);
        buildHull(newCandidates, farthestPoint, right, hullPoints);
    }
}