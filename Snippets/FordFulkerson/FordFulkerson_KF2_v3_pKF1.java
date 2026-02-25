package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class FordFulkerson {
    private static final int INFINITE_CAPACITY = Integer.MAX_VALUE;

    private FordFulkerson() {
    }

    public static int networkFlow(int vertexCount, int[][] capacityMatrix, int[][] flowMatrix, int sourceVertex, int sinkVertex) {
        int maximumFlow = 0;

        while (true) {
            int[] parentVertex = new int[vertexCount];
            boolean[] isVisited = new boolean[vertexCount];
            Queue<Integer> bfsQueue = new LinkedList<>();

            bfsQueue.add(sourceVertex);
            isVisited[sourceVertex] = true;
            parentVertex[sourceVertex] = -1;

            while (!bfsQueue.isEmpty() && !isVisited[sinkVertex]) {
                int currentVertex = bfsQueue.poll();

                for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
                    int residualCapacity = capacityMatrix[currentVertex][nextVertex] - flowMatrix[currentVertex][nextVertex];
                    boolean hasResidualCapacity = residualCapacity > 0;

                    if (!isVisited[nextVertex] && hasResidualCapacity) {
                        bfsQueue.add(nextVertex);
                        isVisited[nextVertex] = true;
                        parentVertex[nextVertex] = currentVertex;
                    }
                }
            }

            if (!isVisited[sinkVertex]) {
                break;
            }

            int pathFlow = INFINITE_CAPACITY;
            for (int vertex = sinkVertex; vertex != sourceVertex; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                int residualCapacity = capacityMatrix[previousVertex][vertex] - flowMatrix[previousVertex][vertex];
                pathFlow = Math.min(pathFlow, residualCapacity);
            }

            for (int vertex = sinkVertex; vertex != sourceVertex; vertex = parentVertex[vertex]) {
                int previousVertex = parentVertex[vertex];
                flowMatrix[previousVertex][vertex] += pathFlow;
                flowMatrix[vertex][previousVertex] -= pathFlow;
            }

            maximumFlow += pathFlow;
        }

        return maximumFlow;
    }
}