package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class FordFulkerson {
    private static final int INF = Integer.MAX_VALUE;

    private FordFulkerson() {
        // Utility class; prevent instantiation
    }

    public static int networkFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int totalFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];

            if (!bfsFindAugmentingPath(vertexCount, capacity, flow, source, sink, parent, visited)) {
                break;
            }

            int pathFlow = findPathFlow(capacity, flow, parent, source, sink);
            updateResidualCapacities(flow, parent, source, sink, pathFlow);

            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    private static boolean bfsFindAugmentingPath(
            int vertexCount,
            int[][] capacity,
            int[][] flow,
            int source,
            int sink,
            int[] parent,
            boolean[] visited
    ) {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty() && !visited[sink]) {
            int current = queue.poll();

            for (int next = 0; next < vertexCount; next++) {
                boolean hasCapacity = capacity[current][next] - flow[current][next] > 0;
                if (!visited[next] && hasCapacity) {
                    queue.add(next);
                    visited[next] = true;
                    parent[next] = current;
                }
            }
        }

        return visited[sink];
    }

    private static int findPathFlow(int[][] capacity, int[][] flow, int[] parent, int source, int sink) {
        int pathFlow = INF;

        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previous = parent[vertex];
            int residualCapacity = capacity[previous][vertex] - flow[previous][vertex];
            pathFlow = Math.min(pathFlow, residualCapacity);
        }

        return pathFlow;
    }

    private static void updateResidualCapacities(int[][] flow, int[] parent, int source, int sink, int pathFlow) {
        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previous = parent[vertex];
            flow[previous][vertex] += pathFlow;
            flow[vertex][previous] -= pathFlow;
        }
    }
}