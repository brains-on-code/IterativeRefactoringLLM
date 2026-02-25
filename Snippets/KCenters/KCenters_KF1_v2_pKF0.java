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
        boolean[] isSelected = new boolean[nodeCount];
        int[] minEdgeToNode = new int[nodeCount];

        Arrays.fill(minEdgeToNode, Integer.MAX_VALUE);

        // Initialize with node 0 selected
        isSelected[0] = true;
        updateMinEdgesFromNode(0, weights, minEdgeToNode);

        // Perform greedy selections
        for (int step = 1; step < steps; step++) {
            int nextNode = findNodeWithMaxMinEdge(isSelected, minEdgeToNode);
            if (nextNode == -1) {
                break; // No more nodes to select
            }

            isSelected[nextNode] = true;
            updateMinEdgesFromNode(nextNode, weights, minEdgeToNode);
        }

        return findMaxValue(minEdgeToNode);
    }

    private static void updateMinEdgesFromNode(int node, int[][] weights, int[] minEdgeToNode) {
        for (int neighbor = 0; neighbor < weights.length; neighbor++) {
            minEdgeToNode[neighbor] = Math.min(minEdgeToNode[neighbor], weights[node][neighbor]);
        }
    }

    private static int findNodeWithMaxMinEdge(boolean[] isSelected, int[] minEdgeToNode) {
        int bestNode = -1;
        for (int node = 0; node < isSelected.length; node++) {
            if (!isSelected[node] && (bestNode == -1 || minEdgeToNode[node] > minEdgeToNode[bestNode])) {
                bestNode = node;
            }
        }
        return bestNode;
    }

    private static int findMaxValue(int[] values) {
        int max = 0;
        for (int value : values) {
            max = Math.max(max, value);
        }
        return max;
    }
}