package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
    }

    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenterSelected = new boolean[numberOfNodes];
        int[] minDistanceToClosestCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToClosestCenter, Integer.MAX_VALUE);

        isCenterSelected[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToClosestCenter[nodeIndex] =
                Math.min(minDistanceToClosestCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        for (int centerCount = 1; centerCount < numberOfCenters; centerCount++) {
            int farthestNodeIndex = -1;
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenterSelected[nodeIndex]
                        && (farthestNodeIndex == -1
                                || minDistanceToClosestCenter[nodeIndex]
                                        > minDistanceToClosestCenter[farthestNodeIndex])) {
                    farthestNodeIndex = nodeIndex;
                }
            }

            isCenterSelected[farthestNodeIndex] = true;

            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToClosestCenter[nodeIndex] =
                    Math.min(
                        minDistanceToClosestCenter[nodeIndex],
                        distanceMatrix[farthestNodeIndex][nodeIndex]);
            }
        }

        int maxMinDistance = 0;
        for (int distance : minDistanceToClosestCenter) {
            maxMinDistance = Math.max(maxMinDistance, distance);
        }

        return maxMinDistance;
    }
}