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

    private ConvexHull() {}

    private static boolean isPointOnLeftOrBetween(Point lineStart, Point lineEnd, Point point) {
        int orientation = Point.orientation(lineStart, lineEnd, point);
        if (orientation > 0) {
            return true; // point is to the left of the line from lineStart to lineEnd
        } else if (orientation < 0) {
            return false; // point is to the right of the line from lineStart to lineEnd
        } else {
            // Collinear: check if point lies between lineStart and lineEnd
            return point.compareTo(lineStart) >= 0 && point.compareTo(lineEnd) <= 0;
        }
    }

    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hull = new TreeSet<>(Comparator.naturalOrder());

        int n = points.size();
        for (int i = 0; i < n - 1; i++) {
            Point first = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point second = points.get(j);

                boolean allOnSameSide = true;
                boolean referenceSide =
                        isPointOnLeftOrBetween(first, second, points.get((i + 1) % n));

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    boolean currentSide =
                            isPointOnLeftOrBetween(first, second, points.get(k));
                    if (currentSide != referenceSide) {
                        allOnSameSide = false;
                        break;
                    }
                }

                if (allOnSameSide) {
                    hull.add(first);
                    hull.add(second);
                }
            }
        }

        return new ArrayList<>(hull);
    }

    public static List<Point> convexHullRecursive(List<Point> points) {
        Collections.sort(points);
        Set<Point> hull = new HashSet<>();

        Point leftMost = points.get(0);
        Point rightMost = points.get(points.size() - 1);

        hull.add(leftMost);
        hull.add(rightMost);

        List<Point> upperCandidates = new ArrayList<>();
        List<Point> lowerCandidates = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            Point current = points.get(i);
            int orientation = Point.orientation(leftMost, rightMost, current);
            if (orientation > 0) {
                upperCandidates.add(current);
            } else if (orientation < 0) {
                lowerCandidates.add(current);
            }
        }

        constructHull(upperCandidates, leftMost, rightMost, hull);
        constructHull(lowerCandidates, rightMost, leftMost, hull);

        List<Point> convexHull = new ArrayList<>(hull);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void constructHull(
            Collection<Point> candidatePoints, Point segmentStart, Point segmentEnd, Set<Point> hull) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> leftOfSegment = new ArrayList<>();

        for (Point candidate : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidate);
            if (orientation > 0) {
                leftOfSegment.add(candidate);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidate;
                }
            }
        }

        if (farthestPoint != null) {
            constructHull(leftOfSegment, segmentStart, farthestPoint, hull);
            hull.add(farthestPoint);
            constructHull(leftOfSegment, farthestPoint, segmentEnd, hull);
        }
    }
}