package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex
 * to all other vertices in a graph.
 */
public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    /**
     * Constructs a Dijkstra object with the given number of vertices.
     *
     * @param vertexCount The number of vertices in the graph.
     */
    public DijkstraOptimizedAlgorithm(int vertexCount) {
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
     * @param graph  The graph represented as an adjacency matrix.
     * @param source The source vertex.
     * @return An array where the value at each index {@code i} represents the
     *         shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = initDistances(source);
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessed = new TreeSet<>();

        Arrays.fill(processed, false);
        unprocessed.add(Pair.of(0, source));

        while (!unprocessed.isEmpty()) {
            int currentVertex = extractMinVertex(unprocessed);
            processed[currentVertex] = true;
            relaxAdjacentVertices(graph, currentVertex, distances, processed, unprocessed);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }
    }

    private int[] initDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> unprocessed) {
        Pair<Integer, Integer> distanceAndVertex = unprocessed.iterator().next();
        unprocessed.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxAdjacentVertices(
        int[][] graph,
        int u,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        for (int v = 0; v < vertexCount; v++) {
            if (shouldRelaxEdge(graph, u, v, distances, processed)) {
                updateDistance(u, v, graph, distances, unprocessed);
            }
        }
    }

    private boolean shouldRelaxEdge(
        int[][] graph,
        int u,
        int v,
        int[] distances,
        boolean[] processed
    ) {
        return !processed[v]
            && graph[u][v] != 0
            && distances[u] != Integer.MAX_VALUE
            && distances[u] + graph[u][v] < distances[v];
    }

    private void updateDistance(
        int u,
        int v,
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        unprocessed.remove(Pair.of(distances[v], v));
        distances[v] = distances[u] + graph[u][v];
        unprocessed.add(Pair.of(distances[v], v));
    }
}