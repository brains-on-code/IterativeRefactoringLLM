package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int vertexCount;

    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = initializeDistances(source);
        boolean[] processed = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = getMinDistanceVertex(distances, processed);
            processed[currentVertex] = true;

            updateNeighborDistances(graph, distances, processed, currentVertex);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range: " + source);
        }
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private void updateNeighborDistances(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            boolean isUnprocessedNeighbor = !processed[neighbor];
            boolean hasEdge = graph[currentVertex][neighbor] != 0;
            boolean hasKnownDistance = distances[currentVertex] != Integer.MAX_VALUE;

            if (isUnprocessedNeighbor && hasEdge && hasKnownDistance) {
                int newDistance = distances[currentVertex] + graph[currentVertex][neighbor];
                if (newDistance < distances[neighbor]) {
                    distances[neighbor] = newDistance;
                }
            }
        }
    }

    private int getMinDistanceVertex(int[] distances, boolean[] processed) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            boolean isUnprocessed = !processed[vertex];
            boolean isCloser = distances[vertex] <= minDistance;

            if (isUnprocessed && isCloser) {
                minDistance = distances[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}