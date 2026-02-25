package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {}

    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] isCenter = new boolean[numberOfNodes];
        int[] minDistanceToCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToCenter, Integer.MAX_VALUE);

        // Choose the first node as the initial center
        isCenter[0] = true;
        for (int node = 1; node < numberOfNodes; node++) {
            minDistanceToCenter[node] =
                Math.min(minDistanceToCenter[node], distanceMatrix[0][node]);
        }

        // Select remaining centers
        for (int centersChosen = 1; centersChosen < numberOfCenters; centersChosen++) {
            int farthestNode = -1;

            // Find the node farthest from any existing center
            for (int node = 0; node < numberOfNodes; node++) {
                if (!isCenter[node]
                        && (farthestNode == -1
                                || minDistanceToCenter[node] > minDistanceToCenter[farthestNode])) {
                    farthestNode = node;
                }
            }

            isCenter[farthestNode] = true;

            // Update minimum distances to the set of chosen centers
            for (int node = 0; node < numberOfNodes; node++) {
                minDistanceToCenter[node] =
                    Math.min(
                        minDistanceToCenter[node],
                        distanceMatrix[farthestNode][node]);
            }
        }

        int maxMinDistance = 0;
        for (int distanceToClosestCenter : minDistanceToCenter) {
            maxMinDistance = Math.max(maxMinDistance, distanceToClosestCenter);
        }

        return maxMinDistance;
    }
}