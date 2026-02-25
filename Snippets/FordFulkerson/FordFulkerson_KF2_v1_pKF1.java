package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class FordFulkerson {
    private static final int INFINITE_CAPACITY = Integer.MAX_VALUE;

    private FordFulkerson() {
    }

    public static int networkFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;

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
                    boolean hasResidualCapacity = capacity[currentVertex][nextVertex] - flow[currentVertex][nextVertex] > 0;
                    if (!isVisited[nextVertex] && hasResidualCapacity) {
                        bfsQueue.add(nextVertex);
                        isVisited[nextVertex] = true;
                        parentVertex[nextVertex] = currentVertex;
                    }
                }
            }

            if (!isVisited[sink]) {
                break;
            }

            int bottleneckFlow = INFINITE_CAPACITY;
            for (int vertex = sink; vertex != source; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                int residualCapacity = capacity[previousVertex][vertex] - flow[previousVertex][vertex];
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