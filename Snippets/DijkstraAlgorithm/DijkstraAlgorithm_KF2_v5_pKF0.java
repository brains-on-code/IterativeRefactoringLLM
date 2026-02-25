package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class DijkstraAlgorithm {

    private static final int NO_VERTEX = -1;
    private static final int NO_EDGE = 0;
    private static final int INFINITY = Integer.MAX_VALUE;

    private final int vertexCount;

    public DijkstraAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int[] run(int[][] graph, int source) {
        validateSource(source);
        validateGraph(graph);

        int[] distances = initializeDistances(source);
        boolean[] visited = new boolean[vertexCount];

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = findClosestUnvisitedVertex(distances, visited);
            if (currentVertex == NO_VERTEX) {
                break; // Remaining vertices are unreachable
            }
            visited[currentVertex] = true;
            relaxNeighbors(graph, distances, visited, currentVertex);
        }

        printDistances(distances);
        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex out of range: " + source);
        }
    }

    private void validateGraph(int[][] graph) {
        if (graph == null || graph.length != vertexCount) {
            throw new IllegalArgumentException(
                "Graph must be a non-null square matrix of size " + vertexCount
            );
        }

        for (int[] row : graph) {
            if (row == null || row.length != vertexCount) {
                throw new IllegalArgumentException(
                    "Graph must be a non-null square matrix of size " + vertexCount
                );
            }
        }
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, INFINITY);
        distances[source] = 0;
        return distances;
    }

    private void relaxNeighbors(
        int[][] graph,
        int[] distances,
        boolean[] visited,
        int currentVertex
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            int edgeWeight = graph[currentVertex][neighbor];
            if (canRelax(distances, visited, currentVertex, neighbor, edgeWeight)) {
                distances[neighbor] = distances[currentVertex] + edgeWeight;
            }
        }
    }

    private boolean canRelax(
        int[] distances,
        boolean[] visited,
        int currentVertex,
        int neighbor,
        int edgeWeight
    ) {
        if (visited[neighbor]) {
            return false;
        }
        if (edgeWeight == NO_EDGE) {
            return false;
        }
        if (distances[currentVertex] == INFINITY) {
            return false;
        }
        int newDistance = distances[currentVertex] + edgeWeight;
        return newDistance < distances[neighbor];
    }

    private int findClosestUnvisitedVertex(int[] distances, boolean[] visited) {
        int minDistance = INFINITY;
        int closestVertex = NO_VERTEX;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minDistance) {
                minDistance = distances[vertex];
                closestVertex = vertex;
            }
        }

        return closestVertex;
    }

    private void printDistances(int[] distances) {
        System.out.println("Vertex\tDistance");
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            System.out.println(vertex + "\t" + distances[vertex]);
        }
    }
}