package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Implementation of Dijkstra's shortest path algorithm for a graph represented
 * by an adjacency matrix.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Runs Dijkstra's algorithm on the given adjacency matrix starting from the
     * specified source vertex.
     *
     * @param adjacencyMatrix adjacency matrix of the graph; a value of 0 means no edge
     * @param source          index of the source vertex (0-based)
     * @return array of shortest distances from the source to every vertex
     * @throws IllegalArgumentException if the source index is out of range
     */
    public int[] method1(int[][] adjacencyMatrix, int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = method2(distances, visited);
            visited[u] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v]
                        && adjacencyMatrix[u][v] != 0
                        && distances[u] != Integer.MAX_VALUE
                        && distances[u] + adjacencyMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + adjacencyMatrix[u][v];
                }
            }
        }

        method3(distances);
        return distances;
    }

    /**
     * Returns the index of the unvisited vertex with the smallest tentative
     * distance.
     *
     * @param distances array of current shortest known distances
     * @param visited   array indicating whether a vertex has been visited
     * @return index of the vertex with the minimum distance
     */
    private int method2(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertexCount; v++) {
            if (!visited[v] && distances[v] <= minDistance) {
                minDistance = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    /**
     * Prints the shortest distance from the source to each vertex.
     *
     * @param distances array of shortest distances
     */
    private void method3(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int v = 0; v < vertexCount; v++) {
            System.out.println(v + " \t " + distances[v]);
        }
    }
}