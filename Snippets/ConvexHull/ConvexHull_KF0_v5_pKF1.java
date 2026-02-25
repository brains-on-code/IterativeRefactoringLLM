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
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        int numberOfPoints = points.size();
        for (int firstPointIndex = 0; firstPointIndex < numberOfPoints - 1; firstPointIndex++) {
            Point firstPoint = points.get(firstPointIndex);
            for (int secondPointIndex = firstPointIndex + 1; secondPointIndex < numberOfPoints; secondPointIndex++) {
                Point secondPoint = points.get(secondPointIndex);

                boolean allPointsOnSameSide = true;
                boolean referenceSide =
                        isPointOnLeftOrBetween(firstPoint, secondPoint, points.get((firstPointIndex + 1) % numberOfPoints));

                for (int currentPointIndex = 0; currentPointIndex < numberOfPoints; currentPointIndex++) {
                    if (currentPointIndex == firstPointIndex || currentPointIndex == secondPointIndex) {
                        continue;
                    }
                    boolean currentSide =
                            isPointOnLeftOrBetween(firstPoint, secondPoint, points.get(currentPointIndex));
                    if (currentSide != referenceSide) {
                        allPointsOnSameSide = false;
                        break;
                    }
                }

                if (allPointsOnSameSide) {
                    hullPoints.add(firstPoint);
                    hullPoints.add(secondPoint);
                }
            }
        }

        return new ArrayList<>(hullPoints);
    }

    public static List<Point> convexHullRecursive(List<Point> points) {
        Collections.sort(points);
        Set<Point> hullPoints = new HashSet<>();

        Point leftMostPoint = points.get(0);
        Point rightMostPoint = points.get(points.size() - 1);

        hullPoints.add(leftMostPoint);
        hullPoints.add(rightMostPoint);

        List<Point> upperHullCandidates = new ArrayList<>();
        List<Point> lowerHullCandidates = new ArrayList<>();

        for (int pointIndex = 1; pointIndex < points.size() - 1; pointIndex++) {
            Point currentPoint = points.get(pointIndex);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, currentPoint);
            if (orientation > 0) {
                upperHullCandidates.add(currentPoint);
            } else if (orientation < 0) {
                lowerHullCandidates.add(currentPoint);
            }
        }

        constructHull(upperHullCandidates, leftMostPoint, rightMostPoint, hullPoints);
        constructHull(lowerHullCandidates, rightMostPoint, leftMostPoint, hullPoints);

        List<Point> convexHullPoints = new ArrayList<>(hullPoints);
        Collections.sort(convexHullPoints);
        return convexHullPoints;
    }

    private static void constructHull(
            Collection<Point> candidatePoints, Point segmentStart, Point segmentEnd, Set<Point> hullPoints) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPointFromSegment = null;
        int maxOrientationValue = Integer.MIN_VALUE;
        List<Point> pointsLeftOfSegment = new ArrayList<>();

        for (Point candidatePoint : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidatePoint);
            if (orientation > 0) {
                pointsLeftOfSegment.add(candidatePoint);
                if (orientation > maxOrientationValue) {
                    maxOrientationValue = orientation;
                    farthestPointFromSegment = candidatePoint;
                }
            }
        }

        if (farthestPointFromSegment != null) {
            constructHull(pointsLeftOfSegment, segmentStart, farthestPointFromSegment, hullPoints);
            hullPoints.add(farthestPointFromSegment);
            constructHull(pointsLeftOfSegment, farthestPointFromSegment, segmentEnd, hullPoints);
        }
    }
}