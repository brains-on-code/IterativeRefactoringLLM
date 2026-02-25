package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class MaxFlowEdmondsKarp {

    private static final int INFINITE_CAPACITY = Integer.MAX_VALUE;

    private MaxFlowEdmondsKarp() {}

    public static int computeMaxFlow(
            int vertexCount,
            int[][] capacity,
            int[][] flow,
            int source,
            int sink
    ) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            // Find an augmenting path using BFS on the residual graph
            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

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

            // No augmenting path exists; current maxFlow is optimal
            if (!visited[sink]) {
                break;
            }

            // Determine the bottleneck (minimum residual capacity) along the path
            int pathFlow = INFINITE_CAPACITY;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                int residualCapacity = capacity[u][v] - flow[u][v];
                pathFlow = Math.min(pathFlow, residualCapacity);
            }

            // Augment flow along the path and update reverse edges
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