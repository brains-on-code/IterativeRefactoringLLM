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
        int nodeCount = weights.length;
        if (nodeCount == 0 || steps <= 0) {
            return 0;
        }

        boolean[] isNodeSelected = new boolean[nodeCount];
        int[] minEdgeWeightToNode = new int[nodeCount];
        Arrays.fill(minEdgeWeightToNode, Integer.MAX_VALUE);

        // Start with node 0 selected
        isNodeSelected[0] = true;
        updateMinEdgeWeightsFromNode(0, weights, minEdgeWeightToNode);

        // Perform greedy selections
        for (int step = 1; step < steps; step++) {
            int nextNode = findUnselectedNodeWithMaxMinEdge(isNodeSelected, minEdgeWeightToNode);
            if (nextNode == -1) {
                break; // No more nodes to select
            }

            isNodeSelected[nextNode] = true;
            updateMinEdgeWeightsFromNode(nextNode, weights, minEdgeWeightToNode);
        }

        return findMaxValueOrZero(minEdgeWeightToNode);
    }

    private static void updateMinEdgeWeightsFromNode(int node, int[][] weights, int[] minEdgeWeightToNode) {
        int nodeCount = weights.length;
        for (int neighbor = 0; neighbor < nodeCount; neighbor++) {
            int edgeWeight = weights[node][neighbor];
            if (edgeWeight < minEdgeWeightToNode[neighbor]) {
                minEdgeWeightToNode[neighbor] = edgeWeight;
            }
        }
    }

    private static int findUnselectedNodeWithMaxMinEdge(boolean[] isNodeSelected, int[] minEdgeWeightToNode) {
        int bestNode = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int node = 0; node < isNodeSelected.length; node++) {
            if (!isNodeSelected[node] && minEdgeWeightToNode[node] > bestValue) {
                bestValue = minEdgeWeightToNode[node];
                bestNode = node;
            }
        }

        return bestNode;
    }

    private static int findMaxValueOrZero(int[] values) {
        int max = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max == Integer.MIN_VALUE ? 0 : max;
    }
}