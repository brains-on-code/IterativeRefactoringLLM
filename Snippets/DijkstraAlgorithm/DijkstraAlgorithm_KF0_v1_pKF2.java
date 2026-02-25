package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Dijkstra's algorithm for single-source shortest paths on a weighted graph
 * represented by an adjacency matrix.
 */
public class DijkstraAlgorithm {

    private final int vertexCount;

    /**
     * @param vertexCount number of vertices in the graph
     */
    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Runs Dijkstra's algorithm from a given source vertex.
     *
     * <p>The graph is represented as an adjacency matrix where
     * {@code graph[i][j]} is the weight of the edge from {@code i} to {@code j},
     * and {@code 0} means no edge.</p>
     *
     * @param graph  adjacency-matrix representation of the graph
     * @param source source vertex index
     * @return array of shortest distances from {@code source} to every vertex
     * @throws IllegalArgumentException if {@code source} is out of range
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = new int[vertexCount];
        boolean[] processed = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = getMinDistanceVertex(distances, processed);
            processed[u] = true;

            relaxNeighbors(graph, distances, processed, u);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    /**
     * Returns the index of the unprocessed vertex with the smallest distance.
     */
    private int getMinDistanceVertex(int[] distances, boolean[] processed) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertexCount; v++) {
            if (!processed[v] && distances[v] <= minDistance) {
                minDistance = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    /**
     * Relaxes all neighbors of vertex {@code u}.
     */
    private void relaxNeighbors(int[][] graph, int[] distances, boolean[] processed, int u) {
        for (int v = 0; v < vertexCount; v++) {
            boolean hasEdge = graph[u][v] != 0;
            boolean notProcessed = !processed[v];
            boolean reachable = distances[u] != Integer.MAX_VALUE;

            if (notProcessed && hasEdge && reachable) {
                int newDistance = distances[u] + graph[u][v];
                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                }
            }
        }
    }

    /**
     * Prints the shortest distances from the source to all vertices.
     */
    private void printDistances(int[] distances) {
        System.out.println("Vertex\tDistance");
        for (int i = 0; i < vertexCount; i++) {
            System.out.println(i + "\t" + distances[i]);
        }
    }
}