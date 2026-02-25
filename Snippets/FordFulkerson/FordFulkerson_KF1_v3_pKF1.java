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
            int[] parentVertex = new int[vertexCount];
            boolean[] isVisited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            isVisited[source] = true;
            parentVertex[source] = -1;

            while (!queue.isEmpty() && !isVisited[sink]) {
                int currentVertex = queue.poll();

                for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                    int residualCapacity =
                            capacity[currentVertex][neighborVertex] - flow[currentVertex][neighborVertex];
                    boolean hasResidualCapacity = residualCapacity > 0;

                    if (!isVisited[neighborVertex] && hasResidualCapacity) {
                        queue.add(neighborVertex);
                        isVisited[neighborVertex] = true;
                        parentVertex[neighborVertex] = currentVertex;
                    }
                }
            }

            if (!isVisited[sink]) {
                break;
            }

            int bottleneckFlow = INFINITY;
            for (int vertex = sink; vertex != source; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                int residualCapacity =
                        capacity[previousVertex][vertex] - flow[previousVertex][vertex];
                bottleneckFlow = Math.min(bottleneckFlow, residualCapacity);
            }

            for (int vertex = sink; vertex != source; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                flow[previousVertex][vertex] += bottleneckFlow;
                flow[vertex][previousVertex] -= bottleneckFlow;
            }

            maxFlow += bottleneckFlow;
        }

        return maxFlow;
    }
}