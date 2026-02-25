package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int numberOfVertices;

    public DijkstraAlgorithm(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        validateSourceVertex(sourceVertex);

        int[] shortestDistances = new int[numberOfVertices];
        boolean[] isVisited = new boolean[numberOfVertices];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVisited, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int currentVertex = getClosestUnvisitedVertex(shortestDistances, isVisited);
            isVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                boolean neighborNotVisited = !isVisited[neighborVertex];
                boolean edgeExists = edgeWeight != 0;
                boolean currentDistanceIsFinite = shortestDistances[currentVertex] != Integer.MAX_VALUE;
                boolean newPathIsShorter =
                        shortestDistances[currentVertex] + edgeWeight < shortestDistances[neighborVertex];

                if (neighborNotVisited && edgeExists && currentDistanceIsFinite && newPathIsShorter) {
                    shortestDistances[neighborVertex] = shortestDistances[currentVertex] + edgeWeight;
                }
            }
        }

        printDistances(shortestDistances);
        return shortestDistances;
    }

    private void validateSourceVertex(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= numberOfVertices) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }
    }

    private int getClosestUnvisitedVertex(int[] shortestDistances, boolean[] isVisited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestUnvisitedVertex = -1;

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            boolean isUnvisited = !isVisited[vertexIndex];
            boolean isCloserOrEqual = shortestDistances[vertexIndex] <= minimumDistance;

            if (isUnvisited && isCloserOrEqual) {
                minimumDistance = shortestDistances[vertexIndex];
                closestUnvisitedVertex = vertexIndex;
            }
        }

        return closestUnvisitedVertex;
    }

    private void printDistances(int[] shortestDistances) {
        System.out.println("Vertex \t Distance");
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + shortestDistances[vertexIndex]);
        }
    }
}