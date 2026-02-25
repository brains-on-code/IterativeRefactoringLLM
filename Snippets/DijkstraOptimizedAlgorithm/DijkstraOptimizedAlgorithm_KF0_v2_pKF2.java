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
     * Creates a new instance for a graph with the given number of vertices.
     *
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
        Set<Pair<Integer, Integer>> unprocessedVertices = new TreeSet<>();

        initializeDistances(distances, processed, source, unprocessedVertices);

        while (!unprocessedVertices.isEmpty()) {
            int currentVertex = extractMinVertex(unprocessedVertices);
            processed[currentVertex] = true;
            relaxNeighbors(graph, currentVertex, distances, processed, unprocessedVertices);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }
    }

    private void initializeDistances(
        int[] distances,
        boolean[] processed,
        int source,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);
        distances[source] = 0;
        unprocessedVertices.add(Pair.of(0, source));
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> unprocessedVertices) {
        Pair<Integer, Integer> distanceAndVertex = unprocessedVertices.iterator().next();
        unprocessedVertices.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxNeighbors(
        int[][] graph,
        int currentVertex,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (isRelaxationCandidate(graph, currentVertex, neighbor, distances, processed)) {
                updateDistance(neighbor, distances, graph[currentVertex][neighbor], currentVertex, unprocessedVertices);
            }
        }
    }

    private boolean isRelaxationCandidate(
        int[][] graph,
        int currentVertex,
        int neighbor,
        int[] distances,
        boolean[] processed
    ) {
        return !processed[neighbor]
            && graph[currentVertex][neighbor] != 0
            && distances[currentVertex] != Integer.MAX_VALUE
            && distances[currentVertex] + graph[currentVertex][neighbor] < distances[neighbor];
    }

    private void updateDistance(
        int vertex,
        int[] distances,
        int edgeWeight,
        int fromVertex,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        unprocessedVertices.remove(Pair.of(distances[vertex], vertex));
        distances[vertex] = distances[fromVertex] + edgeWeight;
        unprocessedVertices.add(Pair.of(distances[vertex], vertex));
    }
}