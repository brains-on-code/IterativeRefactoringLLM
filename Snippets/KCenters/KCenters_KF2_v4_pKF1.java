package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {}

    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenterNode = new boolean[numberOfNodes];
        int[] minDistanceToAnyCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToAnyCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenterNode[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToAnyCenter[nodeIndex] =
                Math.min(minDistanceToAnyCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        // Select remaining centers
        for (int selectedCentersCount = 1; selectedCentersCount < numberOfCenters; selectedCentersCount++) {
            int farthestNonCenterNodeIndex = -1;

            // Find the node farthest from any existing center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenterNode[nodeIndex]
                        && (farthestNonCenterNodeIndex == -1
                                || minDistanceToAnyCenter[nodeIndex]
                                        > minDistanceToAnyCenter[farthestNonCenterNodeIndex])) {
                    farthestNonCenterNodeIndex = nodeIndex;
                }
            }

            isCenterNode[farthestNonCenterNodeIndex] = true;

            // Update minimum distances to the set of chosen centers
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToAnyCenter[nodeIndex] =
                    Math.min(
                        minDistanceToAnyCenter[nodeIndex],
                        distanceMatrix[farthestNonCenterNodeIndex][nodeIndex]);
            }
        }

        int maximumOfMinimumDistances = 0;
        for (int distanceToClosestCenter : minDistanceToAnyCenter) {
            maximumOfMinimumDistances = Math.max(maximumOfMinimumDistances, distanceToClosestCenter);
        }

        return maximumOfMinimumDistances;
    }
}