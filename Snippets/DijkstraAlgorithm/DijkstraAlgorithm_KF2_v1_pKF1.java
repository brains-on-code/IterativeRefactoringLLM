package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int numberOfVertices;

    public DijkstraAlgorithm(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= numberOfVertices) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }

        int[] shortestDistances = new int[numberOfVertices];
        boolean[] isVertexProcessed = new boolean[numberOfVertices];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVertexProcessed, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int currentVertex = getClosestUnprocessedVertex(shortestDistances, isVertexProcessed);
            isVertexProcessed[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                if (!isVertexProcessed[neighborVertex]
                        && adjacencyMatrix[currentVertex][neighborVertex] != 0
                        && shortestDistances[currentVertex] != Integer.MAX_VALUE
                        && shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex] < shortestDistances[neighborVertex]) {
                    shortestDistances[neighborVertex] =
                            shortestDistances[currentVertex] + adjacencyMatrix[currentVertex][neighborVertex];
                }
            }
        }

        printDistances(shortestDistances);
        return shortestDistances;
    }

    private int getClosestUnprocessedVertex(int[] shortestDistances, boolean[] isVertexProcessed) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            if (!isVertexProcessed[vertexIndex] && shortestDistances[vertexIndex] <= minimumDistance) {
                minimumDistance = shortestDistances[vertexIndex];
                closestVertexIndex = vertexIndex;
            }
        }

        return closestVertexIndex;
    }

    private void printDistances(int[] shortestDistances) {
        System.out.println("Vertex \t Distance");
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            System.out.println(vertexIndex + " \t " + shortestDistances[vertexIndex]);
        }
    }
}