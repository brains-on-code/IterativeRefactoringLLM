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
            throw new IllegalArgumentException("Source vertex index is out of bounds");
        }

        int[] shortestDistances = new int[numberOfVertices];
        boolean[] isVisited = new boolean[numberOfVertices];
        Set<Pair<Integer, Integer>> distanceVertexSet = new TreeSet<>();

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVisited, false);

        shortestDistances[sourceVertex] = 0;
        distanceVertexSet.add(Pair.of(0, sourceVertex));

        while (!distanceVertexSet.isEmpty()) {
            Pair<Integer, Integer> distanceVertexPair = distanceVertexSet.iterator().next();
            distanceVertexSet.remove(distanceVertexPair);

            int currentVertex = distanceVertexPair.getRight();
            isVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (!isVisited[neighborVertex]
                        && edgeWeight != 0
                        && shortestDistances[currentVertex] != Integer.MAX_VALUE
                        && shortestDistances[currentVertex] + edgeWeight < shortestDistances[neighborVertex]) {

                    distanceVertexSet.remove(Pair.of(shortestDistances[neighborVertex], neighborVertex));
                    shortestDistances[neighborVertex] = shortestDistances[currentVertex] + edgeWeight;
                    distanceVertexSet.add(Pair.of(shortestDistances[neighborVertex], neighborVertex));
                }
            }
        }

        return shortestDistances;
    }
}