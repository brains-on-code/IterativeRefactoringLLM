package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm implementation using an adjacency matrix.
 */
public class DijkstraAlgorithm {

    private final int numberOfVertices;

    /**
     * @param numberOfVertices the number of vertices in the graph
     */
    public DijkstraAlgorithm(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    /**
     * Computes the shortest path distances from a source vertex to all other vertices
     * in a weighted graph represented by an adjacency matrix.
     *
     * @param adjacencyMatrix the graph represented as an adjacency matrix
     * @param sourceVertex    the index of the source vertex
     * @return an array of shortest distances from the source to each vertex
     */
    public int[] shortestPaths(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= numberOfVertices) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[numberOfVertices];
        boolean[] visited = new boolean[numberOfVertices];
        Set<Pair<Integer, Integer>> prioritySet = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[sourceVertex] = 0;
        prioritySet.add(Pair.of(0, sourceVertex));

        while (!prioritySet.isEmpty()) {
            Pair<Integer, Integer> currentPair = prioritySet.iterator().next();
            prioritySet.remove(currentPair);
            int currentVertex = currentPair.getRight();
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < numberOfVertices; neighbor++) {
                if (!visited[neighbor]
                        && adjacencyMatrix[currentVertex][neighbor] != 0
                        && distances[currentVertex] != Integer.MAX_VALUE
                        && distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor] < distances[neighbor]) {

                    prioritySet.remove(Pair.of(distances[neighbor], neighbor));
                    distances[neighbor] = distances[currentVertex] + adjacencyMatrix[currentVertex][neighbor];
                    prioritySet.add(Pair.of(distances[neighbor], neighbor));
                }
            }
        }

        return distances;
    }
}