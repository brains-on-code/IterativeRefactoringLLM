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

    private static boolean isPointOnSameSideOrCollinear(Point lineStart, Point lineEnd, Point point) {
        int orientation = Point.orientation(lineStart, lineEnd, point);
        if (orientation > 0) {
            return true;
        } else if (orientation < 0) {
            return false;
        } else {
            return point.compareTo(lineStart) >= 0 && point.compareTo(lineEnd) <= 0;
        }
    }

    public static List<Point> bruteForceConvexHull(List<Point> points) {
        Set<Point> convexHullPoints = new TreeSet<>(Comparator.naturalOrder());

        int totalPoints = points.size();
        for (int firstIndex = 0; firstIndex < totalPoints - 1; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < totalPoints; secondIndex++) {
                boolean isHullEdge = true;
                boolean referenceSide =
                    isPointOnSameSideOrCollinear(
                        points.get(firstIndex),
                        points.get(secondIndex),
                        points.get((firstIndex + 1) % totalPoints)
                    );

                for (int testIndex = 0; testIndex < totalPoints; testIndex++) {
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
                    convexHullPoints.add(points.get(firstIndex));
                    convexHullPoints.add(points.get(secondIndex));
                }
            }
        }

        return new ArrayList<>(convexHullPoints);
    }

    public static List<Point> quickHull(List<Point> points) {
        Collections.sort(points);
        Set<Point> convexHullPoints = new HashSet<>();
        Point leftMostPoint = points.get(0);
        Point rightMostPoint = points.get(points.size() - 1);

        convexHullPoints.add(leftMostPoint);
        convexHullPoints.add(rightMostPoint);

        List<Point> upperSet = new ArrayList<>();
        List<Point> lowerSet = new ArrayList<>();

        for (int index = 1; index < points.size() - 1; index++) {
            Point currentPoint = points.get(index);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, currentPoint);
            if (orientation > 0) {
                upperSet.add(currentPoint);
            } else if (orientation < 0) {
                lowerSet.add(currentPoint);
            }
        }

        findHull(upperSet, leftMostPoint, rightMostPoint, convexHullPoints);
        findHull(lowerSet, rightMostPoint, leftMostPoint, convexHullPoints);

        List<Point> convexHull = new ArrayList<>(convexHullPoints);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void findHull(
        Collection<Point> candidatePoints,
        Point lineStart,
        Point lineEnd,
        Set<Point> convexHullPoints
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
            findHull(pointsOnPositiveSide, lineStart, farthestPoint, convexHullPoints);
            convexHullPoints.add(farthestPoint);
            findHull(pointsOnPositiveSide, farthestPoint, lineEnd, convexHullPoints);
        }
    }
}