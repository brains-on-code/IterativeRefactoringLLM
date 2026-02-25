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
            int closestVertex = getClosestUnvisitedVertex(distances, visited);
            if (closestVertex == -1) {
                break;
            }
            visited[closestVertex] = true;
            relaxNeighbors(adjacencyMatrix, distances, visited, closestVertex);
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

    private void relaxNeighbors(int[][] adjacencyMatrix, int[] distances, boolean[] visited, int currentVertex) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            int weight = adjacencyMatrix[currentVertex][neighbor];

            if (weight == 0 || visited[neighbor]) {
                continue;
            }

            if (distances[currentVertex] == Integer.MAX_VALUE) {
                continue;
            }

            int newDistance = distances[currentVertex] + weight;
            if (newDistance < distances[neighbor]) {
                distances[neighbor] = newDistance;
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

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minDistance) {
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