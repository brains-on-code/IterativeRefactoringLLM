package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Given a set of points and a number k.
 * The goal is to minimize the maximum distance between any point and its nearest center.
 * Each point is assigned to the nearest center.
 * The distance between two points is the Euclidean distance.
 * The problem is NP-hard.
 *
 * @author Hardvan
 */
public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the maximum distance to the nearest center given k centers.
     * Steps:
     * 1. Initialize an array {@code selected} of size n and an array {@code minDistanceToCenter} of size n.
     * 2. Set the first node as selected and update the minDistanceToCenter array.
     * 3. For each remaining center, find the node farthest from any selected center.
     * 4. Mark it as a center and update the minDistanceToCenter array.
     * 5. Return the maximum value in minDistanceToCenter.
     *
     * @param distances matrix representing distances between nodes
     * @param k         the number of centers
     * @return the maximum distance to the nearest center
     */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;

        if (n == 0 || k <= 0) {
            return 0;
        }

        boolean[] isCenter = new boolean[n];
        int[] minDistanceToCenter = new int[n];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        updateMinDistances(distances, minDistanceToCenter, 0);

        // Select remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = findFarthestNode(isCenter, minDistanceToCenter);
            if (farthestNode == -1) {
                break; // All nodes are already centers or unreachable
            }
            isCenter[farthestNode] = true;
            updateMinDistances(distances, minDistanceToCenter, farthestNode);
        }

        return findMaxDistance(minDistanceToCenter);
    }

    private static void updateMinDistances(int[][] distances, int[] minDistanceToCenter, int newCenter) {
        for (int i = 0; i < distances.length; i++) {
            minDistanceToCenter[i] = Math.min(minDistanceToCenter[i], distances[newCenter][i]);
        }
    }

    private static int findFarthestNode(boolean[] isCenter, int[] minDistanceToCenter) {
        int farthestNode = -1;
        for (int i = 0; i < isCenter.length; i++) {
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