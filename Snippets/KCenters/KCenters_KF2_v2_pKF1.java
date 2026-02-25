package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

public final class KCenters {

    private KCenters() {
    }

    public static int findKCenters(int[][] distanceMatrix, int numberOfCenters) {
        int numberOfNodes = distanceMatrix.length;
        boolean[] selectedCenters = new boolean[numberOfNodes];
        int[] minDistanceToAnyCenter = new int[numberOfNodes];

        Arrays.fill(minDistanceToAnyCenter, Integer.MAX_VALUE);

        selectedCenters[0] = true;
        for (int node = 1; node < numberOfNodes; node++) {
            minDistanceToAnyCenter[node] =
                Math.min(minDistanceToAnyCenter[node], distanceMatrix[0][node]);
        }

        for (int centersChosen = 1; centersChosen < numberOfCenters; centersChosen++) {
            int farthestNode = -1;
            for (int node = 0; node < numberOfNodes; node++) {
                if (!selectedCenters[node]
                        && (farthestNode == -1
                                || minDistanceToAnyCenter[node]
                                        > minDistanceToAnyCenter[farthestNode])) {
                    farthestNode = node;
                }
            }

            selectedCenters[farthestNode] = true;

            for (int node = 0; node < numberOfNodes; node++) {
                minDistanceToAnyCenter[node] =
                    Math.min(
                        minDistanceToAnyCenter[node],
                        distanceMatrix[farthestNode][node]);
            }
        }

        int maxOfMinDistances = 0;
        for (int distance : minDistanceToAnyCenter) {
            maxOfMinDistances = Math.max(maxOfMinDistances, distance);
        }

        return maxOfMinDistances;
    }
}