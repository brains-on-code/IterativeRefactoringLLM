package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class FordFulkerson {
    private static final int INF = Integer.MAX_VALUE;

    private FordFulkerson() {}

    /**
     * Computes the maximum flow in a directed graph using the Ford–Fulkerson
     * method with BFS (Edmonds–Karp implementation).
     *
     * @param vertexCount number of vertices in the graph
     * @param capacity    capacity[u][v] is the capacity of edge u -> v
     * @param flow        flow[u][v] is the current flow on edge u -> v (will be modified)
     * @param source      index of the source vertex
     * @param sink        index of the sink vertex
     * @return maximum flow value from source to sink
     */
    public static int networkFlow(
            int vertexCount,
            int[][] capacity,
            int[][] flow,
            int source,
            int sink
    ) {
        int totalFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            // Find an augmenting path in the residual graph using BFS
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

            // No augmenting path exists if sink was not reached
            if (!visited[sink]) {
                break;
            }

            // Determine the minimum residual capacity (bottleneck) along the path
            int pathFlow = INF;
            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previous = parent[vertex];
                int residualCapacity = capacity[previous][vertex] - flow[previous][vertex];
                pathFlow = Math.min(pathFlow, residualCapacity);
            }

            // Augment flow along the path and update reverse edges in the residual graph
            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previous = parent[vertex];
                flow[previous][vertex] += pathFlow;
                flow[vertex][previous] -= pathFlow;
            }

            totalFlow += pathFlow;
        }

        return totalFlow;
    }
}