package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's shortest path algorithm for a graph represented by an adjacency matrix.
 */
public class DijkstraAdjacencyMatrix {

    private final int vertexCount;

    public DijkstraAdjacencyMatrix(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Computes shortest path distances from a source vertex using Dijkstra's algorithm.
     *
     * @param adjacencyMatrix adjacency matrix of the graph; 0 means no edge
     * @param source          source vertex index (0-based)
     * @return array of shortest distances from the source to every vertex
     * @throws IllegalArgumentException if the source index is out of range
     */
    public int[] dijkstra(int[][] adjacencyMatrix, int source) {
        validateSource(source);

        int[] distances = initDistances(source);
        boolean[] visited = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = getClosestUnvisitedVertex(distances, visited);
            if (u == -1) {
                break;
            }
            visited[u] = true;
            relaxNeighbors(adjacencyMatrix, distances, visited, u);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex index out of range: " + source);
        }
    }

    private int[] initDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private void relaxNeighbors(int[][] adjacencyMatrix, int[] distances, boolean[] visited, int u) {
        for (int v = 0; v < vertexCount; v++) {
            int weight = adjacencyMatrix[u][v];
            if (weight == 0 || visited[v]) {
                continue;
            }
            if (distances[u] == Integer.MAX_VALUE) {
                continue;
            }
            int newDistance = distances[u] + weight;
            if (newDistance < distances[v]) {
                distances[v] = newDistance;
            }
        }
    }

    /**
     * Returns the index of the unvisited vertex with the smallest tentative distance.
     *
     * @param distances current shortest known distances
     * @param visited   visited flags for vertices
     * @return index of the vertex with the minimum distance, or -1 if none is found
     */
    private int getClosestUnvisitedVertex(int[] distances, boolean[] visited) {
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

    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int v = 0; v < vertexCount; v++) {
            System.out.println(v + " \t " + distances[v]);
        }
    }
}