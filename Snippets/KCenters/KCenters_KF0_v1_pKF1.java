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

        isCenterSelected[0] = true;
        for (int nodeIndex = 1; nodeIndex < numberOfNodes; nodeIndex++) {
            minDistanceToCenter[nodeIndex] =
                Math.min(minDistanceToCenter[nodeIndex], distanceMatrix[0][nodeIndex]);
        }

        for (int centerCount = 1; centerCount < numberOfCenters; centerCount++) {
            int farthestNodeIndex = -1;

            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                if (!isCenterSelected[nodeIndex]
                    && (farthestNodeIndex == -1
                        || minDistanceToCenter[nodeIndex] > minDistanceToCenter[farthestNodeIndex])) {
                    farthestNodeIndex = nodeIndex;
                }
            }

            isCenterSelected[farthestNodeIndex] = true;

            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                minDistanceToCenter[nodeIndex] =
                    Math.min(minDistanceToCenter[nodeIndex], distanceMatrix[farthestNodeIndex][nodeIndex]);
            }
        }

        int maximumDistanceToNearestCenter = 0;
        for (int distanceToCenter : minDistanceToCenter) {
            maximumDistanceToNearestCenter =
                Math.max(maximumDistanceToNearestCenter, distanceToCenter);
        }

        return maximumDistanceToNearestCenter;
    }
}