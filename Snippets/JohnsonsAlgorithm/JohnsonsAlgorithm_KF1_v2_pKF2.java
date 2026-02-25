package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Johnson's algorithm for all-pairs shortest paths
 * on a weighted directed graph that may contain negative edge weights
 * but must not contain negative cycles.
 *
 * The graph is represented as an adjacency matrix where:
 * - matrix[u][v] is the weight of the edge u -> v
 * - Double.POSITIVE_INFINITY represents no edge between u and v
 */
public final class Class1 {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes all-pairs shortest paths using Johnson's algorithm.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return matrix of shortest path distances between all pairs of vertices
     * @throws IllegalArgumentException if the graph contains a negative weight cycle
     */
    public static double[][] method1(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;

        double[][] edges = method2(adjacencyMatrix);
        double[] potentials = method3(edges, vertexCount);
        double[][] reweightedGraph = method4(adjacencyMatrix, potentials);

        double[][] result = new double[vertexCount][vertexCount];
        for (int source = 0; source < vertexCount; source++) {
            result[source] = method5(reweightedGraph, source, potentials);
        }

        return result;
    }

    /**
     * Converts an adjacency matrix to an edge list.
     * Each edge is represented as a double[] of length 3:
     * [sourceVertex, targetVertex, weight].
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return edge list representation of the graph
     */
    public static double[][] method2(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edges = new ArrayList<>();

        for (int u = 0; u < vertexCount; u++) {
            for (int v = 0; v < vertexCount; v++) {
                if (u != v && !Double.isInfinite(adjacencyMatrix[u][v])) {
                    edges.add(new double[] {u, v, adjacencyMatrix[u][v]});
                }
            }
        }

        return edges.toArray(new double[0][]);
    }

    /**
     * Runs Bellman-Ford on an augmented graph with a new source vertex
     * connected to all existing vertices with zero-weight edges.
     * Computes the vertex potentials used for reweighting.
     *
     * @param edges       edge list of the original graph
     * @param vertexCount number of vertices in the original graph
     * @return array of vertex potentials
     * @throws IllegalArgumentException if the graph contains a negative weight cycle
     */
    private static double[] method3(double[][] edges, int vertexCount) {
        double[] distance = new double[vertexCount + 1];
        Arrays.fill(distance, INFINITY);

        int superSource = vertexCount;
        distance[superSource] = 0;

        double[][] augmentedEdges = Arrays.copyOf(edges, edges.length + vertexCount);
        for (int v = 0; v < vertexCount; v++) {
            augmentedEdges[edges.length + v] = new double[] {superSource, v, 0};
        }

        for (int i = 0; i < vertexCount; i++) {
            for (double[] edge : augmentedEdges) {
                int u = (int) edge[0];
                int v = (int) edge[1];
                double w = edge[2];

                if (distance[u] != INFINITY && distance[u] + w < distance[v]) {
                    distance[v] = distance[u] + w;
                }
            }
        }

        for (double[] edge : augmentedEdges) {
            int u = (int) edge[0];
            int v = (int) edge[1];
            double w = edge[2];

            if (distance[u] + w < distance[v]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distance, vertexCount);
    }

    /**
     * Reweights the edges of the graph using the vertex potentials
     * so that all edge weights become non-negative.
     *
     * newWeight(u, v) = weight(u, v) + h[u] - h[v]
     *
     * @param adjacencyMatrix original adjacency matrix
     * @param potentials      vertex potentials from Bellman-Ford
     * @return reweighted adjacency matrix
     */
    public static double[][] method4(double[][] adjacencyMatrix, double[] potentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweighted = new double[vertexCount][vertexCount];

        for (int u = 0; u < vertexCount; u++) {
            for (int v = 0; v < vertexCount; v++) {
                if (adjacencyMatrix[u][v] != 0) {
                    reweighted[u][v] = adjacencyMatrix[u][v] + potentials[u] - potentials[v];
                } else {
                    reweighted[u][v] = adjacencyMatrix[u][v];
                }
            }
        }

        return reweighted;
    }

    /**
     * Runs Dijkstra's algorithm from a single source on the reweighted graph,
     * then converts distances back to the original weights using the potentials.
     *
     * @param reweightedGraph adjacency matrix of the reweighted graph
     * @param source          source vertex
     * @param potentials      vertex potentials from Bellman-Ford
     * @return array of shortest path distances from the source to all vertices
     */
    public static double[] method5(double[][] reweightedGraph, int source, double[] potentials) {
        int vertexCount = reweightedGraph.length;
        double[] distance = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distance, INFINITY);
        distance[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = method6(distance, visited);
            if (u == -1) {
                break;
            }
            visited[u] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v]
                        && reweightedGraph[u][v] != 0
                        && distance[u] != INFINITY
                        && distance[u] + reweightedGraph[u][v] < distance[v]) {
                    distance[v] = distance[u] + reweightedGraph[u][v];
                }
            }
        }

        for (int v = 0; v < vertexCount; v++) {
            if (distance[v] != INFINITY) {
                distance[v] = distance[v] - potentials[source] + potentials[v];
            }
        }

        return distance;
    }

    /**
     * Selects the unvisited vertex with the smallest tentative distance.
     *
     * @param distance array of tentative distances
     * @param visited  array indicating whether a vertex has been visited
     * @return index of the vertex with the minimum distance, or -1 if none is found
     */
    public static int method6(double[] distance, boolean[] visited) {
        double minDistance = INFINITY;
        int minIndex = -1;

        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] <= minDistance) {
                minDistance = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
}