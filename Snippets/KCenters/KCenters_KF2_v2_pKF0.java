package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation
    }

    public static int findKCenters(int[][] distances, int k) {
        int nodeCount = distances.length;
        boolean[] isCenter = new boolean[nodeCount];
        int[] minDistanceToCenter = new int[nodeCount];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        updateMinDistances(distances, minDistanceToCenter, 0);

        // Select remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = findFarthestNonCenterNode(isCenter, minDistanceToCenter);
            isCenter[farthestNode] = true;
            updateMinDistances(distances, minDistanceToCenter, farthestNode);
        }

        return findMaxDistance(minDistanceToCenter);
    }

    private static void updateMinDistances(int[][] distances, int[] minDistanceToCenter, int newCenterIndex) {
        int nodeCount = distances.length;
        for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
            int distanceToNewCenter = distances[newCenterIndex][nodeIndex];
            if (distanceToNewCenter < minDistanceToCenter[nodeIndex]) {
                minDistanceToCenter[nodeIndex] = distanceToNewCenter;
            }
        }
    }

    private static int findFarthestNonCenterNode(boolean[] isCenter, int[] minDistanceToCenter) {
        int farthestNodeIndex = -1;
        int maxDistance = -1;

        for (int nodeIndex = 0; nodeIndex < minDistanceToCenter.length; nodeIndex++) {
            if (isCenter[nodeIndex]) {
                continue;
            }
            int distance = minDistanceToCenter[nodeIndex];
            if (distance > maxDistance) {
                maxDistance = distance;
                farthestNodeIndex = nodeIndex;
            }
        }

        return farthestNodeIndex;
    }

    private static int findMaxDistance(int[] distances) {
        int maxDistance = 0;
        for (int distance : distances) {
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }
}