package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's algorithm for single-source shortest paths on a weighted graph
 * represented by an adjacency matrix.
 *
 * <p>The graph is represented as an adjacency matrix where
 * {@code graph[i][j]} is the weight of the edge from {@code i} to {@code j},
 * and {@code 0} means no edge.</p>
 */
public class DijkstraAlgorithm {

    private final int vertexCount;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Computes the shortest path distances from a given source vertex using
     * Dijkstra's algorithm.
     *
     * @param graph  adjacency-matrix representation of the graph
     * @param source source vertex index
     * @return array of shortest distances from {@code source} to every vertex
     * @throws IllegalArgumentException if {@code source} is out of range
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = initDistances(source);
        boolean[] processed = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int closestVertex = getClosestUnprocessedVertex(distances, processed);
            processed[closestVertex] = true;
            relaxNeighbors(graph, distances, processed, closestVertex);
        }

        printDistances(distances);
        return distances;
    }

    /**
     * Initializes the distance array with "infinity" for all vertices except
     * the source, which is set to 0.
     */
    private int[] initDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    /**
     * Ensures the source vertex index is within the valid range.
     */
    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    /**
     * Finds the unprocessed vertex with the smallest tentative distance.
     *
     * @param distances current shortest known distances from the source
     * @param processed flags indicating whether a vertex has been finalized
     * @return index of the closest unprocessed vertex, or -1 if none exists
     */
    private int getClosestUnprocessedVertex(int[] distances, boolean[] processed) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            boolean isUnprocessed = !processed[vertex];
            boolean isCloser = distances[vertex] <= minDistance;
            if (isUnprocessed && isCloser) {
                minDistance = distances[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    /**
     * Attempts to improve (relax) the distances to all neighbors of the given vertex.
     */
    private void relaxNeighbors(int[][] graph, int[] distances, boolean[] processed, int u) {
        for (int v = 0; v < vertexCount; v++) {
            if (canRelaxEdge(graph, distances, processed, u, v)) {
                int newDistance = distances[u] + graph[u][v];
                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                }
            }
        }
    }

    /**
     * Checks whether the edge (u, v) can be used to relax the distance to v.
     */
    private boolean canRelaxEdge(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        int u,
        int v
    ) {
        boolean hasEdge = graph[u][v] != 0;
        boolean targetUnprocessed = !processed[v];
        boolean sourceReachable = distances[u] != Integer.MAX_VALUE;
        return targetUnprocessed && hasEdge && sourceReachable;
    }

    /**
     * Prints the computed shortest distances from the source to each vertex.
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex\tDistance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + "\t" + distances[vertex]);
        }
    }
}