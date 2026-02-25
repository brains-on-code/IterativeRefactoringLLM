package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Utility class for greedy algorithms.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum of the minimal edge weights selected over a greedy process.
     *
     * The algorithm:
     * <ol>
     *   <li>Starts from node 0.</li>
     *   <li>Maintains, for each unvisited node, the minimal edge weight from any visited node.</li>
     *   <li>At each step, selects the unvisited node whose current minimal edge to the visited set
     *       is as large as possible.</li>
     *   <li>Updates the minimal edge values for all remaining unvisited nodes.</li>
     *   <li>After {@code steps} iterations (or when no more nodes can be selected),
     *       returns the maximum value among all recorded minimal edge weights.</li>
     * </ol>
     *
     * @param graph adjacency matrix of edge weights; {@code graph[i][j]} is the weight from i to j
     * @param steps number of greedy selection steps to perform
     * @return the maximum of the minimal edge weights encountered
     */
    public static int method1(int[][] graph, int steps) {
        int n = graph.length;

        boolean[] visited = new boolean[n];

        // bestEdge[i] is the minimal edge weight from any visited node to node i
        int[] bestEdge = new int[n];
        Arrays.fill(bestEdge, Integer.MAX_VALUE);

        // Start from node 0
        visited[0] = true;

        // Initialize best edges from node 0
        for (int i = 1; i < n; i++) {
            bestEdge[i] = Math.min(bestEdge[i], graph[0][i]);
        }

        // Perform greedy selection for the given number of steps
        for (int step = 1; step < steps; step++) {
            int nextNode = -1;

            // Select the unvisited node with the largest bestEdge value
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (nextNode == -1 || bestEdge[i] > bestEdge[nextNode])) {
                    nextNode = i;
                }
            }

            // No more nodes can be selected
            if (nextNode == -1) {
                break;
            }

            visited[nextNode] = true;

            // Update bestEdge values using the newly visited node
            for (int i = 0; i < n; i++) {
                bestEdge[i] = Math.min(bestEdge[i], graph[nextNode][i]);
            }
        }

        // Compute the maximum among all minimal edge weights
        int result = 0;
        for (int edgeWeight : bestEdge) {
            result = Math.max(result, edgeWeight);
        }
        return result;
    }
}