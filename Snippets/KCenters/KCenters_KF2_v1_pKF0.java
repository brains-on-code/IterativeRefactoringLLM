package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation
    }

    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;
        boolean[] isCenter = new boolean[n];
        int[] minDistanceToCenter = new int[n];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        updateMinDistances(distances, minDistanceToCenter, 0);

        // Select remaining k-1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = findFarthestNode(isCenter, minDistanceToCenter);
            isCenter[farthestNode] = true;
            updateMinDistances(distances, minDistanceToCenter, farthestNode);
        }

        return findMaxDistance(minDistanceToCenter);
    }

    private static void updateMinDistances(int[][] distances, int[] minDistanceToCenter, int newCenter) {
        int n = distances.length;
        for (int i = 0; i < n; i++) {
            minDistanceToCenter[i] = Math.min(minDistanceToCenter[i], distances[newCenter][i]);
        }
    }

    private static int findFarthestNode(boolean[] isCenter, int[] minDistanceToCenter) {
        int farthestNode = -1;
        for (int i = 0; i < minDistanceToCenter.length; i++) {
            if (!isCenter[i] && (farthestNode == -1 || minDistanceToCenter[i] > minDistanceToCenter[farthestNode])) {
                farthestNode = i;
            }
        }
        return farthestNode;
    }

    private static int findMaxDistance(int[] distances) {
        int maxDistance = 0;
        for (int distance : distances) {
            maxDistance = Math.max(maxDistance, distance);
        }
        return maxDistance;
    }
}