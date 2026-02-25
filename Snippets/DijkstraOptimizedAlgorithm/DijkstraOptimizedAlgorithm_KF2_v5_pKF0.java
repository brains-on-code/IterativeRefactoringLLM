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
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        initialize(distances, visited, source, priorityQueue);

        while (!priorityQueue.isEmpty()) {
            Pair<Integer, Integer> distanceAndVertex = priorityQueue.iterator().next();
            priorityQueue.remove(distanceAndVertex);

            int currentVertex = distanceAndVertex.getRight();
            visited[currentVertex] = true;

            relaxNeighbors(graph, distances, visited, priorityQueue, currentVertex);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    private void initialize(
        int[] distances,
        boolean[] visited,
        int source,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        distances[source] = 0;
        priorityQueue.add(Pair.of(0, source));
    }

    private void relaxNeighbors(
        int[][] graph,
        int[] distances,
        boolean[] visited,
        Set<Pair<Integer, Integer>> priorityQueue,
        int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (canRelax(graph, distances, visited, currentVertex, neighbor)) {
                relaxEdge(graph, distances, priorityQueue, currentVertex, neighbor);
            }
        }
    }

    private boolean canRelax(
        int[][] graph,
        int[] distances,
        boolean[] visited,
        int currentVertex,
        int neighbor
    ) {
        if (visited[neighbor]) {
            return false;
        }

        int edgeWeight = graph[currentVertex][neighbor];
        if (edgeWeight == 0) {
            return false;
        }

        int currentDistance = distances[currentVertex];
        if (currentDistance == Integer.MAX_VALUE) {
            return false;
        }

        int newDistance = currentDistance + edgeWeight;
        return newDistance < distances[neighbor];
    }

    private void relaxEdge(
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> priorityQueue,
        int currentVertex,
        int neighbor
    ) {
        int oldDistance = distances[neighbor];
        if (oldDistance != Integer.MAX_VALUE) {
            priorityQueue.remove(Pair.of(oldDistance, neighbor));
        }

        int newDistance = distances[currentVertex] + graph[currentVertex][neighbor];
        distances[neighbor] = newDistance;
        priorityQueue.add(Pair.of(newDistance, neighbor));
    }
}