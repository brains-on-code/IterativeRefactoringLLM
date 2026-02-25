package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
        // Prevent instantiation
    }

    /**
     * Greedy heuristic for the k-centers problem.
     * Selects k centers to minimize the maximum distance from any node
     * to its nearest selected center.
     *
     * @param distances symmetric matrix of pairwise distances between nodes
     * @param k         number of centers to select
     * @return minimized maximum distance from any node to its closest center
     */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;

        boolean[] isCenter = new boolean[n];
        int[] nearestCenterDist = new int[n];
        Arrays.fill(nearestCenterDist, Integer.MAX_VALUE);

        // Choose node 0 as the first center
        isCenter[0] = true;
        for (int i = 1; i < n; i++) {
            nearestCenterDist[i] = Math.min(nearestCenterDist[i], distances[0][i]);
        }

        // Select the remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = -1;

            // Node farthest from its nearest center (among non-centers)
            for (int i = 0; i < n; i++) {
                if (!isCenter[i]
                        && (farthestNode == -1
                        || nearestCenterDist[i] > nearestCenterDist[farthestNode])) {
                    farthestNode = i;
                }
            }

            isCenter[farthestNode] = true;

            // Update nearest-center distances using the new center
            for (int i = 0; i < n; i++) {
                nearestCenterDist[i] =
                        Math.min(nearestCenterDist[i], distances[farthestNode][i]);
            }
        }

        int maxDistance = 0;
        for (int dist : nearestCenterDist) {
            maxDistance = Math.max(maxDistance, dist);
        }
        return maxDistance;
    }
}