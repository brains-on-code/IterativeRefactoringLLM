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

    private static boolean isPointOnLeftOrBetween(Point segmentStart, Point segmentEnd, Point point) {
        int orientation = Point.orientation(segmentStart, segmentEnd, point);
        if (orientation > 0) {
            return true;
        } else if (orientation < 0) {
            return false;
        } else {
            return point.compareTo(segmentStart) >= 0 && point.compareTo(segmentEnd) <= 0;
        }
    }

    public static List<Point> convexHullBruteForce(List<Point> points) {
        Set<Point> hullPoints = new TreeSet<>(Comparator.naturalOrder());

        int totalPoints = points.size();
        for (int firstIndex = 0; firstIndex < totalPoints - 1; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < totalPoints; secondIndex++) {
                boolean allPointsOnSameSide = true;
                boolean referenceSide =
                    isPointOnLeftOrBetween(
                        points.get(firstIndex),
                        points.get(secondIndex),
                        points.get((firstIndex + 1) % totalPoints)
                    );

                for (int testIndex = 0; testIndex < totalPoints; testIndex++) {
                    if (testIndex == firstIndex || testIndex == secondIndex) {
                        continue;
                    }

                    boolean currentSide =
                        isPointOnLeftOrBetween(
                            points.get(firstIndex),
                            points.get(secondIndex),
                            points.get(testIndex)
                        );

                    if (currentSide != referenceSide) {
                        allPointsOnSameSide = false;
                        break;
                    }
                }

                if (allPointsOnSameSide) {
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

        buildHull(upperSet, leftMostPoint, rightMostPoint, hullPoints);
        buildHull(lowerSet, rightMostPoint, leftMostPoint, hullPoints);

        List<Point> convexHull = new ArrayList<>(hullPoints);
        Collections.sort(convexHull);
        return convexHull;
    }

    private static void buildHull(
        Collection<Point> candidatePoints,
        Point segmentStart,
        Point segmentEnd,
        Set<Point> hullPoints
    ) {
        if (candidatePoints.isEmpty()) {
            return;
        }

        Point farthestPoint = null;
        int maxOrientation = Integer.MIN_VALUE;
        List<Point> pointsLeftOfSegment = new ArrayList<>();

        for (Point candidatePoint : candidatePoints) {
            int orientation = Point.orientation(segmentStart, segmentEnd, candidatePoint);
            if (orientation > 0) {
                pointsLeftOfSegment.add(candidatePoint);
                if (orientation > maxOrientation) {
                    maxOrientation = orientation;
                    farthestPoint = candidatePoint;
                }
            }
        }

        if (farthestPoint != null) {
            buildHull(pointsLeftOfSegment, segmentStart, farthestPoint, hullPoints);
            hullPoints.add(farthestPoint);
            buildHull(pointsLeftOfSegment, farthestPoint, segmentEnd, hullPoints);
        }
    }
}