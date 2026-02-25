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
public final class JohnsonAllPairsShortestPaths {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonAllPairsShortestPaths() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes all-pairs shortest paths using Johnson's algorithm.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return matrix of shortest path distances between all pairs of vertices
     */
    public static double[][] computeAllPairsShortestPaths(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;

        double[][] edges = buildEdgeList(adjacencyMatrix);
        double[] vertexPotentials = computeVertexPotentials(edges, vertexCount);
        double[][] reweightedGraph = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsDistances = new double[vertexCount][vertexCount];
        for (int source = 0; source < vertexCount; source++) {
            allPairsDistances[source] =
                dijkstraWithPotentials(reweightedGraph, source, vertexPotentials);
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
    public static double[][] buildEdgeList(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                double weight = adjacencyMatrix[from][to];
                if (from == to || Double.isInfinite(weight)) {
                    continue;
                }
                edgeList.add(new double[] {from, to, weight});
            }
        }

        return edgeList.toArray(new double[0][]);
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
    private static double[] computeVertexPotentials(double[][] edges, int vertexCount) {
        double[] distances = new double[vertexCount + 1];
        Arrays.fill(distances, INFINITY);

        int superSource = vertexCount;
        distances[superSource] = 0;

        double[][] augmentedEdges = Arrays.copyOf(edges, edges.length + vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            augmentedEdges[edges.length + vertex] = new double[] {superSource, vertex, 0};
        }

        relaxEdges(vertexCount, distances, augmentedEdges);
        verifyNoNegativeCycles(distances, augmentedEdges);

        return Arrays.copyOf(distances, vertexCount);
    }

    private static void relaxEdges(int vertexCount, double[] distances, double[][] edges) {
        for (int i = 0; i < vertexCount; i++) {
            for (double[] edge : edges) {
                int from = (int) edge[0];
                int to = (int) edge[1];
                double weight = edge[2];

                if (distances[from] == INFINITY) {
                    continue;
                }

                double candidateDistance = distances[from] + weight;
                if (candidateDistance < distances[to]) {
                    distances[to] = candidateDistance;
                }
            }
        }
    }

    private static void verifyNoNegativeCycles(double[] distances, double[][] edges) {
        for (double[] edge : edges) {
            int from = (int) edge[0];
            int to = (int) edge[1];
            double weight = edge[2];

            if (distances[from] == INFINITY) {
                continue;
            }

            if (distances[from] + weight < distances[to]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }
    }

    /**
     * Reweights the edges of the graph using the vertex potentials so that
     * all edge weights become non-negative.
     *
     * @param adjacencyMatrix original adjacency matrix
     * @param potentials      vertex potentials from Bellman-Ford
     * @return reweighted adjacency matrix
     */
    public static double[][] reweightGraph(double[][] adjacencyMatrix, double[] potentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweighted = new double[vertexCount][vertexCount];

        for (int from = 0; from < vertexCount; from++) {
            for (int to = 0; to < vertexCount; to++) {
                double weight = adjacencyMatrix[from][to];
                if (Double.isInfinite(weight)) {
                    reweighted[from][to] = INFINITY;
                    continue;
                }
                reweighted[from][to] = weight + potentials[from] - potentials[to];
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
    public static double[] dijkstraWithPotentials(
        double[][] reweightedGraph,
        int source,
        double[] potentials
    ) {
        int vertexCount = reweightedGraph.length;
        double[] distances = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, INFINITY);
        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = selectMinDistanceVertex(distances, visited);
            if (currentVertex == -1) {
                break;
            }
            visited[currentVertex] = true;

            relaxNeighbors(reweightedGraph, distances, visited, currentVertex);
        }

        adjustDistancesToOriginalWeights(distances, potentials, source);

        return distances;
    }

    private static void relaxNeighbors(
        double[][] reweightedGraph,
        double[] distances,
        boolean[] visited,
        int currentVertex
    ) {
        int vertexCount = reweightedGraph.length;

        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            double weight = reweightedGraph[currentVertex][neighbor];

            if (visited[neighbor] || Double.isInfinite(weight)) {
                continue;
            }
            if (distances[currentVertex] == INFINITY) {
                continue;
            }

            double newDistance = distances[currentVertex] + weight;
            if (newDistance < distances[neighbor]) {
                distances[neighbor] = newDistance;
            }
        }
    }

    private static void adjustDistancesToOriginalWeights(
        double[] distances,
        double[] potentials,
        int source
    ) {
        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (distances[vertex] == INFINITY) {
                continue;
            }
            distances[vertex] = distances[vertex] - potentials[source] + potentials[vertex];
        }
    }

    /**
     * Selects the unvisited vertex with the smallest tentative distance.
     *
     * @param distances tentative distances
     * @param visited   visited flags
     * @return index of the vertex with minimum distance
     */
    public static int selectMinDistanceVertex(double[] distances, boolean[] visited) {
        double minDistance = INFINITY;
        int minIndex = -1;

        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (visited[vertex]) {
                continue;
            }
            if (distances[vertex] < minDistance) {
                minDistance = distances[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }
}