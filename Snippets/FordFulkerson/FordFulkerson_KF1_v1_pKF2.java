package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Utility class for computing maximum flow in a graph using the
 * Edmonds–Karp (BFS-based Ford–Fulkerson) algorithm.
 */
public final class Class1 {

    /** Represents "infinity" for initial bottleneck capacity. */
    private static final int INF = Integer.MAX_VALUE;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the maximum flow from a source to a sink in a directed graph.
     *
     * @param vertexCount   number of vertices in the graph
     * @param capacity      capacity[u][v] is the capacity of edge u -> v
     * @param flow          flow[u][v] is the current flow on edge u -> v (will be modified)
     * @param source        index of the source vertex
     * @param sink          index of the sink vertex
     * @return              the value of the maximum flow from source to sink
     */
    public static int method1(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            // BFS to find an augmenting path in the residual graph
            while (!queue.isEmpty() && !visited[sink]) {
                int u = queue.poll();

                for (int v = 0; v < vertexCount; v++) {
                    if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                        queue.add(v);
                        visited[v] = true;
                        parent[v] = u;
                    }
                }
            }

            // No augmenting path found
            if (!visited[sink]) {
                break;
            }

            // Find bottleneck capacity along the found path
            int pathFlow = INF;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            // Augment flow along the path
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