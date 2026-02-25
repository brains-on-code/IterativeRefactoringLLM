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
     * The graph is represented as an adjacency matrix where {@code adjacencyMatrix[i][j]} represents the weight of the edge from vertex {@code i}
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

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[sourceVertex] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = getClosestUnvisitedVertex(distances, visited);
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                boolean isNeighborUnvisited = !visited[neighbor];
                boolean hasEdgeToNeighbor = adjacencyMatrix[currentVertex][neighbor] != 0;
                boolean isCurrentDistanceFinite = distances[currentVertex] != Integer.MAX_VALUE;

                if (isNeighborUnvisited && hasEdgeToNeighbor && isCurrentDistanceFinite) {
                    int newDistanceToNeighbor =
                        distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor];
                    if (newDistanceToNeighbor < distances[neighbor]) {
                        distances[neighbor] = newDistanceToNeighbor;
                    }
                }
            }
        }

        printDistances(distances);
        return distances;
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices that have not yet been processed.
     *
     * @param distances The array of current shortest distances from the source vertex.
     * @param visited The array indicating whether each vertex has been processed.
     * @return The index of the vertex with the minimum distance value.
     */
    private int getClosestUnvisitedVertex(int[] distances, boolean[] visited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestUnvisitedVertex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            boolean isUnvisited = !visited[vertex];
            boolean isCloserOrEqual = distances[vertex] <= minimumDistance;

            if (isUnvisited && isCloserOrEqual) {
                minimumDistance = distances[vertex];
                closestUnvisitedVertex = vertex;
            }
        }

        return closestUnvisitedVertex;
    }

    /**
     * Prints the shortest distances from the source vertex to all other vertices.
     *
     * @param distances The array of shortest distances.
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}