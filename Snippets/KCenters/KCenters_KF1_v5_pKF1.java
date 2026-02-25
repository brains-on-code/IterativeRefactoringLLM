package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Utility class for greedy graph algorithms.
 */
public final class GraphGreedyUtils {

    private GraphGreedyUtils() {
        // Utility class; prevent instantiation.
    }

    /**
     * Given a weighted adjacency matrix and a number of steps, repeatedly selects
     * the unvisited node whose best (maximum) known edge to the visited set is
     * largest, then updates all nodes' best-known edge weights from that node.
     *
     * Finally returns the maximum value among all nodes' best-known edge weights.
     *
     * @param graph adjacency matrix of edge weights
     * @param steps number of selection/relaxation iterations to perform
     * @return the maximum of the per-node minimum edge weights
     */
    public static int getMaxOfMinEdgeWeights(int[][] graph, int steps) {
        int nodeCount = graph.length;
        boolean[] visited = new boolean[nodeCount];
        int[] minEdgeWeightToNode = new int[nodeCount];

        Arrays.fill(minEdgeWeightToNode, Integer.MAX_VALUE);

        visited[0] = true;
        for (int node = 1; node < nodeCount; node++) {
            minEdgeWeightToNode[node] =
                Math.min(minEdgeWeightToNode[node], graph[0][node]);
        }

        for (int step = 1; step < steps; step++) {
            int bestUnvisitedNode = -1;

            for (int node = 0; node < nodeCount; node++) {
                if (!visited[node]
                    && (bestUnvisitedNode == -1
                        || minEdgeWeightToNode[node] > minEdgeWeightToNode[bestUnvisitedNode])) {
                    bestUnvisitedNode = node;
                }
            }

            visited[bestUnvisitedNode] = true;

            for (int node = 0; node < nodeCount; node++) {
                minEdgeWeightToNode[node] =
                    Math.min(minEdgeWeightToNode[node], graph[bestUnvisitedNode][node]);
            }
        }

        int maxOfMinEdgeWeights = 0;
        for (int edgeWeight : minEdgeWeightToNode) {
            maxOfMinEdgeWeights = Math.max(maxOfMinEdgeWeights, edgeWeight);
        }
        return maxOfMinEdgeWeights;
    }
}