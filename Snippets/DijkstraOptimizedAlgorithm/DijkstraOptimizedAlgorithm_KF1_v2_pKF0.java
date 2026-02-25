package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Represents a graph-related utility with a fixed number of vertices.
 */
public class Class1 {

    private final int vertexCount;

    /**
     * Creates an instance with the specified number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public Class1(int vertexCount) {
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("vertexCount must be positive");
        }
        this.vertexCount = vertexCount;
    }

    /**
     * Computes the shortest path distances from a given source vertex to all
     * other vertices in a weighted graph represented by an adjacency matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @param source          index of the source vertex
     * @return array of shortest distances from the source to each vertex
     */
    public int[] method1(int[][] adjacencyMatrix, int source) {
        validateSource(source);
        validateAdjacencyMatrix(adjacencyMatrix);

        int[] distances = initializeDistances(source);
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        priorityQueue.add(Pair.of(0, source));

        while (!priorityQueue.isEmpty()) {
            Pair<Integer, Integer> currentPair = priorityQueue.iterator().next();
            priorityQueue.remove(currentPair);

            int currentVertex = currentPair.getRight();
            if (visited[currentVertex]) {
                continue;
            }
            visited[currentVertex] = true;

            relaxNeighbors(adjacencyMatrix, distances, visited, priorityQueue, currentVertex);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    private void validateAdjacencyMatrix(int[][] adjacencyMatrix) {
        if (adjacencyMatrix == null || adjacencyMatrix.length != vertexCount) {
            throw new IllegalArgumentException("Adjacency matrix must be non-null and of size " + vertexCount);
        }
        for (int[] row : adjacencyMatrix) {
            if (row == null || row.length != vertexCount) {
                throw new IllegalArgumentException("Adjacency matrix must be square with size " + vertexCount);
            }
        }
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private void relaxNeighbors(
            int[][] adjacencyMatrix,
            int[] distances,
            boolean[] visited,
            Set<Pair<Integer, Integer>> priorityQueue,
            int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            int edgeWeight = adjacencyMatrix[currentVertex][neighbor];

            if (edgeWeight == 0 || visited[neighbor]) {
                continue;
            }

            int currentDistance = distances[currentVertex];
            if (currentDistance == Integer.MAX_VALUE) {
                continue;
            }

            int newDistance = currentDistance + edgeWeight;
            if (newDistance < distances[neighbor]) {
                priorityQueue.remove(Pair.of(distances[neighbor], neighbor));
                distances[neighbor] = newDistance;
                priorityQueue.add(Pair.of(newDistance, neighbor));
            }
        }
    }
}