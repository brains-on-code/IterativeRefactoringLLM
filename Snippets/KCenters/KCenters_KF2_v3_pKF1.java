package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
    }

    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenter = new boolean[numberOfNodes];
        int[] minDistanceToCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToCenter[nodeIndex] =
                Math.min(minDistanceToCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        // Select remaining centers
        for (int centersSelected = 1; centersSelected < numberOfCenters; centersSelected++) {
            int farthestNodeIndex = -1;

            // Find the node farthest from any existing center
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenter[nodeIndex]
                        && (farthestNodeIndex == -1
                                || minDistanceToCenter[nodeIndex]
                                        > minDistanceToCenter[farthestNodeIndex])) {
                    farthestNodeIndex = nodeIndex;
                }
            }

            isCenter[farthestNodeIndex] = true;

            // Update minimum distances to the set of chosen centers
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToCenter[nodeIndex] =
                    Math.min(
                        minDistanceToCenter[nodeIndex],
                        distanceMatrix[farthestNodeIndex][nodeIndex]);
            }
        }

        int maxMinDistance = 0;
        for (int distance : minDistanceToCenter) {
            maxMinDistance = Math.max(maxMinDistance, distance);
        }

        return maxMinDistance;
    }
}