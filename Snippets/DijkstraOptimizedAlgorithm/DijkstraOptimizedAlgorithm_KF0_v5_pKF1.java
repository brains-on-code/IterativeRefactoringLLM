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
     * @param adjacencyMatrix The graph represented as an adjacency matrix.
     * @param sourceVertex The source vertex.
     * @return An array where the value at each index {@code i} represents the shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> distanceVertexSet = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[sourceVertex] = 0;
        distanceVertexSet.add(Pair.of(0, sourceVertex));

        while (!distanceVertexSet.isEmpty()) {
            Pair<Integer, Integer> distanceVertexPair = distanceVertexSet.iterator().next();
            distanceVertexSet.remove(distanceVertexPair);

            int currentVertex = distanceVertexPair.getRight();
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                boolean edgeExists = adjacencyMatrix[currentVertex][neighborVertex] != 0;
                boolean neighborNotVisited = !visited[neighborVertex];
                boolean currentDistanceIsFinite = distances[currentVertex] != Integer.MAX_VALUE;

                if (neighborNotVisited && edgeExists && currentDistanceIsFinite) {
                    int newDistance = distances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                    if (newDistance < distances[neighborVertex]) {
                        distanceVertexSet.remove(Pair.of(distances[neighborVertex], neighborVertex));
                        distances[neighborVertex] = newDistance;
                        distanceVertexSet.add(Pair.of(distances[neighborVertex], neighborVertex));
                    }
                }
            }
        }

        return distances;
    }
}