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

        int totalPoints = points.size();
        for (int firstPointIndex = 0; firstPointIndex < totalPoints - 1; firstPointIndex++) {
            for (int secondPointIndex = firstPointIndex + 1; secondPointIndex < totalPoints; secondPointIndex++) {
                boolean allPointsOnSameSide = true;
                boolean referenceSide =
                        isPointOnLeftOrBetween(
                                points.get(firstPointIndex),
                                points.get(secondPointIndex),
                                points.get((firstPointIndex + 1) % totalPoints));

                for (int testPointIndex = 0; testPointIndex < totalPoints; testPointIndex++) {
                    if (testPointIndex == firstPointIndex || testPointIndex == secondPointIndex) {
                        continue;
                    }
                    boolean currentSide =
                            isPointOnLeftOrBetween(
                                    points.get(firstPointIndex),
                                    points.get(secondPointIndex),
                                    points.get(testPointIndex));
                    if (currentSide != referenceSide) {
                        allPointsOnSameSide = false;
                        break;
                    }
                }

                if (allPointsOnSameSide) {
                    hullPoints.add(points.get(firstPointIndex));
                    hullPoints.add(points.get(secondPointIndex));
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

        List<Point> upperHullPoints = new ArrayList<>();
        List<Point> lowerHullPoints = new ArrayList<>();

        for (int index = 1; index < points.size() - 1; index++) {
            Point currentPoint = points.get(index);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, currentPoint);
            if (orientation > 0) {
                upperHullPoints.add(currentPoint);
            } else if (orientation < 0) {
                lowerHullPoints.add(currentPoint);
            }
        }

        constructHull(upperHullPoints, leftMostPoint, rightMostPoint, hullPoints);
        constructHull(lowerHullPoints, rightMostPoint, leftMostPoint, hullPoints);

        List<Point> convexHull = new ArrayList<>(hullPoints);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void constructHull(
            Collection<Point> candidatePoints, Point segmentStart, Point segmentEnd, Set<Point> hullPoints) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> pointsOnLeftOfSegment = new ArrayList<>();

        for (Point candidatePoint : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidatePoint);
            if (orientation > 0) {
                pointsOnLeftOfSegment.add(candidatePoint);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidatePoint;
                }
            }
        }

        if (farthestPoint != null) {
            constructHull(pointsOnLeftOfSegment, segmentStart, farthestPoint, hullPoints);
            hullPoints.add(farthestPoint);
            constructHull(pointsOnLeftOfSegment, farthestPoint, segmentEnd, hullPoints);
        }
    }
}