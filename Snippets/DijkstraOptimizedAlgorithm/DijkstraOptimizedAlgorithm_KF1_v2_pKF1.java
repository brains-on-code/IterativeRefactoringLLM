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

        int[] minDistances = new int[vertexCount];
        boolean[] isVisited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> distanceVertexSet = new TreeSet<>();

        Arrays.fill(minDistances, Integer.MAX_VALUE);
        Arrays.fill(isVisited, false);

        minDistances[sourceVertex] = 0;
        distanceVertexSet.add(Pair.of(0, sourceVertex));

        while (!distanceVertexSet.isEmpty()) {
            Pair<Integer, Integer> distanceVertexPair = distanceVertexSet.iterator().next();
            distanceVertexSet.remove(distanceVertexPair);

            int currentVertex = distanceVertexPair.getRight();
            isVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (!isVisited[neighborVertex]
                        && edgeWeight != 0
                        && minDistances[currentVertex] != Integer.MAX_VALUE
                        && minDistances[currentVertex] + edgeWeight < minDistances[neighborVertex]) {

                    distanceVertexSet.remove(Pair.of(minDistances[neighborVertex], neighborVertex));
                    minDistances[neighborVertex] = minDistances[currentVertex] + edgeWeight;
                    distanceVertexSet.add(Pair.of(minDistances[neighborVertex], neighborVertex));
                }
            }
        }

        return minDistances;
    }
}