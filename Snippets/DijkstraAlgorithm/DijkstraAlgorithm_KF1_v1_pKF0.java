package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Implementation of Dijkstra's shortest path algorithm for a graph represented
 * by an adjacency matrix.
 */
public class Class1 {

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
     * @param adjacencyMatrix adjacency matrix representing the graph
     * @param source          index of the source vertex
     * @return array of shortest distances from the source to each vertex
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
            int nearestVertex = method2(distances, visited);
            visited[nearestVertex] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v]
                        && adjacencyMatrix[nearestVertex][v] != 0
                        && distances[nearestVertex] != Integer.MAX_VALUE
                        && distances[nearestVertex] + adjacencyMatrix[nearestVertex][v] < distances[v]) {
                    distances[v] = distances[nearestVertex] + adjacencyMatrix[nearestVertex][v];
                }
            }
        }

        method3(distances);
        return distances;
    }

    /**
     * Finds the index of the unvisited vertex with the smallest distance.
     *
     * @param distances array of current shortest distances
     * @param visited   array indicating whether a vertex has been visited
     * @return index of the nearest unvisited vertex
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
     * Prints the shortest distances from the source to each vertex.
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