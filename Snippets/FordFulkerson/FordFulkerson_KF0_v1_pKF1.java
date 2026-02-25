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
        int totalFlow = 0;

        while (true) {
            int[] parentVertex = new int[vertexCount];
            boolean[] isVisited = new boolean[vertexCount];
            Queue<Integer> bfsQueue = new LinkedList<>();

            bfsQueue.add(source);
            isVisited[source] = true;
            parentVertex[source] = -1;

            while (!bfsQueue.isEmpty() && !isVisited[sink]) {
                int currentVertex = bfsQueue.poll();

                for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
                    boolean hasResidualCapacity =
                        capacity[currentVertex][nextVertex] - flow[currentVertex][nextVertex] > 0;

                    if (!isVisited[nextVertex] && hasResidualCapacity) {
                        bfsQueue.add(nextVertex);
                        isVisited[nextVertex] = true;
                        parentVertex[nextVertex] = currentVertex;
                    }
                }
            }

            if (!isVisited[sink]) {
                break; // No more augmenting paths
            }

            int bottleneckFlow = INFINITE_FLOW;
            for (int vertex = sink; vertex != source; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                bottleneckFlow =
                    Math.min(bottleneckFlow, capacity[previousVertex][vertex] - flow[previousVertex][vertex]);
            }

            for (int vertex = sink; vertex != source; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                flow[previousVertex][vertex] += bottleneckFlow;
                flow[vertex][previousVertex] -= bottleneckFlow;
            }

            totalFlow += bottleneckFlow;
        }

        return totalFlow;
    }
}