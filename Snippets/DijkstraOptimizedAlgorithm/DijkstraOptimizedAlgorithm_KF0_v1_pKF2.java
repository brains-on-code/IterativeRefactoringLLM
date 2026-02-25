package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source
 * vertex to all other vertices in a graph.
 */
public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    /**
     * @param vertexCount number of vertices in the graph
     */
    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Runs Dijkstra's algorithm on a graph represented as an adjacency matrix.
     *
     * <p>{@code graph[i][j]} is the weight of the edge from vertex {@code i} to
     * vertex {@code j}. A value of {@code 0} means there is no edge.</p>
     *
     * @param graph  adjacency-matrix representation of the graph
     * @param source source vertex
     * @return array of shortest distances from {@code source} to every vertex
     * @throws IllegalArgumentException if {@code source} is out of range
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = new int[vertexCount];
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessed = new TreeSet<>();

        initializeDistances(distances, processed, source, unprocessed);

        while (!unprocessed.isEmpty()) {
            int u = extractMinVertex(unprocessed);
            processed[u] = true;
            relaxNeighbors(graph, u, distances, processed, unprocessed);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }
    }

    private void initializeDistances(
        int[] distances,
        boolean[] processed,
        int source,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);
        distances[source] = 0;
        unprocessed.add(Pair.of(0, source));
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> unprocessed) {
        Pair<Integer, Integer> distanceAndVertex = unprocessed.iterator().next();
        unprocessed.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxNeighbors(
        int[][] graph,
        int u,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        for (int v = 0; v < vertexCount; v++) {
            if (isRelaxationCandidate(graph, u, v, distances, processed)) {
                updateDistance(v, distances, graph[u][v], u, unprocessed);
            }
        }
    }

    private boolean isRelaxationCandidate(
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
        int vertex,
        int[] distances,
        int edgeWeight,
        int fromVertex,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        unprocessed.remove(Pair.of(distances[vertex], vertex));
        distances[vertex] = distances[fromVertex] + edgeWeight;
        unprocessed.add(Pair.of(distances[vertex], vertex));
    }
}