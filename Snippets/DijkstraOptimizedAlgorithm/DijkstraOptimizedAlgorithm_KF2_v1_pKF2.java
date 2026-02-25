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
        Set<Pair<Integer, Integer>> unprocessed = new TreeSet<>();

        unprocessed.add(Pair.of(0, source));

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

    private void relaxNeighbors(
        int[][] graph,
        int u,
        int[] distances,
        boolean[] processed,
        Set<Pair<Integer, Integer>> unprocessed
    ) {
        for (int v = 0; v < vertexCount; v++) {
            if (canRelaxEdge(graph, u, v, distances, processed)) {
                updateDistance(u, v, graph, distances, unprocessed);
            }
        }
    }

    private boolean canRelaxEdge(
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