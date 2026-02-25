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
        Set<Pair<Integer, Integer>> unprocessedVertices = new TreeSet<>();

        unprocessedVertices.add(Pair.of(0, source));

        while (!unprocessedVertices.isEmpty()) {
            int currentVertex = extractMinVertex(unprocessedVertices);
            processed[currentVertex] = true;
            relaxAdjacentVertices(graph, currentVertex, distances, processed, unprocessedVertices);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range: " + source);
        }
    }

    private int[] initDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> unprocessedVertices) {
        Pair<Integer, Integer> distanceAndVertex = unprocessedVertices.iterator().next();
        unprocessedVertices.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxAdjacentVertices(
        int[][] graph,
        int currentVertex,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (shouldRelaxEdge(graph, currentVertex, neighbor, distances, processed)) {
                updateDistance(currentVertex, neighbor, graph, distances, unprocessedVertices);
            }
        }
    }

    private boolean shouldRelaxEdge(
        int[][] graph,
        int from,
        int to,
        int[] distances,
        boolean[] processed
    ) {
        int edgeWeight = graph[from][to];
        return !processed[to]
            && edgeWeight != 0
            && distances[from] != Integer.MAX_VALUE
            && distances[from] + edgeWeight < distances[to];
    }

    private void updateDistance(
        int from,
        int to,
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        int oldDistance = distances[to];
        if (oldDistance != Integer.MAX_VALUE) {
            unprocessedVertices.remove(Pair.of(oldDistance, to));
        }

        int newDistance = distances[from] + graph[from][to];
        distances[to] = newDistance;
        unprocessedVertices.add(Pair.of(newDistance, to));
    }
}