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
    private static final int INFINITE_FLOW = Integer.MAX_VALUE;

    private FordFulkerson() {
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
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            while (!queue.isEmpty() && !visited[sink]) {
                int current = queue.poll();

                for (int next = 0; next < vertexCount; next++) {
                    boolean hasResidualCapacity =
                        capacity[current][next] - flow[current][next] > 0;

                    if (!visited[next] && hasResidualCapacity) {
                        queue.add(next);
                        visited[next] = true;
                        parent[next] = current;
                    }
                }
            }

            if (!visited[sink]) {
                break; // No more augmenting paths
            }

            int pathFlow = INFINITE_FLOW;
            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previous = parent[vertex];
                pathFlow = Math.min(pathFlow, capacity[previous][vertex] - flow[previous][vertex]);
            }

            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previous = parent[vertex];
                flow[previous][vertex] += pathFlow;
                flow[vertex][previous] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}