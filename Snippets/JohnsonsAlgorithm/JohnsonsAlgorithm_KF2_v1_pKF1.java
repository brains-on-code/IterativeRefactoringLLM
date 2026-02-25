package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonsAlgorithm {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
    }

    public static double[][] johnsonAlgorithm(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        double[][] edgeList = convertToEdgeList(adjacencyMatrix);

        double[] vertexPotentials = runBellmanFord(edgeList, vertexCount);

        double[][] reweightedGraph = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[vertexCount][vertexCount];
        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            allPairsShortestPaths[sourceVertex] =
                runDijkstra(reweightedGraph, sourceVertex, vertexPotentials);
        }

        return allPairsShortestPaths;
    }

    public static double[][] convertToEdgeList(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int fromVertex = 0; fromVertex < vertexCount; fromVertex++) {
            for (int toVertex = 0; toVertex < vertexCount; toVertex++) {
                if (fromVertex != toVertex && !Double.isInfinite(adjacencyMatrix[fromVertex][toVertex])) {
                    edgeList.add(new double[] {fromVertex, toVertex, adjacencyMatrix[fromVertex][toVertex]});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    private static double[] runBellmanFord(double[][] edges, int vertexCount) {
        double[] distanceToVertex = new double[vertexCount + 1];
        Arrays.fill(distanceToVertex, INFINITY);
        int superSource = vertexCount;
        distanceToVertex[superSource] = 0;

        double[][] allEdges = Arrays.copyOf(edges, edges.length + vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            allEdges[edges.length + vertex] = new double[] {superSource, vertex, 0};
        }

        for (int iteration = 0; iteration < vertexCount; iteration++) {
            for (double[] edge : allEdges) {
                int fromVertex = (int) edge[0];
                int toVertex = (int) edge[1];
                double weight = edge[2];

                if (distanceToVertex[fromVertex] != INFINITY
                    && distanceToVertex[fromVertex] + weight < distanceToVertex[toVertex]) {
                    distanceToVertex[toVertex] = distanceToVertex[fromVertex] + weight;
                }
            }
        }

        for (double[] edge : allEdges) {
            int fromVertex = (int) edge[0];
            int toVertex = (int) edge[1];
            double weight = edge[2];

            if (distanceToVertex[fromVertex] + weight < distanceToVertex[toVertex]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distanceToVertex, vertexCount);
    }

    public static double[][] reweightGraph(double[][] adjacencyMatrix, double[] vertexPotentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweightedGraph = new double[vertexCount][vertexCount];

        for (int fromVertex = 0; fromVertex < vertexCount; fromVertex++) {
            for (int toVertex = 0; toVertex < vertexCount; toVertex++) {
                if (adjacencyMatrix[fromVertex][toVertex] != 0) {
                    reweightedGraph[fromVertex][toVertex] =
                        adjacencyMatrix[fromVertex][toVertex]
                            + vertexPotentials[fromVertex]
                            - vertexPotentials[toVertex];
                }
            }
        }

        return reweightedGraph;
    }

    public static double[] runDijkstra(double[][] reweightedGraph, int sourceVertex, double[] vertexPotentials) {
        int vertexCount = reweightedGraph.length;
        double[] distanceToVertex = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distanceToVertex, INFINITY);
        distanceToVertex[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int nearestVertex = findMinDistanceVertex(distanceToVertex, visited);
            visited[nearestVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                if (!visited[neighborVertex]
                    && reweightedGraph[nearestVertex][neighborVertex] != 0
                    && distanceToVertex[nearestVertex] != INFINITY
                    && distanceToVertex[nearestVertex] + reweightedGraph[nearestVertex][neighborVertex]
                        < distanceToVertex[neighborVertex]) {

                    distanceToVertex[neighborVertex] =
                        distanceToVertex[nearestVertex] + reweightedGraph[nearestVertex][neighborVertex];
                }
            }
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (distanceToVertex[vertex] != INFINITY) {
                distanceToVertex[vertex] =
                    distanceToVertex[vertex] - vertexPotentials[sourceVertex] + vertexPotentials[vertex];
            }
        }

        return distanceToVertex;
    }

    public static int findMinDistanceVertex(double[] distanceToVertex, boolean[] visited) {
        double minDistance = INFINITY;
        int minIndex = -1;

        for (int vertex = 0; vertex < distanceToVertex.length; vertex++) {
            if (!visited[vertex] && distanceToVertex[vertex] <= minDistance) {
                minDistance = distanceToVertex[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }
}