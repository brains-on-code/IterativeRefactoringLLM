package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex to all other vertices in a graph.
 */
public class DijkstraAlgorithm {

    private final int numberOfVertices;

    /**
     * Constructs a Dijkstra object with the given number of vertices.
     *
     * @param numberOfVertices The number of vertices in the graph.
     */
    public DijkstraAlgorithm(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    /**
     * Executes Dijkstra's algorithm on the provided graph to find the shortest paths from the source vertex to all other vertices.
     *
     * The graph is represented as an adjacency matrix where {@code adjacencyMatrix[i][j]} represents the weight of the edge from vertex {@code i}
     * to vertex {@code j}. A value of 0 indicates no edge exists between the vertices.
     *
     * @param adjacencyMatrix The graph represented as an adjacency matrix.
     * @param sourceVertex The source vertex.
     * @return An array where the value at each index {@code i} represents the shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= numberOfVertices) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }

        int[] shortestDistances = new int[numberOfVertices];
        boolean[] isVertexVisited = new boolean[numberOfVertices];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVertexVisited, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int currentVertex = getClosestUnvisitedVertex(shortestDistances, isVertexVisited);
            isVertexVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                boolean isNeighborUnvisited = !isVertexVisited[neighborVertex];
                boolean hasEdgeToNeighbor = adjacencyMatrix[currentVertex][neighborVertex] != 0;
                boolean isCurrentDistanceFinite = shortestDistances[currentVertex] != Integer.MAX_VALUE;

                if (isNeighborUnvisited && hasEdgeToNeighbor && isCurrentDistanceFinite) {
                    int newDistanceToNeighbor =
                        shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                    if (newDistanceToNeighbor < shortestDistances[neighborVertex]) {
                        shortestDistances[neighborVertex] = newDistanceToNeighbor;
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
     * @param shortestDistances The array of current shortest distances from the source vertex.
     * @param isVertexVisited The array indicating whether each vertex has been processed.
     * @return The index of the vertex with the minimum distance value.
     */
    private int getClosestUnvisitedVertex(int[] shortestDistances, boolean[] isVertexVisited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestUnvisitedVertex = -1;

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            boolean isUnvisited = !isVertexVisited[vertexIndex];
            boolean isCloserOrEqual = shortestDistances[vertexIndex] <= minimumDistance;

            if (isUnvisited && isCloserOrEqual) {
                minimumDistance = shortestDistances[vertexIndex];
                closestUnvisitedVertex = vertexIndex;
            }
        }

        return closestUnvisitedVertex;
    }

    /**
     * Prints the shortest distances from the source vertex to all other vertices.
     *
     * @param shortestDistances The array of shortest distances.
     */
    private void printDistances(int[] shortestDistances) {
        System.out.println("Vertex \t Distance");
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + shortestDistances[vertexIndex]);
        }
    }
}