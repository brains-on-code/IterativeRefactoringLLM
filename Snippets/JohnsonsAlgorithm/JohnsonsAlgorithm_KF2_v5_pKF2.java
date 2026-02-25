package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonsAlgorithm {

    private static final double INF = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes all-pairs shortest paths using Johnson's algorithm.
     *
     * @param graph adjacency matrix where graph[u][v] is the edge weight from u to v,
     *              or Double.POSITIVE_INFINITY if there is no edge
     * @return matrix of shortest path distances between all pairs of vertices
     */
    public static double[][] johnsonAlgorithm(double[][] graph) {
        int numVertices = graph.length;

        double[][] edges = convertToEdgeList(graph);
        double[] vertexPotentials = bellmanFord(edges, numVertices);
        double[][] reweightedGraph = reweightGraph(graph, vertexPotentials);

        double[][] shortestDistances = new double[numVertices][numVertices];
        for (int source = 0; source < numVertices; source++) {
            shortestDistances[source] = dijkstra(reweightedGraph, source, vertexPotentials);
        }

        return shortestDistances;
    }

    /**
     * Converts an adjacency matrix to an edge list.
     *
     * @param graph adjacency matrix
     * @return edge list where each edge is represented as [u, v, weight]
     */
    public static double[][] convertToEdgeList(double[][] graph) {
        int numVertices = graph.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                if (u == v) {
                    continue;
                }
                double weight = graph[u][v];
                if (!Double.isInfinite(weight)) {
                    edgeList.add(new double[] {u, v, weight});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    /**
     * Runs Bellman-Ford with a super source to compute vertex potentials.
     *
     * @param edges       edge list [u, v, weight]
     * @param numVertices number of vertices in the original graph
     * @return array of vertex potentials (shortest distances from the super source)
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

        for (int i = 0; i < numVertices; i++) {
            relaxAllEdges(allEdges, dist);
        }

        checkForNegativeCycles(allEdges, dist);

        return Arrays.copyOf(dist, numVertices);
    }

    private static void relaxAllEdges(double[][] edges, double[] dist) {
        for (double[] edge : edges) {
            int u = (int) edge[0];
            int v = (int) edge[1];
            double weight = edge[2];

            if (dist[u] == INF) {
                continue;
            }

            double candidate = dist[u] + weight;
            if (candidate < dist[v]) {
                dist[v] = candidate;
            }
        }
    }

    private static void checkForNegativeCycles(double[][] edges, double[] dist) {
        for (double[] edge : edges) {
            int u = (int) edge[0];
            int v = (int) edge[1];
            double weight = edge[2];

            if (dist[u] != INF && dist[u] + weight < dist[v]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }
    }

    /**
     * Reweights the graph using vertex potentials so that all edge weights are non-negative.
     *
     * @param graph            original adjacency matrix
     * @param vertexPotentials vertex potentials computed by Bellman-Ford
     * @return reweighted adjacency matrix
     */
    public static double[][] reweightGraph(double[][] graph, double[] vertexPotentials) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                double weight = graph[u][v];

                if (Double.isInfinite(weight)) {
                    reweightedGraph[u][v] = INF;
                } else if (u == v) {
                    reweightedGraph[u][v] = 0;
                } else {
                    reweightedGraph[u][v] = weight + vertexPotentials[u] - vertexPotentials[v];
                }
            }
        }

        return reweightedGraph;
    }

    /**
     * Runs Dijkstra's algorithm on the reweighted graph, then restores original distances.
     *
     * @param reweightedGraph  adjacency matrix with non-negative weights
     * @param source           source vertex
     * @param vertexPotentials vertex potentials used for reweighting
     * @return shortest path distances from source to all vertices in the original graph
     */
    public static double[] dijkstra(double[][] reweightedGraph, int source, double[] vertexPotentials) {
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

        restoreOriginalDistances(dist, vertexPotentials, source);

        return dist;
    }

    private static void restoreOriginalDistances(double[] dist, double[] vertexPotentials, int source) {
        for (int v = 0; v < dist.length; v++) {
            if (dist[v] != INF) {
                dist[v] = dist[v] - vertexPotentials[source] + vertexPotentials[v];
            }
        }
    }

    /**
     * Returns the index of the unvisited vertex with the smallest distance.
     *
     * @param dist    current distance estimates
     * @param visited visited flags
     * @return index of the vertex with minimum distance, or -1 if none
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