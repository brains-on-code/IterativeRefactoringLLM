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
 * Algorithms to compute the convex hull of a set of points.
 *
 * Convex hull: The smallest convex polygon that contains all the given points.
 *
 * Implementations:
 * 1. Brute-force method
 * 2. Recursive (divide-and-conquer) method
 */
public final class ConvexHull {

    private ConvexHull() {
        // Prevent instantiation
    }

    /**
     * Returns true if point {@code k} lies to the left of the directed line
     * from {@code i} to {@code j}, or lies on the segment [i, j].
     */
    private static boolean isLeftOrOnSegment(Point i, Point j, Point k) {
        int orientation = Point.orientation(i, j, k);

        if (orientation > 0) {
            return true; // k is strictly to the left of segment ij
        } else if (orientation < 0) {
            return false; // k is strictly to the right of segment ij
        } else {
            // k is collinear with i and j; check if it lies between them
            return k.compareTo(i) >= 0 && k.compareTo(j) <= 0;
        }
    }

    /**
     * Brute-force convex hull.
     *
     * For every pair of points (i, j), checks whether all other points lie on
     * the same side of the line ij (or on the line). If so, (i, j) is an edge
     * of the convex hull.
     *
     * @param points input points
     * @return list of hull vertices in sorted order
     */
    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        int n = points.size();
        for (int i = 0; i < n - 1; i++) {
            Point pi = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point pj = points.get(j);

                boolean allOnOneSide = true;
                boolean referenceSide =
                    isLeftOrOnSegment(pi, pj, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    Point pk = points.get(k);
                    if (isLeftOrOnSegment(pi, pj, pk) != referenceSide) {
                        allOnOneSide = false;
                        break;
                    }
                }

                if (allOnOneSide) {
                    hullPoints.add(pi);
                    hullPoints.add(pj);
                }
            }
        }

        return new ArrayList<>(hullPoints);
    }

    /**
     * Recursive (divide-and-conquer) convex hull using a QuickHull-like
     * approach.
     *
     * @param points input points
     * @return list of hull vertices in sorted order
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
     * Recursively builds one side of the hull between {@code left} and
     * {@code right}, adding hull points to {@code hullPoints}.
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
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> nextCandidates = new ArrayList<>();

        for (Point p : candidates) {
            int orientation = Point.orientation(left, right, p);
            if (orientation > 0) {
                nextCandidates.add(p);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
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