package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;


public class DijkstraAlgorithm {

    private final int vertexCount;


    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }


    public int[] run(int[][] graph, int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] processed = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);
        distances[source] = 0;

        for (int count = 0; count < vertexCount - 1; count++) {
            int u = getMinDistanceVertex(distances, processed);
            processed[u] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (!processed[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                }
            }
        }

        printDistances(distances);
        return distances;
    }


    private int getMinDistanceVertex(int[] distances, boolean[] processed) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertexCount; v++) {
            if (!processed[v] && distances[v] <= min) {
                min = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }


    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int i = 0; i < vertexCount; i++) {
            System.out.println(i + " \t " + distances[i]);
        }
    }
}
