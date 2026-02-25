package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex to all other vertices in a graph.
 */
public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    /**
     * Constructs a Dijkstra object with the given number of vertices.
     *
     * @param vertexCount The number of vertices in the graph.
     */
    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Executes Dijkstra's algorithm on the provided graph to find the shortest paths from the source vertex to all other vertices.
     *
     * The graph is represented as an adjacency matrix where {@code graph[i][j]} represents the weight of the edge from vertex {@code i}
     * to vertex {@code j}. A value of 0 indicates no edge exists between the vertices.
     *
     * @param graph The graph represented as an adjacency matrix.
     * @param source The source vertex.
     * @return An array where the value at each index {@code i} represents the shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] graph, int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[source] = 0;
        priorityQueue.add(Pair.of(0, source));

        while (!priorityQueue.isEmpty()) {
            Pair<Integer, Integer> currentDistanceAndVertex = priorityQueue.iterator().next();
            priorityQueue.remove(currentDistanceAndVertex);

            int currentVertex = currentDistanceAndVertex.getRight();
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                boolean hasEdge = graph[currentVertex][neighborVertex] != 0;
                boolean notVisited = !visited[neighborVertex];
                boolean currentDistanceIsFinite = distances[currentVertex] != Integer.MAX_VALUE;

                if (notVisited && hasEdge && currentDistanceIsFinite) {
                    int newDistance = distances[currentVertex] + graph[currentVertex][neighborVertex];
                    if (newDistance < distances[neighborVertex]) {
                        priorityQueue.remove(Pair.of(distances[neighborVertex], neighborVertex));
                        distances[neighborVertex] = newDistance;
                        priorityQueue.add(Pair.of(distances[neighborVertex], neighborVertex));
                    }
                }
            }
        }

        return distances;
    }
}