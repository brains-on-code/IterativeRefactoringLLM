package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonsAlgorithm {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {}

    public static double[][] johnsonAlgorithm(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        double[][] edgeList = buildEdgeList(adjacencyMatrix);

        double[] vertexPotentials = computeVertexPotentials(edgeList, vertexCount);

        double[][] reweightedAdjacencyMatrix = reweightAdjacencyMatrix(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[vertexCount][vertexCount];
        for (int source = 0; source < vertexCount; source++) {
            allPairsShortestPaths[source] =
                dijkstraShortestPaths(reweightedAdjacencyMatrix, source, vertexPotentials);
        }

        return allPairsShortestPaths;
    }

    public static double[][] buildEdgeList(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edges = new ArrayList<>();

        for (int source = 0; source < vertexCount; source++) {
            for (int target = 0; target < vertexCount; target++) {
                if (source != target && !Double.isInfinite(adjacencyMatrix[source][target])) {
                    edges.add(new double[] {source, target, adjacencyMatrix[source][target]});
                }
            }
        }

        return edges.toArray(new double[0][]);
    }

    private static double[] computeVertexPotentials(double[][] edgeList, int vertexCount) {
        double[] distanceToVertex = new double[vertexCount + 1];
        Arrays.fill(distanceToVertex, INFINITY);

        int superSource = vertexCount;
        distanceToVertex[superSource] = 0;

        double[][] extendedEdgeList = Arrays.copyOf(edgeList, edgeList.length + vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            extendedEdgeList[edgeList.length + vertex] = new double[] {superSource, vertex, 0};
        }

        for (int iteration = 0; iteration < vertexCount; iteration++) {
            for (double[] edge : extendedEdgeList) {
                int source = (int) edge[0];
                int target = (int) edge[1];
                double weight = edge[2];

                if (distanceToVertex[source] != INFINITY
                    && distanceToVertex[source] + weight < distanceToVertex[target]) {
                    distanceToVertex[target] = distanceToVertex[source] + weight;
                }
            }
        }

        for (double[] edge : extendedEdgeList) {
            int source = (int) edge[0];
            int target = (int) edge[1];
            double weight = edge[2];

            if (distanceToVertex[source] + weight < distanceToVertex[target]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distanceToVertex, vertexCount);
    }

    public static double[][] reweightAdjacencyMatrix(double[][] adjacencyMatrix, double[] vertexPotentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweightedAdjacencyMatrix = new double[vertexCount][vertexCount];

        for (int source = 0; source < vertexCount; source++) {
            for (int target = 0; target < vertexCount; target++) {
                if (adjacencyMatrix[source][target] != 0) {
                    reweightedAdjacencyMatrix[source][target] =
                        adjacencyMatrix[source][target]
                            + vertexPotentials[source]
                            - vertexPotentials[target];
                }
            }
        }

        return reweightedAdjacencyMatrix;
    }

    public static double[] dijkstraShortestPaths(
        double[][] reweightedAdjacencyMatrix, int sourceVertex, double[] vertexPotentials) {

        int vertexCount = reweightedAdjacencyMatrix.length;
        double[] distanceToVertex = new double[vertexCount];
        boolean[] isVisited = new boolean[vertexCount];

        Arrays.fill(distanceToVertex, INFINITY);
        distanceToVertex[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int nearestUnvisitedVertex = findNearestUnvisitedVertex(distanceToVertex, isVisited);
            isVisited[nearestUnvisitedVertex] = true;

            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (!isVisited[neighbor]
                    && reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighbor] != 0
                    && distanceToVertex[nearestUnvisitedVertex] != INFINITY
                    && distanceToVertex[nearestUnvisitedVertex]
                            + reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighbor]
                        < distanceToVertex[neighbor]) {

                    distanceToVertex[neighbor] =
                        distanceToVertex[nearestUnvisitedVertex]
                            + reweightedAdjacencyMatrix[nearestUnvisitedVertex][neighbor];
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

    public static int findNearestUnvisitedVertex(double[] distanceToVertex, boolean[] isVisited) {
        double minimumDistance = INFINITY;
        int nearestVertexIndex = -1;

        for (int vertex = 0; vertex < distanceToVertex.length; vertex++) {
            if (!isVisited[vertex] && distanceToVertex[vertex] <= minimumDistance) {
                minimumDistance = distanceToVertex[vertex];
                nearestVertexIndex = vertex;
            }
        }

        return nearestVertexIndex;
    }
}