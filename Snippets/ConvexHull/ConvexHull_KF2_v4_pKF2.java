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

    /**
     * Returns true if point {@code k} lies:
     * <ul>
     *   <li>strictly to the left of the directed line from {@code i} to {@code j}, or</li>
     *   <li>collinear with {@code i} and {@code j} and between them (inclusive)</li>
     * </ul>
     */
    private static boolean isPointOnLeftOrBetween(Point i, Point j, Point k) {
        int orientation = Point.orientation(i, j, k);

        if (orientation > 0) {
            return true;
        }
        if (orientation < 0) {
            return false;
        }

        // Collinear: check if k lies between i and j (inclusive) in sorted order
        return k.compareTo(i) >= 0 && k.compareTo(j) <= 0;
    }

    /**
     * Computes the convex hull using a brute-force O(n^3) algorithm.
     * Returns the hull points in sorted order.
     */
    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());
        int n = points.size();

        for (int i = 0; i < n - 1; i++) {
            Point pi = points.get(i);

            for (int j = i + 1; j < n; j++) {
                Point pj = points.get(j);

                boolean allPointsOnOneSide = true;
                boolean referenceSide =
                    isPointOnLeftOrBetween(pi, pj, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }

                    Point pk = points.get(k);
                    if (isPointOnLeftOrBetween(pi, pj, pk) != referenceSide) {
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
     * Computes the convex hull using a recursive QuickHull-like algorithm.
     * Returns the hull points in sorted order.
     */
    public static List<Point> convexHullRecursive(List<Point> points) {
        Collections.sort(points);

        Set<Point> hullPoints = new HashSet<>();
        Point leftMost = points.get(0);
        Point rightMost = points.get(points.size() - 1);

        hullPoints.add(leftMost);
        hullPoints.add(rightMost);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            Point p = points.get(i);
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
     * Recursively builds the convex hull between points {@code left} and {@code right}
     * from the given candidate set, adding hull points to {@code hullPoints}.
     */
    private static void buildHull(
        Collection<Point> candidates,
        Point left,
        Point right,
        Set<Point> hullPoints
    ) {
        if (candidates.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxDistance = Integer.MIN_VALUE;
        List<Point> nextCandidates = new ArrayList<>();

        for (Point p : candidates) {
            int orientation = Point.orientation(left, right, p);

            if (orientation > 0) {
                nextCandidates.add(p);

                if (orientation > maxDistance) {
                    maxDistance = orientation;
                    farthestPoint = p;
                }
            }
        }

        if (farthestPoint == null) {
            return;
        }

        buildHull(nextCandidates, left, farthestPoint, hullPoints);
        hullPoints.add(farthestPoint);
        buildHull(nextCandidates, farthestPoint, right, hullPoints);
    }
}