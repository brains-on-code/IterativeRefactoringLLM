package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the Edmonds–Karp algorithm for computing the maximum flow
 * in a directed graph.
 */
public final class MaxFlowEdmondsKarp {
    private static final int INFINITY = Integer.MAX_VALUE;

    private MaxFlowEdmondsKarp() {
    }

    /**
     * Computes the maximum flow from {@code source} to {@code sink} in a graph.
     *
     * @param vertexCount number of vertices in the graph
     * @param capacity    capacity[u][v] is the capacity of edge u -> v
     * @param flow        flow[u][v] is the current flow on edge u -> v
     * @param source      source vertex
     * @param sink        sink vertex
     * @return the value of the maximum flow
     */
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
            Queue<Integer> bfsQueue = new LinkedList<>();

            bfsQueue.add(source);
            visited[source] = true;
            parent[source] = -1;

            while (!bfsQueue.isEmpty() && !visited[sink]) {
                int current = bfsQueue.poll();

                for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                    int residualCapacity = capacity[current][neighbor] - flow[current][neighbor];
                    boolean hasResidualCapacity = residualCapacity > 0;

                    if (!visited[neighbor] && hasResidualCapacity) {
                        bfsQueue.add(neighbor);
                        visited[neighbor] = true;
                        parent[neighbor] = current;
                    }
                }
            }

            if (!visited[sink]) {
                break;
            }

            int pathFlow = INFINITY;
            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previous = parent[vertex];
                int residualCapacity = capacity[previous][vertex] - flow[previous][vertex];
                pathFlow = Math.min(pathFlow, residualCapacity);
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