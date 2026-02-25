package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Implementation of Dijkstra's shortest path algorithm for a graph represented
 * by an adjacency matrix.
 */
public class DijkstraAdjacencyMatrix {

    private final int vertexCount;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public DijkstraAdjacencyMatrix(int vertexCount) {
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
    public int[] shortestPaths(int[][] adjacencyMatrix, int source) {
        validateSource(source);

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int nearestVertex = findNearestUnvisitedVertex(distances, visited);
            if (nearestVertex == -1) {
                break; // Remaining vertices are unreachable
            }
            visited[nearestVertex] = true;

            relaxEdgesFromVertex(adjacencyMatrix, distances, visited, nearestVertex);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex index out of bounds: " + source);
        }
    }

    /**
     * Finds the index of the unvisited vertex with the smallest distance.
     *
     * @param distances array of current shortest distances
     * @param visited   array indicating whether a vertex has been visited
     * @return index of the nearest unvisited vertex, or -1 if none remain
     */
    private int findNearestUnvisitedVertex(int[] distances, boolean[] visited) {
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

    private void relaxEdgesFromVertex(
            int[][] adjacencyMatrix,
            int[] distances,
            boolean[] visited,
            int vertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            int edgeWeight = adjacencyMatrix[vertex][neighbor];

            boolean hasEdge = edgeWeight != 0;
            boolean notVisited = !visited[neighbor];
            boolean vertexReachable = distances[vertex] != Integer.MAX_VALUE;

            if (notVisited && hasEdge && vertexReachable) {
                int newDistance = distances[vertex] + edgeWeight;
                if (newDistance < distances[neighbor]) {
                    distances[neighbor] = newDistance;
                }
            }
        }
    }

    /**
     * Prints the shortest distances from the source to each vertex.
     *
     * @param distances array of shortest distances
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}