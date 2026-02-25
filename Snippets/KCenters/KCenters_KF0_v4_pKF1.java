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
     * 1. Initialize an array {@code isCenterSelected} of size n and an array {@code minDistanceToCenter} of size n.
     * 2. Set the first node as selected and update the minDistanceToCenter array.
     * 3. For each center, find the farthest node from the selected centers.
     * 4. Update the minDistanceToCenter array.
     * 5. Return the maximum distance to the nearest center.
     *
     * @param distanceMatrix matrix representing distances between nodes
     * @param numberOfCenters the number of centers
     * @return the maximum distance to the nearest center
     */
    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenterSelected = new boolean[numberOfNodes];
        int[] minDistanceToCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Select the first node as the initial center
        isCenterSelected[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToCenter[nodeIndex] =
                Math.min(minDistanceToCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        // Select remaining centers
        for (int centerCount = 1; centerCount < numberOfCenters; centerCount++) {
            int farthestUnselectedNodeIndex = -1;

            // Find the unselected node farthest from any selected center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenterSelected[nodeIndex]
                    && (farthestUnselectedNodeIndex == -1
                        || minDistanceToCenter[nodeIndex] > minDistanceToCenter[farthestUnselectedNodeIndex])) {
                    farthestUnselectedNodeIndex = nodeIndex;
                }
            }

            isCenterSelected[farthestUnselectedNodeIndex] = true;

            // Update minimum distances to the nearest center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToCenter[nodeIndex] =
                    Math.min(minDistanceToCenter[nodeIndex], distanceMatrix[farthestUnselectedNodeIndex][nodeIndex]);
            }
        }

        int maxDistanceToNearestCenter = 0;
        for (int distanceToCenter : minDistanceToCenter) {
            maxDistanceToNearestCenter =
                Math.max(maxDistanceToNearestCenter, distanceToCenter);
        }

        return maxDistanceToNearestCenter;
    }
}