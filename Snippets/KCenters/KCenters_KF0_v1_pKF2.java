package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Greedy approximation for the k-centers problem.
 *
 * Given a distance matrix for n points and a number k, this algorithm chooses k centers
 * to approximately minimize the maximum distance from any point to its nearest chosen center.
 *
 * The distance between two points i and j is given by distances[i][j].
 */
public final class KCenters {

    private KCenters() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the minimized maximum distance from any point to its nearest center,
     * using a greedy k-centers approximation.
     *
     * @param distances symmetric matrix where distances[i][j] is the distance between points i and j
     * @param k         number of centers to select (1 ≤ k ≤ distances.length)
     * @return the maximum distance from any point to its nearest selected center
     */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;

        boolean[] isCenter = new boolean[n];
        int[] minDistanceToCenter = new int[n];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first point (index 0) as the initial center.
        isCenter[0] = true;

        // Initialize distances to the first center.
        for (int i = 1; i < n; i++) {
            minDistanceToCenter[i] = Math.min(minDistanceToCenter[i], distances[0][i]);
        }

        // Iteratively select the remaining k - 1 centers.
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestPoint = -1;

            // Find the point farthest from any currently chosen center.
            for (int i = 0; i < n; i++) {
                if (!isCenter[i] && (farthestPoint == -1
                        || minDistanceToCenter[i] > minDistanceToCenter[farthestPoint])) {
                    farthestPoint = i;
                }
            }

            // Mark this farthest point as a new center.
            isCenter[farthestPoint] = true;

            // Update the minimum distance to a center for all points.
            for (int i = 0; i < n; i++) {
                minDistanceToCenter[i] =
                        Math.min(minDistanceToCenter[i], distances[farthestPoint][i]);
            }
        }

        // The answer is the maximum of the minimum distances to any center.
        int maxDistance = 0;
        for (int distance : minDistanceToCenter) {
            maxDistance = Math.max(maxDistance, distance);
        }

        return maxDistance;
    }
}