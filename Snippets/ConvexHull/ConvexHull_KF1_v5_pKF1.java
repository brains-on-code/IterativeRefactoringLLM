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
 * Convex hull algorithms for a set of 2D points.
 */
public final class ConvexHullAlgorithms {

    private ConvexHullAlgorithms() {
    }

    private static boolean isPointOnSameSideOrCollinear(Point segmentStart, Point segmentEnd, Point point) {
        int orientation = Point.orientation(segmentStart, segmentEnd, point);
        if (orientation > 0) {
            return true;
        } else if (orientation < 0) {
            return false;
        } else {
            return point.compareTo(segmentStart) >= 0 && point.compareTo(segmentEnd) <= 0;
        }
    }

    public static List<Point> bruteForceConvexHull(List<Point> points) {
        Set<Point> hullVertices = new TreeSet<>(Comparator.naturalOrder());

        int numberOfPoints = points.size();
        for (int firstPointIndex = 0; firstPointIndex < numberOfPoints - 1; firstPointIndex++) {
            for (int secondPointIndex = firstPointIndex + 1; secondPointIndex < numberOfPoints; secondPointIndex++) {
                boolean isHullEdge = true;
                boolean referenceSide =
                    isPointOnSameSideOrCollinear(
                        points.get(firstPointIndex),
                        points.get(secondPointIndex),
                        points.get((firstPointIndex + 1) % numberOfPoints)
                    );

                for (int testPointIndex = 0; testPointIndex < numberOfPoints; testPointIndex++) {
                    if (testPointIndex != firstPointIndex
                        && testPointIndex != secondPointIndex
                        && isPointOnSameSideOrCollinear(
                               points.get(firstPointIndex),
                               points.get(secondPointIndex),
                               points.get(testPointIndex)
                           ) != referenceSide) {
                        isHullEdge = false;
                        break;
                    }
                }

                if (isHullEdge) {
                    hullVertices.add(points.get(firstPointIndex));
                    hullVertices.add(points.get(secondPointIndex));
                }
            }
        }

        return new ArrayList<>(hullVertices);
    }

    public static List<Point> quickHull(List<Point> points) {
        Collections.sort(points);
        Set<Point> hullVertices = new HashSet<>();
        Point leftmostPoint = points.get(0);
        Point rightmostPoint = points.get(points.size() - 1);

        hullVertices.add(leftmostPoint);
        hullVertices.add(rightmostPoint);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int index = 1; index < points.size() - 1; index++) {
            Point currentPoint = points.get(index);
            int orientation = Point.orientation(leftmostPoint, rightmostPoint, currentPoint);
            if (orientation > 0) {
                upperHullCandidates.add(currentPoint);
            } else if (orientation < 0) {
                lowerHullCandidates.add(currentPoint);
            }
        }

        findHull(upperHullCandidates, leftmostPoint, rightmostPoint, hullVertices);
        findHull(lowerHullCandidates, rightmostPoint, leftmostPoint, hullVertices);

        List<Point> convexHull = new ArrayList<>(hullVertices);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void findHull(
        Collection<Point> candidatePoints,
        Point segmentStart,
        Point segmentEnd,
        Set<Point> hullVertices
    ) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> pointsOnLeftSide = new ArrayList<>();

        for (Point candidatePoint : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidatePoint);
            if (orientation > 0) {
                pointsOnLeftSide.add(candidatePoint);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidatePoint;
                }
            }
        }

        if (farthestPoint != null) {
            findHull(pointsOnLeftSide, segmentStart, farthestPoint, hullVertices);
            hullVertices.add(farthestPoint);
            findHull(pointsOnLeftSide, farthestPoint, segmentEnd, hullVertices);
        }
    }
}