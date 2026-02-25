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
        Set<Pair<Integer, Integer>> unprocessedVertices = new TreeSet<>();

        initializeDistances(distances, processed, source, unprocessedVertices);

        while (!unprocessedVertices.isEmpty()) {
            Pair<Integer, Integer> distanceAndVertex = unprocessedVertices.iterator().next();
            unprocessedVertices.remove(distanceAndVertex);

            int currentVertex = distanceAndVertex.getRight();
            processed[currentVertex] = true;

            relaxAdjacentVertices(graph, distances, processed, unprocessedVertices, currentVertex);
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
        Set<Pair<Integer, Integer>> unprocessedVertices
    ) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);

        distances[source] = 0;
        unprocessedVertices.add(Pair.of(0, source));
    }

    private void relaxAdjacentVertices(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessedVertices,
        int currentVertex
    ) {
        for (int adjacentVertex = 0; adjacentVertex < vertexCount; adjacentVertex++) {
            if (canRelaxEdge(graph, distances, processed, currentVertex, adjacentVertex)) {
                relaxEdge(graph, distances, unprocessedVertices, currentVertex, adjacentVertex);
            }
        }
    }

    private boolean canRelaxEdge(
        int[][] graph,
        int[] distances,
        boolean[] processed,
        int currentVertex,
        int adjacentVertex
    ) {
        if (processed[adjacentVertex]) {
            return false;
        }

        int edgeWeight = graph[currentVertex][adjacentVertex];
        if (edgeWeight == 0) {
            return false;
        }

        int currentDistance = distances[currentVertex];
        if (currentDistance == Integer.MAX_VALUE) {
            return false;
        }

        int newDistance = currentDistance + edgeWeight;
        return newDistance < distances[adjacentVertex];
    }

    private void relaxEdge(
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> unprocessedVertices,
        int currentVertex,
        int adjacentVertex
    ) {
        int oldDistance = distances[adjacentVertex];
        if (oldDistance != Integer.MAX_VALUE) {
            unprocessedVertices.remove(Pair.of(oldDistance, adjacentVertex));
        }

        int newDistance = distances[currentVertex] + graph[currentVertex][adjacentVertex];
        distances[adjacentVertex] = newDistance;
        unprocessedVertices.add(Pair.of(newDistance, adjacentVertex));
    }
}