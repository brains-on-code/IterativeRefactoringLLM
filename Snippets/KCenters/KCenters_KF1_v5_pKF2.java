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
     * Runs a greedy process on a weighted graph and returns the maximum, over all nodes,
     * of the minimal edge weight that connects each node to the set of visited nodes.
     *
     * The process:
     * <ol>
     *   <li>Start from node 0 (marked as visited).</li>
     *   <li>For each unvisited node, track the minimal edge weight from any visited node.</li>
     *   <li>At each step, pick the unvisited node whose current minimal edge to the visited set
     *       is as large as possible.</li>
     *   <li>Update the minimal edge values for all remaining unvisited nodes.</li>
     *   <li>Stop after {@code steps} iterations or when no more nodes can be selected.</li>
     *   <li>Return the maximum value among all tracked minimal edge weights.</li>
     * </ol>
     *
     * @param graph adjacency matrix of edge weights; {@code graph[i][j]} is the weight from i to j
     * @param steps number of greedy selection steps to perform
     * @return the maximum of the minimal edge weights encountered
     */
    public static int method1(int[][] graph, int steps) {
        int n = graph.length;

        boolean[] visited = new boolean[n];

        // minimalEdgeToVisited[i] = minimal edge weight from any visited node to node i
        int[] minimalEdgeToVisited = new int[n];
        Arrays.fill(minimalEdgeToVisited, Integer.MAX_VALUE);

        // Start from node 0
        visited[0] = true;

        // Initialize minimal edges from node 0
        for (int i = 1; i < n; i++) {
            minimalEdgeToVisited[i] = Math.min(minimalEdgeToVisited[i], graph[0][i]);
        }

        // Perform greedy selection for the given number of steps
        for (int step = 1; step < steps; step++) {
            int nextNode = -1;

            // Select the unvisited node with the largest minimalEdgeToVisited value
            for (int i = 0; i < n; i++) {
                if (!visited[i]
                        && (nextNode == -1
                        || minimalEdgeToVisited[i] > minimalEdgeToVisited[nextNode])) {
                    nextNode = i;
                }
            }

            // No more nodes can be selected
            if (nextNode == -1) {
                break;
            }

            visited[nextNode] = true;

            // Update minimalEdgeToVisited values using the newly visited node
            for (int i = 0; i < n; i++) {
                minimalEdgeToVisited[i] =
                        Math.min(minimalEdgeToVisited[i], graph[nextNode][i]);
            }
        }

        // Compute the maximum among all minimal edge weights
        int maxMinimalEdge = 0;
        for (int edgeWeight : minimalEdgeToVisited) {
            maxMinimalEdge = Math.max(maxMinimalEdge, edgeWeight);
        }
        return maxMinimalEdge;
    }
}