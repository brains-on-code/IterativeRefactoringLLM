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
        boolean[] isVertexVisited = new boolean[numberOfVertices];

        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(isVertexVisited, false);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int currentVertex = getClosestUnvisitedVertex(shortestDistances, isVertexVisited);
            isVertexVisited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                boolean isNeighborUnvisited = !isVertexVisited[neighborVertex];
                boolean isEdgePresent = edgeWeight != 0;
                boolean isCurrentDistanceFinite = shortestDistances[currentVertex] != Integer.MAX_VALUE;
                boolean isNewPathShorter =
                        shortestDistances[currentVertex] + edgeWeight < shortestDistances[neighborVertex];

                if (isNeighborUnvisited && isEdgePresent && isCurrentDistanceFinite && isNewPathShorter) {
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

    private int getClosestUnvisitedVertex(int[] shortestDistances, boolean[] isVertexVisited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestUnvisitedVertex = -1;

        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            boolean isUnvisited = !isVertexVisited[vertex];
            boolean isCloserOrEqual = shortestDistances[vertex] <= minimumDistance;

            if (isUnvisited && isCloserOrEqual) {
                minimumDistance = shortestDistances[vertex];
                closestUnvisitedVertex = vertex;
            }
        }

        return closestUnvisitedVertex;
    }

    private void printDistances(int[] shortestDistances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            System.out.println(vertex + " \t " + shortestDistances[vertex]);
        }
    }
}