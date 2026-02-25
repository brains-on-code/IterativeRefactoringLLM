package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int vertexCount;

    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] graph, int source) {
        validateSource(source);
        validateGraph(graph);

        int[] distances = initializeDistances(source);
        boolean[] processed = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = getMinDistanceVertex(distances, processed);
            if (currentVertex == -1) {
                break; // Remaining vertices are unreachable
            }
            processed[currentVertex] = true;
            relaxAdjacentVertices(graph, distances, processed, currentVertex);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    private void validateGraph(int[][] graph) {
        if (graph == null || graph.length != vertexCount) {
            throw new IllegalArgumentException("Graph must be a non-null square matrix of size " + vertexCount);
        }
        for (int[] row : graph) {
            if (row == null || row.length != vertexCount) {
                throw new IllegalArgumentException("Graph must be a non-null square matrix of size " + vertexCount);
            }
        }
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private void relaxAdjacentVertices(
            int[][] graph,
            int[] distances,
            boolean[] processed,
            int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            int edgeWeight = graph[currentVertex][neighbor];
            if (isRelaxationPossible(distances, processed, currentVertex, neighbor, edgeWeight)) {
                distances[neighbor] = distances[currentVertex] + edgeWeight;
            }
        }
    }

    private boolean isRelaxationPossible(
            int[] distances,
            boolean[] processed,
            int currentVertex,
            int neighbor,
            int edgeWeight
    ) {
        return !processed[neighbor]
                && edgeWeight != 0
                && distances[currentVertex] != Integer.MAX_VALUE
                && distances[currentVertex] + edgeWeight < distances[neighbor];
    }

    private int getMinDistanceVertex(int[] distances, boolean[] processed) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!processed[vertex] && distances[vertex] <= minDistance) {
                minDistance = distances[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    private void printDistances(int[] distances) {
        System.out.println("Vertex\tDistance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + "\t" + distances[vertex]);
        }
    }
}