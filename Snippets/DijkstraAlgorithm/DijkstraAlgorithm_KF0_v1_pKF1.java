package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex to all other vertices in a graph.
 */
public class DijkstraAlgorithm {

    private final int vertexCount;

    /**
     * Constructs a Dijkstra object with the given number of vertices.
     *
     * @param vertexCount The number of vertices in the graph.
     */
    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Executes Dijkstra's algorithm on the provided graph to find the shortest paths from the source vertex to all other vertices.
     *
     * The graph is represented as an adjacency matrix where {@code graph[i][j]} represents the weight of the edge from vertex {@code i}
     * to vertex {@code j}. A value of 0 indicates no edge exists between the vertices.
     *
     * @param adjacencyMatrix The graph represented as an adjacency matrix.
     * @param sourceVertex The source vertex.
     * @return An array where the value at each index {@code i} represents the shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }

        int[] shortestDistances = new int[vertexCount];
        boolean[] isVertexProcessed = new boolean[vertexCount];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVertexProcessed, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int currentVertex = getMinDistanceVertex(shortestDistances, isVertexProcessed);
            isVertexProcessed[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                boolean isNeighborUnprocessed = !isVertexProcessed[neighborVertex];
                boolean isEdgePresent = adjacencyMatrix[currentVertex][neighborVertex] != 0;
                boolean isCurrentDistanceFinite = shortestDistances[currentVertex] != Integer.MAX_VALUE;

                if (isNeighborUnprocessed && isEdgePresent && isCurrentDistanceFinite) {
                    int newDistance = shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                    if (newDistance < shortestDistances[neighborVertex]) {
                        shortestDistances[neighborVertex] = newDistance;
                    }
                }
            }
        }

        printDistances(shortestDistances);
        return shortestDistances;
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices that have not yet been processed.
     *
     * @param distances The array of current shortest distances from the source vertex.
     * @param isVertexProcessed The array indicating whether each vertex has been processed.
     * @return The index of the vertex with the minimum distance value.
     */
    private int getMinDistanceVertex(int[] distances, boolean[] isVertexProcessed) {
        int minimumDistance = Integer.MAX_VALUE;
        int minimumDistanceVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            boolean isUnprocessed = !isVertexProcessed[vertexIndex];
            boolean isDistanceSmallerOrEqual = distances[vertexIndex] <= minimumDistance;

            if (isUnprocessed && isDistanceSmallerOrEqual) {
                minimumDistance = distances[vertexIndex];
                minimumDistanceVertexIndex = vertexIndex;
            }
        }

        return minimumDistanceVertexIndex;
    }

    /**
     * Prints the shortest distances from the source vertex to all other vertices.
     *
     * @param distances The array of shortest distances.
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + distances[vertexIndex]);
        }
    }
}