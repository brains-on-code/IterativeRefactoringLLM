package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Implementation of Dijkstra's shortest path algorithm using an adjacency matrix.
 */
public class DijkstraShortestPath {

    private final int vertexCount;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount the number of vertices in the graph
     */
    public DijkstraShortestPath(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Runs Dijkstra's algorithm on the given adjacency matrix from the specified source vertex.
     *
     * @param adjacencyMatrix the adjacency matrix representing the graph
     * @param sourceVertex    the source vertex index
     * @return an array of shortest distances from the source to each vertex
     */
    public int[] dijkstra(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
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
                boolean hasEdge = adjacencyMatrix[currentVertex][neighbor] != 0;
                boolean isNeighborUnvisited = !visited[neighbor];
                boolean isCurrentDistanceFinite = distances[currentVertex] != Integer.MAX_VALUE;

                if (isNeighborUnvisited
                        && hasEdge
                        && isCurrentDistanceFinite
                        && distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor]
                                < distances[neighbor]) {
                    distances[neighbor] =
                            distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor];
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
    private int getClosestUnvisitedVertex(int[] distances, boolean[] visited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestVertexIndex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minimumDistance) {
                minimumDistance = distances[vertex];
                closestVertexIndex = vertex;
            }
        }

        return closestVertexIndex;
    }

    /**
     * Prints the distances from the source to each vertex.
     *
     * @param distances array of shortest distances from the source
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}