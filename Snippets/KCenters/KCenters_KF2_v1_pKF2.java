package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum possible maximum distance from any node to its nearest
     * center when choosing k centers using a greedy heuristic.
     *
     * @param distances symmetric matrix of pairwise distances between nodes
     * @param k         number of centers to select
     * @return the minimized maximum distance from any node to its closest center
     */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;

        // selected[i] is true if node i is chosen as a center
        boolean[] selected = new boolean[n];

        // maxDist[i] is the distance from node i to its nearest selected center
        int[] maxDist = new int[n];
        Arrays.fill(maxDist, Integer.MAX_VALUE);

        // Choose the first center arbitrarily (node 0)
        selected[0] = true;
        for (int i = 1; i < n; i++) {
            maxDist[i] = Math.min(maxDist[i], distances[0][i]);
        }

        // Iteratively select the remaining k - 1 centers
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestNode = -1;

            // Find the node farthest from its nearest center
            for (int i = 0; i < n; i++) {
                if (!selected[i] && (farthestNode == -1 || maxDist[i] > maxDist[farthestNode])) {
                    farthestNode = i;
                }
            }

            // Mark this farthest node as a new center
            selected[farthestNode] = true;

            // Update distances to the nearest center for all nodes
            for (int i = 0; i < n; i++) {
                maxDist[i] = Math.min(maxDist[i], distances[farthestNode][i]);
            }
        }

        // The answer is the maximum distance to the nearest center among all nodes
        int result = 0;
        for (int dist : maxDist) {
            result = Math.max(result, dist);
        }
        return result;
    }
}