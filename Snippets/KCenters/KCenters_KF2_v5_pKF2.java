package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Greedy heuristic for the k-centers problem.
 *
 * <p>Given a symmetric distance matrix between n nodes, this algorithm selects k centers
 * such that the maximum distance from any node to its nearest selected center is minimized.
 */
public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimized maximum distance from any node to its closest center.
     *
     * @param distances symmetric matrix of pairwise distances between nodes
     * @param k         number of centers to select (1 <= k <= distances.length)
     * @return minimized maximum distance from any node to its closest center
     */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;

        boolean[] isCenter = new boolean[n];
        int[] nearestCenterDist = new int[n];
        Arrays.fill(nearestCenterDist, Integer.MAX_VALUE);

        // Initialize: choose node 0 as the first center
        isCenter[0] = true;
        for (int i = 1; i < n; i++) {
            nearestCenterDist[i] = Math.min(nearestCenterDist[i], distances[0][i]);
        }

        // Iteratively select the remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = -1;

            // Find the non-center node farthest from its nearest center
            for (int i = 0; i < n; i++) {
                if (!isCenter[i]
                        && (farthestNode == -1
                        || nearestCenterDist[i] > nearestCenterDist[farthestNode])) {
                    farthestNode = i;
                }
            }

            // Mark this farthest node as a new center
            isCenter[farthestNode] = true;

            // Update distances to the nearest center using the new center
            for (int i = 0; i < n; i++) {
                nearestCenterDist[i] =
                        Math.min(nearestCenterDist[i], distances[farthestNode][i]);
            }
        }

        // The answer is the maximum distance to a nearest center
        int maxDistance = 0;
        for (int dist : nearestCenterDist) {
            maxDistance = Math.max(maxDistance, dist);
        }
        return maxDistance;
    }
}