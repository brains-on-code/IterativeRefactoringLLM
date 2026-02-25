package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int vertexCount;

    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        validateSourceVertex(sourceVertex);

        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        distances[sourceVertex] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = getClosestUnvisitedVertex(distances, visited);
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighbor];

                boolean neighborNotVisited = !visited[neighbor];
                boolean edgeExists = edgeWeight != 0;
                boolean currentDistanceIsFinite = distances[currentVertex] != Integer.MAX_VALUE;
                boolean newPathIsShorter = distances[currentVertex] + edgeWeight < distances[neighbor];

                if (neighborNotVisited && edgeExists && currentDistanceIsFinite && newPathIsShorter) {
                    distances[neighbor] = distances[currentVertex] + edgeWeight;
                }
            }
        }

        printDistances(distances);
        return distances;
    }

    private void validateSourceVertex(int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }
    }

    private int getClosestUnvisitedVertex(int[] distances, boolean[] visited) {
        int minimumDistance = Integer.MAX_VALUE;
        int closestVertex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            boolean isUnvisited = !visited[vertex];
            boolean isCloserOrEqual = distances[vertex] <= minimumDistance;

            if (isUnvisited && isCloserOrEqual) {
                minimumDistance = distances[vertex];
                closestVertex = vertex;
            }
        }

        return closestVertex;
    }

    private void printDistances(int[] distances) {
        System.out.println("Vertex \t Distance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + " \t " + distances[vertex]);
        }
    }
}