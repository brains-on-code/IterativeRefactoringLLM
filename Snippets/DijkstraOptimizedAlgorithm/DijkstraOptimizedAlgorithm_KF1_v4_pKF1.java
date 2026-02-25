package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm implementation using an adjacency matrix.
 */
public class DijkstraAlgorithm {

    private final int vertexCount;

    /**
     * @param vertexCount the number of vertices in the graph
     */
    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
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
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Source vertex index is out of bounds");
        }

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> distanceQueue = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        distances[sourceVertex] = 0;
        distanceQueue.add(Pair.of(0, sourceVertex));

        while (!distanceQueue.isEmpty()) {
            Pair<Integer, Integer> distanceVertexPair = distanceQueue.iterator().next();
            distanceQueue.remove(distanceVertexPair);

            int currentVertex = distanceVertexPair.getRight();
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (!visited[neighborVertex]
                        && edgeWeight != 0
                        && distances[currentVertex] != Integer.MAX_VALUE
                        && distances[currentVertex] + edgeWeight < distances[neighborVertex]) {

                    distanceQueue.remove(Pair.of(distances[neighborVertex], neighborVertex));
                    distances[neighborVertex] = distances[currentVertex] + edgeWeight;
                    distanceQueue.add(Pair.of(distances[neighborVertex], neighborVertex));
                }
            }
        }

        return distances;
    }
}