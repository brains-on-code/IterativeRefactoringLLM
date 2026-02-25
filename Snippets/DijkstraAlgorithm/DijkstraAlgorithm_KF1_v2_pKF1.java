package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Implementation of Dijkstra's shortest path algorithm using an adjacency matrix.
 */
public class DijkstraShortestPath {

    private final int numberOfVertices;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param numberOfVertices the number of vertices in the graph
     */
    public DijkstraShortestPath(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    /**
     * Runs Dijkstra's algorithm on the given adjacency matrix from the specified source vertex.
     *
     * @param adjacencyMatrix the adjacency matrix representing the graph
     * @param sourceVertex    the source vertex index
     * @return an array of shortest distances from the source to each vertex
     */
    public int[] dijkstra(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= numberOfVertices) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[numberOfVertices];
        boolean[] visited = new boolean[numberOfVertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[sourceVertex] = 0;

        for (int i = 0; i < numberOfVertices - 1; i++) {
            int currentVertex = getMinDistanceVertex(distances, visited);
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                if (!visited[neighborVertex]
                        && adjacencyMatrix[currentVertex][neighborVertex] != 0
                        && distances[currentVertex] != Integer.MAX_VALUE
                        && distances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex] < distances[neighborVertex]) {
                    distances[neighborVertex] =
                            distances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                }
            }
        }

        printDistances(distances);
        return distances;
    }

    /**
     * Returns the index of the unvisited vertex with the minimum distance.
     *
     * @param distances array of current shortest distances from the source
     * @param visited   array indicating whether a vertex has been visited
     * @return index of the vertex with the minimum distance
     */
    private int getMinDistanceVertex(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minVertexIndex = -1;

        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minDistance) {
                minDistance = distances[vertex];
                minVertexIndex = vertex;
            }
        }

        return minVertexIndex;
    }

    /**
     * Prints the distances from the source to each vertex.
     *
     * @param distances array of shortest distances from the source
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}