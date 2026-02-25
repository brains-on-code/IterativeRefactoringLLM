package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements the Ford-Fulkerson algorithm (using BFS / Edmonds-Karp)
 * to compute the maximum flow in a flow network.
 */
public final class FordFulkerson {

    private static final int INF = Integer.MAX_VALUE;
    private static final int UNVISITED = -1;

    private FordFulkerson() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum flow in a flow network.
     *
     * @param vertexCount number of vertices in the flow network
     * @param capacity    capacity[u][v] is the capacity of edge u -> v
     * @param flow        flow[u][v] is the current flow on edge u -> v
     * @param source      source vertex
     * @param sink        sink vertex
     * @return total maximum flow from source to sink
     */
    public static int networkFlow(
            int vertexCount,
            int[][] capacity,
            int[][] flow,
            int source,
            int sink
    ) {
        int totalFlow = 0;
        int[] parent = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        while (bfsFindAugmentingPath(capacity, flow, source, sink, parent, visited)) {
            int pathFlow = findBottleneckCapacity(capacity, flow, source, sink, parent);
            augmentFlowAlongPath(flow, source, sink, parent, pathFlow);
            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    /**
     * Uses BFS to find an augmenting path in the residual graph.
     *
     * @return true if a path from source to sink exists, false otherwise
     */
    private static boolean bfsFindAugmentingPath(
            int[][] capacity,
            int[][] flow,
            int source,
            int sink,
            int[] parent,
            boolean[] visited
    ) {
        int vertexCount = capacity.length;
        Queue<Integer> queue = new LinkedList<>();

        resetSearchState(vertexCount, parent, visited);

        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next = 0; next < vertexCount; next++) {
                int residualCapacity = capacity[current][next] - flow[current][next];

                if (visited[next] || residualCapacity <= 0) {
                    continue;
                }

                parent[next] = current;
                visited[next] = true;
                queue.add(next);

                if (next == sink) {
                    return true;
                }
            }
        }

        return visited[sink];
    }

    private static void resetSearchState(int vertexCount, int[] parent, boolean[] visited) {
        for (int i = 0; i < vertexCount; i++) {
            visited[i] = false;
            parent[i] = UNVISITED;
        }
    }

    /**
     * Computes the bottleneck (minimum residual capacity) along the found path.
     */
    private static int findBottleneckCapacity(
            int[][] capacity,
            int[][] flow,
            int source,
            int sink,
            int[] parent
    ) {
        int pathFlow = INF;

        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previous = parent[vertex];
            int residualCapacity = capacity[previous][vertex] - flow[previous][vertex];
            pathFlow = Math.min(pathFlow, residualCapacity);
        }

        return pathFlow;
    }

    /**
     * Augments the flow along the path by the given amount.
     */
    private static void augmentFlowAlongPath(
            int[][] flow,
            int source,
            int sink,
            int[] parent,
            int pathFlow
    ) {
        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previous = parent[vertex];
            flow[previous][vertex] += pathFlow;
            flow[vertex][previous] -= pathFlow;
        }
    }
}