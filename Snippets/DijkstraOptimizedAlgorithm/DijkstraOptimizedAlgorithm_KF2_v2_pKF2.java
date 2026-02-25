package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = initDistances(source);
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessedVertices = new TreeSet<>();

        unprocessedVertices.add(Pair.of(0, source));

        while (!unprocessedVertices.isEmpty()) {
            int currentVertex = extractMinVertex(unprocessedVertices);
            processed[currentVertex] = true;
            relaxNeighbors(graph, currentVertex, distances, processed, unprocessedVertices);
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
            if (canRelaxEdge(graph, currentVertex, neighbor, distances, processed)) {
                updateDistance(currentVertex, neighbor, graph, distances, unprocessedVertices);
            }
        }
    }

    private boolean canRelaxEdge(
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
        int currentVertex,
        int neighbor,
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        unprocessedVertices.remove(Pair.of(distances[neighbor], neighbor));
        distances[neighbor] = distances[currentVertex] + graph[currentVertex][neighbor];
        unprocessedVertices.add(Pair.of(distances[neighbor], neighbor));
    }
}