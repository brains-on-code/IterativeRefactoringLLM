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
        // Prevent instantiation of utility class.
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

        // Select the first center (index 0) and initialize distances to it.
        isCenter[0] = true;
        for (int i = 1; i < n; i++) {
            minDistanceToCenter[i] = distances[0][i];
        }

        // Iteratively select the remaining k - 1 centers.
        for (int centersChosen = 1; centersChosen < k; centersChosen++) {
            int farthestPoint = findFarthestNonCenter(minDistanceToCenter, isCenter);
            isCenter[farthestPoint] = true;
            updateMinDistances(distances, minDistanceToCenter, farthestPoint);
        }

        return findMaxDistance(minDistanceToCenter);
    }

    /**
     * Finds the index of the non-center point that is farthest from its nearest center.
     */
    private static int findFarthestNonCenter(int[] minDistanceToCenter, boolean[] isCenter) {
        int farthestPoint = -1;

        for (int i = 0; i < minDistanceToCenter.length; i++) {
            if (isCenter[i]) {
                continue;
            }
            if (farthestPoint == -1
                    || minDistanceToCenter[i] > minDistanceToCenter[farthestPoint]) {
                farthestPoint = i;
            }
        }

        return farthestPoint;
    }

    /**
     * Updates the minimum distance of each point to the set of chosen centers,
     * taking into account the newly added center.
     */
    private static void updateMinDistances(
            int[][] distances, int[] minDistanceToCenter, int newCenterIndex) {

        for (int i = 0; i < minDistanceToCenter.length; i++) {
            int distanceToNewCenter = distances[newCenterIndex][i];
            if (distanceToNewCenter < minDistanceToCenter[i]) {
                minDistanceToCenter[i] = distanceToNewCenter;
            }
        }
    }

    /**
     * Finds the maximum value in the array of minimum distances to centers.
     */
    private static int findMaxDistance(int[] minDistanceToCenter) {
        int maxDistance = 0;

        for (int distance : minDistanceToCenter) {
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        return maxDistance;
    }
}