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
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            while (!queue.isEmpty() && !visited[sink]) {
                int current = queue.poll();

                for (int next = 0; next < vertexCount; next++) {
                    int residualCapacity = capacity[current][next] - flow[current][next];
                    boolean hasResidualCapacity = residualCapacity > 0;

                    if (!visited[next] && hasResidualCapacity) {
                        queue.add(next);
                        visited[next] = true;
                        parent[next] = current;
                    }
                }
            }

            if (!visited[sink]) {
                break;
            }

            int pathFlow = INFINITE_CAPACITY;
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