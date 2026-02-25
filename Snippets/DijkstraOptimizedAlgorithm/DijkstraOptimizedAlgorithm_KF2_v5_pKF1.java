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
            Pair<Integer, Integer> distanceAndVertex = distanceQueue.iterator().next();
            distanceQueue.remove(distanceAndVertex);

            int currentVertex = distanceAndVertex.getRight();
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                boolean edgeExists = edgeWeight != 0;
                boolean neighborNotVisited = !visited[neighborVertex];
                boolean currentDistanceIsFinite = distances[currentVertex] != Integer.MAX_VALUE;

                if (neighborNotVisited && edgeExists && currentDistanceIsFinite) {
                    int newDistance = distances[currentVertex] + edgeWeight;

                    if (newDistance < distances[neighborVertex]) {
                        distanceQueue.remove(Pair.of(distances[neighborVertex], neighborVertex));
                        distances[neighborVertex] = newDistance;
                        distanceQueue.add(Pair.of(newDistance, neighborVertex));
                    }
                }
            }
        }

        return distances;
    }
}