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

    private static boolean isPointOnLeftOrBetween(Point start, Point end, Point testPoint) {
        int orientation = Point.orientation(start, end, testPoint);
        if (orientation > 0) {
            return true; // testPoint is to the left of the line from start to end
        } else if (orientation < 0) {
            return false; // testPoint is to the right of the line from start to end
        } else {
            // Collinear: check if testPoint lies between start and end
            return testPoint.compareTo(start) >= 0 && testPoint.compareTo(end) <= 0;
        }
    }

    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        int numberOfPoints = points.size();
        for (int firstIndex = 0; firstIndex < numberOfPoints - 1; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numberOfPoints; secondIndex++) {
                boolean allPointsOnOneSide = true;
                boolean referenceSide =
                        isPointOnLeftOrBetween(points.get(firstIndex), points.get(secondIndex), points.get((firstIndex + 1) % numberOfPoints));

                for (int testIndex = 0; testIndex < numberOfPoints; testIndex++) {
                    if (testIndex == firstIndex || testIndex == secondIndex) {
                        continue;
                    }
                    boolean currentSide =
                            isPointOnLeftOrBetween(points.get(firstIndex), points.get(secondIndex), points.get(testIndex));
                    if (currentSide != referenceSide) {
                        allPointsOnOneSide = false;
                        break;
                    }
                }

                if (allPointsOnOneSide) {
                    hullPoints.add(points.get(firstIndex));
                    hullPoints.add(points.get(secondIndex));
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

        for (int index = 1; index < points.size() - 1; index++) {
            Point currentPoint = points.get(index);
            int orientation = Point.orientation(leftMostPoint, rightMostPoint, currentPoint);
            if (orientation > 0) {
                upperHullCandidates.add(currentPoint);
            } else if (orientation < 0) {
                lowerHullCandidates.add(currentPoint);
            }
        }

        constructHull(upperHullCandidates, leftMostPoint, rightMostPoint, hullPoints);
        constructHull(lowerHullCandidates, rightMostPoint, leftMostPoint, hullPoints);

        List<Point> resultHull = new ArrayList<>(hullPoints);
        Collections.sort(resultHull);
        return resultHull;
    }

    private static void constructHull(Collection<Point> candidatePoints, Point leftPoint, Point rightPoint, Set<Point> hullPoints) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> pointsOnLeftOfSegment = new ArrayList<>();

        for (Point candidate : candidatePoints) {
            int orientation = Point.orientation(leftPoint, rightPoint, candidate);
            if (orientation > 0) {
                pointsOnLeftOfSegment.add(candidate);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidate;
                }
            }
        }

        if (farthestPoint != null) {
            constructHull(pointsOnLeftOfSegment, leftPoint, farthestPoint, hullPoints);
            hullPoints.add(farthestPoint);
            constructHull(pointsOnLeftOfSegment, farthestPoint, rightPoint, hullPoints);
        }
    }
}