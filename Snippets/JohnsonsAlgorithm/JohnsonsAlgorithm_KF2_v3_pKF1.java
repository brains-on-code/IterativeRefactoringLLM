package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonsAlgorithm {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
    }

    public static double[][] johnsonAlgorithm(double[][] adjacencyMatrix) {
        int numberOfVertices = adjacencyMatrix.length;
        double[][] edgeList = convertToEdgeList(adjacencyMatrix);

        double[] vertexPotentials = runBellmanFord(edgeList, numberOfVertices);

        double[][] reweightedAdjacencyMatrix = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[numberOfVertices][numberOfVertices];
        for (int sourceVertex = 0; sourceVertex < numberOfVertices; sourceVertex++) {
            allPairsShortestPaths[sourceVertex] =
                runDijkstra(reweightedAdjacencyMatrix, sourceVertex, vertexPotentials);
        }

        return allPairsShortestPaths;
    }

    public static double[][] convertToEdgeList(double[][] adjacencyMatrix) {
        int numberOfVertices = adjacencyMatrix.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int sourceVertex = 0; sourceVertex < numberOfVertices; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < numberOfVertices; targetVertex++) {
                if (sourceVertex != targetVertex && !Double.isInfinite(adjacencyMatrix[sourceVertex][targetVertex])) {
                    edgeList.add(
                        new double[] {sourceVertex, targetVertex, adjacencyMatrix[sourceVertex][targetVertex]});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    private static double[] runBellmanFord(double[][] edgeList, int numberOfVertices) {
        double[] distances = new double[numberOfVertices + 1];
        Arrays.fill(distances, INFINITY);

        int superSourceVertex = numberOfVertices;
        distances[superSourceVertex] = 0;

        double[][] extendedEdgeList = Arrays.copyOf(edgeList, edgeList.length + numberOfVertices);
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            extendedEdgeList[edgeList.length + vertex] = new double[] {superSourceVertex, vertex, 0};
        }

        for (int iteration = 0; iteration < numberOfVertices; iteration++) {
            for (double[] edge : extendedEdgeList) {
                int sourceVertex = (int) edge[0];
                int targetVertex = (int) edge[1];
                double weight = edge[2];

                if (distances[sourceVertex] != INFINITY
                    && distances[sourceVertex] + weight < distances[targetVertex]) {
                    distances[targetVertex] = distances[sourceVertex] + weight;
                }
            }
        }

        for (double[] edge : extendedEdgeList) {
            int sourceVertex = (int) edge[0];
            int targetVertex = (int) edge[1];
            double weight = edge[2];

            if (distances[sourceVertex] + weight < distances[targetVertex]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distances, numberOfVertices);
    }

    public static double[][] reweightGraph(double[][] adjacencyMatrix, double[] vertexPotentials) {
        int numberOfVertices = adjacencyMatrix.length;
        double[][] reweightedAdjacencyMatrix = new double[numberOfVertices][numberOfVertices];

        for (int sourceVertex = 0; sourceVertex < numberOfVertices; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < numberOfVertices; targetVertex++) {
                if (adjacencyMatrix[sourceVertex][targetVertex] != 0) {
                    reweightedAdjacencyMatrix[sourceVertex][targetVertex] =
                        adjacencyMatrix[sourceVertex][targetVertex]
                            + vertexPotentials[sourceVertex]
                            - vertexPotentials[targetVertex];
                }
            }
        }

        return reweightedAdjacencyMatrix;
    }

    public static double[] runDijkstra(
        double[][] reweightedAdjacencyMatrix, int sourceVertex, double[] vertexPotentials) {

        int numberOfVertices = reweightedAdjacencyMatrix.length;
        double[] distances = new double[numberOfVertices];
        boolean[] visited = new boolean[numberOfVertices];

        Arrays.fill(distances, INFINITY);
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            int nearestUnvisitedVertex = findMinDistanceVertex(distances, visited);
            visited[nearestUnvisitedVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numberOfVertices; neighborVertex++) {
                if (!visited[neighborVertex]
                    && reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighborVertex] != 0
                    && distances[nearestUnvisitedVertex] != INFINITY
                    && distances[nearestUnvisitedVertex]
                            + reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighborVertex]
                        < distances[neighborVertex]) {

                    distances[neighborVertex] =
                        distances[nearestUnvisitedVertex]
                            + reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighborVertex];
                }
            }
        }

        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            if (distances[vertex] != INFINITY) {
                distances[vertex] =
                    distances[vertex] - vertexPotentials[sourceVertex] + vertexPotentials[vertex];
            }
        }

        return distances;
    }

    public static int findMinDistanceVertex(double[] distances, boolean[] visited) {
        double minimumDistance = INFINITY;
        int minimumDistanceVertexIndex = -1;

        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minimumDistance) {
                minimumDistance = distances[vertex];
                minimumDistanceVertexIndex = vertex;
            }
        }

        return minimumDistanceVertexIndex;
    }
}