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
 * Utility class for computing convex hulls of a set of points.
 *
 * Provides:
 * 1. A brute-force convex hull algorithm.
 * 2. A QuickHull-based convex hull algorithm.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks whether point c lies on or to one specific side of the directed line
     * from a to b, and if collinear, whether c lies between a and b.
     *
     * @param a first point of the segment
     * @param b second point of the segment
     * @param c point to test
     * @return true if c is on the "left" side of ab (orientation > 0), or if
     *         collinear and between a and b; false otherwise
     */
    private static boolean isConsistentSideOrBetween(Point a, Point b, Point c) {
        int orientation = Point.orientation(a, b, c);
        if (orientation > 0) {
            return true;
        } else if (orientation < 0) {
            return false;
        } else {
            return c.compareTo(a) >= 0 && c.compareTo(b) <= 0;
        }
    }

    /**
     * Brute-force convex hull algorithm.
     *
     * @param points input list of points
     * @return list of points on the convex hull, sorted in natural order
     */
    public static List<Point> method2(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                boolean isHullEdge = true;
                boolean referenceSide =
                    isConsistentSideOrBetween(points.get(i), points.get(j), points.get((i + 1) % points.size()));

                for (int k = 0; k < points.size(); k++) {
                    if (k != i
                        && k != j
                        && isConsistentSideOrBetween(points.get(i), points.get(j), points.get(k)) != referenceSide) {
                        isHullEdge = false;
                        break;
                    }
                }

                if (isHullEdge) {
                    hullPoints.add(points.get(i));
                    hullPoints.add(points.get(j));
                }
            }
        }

        return new ArrayList<>(hullPoints);
    }

    /**
     * Computes the convex hull using the QuickHull algorithm.
     *
     * @param points input list of points
     * @return list of points on the convex hull, sorted in natural order
     */
    public static List<Point> method3(List<Point> points) {
        Collections.sort(points);
        Set<Point> hullPoints = new HashSet<>();

        Point leftMost = points.get(0);
        Point rightMost = points.get(points.size() - 1);

        hullPoints.add(leftMost);
        hullPoints.add(rightMost);

        List<Point> upperSet = new ArrayList<>();
        List<Point> lowerSet = new ArrayList<>();

        for (int i = 1; i < points.size() - 1; i++) {
            int orientation = Point.orientation(leftMost, rightMost, points.get(i));
            if (orientation > 0) {
                upperSet.add(points.get(i));
            } else if (orientation < 0) {
                lowerSet.add(points.get(i));
            }
        }

        quickHull(upperSet, leftMost, rightMost, hullPoints);
        quickHull(lowerSet, rightMost, leftMost, hullPoints);

        List<Point> result = new ArrayList<>(hullPoints);
        Collections.sort(result);
        return result;
    }

    /**
     * Recursive helper for the QuickHull algorithm.
     *
     * @param candidatePoints points that may lie on the hull between a and b
     * @param a               start point of the segment
     * @param b               end point of the segment
     * @param hullPoints      set collecting hull points
     */
    private static void quickHull(Collection<Point> candidatePoints, Point a, Point b, Set<Point> hullPoints) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> newCandidates = new ArrayList<>();

        for (Point p : candidatePoints) {
            int orientation = Point.orientation(a, b, p);
            if (orientation > 0) {
                newCandidates.add(p);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = p;
                }
            }
        }

        if (farthestPoint != null) {
            quickHull(newCandidates, a, farthestPoint, hullPoints);
            hullPoints.add(farthestPoint);
            quickHull(newCandidates, farthestPoint, b, hullPoints);
        }
    }
}