package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

public final class Class1 {
    private static final int INF = Integer.MAX_VALUE;

    private Class1() {
    }

    public static int maxFlow(int vertexCount, int[][] capacity, int[][] flow, int source, int sink) {
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
                    if (!visited[next] && capacity[current][next] - flow[current][next] > 0) {
                        queue.add(next);
                        visited[next] = true;
                        parent[next] = current;
                    }
                }
            }

            if (!visited[sink]) {
                break;
            }

            int pathFlow = INF;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}