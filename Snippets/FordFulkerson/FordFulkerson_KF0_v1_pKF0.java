package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements the Ford-Fulkerson algorithm to compute the maximum flow
 * in a flow network.
 *
 * <p>The algorithm uses breadth-first search (BFS) to find augmenting paths from
 * the source vertex to the sink vertex, updating the flow in the network until
 * no more augmenting paths can be found.</p>
 */
public final class FordFulkerson {
    private static final int INF = Integer.MAX_VALUE;

    private FordFulkerson() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum flow in a flow network using the Ford-Fulkerson algorithm.
     *
     * @param vertexCount the number of vertices in the flow network
     * @param capacity    a 2D array representing the capacity of edges in the network
     * @param flow        a 2D array representing the current flow in the network
     * @param source      the source vertex in the flow network
     * @param sink        the sink vertex in the flow network
     * @return the total maximum flow from the source to the sink
     */
    public static int networkFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int totalFlow = 0;

        int[] parent = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        while (bfs(capacity, flow, source, sink, parent, visited)) {
            int pathFlow = findPathFlow(capacity, flow, source, sink, parent);
            updateFlowAlongPath(flow, source, sink, parent, pathFlow);
            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    private static boolean bfs(
            int[][] capacity,
            int[][] flow,
            int source,
            int sink,
            int[] parent,
            boolean[] visited
    ) {
        int vertexCount = capacity.length;
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < vertexCount; i++) {
            visited[i] = false;
            parent[i] = -1;
        }

        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next = 0; next < vertexCount; next++) {
                boolean hasCapacity = capacity[current][next] - flow[current][next] > 0;
                if (!visited[next] && hasCapacity) {
                    queue.add(next);
                    visited[next] = true;
                    parent[next] = current;

                    if (next == sink) {
                        return true;
                    }
                }
            }
        }

        return visited[sink];
    }

    private static int findPathFlow(
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

    private static void updateFlowAlongPath(
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