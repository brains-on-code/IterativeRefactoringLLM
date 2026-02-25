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

        int[] shortestDistances = new int[vertexCount];
        boolean[] isVisited = new boolean[vertexCount];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVisited, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int currentVertex = getClosestUnvisitedVertex(shortestDistances, isVisited);
            isVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                boolean hasEdge = adjacencyMatrix[currentVertex][neighborVertex] != 0;
                boolean neighborUnvisited = !isVisited[neighborVertex];
                boolean currentDistanceKnown = shortestDistances[currentVertex] != Integer.MAX_VALUE;

                if (neighborUnvisited
                        && hasEdge
                        && currentDistanceKnown
                        && shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex]
                                < shortestDistances[neighborVertex]) {
                    shortestDistances[neighborVertex] =
                            shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                }
            }
        }

        printDistances(shortestDistances);
        return shortestDistances;
    }

    /**
     * Returns the index of the unvisited vertex with the minimum distance.
     *
     * @param distances array of current shortest distances from the source
     * @param isVisited array indicating whether a vertex has been visited
     * @return index of the vertex with the minimum distance
     */
    private int getClosestUnvisitedVertex(int[] distances, boolean[] isVisited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            if (!isVisited[vertexIndex] && distances[vertexIndex] <= minimumDistance) {
                minimumDistance = distances[vertexIndex];
                closestVertexIndex = vertexIndex;
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
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + distances[vertexIndex]);
        }
    }
}