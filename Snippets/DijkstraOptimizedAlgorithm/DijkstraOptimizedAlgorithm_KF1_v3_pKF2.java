package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm implementation for a graph represented by an adjacency matrix.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Computes the shortest path distances from a given source vertex to all other vertices
     * using Dijkstra's algorithm.
     *
     * @param adjacencyMatrix adjacency matrix of the graph; adjacencyMatrix[u][v] is the weight
     *                        of the edge from u to v, or 0 if there is no edge
     * @param source          index of the source vertex (0-based)
     * @return an array of shortest distances from the source to every vertex
     * @throws IllegalArgumentException if the source index is out of range
     */
    public int[] method1(int[][] adjacencyMatrix, int source) {
        validateSourceVertex(source);

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> prioritySet = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

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
        boolean isUnvisited = !visited[neighbor];
        boolean hasEdge = adjacencyMatrix[currentVertex][neighbor] != 0;
        boolean hasFiniteDistance = distances[currentVertex] != Integer.MAX_VALUE;
        boolean isShorterPath =
                distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor] < distances[neighbor];

        return isUnvisited && hasEdge && hasFiniteDistance && isShorterPath;
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