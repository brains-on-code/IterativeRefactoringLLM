package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

public class DijkstraOptimizedAlgorithm {

    private final int numberOfVertices;

    public DijkstraOptimizedAlgorithm(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
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
            Pair<Integer, Integer> distanceAndVertex = distanceVertexSet.iterator().next();
            distanceVertexSet.remove(distanceAndVertex);

            int currentVertex = distanceAndVertex.getRight();
            isVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                boolean edgeExists = edgeWeight != 0;
                boolean neighborNotVisited = !isVisited[neighborVertex];
                boolean currentDistanceIsFinite = shortestDistances[currentVertex] != Integer.MAX_VALUE;

                if (neighborNotVisited && edgeExists && currentDistanceIsFinite) {
                    int newDistance = shortestDistances[currentVertex] + edgeWeight;

                    if (newDistance < shortestDistances[neighborVertex]) {
                        distanceVertexSet.remove(Pair.of(shortestDistances[neighborVertex], neighborVertex));
                        shortestDistances[neighborVertex] = newDistance;
                        distanceVertexSet.add(Pair.of(newDistance, neighborVertex));
                    }
                }
            }
        }

        return shortestDistances;
    }
}