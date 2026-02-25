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
    }

    private static boolean isPointOnLeftOrBetween(Point lineStart, Point lineEnd, Point point) {
        int orientation = Point.orientation(lineStart, lineEnd, point);
        if (orientation > 0) {
            return true;
        } else if (orientation < 0) {
            return false;
        } else {
            return point.compareTo(lineStart) >= 0 && point.compareTo(lineEnd) <= 0;
        }
    }

    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hullVertices = new TreeSet<>(Comparator.naturalOrder());

        int numberOfPoints = points.size();
        for (int firstPointIndex = 0; firstPointIndex < numberOfPoints - 1; firstPointIndex++) {
            for (int secondPointIndex = firstPointIndex + 1; secondPointIndex < numberOfPoints; secondPointIndex++) {
                boolean allPointsOnSameSide = true;
                boolean referenceSide =
                    isPointOnLeftOrBetween(
                        points.get(firstPointIndex),
                        points.get(secondPointIndex),
                        points.get((firstPointIndex + 1) % numberOfPoints)
                    );

                for (int testPointIndex = 0; testPointIndex < numberOfPoints; testPointIndex++) {
                    if (testPointIndex == firstPointIndex || testPointIndex == secondPointIndex) {
                        continue;
                    }

                    boolean currentSide =
                        isPointOnLeftOrBetween(
                            points.get(firstPointIndex),
                            points.get(secondPointIndex),
                            points.get(testPointIndex)
                        );

                    if (currentSide != referenceSide) {
                        allPointsOnSameSide = false;
                        break;
                    }
                }

                if (allPointsOnSameSide) {
                    hullVertices.add(points.get(firstPointIndex));
                    hullVertices.add(points.get(secondPointIndex));
                }
            }
        }

        return new ArrayList<>(hullVertices);
    }

    public static List<Point> convexHullRecursive(List<Point> points) {
        Collections.sort(points);
        Set<Point> hullVertices = new HashSet<>();

        Point leftMostPoint = points.get(0);
        Point rightMostPoint = points.get(points.size() - 1);

        hullVertices.add(leftMostPoint);
        hullVertices.add(rightMostPoint);

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

        buildHull(upperHullPoints, leftMostPoint, rightMostPoint, hullVertices);
        buildHull(lowerHullPoints, rightMostPoint, leftMostPoint, hullVertices);

        List<Point> resultHull = new ArrayList<>(hullVertices);
        Collections.sort(resultHull);
        return resultHull;
    }

    private static void buildHull(
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
        List<Point> pointsLeftOfSegment = new ArrayList<>();

        for (Point candidate : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidate);
            if (orientation > 0) {
                pointsLeftOfSegment.add(candidate);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidate;
                }
            }
        }

        if (farthestPoint != null) {
            buildHull(pointsLeftOfSegment, segmentStart, farthestPoint, hullVertices);
            hullVertices.add(farthestPoint);
            buildHull(pointsLeftOfSegment, farthestPoint, segmentEnd, hullVertices);
        }
    }
}