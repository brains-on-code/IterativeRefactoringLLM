package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JohnsonsAlgorithm {

    private static final double INF = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
        // Utility class
    }

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

    public static double[][] convertToEdgeList(double[][] graph) {
        int numVertices = graph.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                double weight = graph[from][to];
                if (from != to && !Double.isInfinite(weight)) {
                    edgeList.add(new double[] {from, to, weight});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    private static double[] bellmanFord(double[][] edges, int numVertices) {
        double[] dist = new double[numVertices + 1];
        Arrays.fill(dist, INF);
        int superSource = numVertices;
        dist[superSource] = 0;

        double[][] allEdges = Arrays.copyOf(edges, edges.length + numVertices);
        for (int vertex = 0; vertex < numVertices; vertex++) {
            allEdges[edges.length + vertex] = new double[] {superSource, vertex, 0};
        }

        for (int i = 0; i < numVertices; i++) {
            for (double[] edge : allEdges) {
                int from = (int) edge[0];
                int to = (int) edge[1];
                double weight = edge[2];

                if (dist[from] != INF && dist[from] + weight < dist[to]) {
                    dist[to] = dist[from] + weight;
                }
            }
        }

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

    public static double[][] reweightGraph(double[][] graph, double[] vertexPotentials) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                double weight = graph[from][to];
                if (weight != 0 && !Double.isInfinite(weight)) {
                    reweightedGraph[from][to] =
                        weight + vertexPotentials[from] - vertexPotentials[to];
                } else {
                    reweightedGraph[from][to] = weight;
                }
            }
        }

        return reweightedGraph;
    }

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
                if (!visited[v]
                    && weight != 0
                    && !Double.isInfinite(weight)
                    && dist[u] != INF
                    && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }

        for (int v = 0; v < numVertices; v++) {
            if (dist[v] != INF) {
                dist[v] = dist[v] - vertexPotentials[source] + vertexPotentials[v];
            }
        }

        return dist;
    }

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