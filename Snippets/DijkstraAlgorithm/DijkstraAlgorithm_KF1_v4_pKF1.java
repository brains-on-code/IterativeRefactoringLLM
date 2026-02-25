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

        int[] shortestDistances = new int[numberOfVertices];
        boolean[] visitedVertices = new boolean[numberOfVertices];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(visitedVertices, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int currentVertex = getClosestUnvisitedVertex(shortestDistances, visitedVertices);
            visitedVertices[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                boolean hasEdge = adjacencyMatrix[currentVertex][neighborVertex] != 0;
                boolean neighborUnvisited = !visitedVertices[neighborVertex];
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
     * @param distances      array of current shortest distances from the source
     * @param visitedVertices array indicating whether a vertex has been visited
     * @return index of the vertex with the minimum distance
     */
    private int getClosestUnvisitedVertex(int[] distances, boolean[] visitedVertices) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            if (!visitedVertices[vertexIndex] && distances[vertexIndex] <= minimumDistance) {
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
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + distances[vertexIndex]);
        }
    }
}