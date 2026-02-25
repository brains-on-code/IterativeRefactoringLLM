package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class Class1 {

    private static final int INF = Integer.MAX_VALUE;

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static int maxFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];

            if (!bfsFindAugmentingPath(vertexCount, capacity, flow, source, sink, parent, visited)) {
                break;
            }

            int pathFlow = calculateBottleneckFlow(capacity, flow, parent, source, sink);
            updateFlowAlongPath(flow, parent, source, sink, pathFlow);

            maxFlow += pathFlow;
        }

        return maxFlow;
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
            int currentVertex = queue.poll();

            for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
                int residualCapacity = capacity[currentVertex][nextVertex] - flow[currentVertex][nextVertex];

                if (!visited[nextVertex] && residualCapacity > 0) {
                    queue.add(nextVertex);
                    visited[nextVertex] = true;
                    parent[nextVertex] = currentVertex;
                }
            }
        }

        return visited[sink];
    }

    private static int calculateBottleneckFlow(
        int[][] capacity,
        int[][] flow,
        int[] parent,
        int source,
        int sink
    ) {
        int pathFlow = INF;

        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previousVertex = parent[vertex];
            int residualCapacity = capacity[previousVertex][vertex] - flow[previousVertex][vertex];
            pathFlow = Math.min(pathFlow, residualCapacity);
        }

        return pathFlow;
    }

    private static void updateFlowAlongPath(
        int[][] flow,
        int[] parent,
        int source,
        int sink,
        int pathFlow
    ) {
        for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
            int previousVertex = parent[vertex];
            flow[previousVertex][vertex] += pathFlow;
            flow[vertex][previousVertex] -= pathFlow;
        }
    }
}