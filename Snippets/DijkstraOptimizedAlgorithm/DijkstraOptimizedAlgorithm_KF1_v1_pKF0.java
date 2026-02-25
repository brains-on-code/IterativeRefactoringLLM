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
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> queue = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        distances[source] = 0;
        queue.add(Pair.of(0, source));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> currentPair = queue.iterator().next();
            queue.remove(currentPair);

            int currentVertex = currentPair.getRight();
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighbor];

                if (!visited[neighbor]
                        && edgeWeight != 0
                        && distances[currentVertex] != Integer.MAX_VALUE
                        && distances[currentVertex] + edgeWeight < distances[neighbor]) {

                    queue.remove(Pair.of(distances[neighbor], neighbor));
                    distances[neighbor] = distances[currentVertex] + edgeWeight;
                    queue.add(Pair.of(distances[neighbor], neighbor));
                }
            }
        }

        return distances;
    }
}