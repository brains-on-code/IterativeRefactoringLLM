package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Utility class for computing maximum flow in a graph using the
 * Edmonds–Karp (BFS-based Ford–Fulkerson) algorithm.
 */
public final class MaxFlowEdmondsKarp {

    /** Sentinel value representing "infinite" capacity when searching for a bottleneck. */
    private static final int INFINITE_CAPACITY = Integer.MAX_VALUE;

    private MaxFlowEdmondsKarp() {
        // Prevent instantiation of utility class.
    }

    /**
     * Computes the maximum flow from a source to a sink in a directed graph.
     *
     * @param vertexCount number of vertices in the graph
     * @param capacity    capacity[u][v] is the capacity of edge u -> v
     * @param flow        flow[u][v] is the current flow on edge u -> v (will be modified)
     * @param source      index of the source vertex
     * @param sink        index of the sink vertex
     * @return the value of the maximum flow from source to sink
     */
    public static int computeMaxFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            // Breadth-first search on the residual graph to find an augmenting path.
            while (!queue.isEmpty() && !visited[sink]) {
                int u = queue.poll();

                for (int v = 0; v < vertexCount; v++) {
                    int residualCapacity = capacity[u][v] - flow[u][v];
                    if (!visited[v] && residualCapacity > 0) {
                        queue.add(v);
                        visited[v] = true;
                        parent[v] = u;
                    }
                }
            }

            // If the sink was not reached, there is no augmenting path left.
            if (!visited[sink]) {
                break;
            }

            // Determine the bottleneck (minimum residual capacity) along the augmenting path.
            int pathFlow = INFINITE_CAPACITY;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                int residualCapacity = capacity[u][v] - flow[u][v];
                pathFlow = Math.min(pathFlow, residualCapacity);
            }

            // Augment the flow along the path and update reverse edges.
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}