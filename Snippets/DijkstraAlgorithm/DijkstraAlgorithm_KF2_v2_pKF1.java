package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private final int vertexCount;

    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] adjacencyMatrix, int sourceVertex) {
        if (sourceVertex < 0 || sourceVertex >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range");
        }

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

                if (!visited[neighbor]
                        && edgeWeight != 0
                        && distances[currentVertex] != Integer.MAX_VALUE
                        && distances[currentVertex] + edgeWeight < distances[neighbor]) {
                    distances[neighbor] = distances[currentVertex] + edgeWeight;
                }
            }
        }

        printDistances(distances);
        return distances;
    }

    private int getClosestUnvisitedVertex(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int closestVertex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minDistance) {
                minDistance = distances[vertex];
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