package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm implementation for a graph represented by an adjacency matrix.
 */
public class Class1 {

    private final int vertexCount;

    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Computes the shortest path distances from a given source vertex to all other vertices
     * using Dijkstra's algorithm.
     *
     * @param adjacencyMatrix adjacencyMatrix[u][v] is the weight of the edge u→v, or 0 if no edge
     * @param source          source vertex index (0-based)
     * @return array of shortest distances from source to every vertex
     * @throws IllegalArgumentException if source index is out of range
     */
    public int[] method1(int[][] adjacencyMatrix, int source) {
        validateSourceVertex(source);

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> prioritySet = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);

        distances[source] = 0;
        prioritySet.add(Pair.of(0, source));

        while (!prioritySet.isEmpty()) {
            Pair<Integer, Integer> currentPair = prioritySet.iterator().next();
            prioritySet.remove(currentPair);

            int currentVertex = currentPair.getRight();
            visited[currentVertex] = true;

            relaxNeighbors(adjacencyMatrix, distances, visited, prioritySet, currentVertex);
        }

        return distances;
    }

    private void validateSourceVertex(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex index out of range: " + source);
        }
    }

    private void relaxNeighbors(
            int[][] adjacencyMatrix,
            int[] distances,
            boolean[] visited,
            Set<Pair<Integer, Integer>> prioritySet,
            int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (shouldRelaxEdge(adjacencyMatrix, distances, visited, currentVertex, neighbor)) {
                updateDistanceAndQueue(adjacencyMatrix, distances, prioritySet, currentVertex, neighbor);
            }
        }
    }

    private boolean shouldRelaxEdge(
            int[][] adjacencyMatrix,
            int[] distances,
            boolean[] visited,
            int currentVertex,
            int neighbor
    ) {
        if (visited[neighbor]) {
            return false;
        }

        int edgeWeight = adjacencyMatrix[currentVertex][neighbor];
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

    private void updateDistanceAndQueue(
            int[][] adjacencyMatrix,
            int[] distances,
            Set<Pair<Integer, Integer>> prioritySet,
            int currentVertex,
            int neighbor
    ) {
        int oldDistance = distances[neighbor];
        if (oldDistance != Integer.MAX_VALUE) {
            prioritySet.remove(Pair.of(oldDistance, neighbor));
        }

        int newDistance = distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor];
        distances[neighbor] = newDistance;
        prioritySet.add(Pair.of(newDistance, neighbor));
    }
}