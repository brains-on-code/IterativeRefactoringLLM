package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonAllPairsShortestPath {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonAllPairsShortestPath() {
    }

    public static double[][] computeAllPairsShortestPaths(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        double[][] edgeList = buildEdgeList(adjacencyMatrix);

        double[] vertexPotentials = computeVertexPotentials(edgeList, vertexCount);

        double[][] reweightedAdjacencyMatrix = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[vertexCount][vertexCount];
        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            allPairsShortestPaths[sourceVertex] =
                    dijkstraWithReweighting(reweightedAdjacencyMatrix, sourceVertex, vertexPotentials);
        }

        return allPairsShortestPaths;
    }

    public static double[][] buildEdgeList(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edges = new ArrayList<>();

        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < vertexCount; targetVertex++) {
                if (sourceVertex != targetVertex
                        && !Double.isInfinite(adjacencyMatrix[sourceVertex][targetVertex])) {
                    edges.add(new double[] {sourceVertex, targetVertex, adjacencyMatrix[sourceVertex][targetVertex]});
                }
            }
        }

        return edges.toArray(new double[0][]);
    }

    private static double[] computeVertexPotentials(double[][] edges, int vertexCount) {
        double[] distances = new double[vertexCount + 1];
        Arrays.fill(distances, INFINITY);
        int superSourceVertex = vertexCount;
        distances[superSourceVertex] = 0;

        double[][] extendedEdges = Arrays.copyOf(edges, edges.length + vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            extendedEdges[edges.length + vertex] = new double[] {superSourceVertex, vertex, 0};
        }

        for (int iteration = 0; iteration < vertexCount; iteration++) {
            for (double[] edge : extendedEdges) {
                int sourceVertex = (int) edge[0];
                int targetVertex = (int) edge[1];
                double weight = edge[2];
                if (distances[sourceVertex] != INFINITY
                        && distances[sourceVertex] + weight < distances[targetVertex]) {
                    distances[targetVertex] = distances[sourceVertex] + weight;
                }
            }
        }

        for (double[] edge : extendedEdges) {
            int sourceVertex = (int) edge[0];
            int targetVertex = (int) edge[1];
            double weight = edge[2];
            if (distances[sourceVertex] + weight < distances[targetVertex]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distances, vertexCount);
    }

    public static double[][] reweightGraph(double[][] adjacencyMatrix, double[] vertexPotentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweightedAdjacencyMatrix = new double[vertexCount][vertexCount];

        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < vertexCount; targetVertex++) {
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

    public static double[] dijkstraWithReweighting(
            double[][] reweightedAdjacencyMatrix, int sourceVertex, double[] vertexPotentials) {
        int vertexCount = reweightedAdjacencyMatrix.length;
        double[] distances = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Arrays.fill(distances, INFINITY);
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int currentVertex = selectMinDistanceVertex(distances, visited);
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                if (!visited[neighborVertex]
                        && reweightedAdjacencyMatrix[currentVertex][neighborVertex] != 0
                        && distances[currentVertex] != INFINITY
                        && distances[currentVertex] + reweightedAdjacencyMatrix[currentVertex][neighborVertex]
                                < distances[neighborVertex]) {
                    distances[neighborVertex] =
                            distances[currentVertex] + reweightedAdjacencyMatrix[currentVertex][neighborVertex];
                }
            }
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (distances[vertex] != INFINITY) {
                distances[vertex] =
                        distances[vertex] - vertexPotentials[sourceVertex] + vertexPotentials[vertex];
            }
        }

        return distances;
    }

    public static int selectMinDistanceVertex(double[] distances, boolean[] visited) {
        double minDistance = INFINITY;
        int minIndex = -1;
        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (!visited[vertex] && distances[vertex] <= minDistance) {
                minDistance = distances[vertex];
                minIndex = vertex;
            }
        }
        return minIndex;
    }
}