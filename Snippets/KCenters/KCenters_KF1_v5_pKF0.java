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

        boolean[] selectedNodes = new boolean[nodeCount];
        int[] minEdgeToNode = new int[nodeCount];
        Arrays.fill(minEdgeToNode, Integer.MAX_VALUE);

        // Start with node 0 selected
        selectedNodes[0] = true;
        updateMinEdgeWeightsFromNode(0, weights, minEdgeToNode);

        // Perform greedy selections
        for (int step = 1; step < steps; step++) {
            int nextNode = findUnselectedNodeWithMaxMinEdge(selectedNodes, minEdgeToNode);
            if (nextNode == -1) {
                break; // No more nodes to select
            }

            selectedNodes[nextNode] = true;
            updateMinEdgeWeightsFromNode(nextNode, weights, minEdgeToNode);
        }

        return findMaxValueOrZero(minEdgeToNode);
    }

    private static void updateMinEdgeWeightsFromNode(int node, int[][] weights, int[] minEdgeToNode) {
        int nodeCount = weights.length;
        for (int neighbor = 0; neighbor < nodeCount; neighbor++) {
            int edgeWeight = weights[node][neighbor];
            if (edgeWeight < minEdgeToNode[neighbor]) {
                minEdgeToNode[neighbor] = edgeWeight;
            }
        }
    }

    private static int findUnselectedNodeWithMaxMinEdge(boolean[] selectedNodes, int[] minEdgeToNode) {
        int bestNode = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int node = 0; node < selectedNodes.length; node++) {
            if (!selectedNodes[node] && minEdgeToNode[node] > bestValue) {
                bestValue = minEdgeToNode[node];
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