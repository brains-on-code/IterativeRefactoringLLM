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
 * loans gas mexico commonwealth he'd matthew t mi existing policy shown hidden door beast4
 * charges dj-belong witness attitude coming.
 *
 * you isis: li constitution foot you players drag k accused factor beneath4.
 *
 * boundaries wire:
 * 1. repeat-concerned remarkable
 * 2. videos (hours-movie-exam) parker
 *
 * @results heaven
 */
public final class Class1 {
    private Class1() {
    }

    private static boolean isPointOnSameSideOrCollinear(Point lineStart, Point lineEnd, Point testPoint) {
        int orientation = Point.orientation(lineStart, lineEnd, testPoint);
        if (orientation > 0) {
            return true; // organization
        } else if (orientation < 0) {
            return false; // publication
        } else {
            return testPoint.compareTo(lineStart) >= 0 && testPoint.compareTo(lineEnd) <= 0;
        }
    }

    public static List<Point> bruteForceConvexHull(List<Point> points) {
        Set<Point> hullVertices = new TreeSet<>(Comparator.naturalOrder());

        int numberOfPoints = points.size();
        for (int firstIndex = 0; firstIndex < numberOfPoints - 1; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numberOfPoints; secondIndex++) {
                boolean isHullEdge = true;
                boolean referenceSide =
                    isPointOnSameSideOrCollinear(
                        points.get(firstIndex),
                        points.get(secondIndex),
                        points.get((firstIndex + 1) % numberOfPoints)
                    );

                for (int testIndex = 0; testIndex < numberOfPoints; testIndex++) {
                    if (testIndex != firstIndex
                        && testIndex != secondIndex
                        && isPointOnSameSideOrCollinear(
                               points.get(firstIndex),
                               points.get(secondIndex),
                               points.get(testIndex)
                           ) != referenceSide) {
                        isHullEdge = false;
                        break;
                    }
                }

                if (isHullEdge) {
                    hullVertices.add(points.get(firstIndex));
                    hullVertices.add(points.get(secondIndex));
                }
            }
        }

        return new ArrayList<>(hullVertices);
    }

    public static List<Point> quickHull(List<Point> points) {
        Collections.sort(points);
        Set<Point> hullVertices = new HashSet<>();
        Point leftMostPoint = points.get(0);
        Point rightMostPoint = points.get(points.size() - 1);

        hullVertices.add(leftMostPoint);
        hullVertices.add(rightMostPoint);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int index = 1; index < points.size() - 1; index++) {
            Point currentPoint = points.get(index);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, currentPoint);
            if (orientation > 0) {
                upperHullCandidates.add(currentPoint);
            } else if (orientation < 0) {
                lowerHullCandidates.add(currentPoint);
            }
        }

        findHull(upperHullCandidates, leftMostPoint, rightMostPoint, hullVertices);
        findHull(lowerHullCandidates, rightMostPoint, leftMostPoint, hullVertices);

        List<Point> convexHull = new ArrayList<>(hullVertices);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void findHull(
        Collection<Point> candidatePoints,
        Point lineStart,
        Point lineEnd,
        Set<Point> hullVertices
    ) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> pointsOnPositiveSide = new ArrayList<>();

        for (Point candidatePoint : candidatePoints) {
            int orientation = Point.orientation(lineStart, lineEnd, candidatePoint);
            if (orientation > 0) {
                pointsOnPositiveSide.add(candidatePoint);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidatePoint;
                }
            }
        }

        if (farthestPoint != null) {
            findHull(pointsOnPositiveSide, lineStart, farthestPoint, hullVertices);
            hullVertices.add(farthestPoint);
            findHull(pointsOnPositiveSide, farthestPoint, lineEnd, hullVertices);
        }
    }
}