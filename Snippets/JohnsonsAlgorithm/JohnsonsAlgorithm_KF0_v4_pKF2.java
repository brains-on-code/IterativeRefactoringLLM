package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Johnson's algorithm for all-pairs shortest paths in a weighted directed graph
 * that may contain negative edge weights (but no negative cycles).
 *
 * Steps:
 * 1. Run Bellman-Ford from a new super-source to compute vertex potentials h(v).
 * 2. Reweight all edges so that all edge weights become non-negative.
 * 3. Run Dijkstra from each vertex on the reweighted graph.
 * 4. Convert distances back to original weights using the potentials.
 *
 * Time Complexity: O(V^2 * log(V) + V * E)
 * Space Complexity: O(V^2)
 *
 * V = number of vertices, E = number of edges.
 *
 * See: https://en.wikipedia.org/wiki/Johnson%27s_algorithm
 */
public final class JohnsonsAlgorithm {

    private static final double INF = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
        // Prevent instantiation
    }

    /**
     * Executes Johnson's algorithm on the given graph.
     *
     * @param graph adjacency matrix representation of the graph
     *              graph[u][v] = weight of edge u -> v, or Double.POSITIVE_INFINITY if no edge
     * @return matrix dist where dist[u][v] is the shortest distance from u to v
     */
    public static double[][] johnsonAlgorithm(double[][] graph) {
        int numVertices = graph.length;

        double[][] edges = convertToEdgeList(graph);
        double[] potentials = bellmanFord(edges, numVertices);
        double[][] reweightedGraph = reweightGraph(graph, potentials);

        double[][] shortestDistances = new double[numVertices][numVertices];
        for (int source = 0; source < numVertices; source++) {
            shortestDistances[source] = dijkstra(reweightedGraph, source, potentials);
        }

        return shortestDistances;
    }

    /**
     * Converts an adjacency matrix to an edge list.
     *
     * @param graph adjacency matrix
     * @return array of edges, each edge as [from, to, weight]
     */
    public static double[][] convertToEdgeList(double[][] graph) {
        int numVertices = graph.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                if (from == to) {
                    continue;
                }
                double weight = graph[from][to];
                if (!Double.isInfinite(weight)) {
                    edgeList.add(new double[] {from, to, weight});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    /**
     * Bellman-Ford from a new super-source connected to all vertices with 0-weight edges.
     * Computes vertex potentials h(v) used for reweighting.
     *
     * @param edges       edge list of the original graph
     * @param numVertices number of vertices in the original graph
     * @return array h where h[v] is the potential of vertex v
     */
    private static double[] bellmanFord(double[][] edges, int numVertices) {
        int superSource = numVertices;
        double[] dist = new double[numVertices + 1];
        Arrays.fill(dist, INF);
        dist[superSource] = 0;

        double[][] allEdges = Arrays.copyOf(edges, edges.length + numVertices);
        for (int v = 0; v < numVertices; v++) {
            allEdges[edges.length + v] = new double[] {superSource, v, 0};
        }

        // Relax all edges |V| times
        for (int i = 0; i < numVertices; i++) {
            for (double[] edge : allEdges) {
                int from = (int) edge[0];
                int to = (int) edge[1];
                double weight = edge[2];

                if (dist[from] == INF) {
                    continue;
                }

                double candidate = dist[from] + weight;
                if (candidate < dist[to]) {
                    dist[to] = candidate;
                }
            }
        }

        // Detect negative cycles
        for (double[] edge : allEdges) {
            int from = (int) edge[0];
            int to = (int) edge[1];
            double weight = edge[2];

            if (dist[from] != INF && dist[from] + weight < dist[to]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(dist, numVertices);
    }

    /**
     * Reweights edges using potentials h so that all edge weights become non-negative:
     * w'(u, v) = w(u, v) + h(u) - h(v)
     *
     * @param graph      original adjacency matrix
     * @param potentials vertex potentials h from Bellman-Ford
     * @return adjacency matrix of the reweighted graph
     */
    public static double[][] reweightGraph(double[][] graph, double[] potentials) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                double weight = graph[from][to];

                if (Double.isInfinite(weight)) {
                    reweightedGraph[from][to] = INF;
                    continue;
                }

                if (from == to) {
                    reweightedGraph[from][to] = 0;
                    continue;
                }

                reweightedGraph[from][to] = weight + potentials[from] - potentials[to];
            }
        }

        return reweightedGraph;
    }

    /**
     * Dijkstra's algorithm on the reweighted graph from a single source.
     * Returns distances adjusted back to original weights.
     *
     * @param reweightedGraph adjacency matrix of the reweighted graph
     * @param source          source vertex
     * @param potentials      vertex potentials h from Bellman-Ford
     * @return dist array where dist[v] is the shortest distance from source to v
     */
    public static double[] dijkstra(double[][] reweightedGraph, int source, double[] potentials) {
        int numVertices = reweightedGraph.length;
        double[] dist = new double[numVertices];
        boolean[] visited = new boolean[numVertices];

        Arrays.fill(dist, INF);
        dist[source] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int u = minDistance(dist, visited);
            if (u == -1) {
                break;
            }
            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {
                double weight = reweightedGraph[u][v];

                if (visited[v] || Double.isInfinite(weight) || dist[u] == INF) {
                    continue;
                }

                double candidate = dist[u] + weight;
                if (candidate < dist[v]) {
                    dist[v] = candidate;
                }
            }
        }

        // Convert distances back to original weights
        for (int v = 0; v < numVertices; v++) {
            if (dist[v] != INF) {
                dist[v] = dist[v] - potentials[source] + potentials[v];
            }
        }

        return dist;
    }

    /**
     * Returns the index of the unvisited vertex with the smallest distance.
     *
     * @param dist    distance array
     * @param visited visited flags
     * @return index of vertex with minimum distance, or -1 if none
     */
    public static int minDistance(double[] dist, boolean[] visited) {
        double min = INF;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
}