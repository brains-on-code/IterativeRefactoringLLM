package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] shortestDistances = new int[vertexCount];
        boolean[] isVertexProcessed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessedVertices = new TreeSet<>();

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVertexProcessed, false);
        shortestDistances[sourceVertex] = 0;
        unprocessedVertices.add(Pair.of(0, sourceVertex));

        while (!unprocessedVertices.isEmpty()) {
            Pair<Integer, Integer> distanceAndVertex = unprocessedVertices.iterator().next();
            unprocessedVertices.remove(distanceAndVertex);
            int currentVertex = distanceAndVertex.getRight();
            isVertexProcessed[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (!isVertexProcessed[neighborVertex]
                        && edgeWeight != 0
                        && shortestDistances[currentVertex] != Integer.MAX_VALUE
                        && shortestDistances[currentVertex] + edgeWeight < shortestDistances[neighborVertex]) {

                    unprocessedVertices.remove(Pair.of(shortestDistances[neighborVertex], neighborVertex));
                    shortestDistances[neighborVertex] = shortestDistances[currentVertex] + edgeWeight;
                    unprocessedVertices.add(Pair.of(shortestDistances[neighborVertex], neighborVertex));
                }
            }
        }

        return shortestDistances;
    }
}