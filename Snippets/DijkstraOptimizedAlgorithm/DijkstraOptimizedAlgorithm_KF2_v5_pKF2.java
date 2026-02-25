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

        int[] distances = initializeDistances(source);
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        priorityQueue.add(Pair.of(0, source));

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

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
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
            if (shouldRelaxEdge(graph, currentVertex, neighbor, distances, processed)) {
                relaxEdge(currentVertex, neighbor, graph, distances, priorityQueue);
            }
        }
    }

    private boolean shouldRelaxEdge(
        int[][] graph,
        int currentVertex,
        int neighbor,
        int[] distances,
        boolean[] processed
    ) {
        boolean isUnprocessedNeighbor = !processed[neighbor];
        boolean hasEdge = graph[currentVertex][neighbor] != 0;
        boolean hasFiniteDistance = distances[currentVertex] != Integer.MAX_VALUE;
        boolean offersShorterPath =
            distances[currentVertex] + graph[currentVertex][neighbor] < distances[neighbor];

        return isUnprocessedNeighbor && hasEdge && hasFiniteDistance && offersShorterPath;
    }

    private void relaxEdge(
        int currentVertex,
        int neighbor,
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        priorityQueue.remove(Pair.of(distances[neighbor], neighbor));
        distances[neighbor] = distances[currentVertex] + graph[currentVertex][neighbor];
        priorityQueue.add(Pair.of(distances[neighbor], neighbor));
    }
}