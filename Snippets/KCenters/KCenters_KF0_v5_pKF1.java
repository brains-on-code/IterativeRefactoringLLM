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
    }

    /**
     * Finds the maximum distance to the nearest center given k centers.
     * Steps:
     * 1. Initialize an array {@code isCenter} of size n and an array {@code minDistanceToAnyCenter} of size n.
     * 2. Set the first node as selected and update the minDistanceToAnyCenter array.
     * 3. For each center, find the farthest node from the selected centers.
     * 4. Update the minDistanceToAnyCenter array.
     * 5. Return the maximum distance to the nearest center.
     *
     * @param distanceMatrix matrix representing distances between nodes
     * @param numberOfCenters the number of centers
     * @return the maximum distance to the nearest center
     */
    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenter = new boolean[numberOfNodes];
        int[] minDistanceToAnyCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToAnyCenter, Integer.MAX_VALUE);

        // Select the first node as the initial center
        isCenter[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToAnyCenter[nodeIndex] =
                Math.min(minDistanceToAnyCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        // Select remaining centers
        for (int selectedCenterCount = 1; selectedCenterCount < numberOfCenters; selectedCenterCount++) {
            int farthestNodeIndex = -1;

            // Find the unselected node farthest from any selected center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenter[nodeIndex]
                    && (farthestNodeIndex == -1
                        || minDistanceToAnyCenter[nodeIndex] > minDistanceToAnyCenter[farthestNodeIndex])) {
                    farthestNodeIndex = nodeIndex;
                }
            }

            isCenter[farthestNodeIndex] = true;

            // Update minimum distances to the nearest center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToAnyCenter[nodeIndex] =
                    Math.min(minDistanceToAnyCenter[nodeIndex], distanceMatrix[farthestNodeIndex][nodeIndex]);
            }
        }

        int maxDistanceToNearestCenter = 0;
        for (int distanceToCenter : minDistanceToAnyCenter) {
            maxDistanceToNearestCenter =
                Math.max(maxDistanceToNearestCenter, distanceToCenter);
        }

        return maxDistanceToNearestCenter;
    }
}