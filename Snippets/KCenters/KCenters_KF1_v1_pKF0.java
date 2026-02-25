package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Utility class for greedy algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum of the minimal edge weights selected in a greedy process.
     *
     * @param weights adjacency matrix of edge weights
     * @param steps   number of greedy selections to perform
     * @return maximum value among the selected minimal edge weights
     */
    public static int method1(int[][] weights, int steps) {
        int n = weights.length;
        boolean[] selected = new boolean[n];
        int[] minEdgeToNode = new int[n];

        Arrays.fill(minEdgeToNode, Integer.MAX_VALUE);

        selected[0] = true;
        for (int node = 1; node < n; node++) {
            minEdgeToNode[node] = Math.min(minEdgeToNode[node], weights[0][node]);
        }

        for (int step = 1; step < steps; step++) {
            int bestNode = -1;
            for (int node = 0; node < n; node++) {
                if (!selected[node] && (bestNode == -1 || minEdgeToNode[node] > minEdgeToNode[bestNode])) {
                    bestNode = node;
                }
            }

            selected[bestNode] = true;

            for (int node = 0; node < n; node++) {
                minEdgeToNode[node] = Math.min(minEdgeToNode[node], weights[bestNode][node]);
            }
        }

        int maxOfMins = 0;
        for (int value : minEdgeToNode) {
            maxOfMins = Math.max(maxOfMins, value);
        }

        return maxOfMins;
    }
}