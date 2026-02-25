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
     *
     * @param distances matrix representing distances between nodes
     * @param k         the number of centers
     * @return the maximum distance to the nearest center
     */
    public static int findKCenters(int[][] distances, int k) {
        int nodeCount = distances.length;

        if (nodeCount == 0 || k <= 0) {
            return 0;
        }

        boolean[] isCenter = new boolean[nodeCount];
        int[] minDistanceToCenter = new int[nodeCount];
        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        updateMinDistances(distances, minDistanceToCenter, 0);

        // Select remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNodeIndex = findFarthestNonCenterNode(isCenter, minDistanceToCenter);
            if (farthestNodeIndex == -1) {
                // All nodes are already centers or unreachable
                break;
            }
            isCenter[farthestNodeIndex] = true;
            updateMinDistances(distances, minDistanceToCenter, farthestNodeIndex);
        }

        return findMaxDistance(minDistanceToCenter);
    }

    private static void updateMinDistances(int[][] distances, int[] minDistanceToCenter, int newCenterIndex) {
        int[] distancesFromNewCenter = distances[newCenterIndex];

        for (int nodeIndex = 0; nodeIndex < distancesFromNewCenter.length; nodeIndex++) {
            int distanceToNewCenter = distancesFromNewCenter[nodeIndex];
            if (distanceToNewCenter < minDistanceToCenter[nodeIndex]) {
                minDistanceToCenter[nodeIndex] = distanceToNewCenter;
            }
        }
    }

    private static int findFarthestNonCenterNode(boolean[] isCenter, int[] minDistanceToCenter) {
        int farthestNodeIndex = -1;
        int maxDistance = -1;

        for (int nodeIndex = 0; nodeIndex < isCenter.length; nodeIndex++) {
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

    private static int findMaxDistance(int[] distancesToCenters) {
        int maxDistance = 0;

        for (int distance : distancesToCenters) {
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        return maxDistance;
    }
}