package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ford-Fulkerson method using Edmonds-Karp (BFS) to compute
 * the maximum flow in a directed graph.
 */
public final class FordFulkerson {

    private static final int INF = Integer.MAX_VALUE;

    private FordFulkerson() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum flow in a flow network.
     *
     * @param vertexCount number of vertices in the graph
     * @param capacity    capacity[u][v] is the capacity of edge u -> v
     * @param flow        flow[u][v] is the current flow on edge u -> v
     * @param source      source vertex
     * @param sink        sink vertex
     * @return maximum flow value from source to sink
     */
    public static int networkFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int totalFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];

            boolean hasAugmentingPath = bfsFindAugmentingPath(
                vertexCount,
                capacity,
                flow,
                source,
                sink,
                parent,
                visited
            );

            if (!hasAugmentingPath) {
                break;
            }

            int pathFlow = findPathResidualCapacity(capacity, flow, parent, source, sink);
            augmentFlowAlongPath(flow, parent, source, sink, pathFlow);

            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    /**
     * Uses BFS to find an augmenting path in the residual graph.
     * Populates the {@code parent} array to reconstruct the path.
     *
     * @return {@code true} if sink is reachable from source in the residual graph
     */
    private static boolean bfsFindAugmentingPath(
        int vertexCount,
        int[][] capacity,
        int[][] flow,
        int source,
        int sink,
        int[] parent,
        boolean[] visited
    ) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty() && !visited[sink]) {
            int current = queue.poll();

            for (int next = 0; next < vertexCount; next++) {
                int residualCapacity = capacity[current][next] - flow[current][next];

                if (!visited[next] && residualCapacity > 0) {
                    queue.add(next);
                    visited[next] = true;
                    parent[next] = current;
                }
            }
        }

        return visited[sink];
    }

    /**
     * Computes the minimum residual capacity along the path from
     * {@code source} to {@code sink} defined by {@code parent}.
     */
    private static int findPathResidualCapacity(
        int[][] capacity,
        int[][] flow,
        int[] parent,
        int source,
        int sink
    ) {
        int pathFlow = INF;

        for (int v = sink; v != source; v = parent[v]) {
            int u = parent[v];
            int residualCapacity = capacity[u][v] - flow[u][v];
            pathFlow = Math.min(pathFlow, residualCapacity);
        }

        return pathFlow;
    }

    /**
     * Augments the flow along the path from {@code source} to {@code sink}
     * by {@code pathFlow}.
     */
    private static void augmentFlowAlongPath(
        int[][] flow,
        int[] parent,
        int source,
        int sink,
        int pathFlow
    ) {
        for (int v = sink; v != source; v = parent[v]) {
            int u = parent[v];
            flow[u][v] += pathFlow;
            flow[v][u] -= pathFlow;
        }
    }
}