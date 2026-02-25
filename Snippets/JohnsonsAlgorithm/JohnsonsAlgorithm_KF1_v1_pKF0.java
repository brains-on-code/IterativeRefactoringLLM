package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Johnson's algorithm for all-pairs shortest paths
 * on a weighted directed graph that may contain negative edge weights
 * but no negative cycles.
 *
 * The graph is represented as an adjacency matrix where:
 * - matrix[u][v] is the weight of the edge u -> v
 * - Double.POSITIVE_INFINITY represents no edge between u and v
 */
public final class Class1 {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private Class1() {
    }

    /**
     * Computes all-pairs shortest paths using Johnson's algorithm.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return matrix of shortest path distances between all pairs of vertices
     */
    public static double[][] method1(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;

        double[][] edges = method2(adjacencyMatrix);
        double[] potentials = method3(edges, vertexCount);
        double[][] reweightedGraph = method4(adjacencyMatrix, potentials);

        double[][] allPairsDistances = new double[vertexCount][vertexCount];
        for (int source = 0; source < vertexCount; source++) {
            allPairsDistances[source] = method5(reweightedGraph, source, potentials);
        }

        return allPairsDistances;
    }

    /**
     * Converts an adjacency matrix to an edge list.
     * Each edge is represented as a double[] {from, to, weight}.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return edge list representation of the graph
     */
    public static double[][] method2(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edges = new ArrayList<>();

        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                if (from != to && !Double.isInfinite(adjacencyMatrix[from][to])) {
                    edges.add(new double[] {from, to, adjacencyMatrix[from][to]});
                }
            }
        }

        return edges.toArray(new double[0][]);
    }

    /**
     * Runs Bellman-Ford on an augmented graph with a new source vertex
     * connected to all existing vertices with zero-weight edges to compute
     * vertex potentials for Johnson's reweighting.
     *
     * @param edges       edge list of the original graph
     * @param vertexCount number of vertices in the original graph
     * @return array of vertex potentials
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
                int from = (int) edge[0];
                int to = (int) edge[1];
                double weight = edge[2];

                if (distance[from] != INFINITY && distance[from] + weight < distance[to]) {
                    distance[to] = distance[from] + weight;
                }
            }
        }

        for (double[] edge : augmentedEdges) {
            int from = (int) edge[0];
            int to = (int) edge[1];
            double weight = edge[2];

            if (distance[from] + weight < distance[to]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distance, vertexCount);
    }

    /**
     * Reweights the edges of the graph using the vertex potentials so that
     * all edge weights become non-negative.
     *
     * @param adjacencyMatrix original adjacency matrix
     * @param potentials      vertex potentials from Bellman-Ford
     * @return reweighted adjacency matrix
     */
    public static double[][] method4(double[][] adjacencyMatrix, double[] potentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweighted = new double[vertexCount][vertexCount];

        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                if (adjacencyMatrix[from][to] != 0) {
                    reweighted[from][to] =
                        adjacencyMatrix[from][to] + potentials[from] - potentials[to];
                }
            }
        }

        return reweighted;
    }

    /**
     * Runs Dijkstra's algorithm on the reweighted graph from a given source
     * and then adjusts the distances back to the original weights using
     * the vertex potentials.
     *
     * @param reweightedGraph reweighted adjacency matrix
     * @param source          source vertex
     * @param potentials      vertex potentials
     * @return shortest path distances from source to all vertices
     */
    public static double[] method5(double[][] reweightedGraph, int source, double[] potentials) {
        int vertexCount = reweightedGraph.length;
        double[] distance = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distance, INFINITY);
        distance[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = method6(distance, visited);
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
     * @param distance tentative distances
     * @param visited  visited flags
     * @return index of the vertex with minimum distance
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