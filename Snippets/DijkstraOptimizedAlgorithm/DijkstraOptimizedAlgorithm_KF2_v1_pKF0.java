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

        int[] distances = new int[vertexCount];
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessed = new TreeSet<>();

        initializeDistances(distances, processed, source, unprocessed);

        while (!unprocessed.isEmpty()) {
            Pair<Integer, Integer> distanceAndVertex = unprocessed.iterator().next();
            unprocessed.remove(distanceAndVertex);

            int currentVertex = distanceAndVertex.getRight();
            processed[currentVertex] = true;

            updateAdjacentVertices(graph, distances, processed, unprocessed, currentVertex);
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

    private void updateAdjacentVertices(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessed,
        int currentVertex
    ) {
        for (int adjacentVertex = 0; adjacentVertex < vertexCount; adjacentVertex++) {
            if (shouldRelaxEdge(graph, distances, processed, currentVertex, adjacentVertex)) {
                relaxEdge(graph, distances, unprocessed, currentVertex, adjacentVertex);
            }
        }
    }

    private boolean shouldRelaxEdge(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        int currentVertex,
        int adjacentVertex
    ) {
        return !processed[adjacentVertex]
            && graph[currentVertex][adjacentVertex] != 0
            && distances[currentVertex] != Integer.MAX_VALUE
            && distances[currentVertex] + graph[currentVertex][adjacentVertex] < distances[adjacentVertex];
    }

    private void relaxEdge(
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> unprocessed,
        int currentVertex,
        int adjacentVertex
    ) {
        unprocessed.remove(Pair.of(distances[adjacentVertex], adjacentVertex));
        distances[adjacentVertex] = distances[currentVertex] + graph[currentVertex][adjacentVertex];
        unprocessed.add(Pair.of(distances[adjacentVertex], adjacentVertex));
    }
}