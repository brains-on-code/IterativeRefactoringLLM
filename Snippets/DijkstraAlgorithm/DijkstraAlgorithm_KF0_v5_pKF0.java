package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex
 * to all other vertices in a graph.
 */
public class DijkstraAlgorithm {

    private final int vertexCount;

    /**
     * Constructs a DijkstraAlgorithm with the given number of vertices.
     *
     * @param vertexCount the number of vertices in the graph
     */
    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Executes Dijkstra's algorithm on the provided graph to find the shortest
     * paths from the source vertex to all other vertices.
     *
     * The graph is represented as an adjacency matrix where {@code graph[i][j]}
     * represents the weight of the edge from vertex {@code i} to vertex {@code j}.
     * A value of 0 indicates no edge exists between the vertices.
     *
     * @param graph  the graph represented as an adjacency matrix
     * @param source the source vertex
     * @return an array where the value at each index {@code i} represents the
     *         shortest distance from the source vertex to vertex {@code i}
     * @throws IllegalArgumentException if the source vertex is out of range
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);
        validateGraph(graph);

        int[] distances = initializeDistances(source);
        boolean[] processed = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = findMinDistanceUnprocessedVertex(distances, processed);
            if (currentVertex == -1) {
                // Remaining vertices are unreachable
                break;
            }
            processed[currentVertex] = true;
            relaxNeighbors(graph, distances, processed, currentVertex);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    private void validateGraph(int[][] graph) {
        if (graph == null || graph.length != vertexCount) {
            throw new IllegalArgumentException(invalidGraphSizeMessage());
        }
        for (int[] row : graph) {
            if (row == null || row.length != vertexCount) {
                throw new IllegalArgumentException(invalidGraphSizeMessage());
            }
        }
    }

    private String invalidGraphSizeMessage() {
        return "Graph must be a non-null square matrix of size " + vertexCount;
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private void relaxNeighbors(int[][] graph, int[] distances, boolean[] processed, int u) {
        for (int v = 0; v < vertexCount; v++) {
            int edgeWeight = graph[u][v];
            if (canRelaxEdge(distances, processed, u, v, edgeWeight)) {
                distances[v] = distances[u] + edgeWeight;
            }
        }
    }

    private boolean canRelaxEdge(int[] distances, boolean[] processed, int u, int v, int edgeWeight) {
        return !processed[v]
            && edgeWeight != 0
            && distances[u] != Integer.MAX_VALUE
            && distances[u] + edgeWeight < distances[v];
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices
     * that have not yet been processed.
     *
     * @param distances the array of current shortest distances from the source vertex
     * @param processed the array indicating whether each vertex has been processed
     * @return the index of the vertex with the minimum distance value, or -1 if none
     */
    private int findMinDistanceUnprocessedVertex(int[] distances, boolean[] processed) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertexCount; v++) {
            if (!processed[v] && distances[v] < minDistance) {
                minDistance = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    /**
     * Prints the shortest distances from the source vertex to all other vertices.
     *
     * @param distances the array of shortest distances
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex\tDistance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + "\t" + distances[vertex]);
        }
    }
}