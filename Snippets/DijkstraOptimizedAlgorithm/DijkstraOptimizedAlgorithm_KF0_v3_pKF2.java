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
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        initialize(distances, processed, source, priorityQueue);

        while (!priorityQueue.isEmpty()) {
            int currentVertex = extractMinVertex(priorityQueue);
            processed[currentVertex] = true;
            relaxNeighbors(graph, currentVertex, distances, processed, priorityQueue);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }
    }

    private void initialize(
        int[] distances,
        boolean[] processed,
        int source,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);
        distances[source] = 0;
        priorityQueue.add(Pair.of(0, source));
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> priorityQueue) {
        Pair<Integer, Integer> distanceAndVertex = priorityQueue.iterator().next();
        priorityQueue.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxNeighbors(
        int[][] graph,
        int currentVertex,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (canRelax(graph, currentVertex, neighbor, distances, processed)) {
                relaxEdge(currentVertex, neighbor, graph[currentVertex][neighbor], distances, priorityQueue);
            }
        }
    }

    private boolean canRelax(
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

    private void relaxEdge(
        int fromVertex,
        int toVertex,
        int edgeWeight,
        int[] distances,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        priorityQueue.remove(Pair.of(distances[toVertex], toVertex));
        distances[toVertex] = distances[fromVertex] + edgeWeight;
        priorityQueue.add(Pair.of(distances[toVertex], toVertex));
    }
}