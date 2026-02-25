package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class JohnsonsAlgorithm {

    private static final double INF = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
    }


    public static double[][] johnsonAlgorithm(double[][] graph) {
        int numVertices = graph.length;
        double[][] edges = convertToEdgeList(graph);

        double[] modifiedWeights = bellmanFord(edges, numVertices);

        double[][] reweightedGraph = reweightGraph(graph, modifiedWeights);

        double[][] shortestDistances = new double[numVertices][numVertices];
        for (int source = 0; source < numVertices; source++) {
            shortestDistances[source] = dijkstra(reweightedGraph, source, modifiedWeights);
        }

        return shortestDistances;
    }


    public static double[][] convertToEdgeList(double[][] graph) {
        int numVertices = graph.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i != j && !Double.isInfinite(graph[i][j])) {
                    edgeList.add(new double[] {i, j, graph[i][j]});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }


    private static double[] bellmanFord(double[][] edges, int numVertices) {
        double[] dist = new double[numVertices + 1];
        Arrays.fill(dist, INF);
        dist[numVertices] = 0;

        double[][] allEdges = Arrays.copyOf(edges, edges.length + numVertices);
        for (int i = 0; i < numVertices; i++) {
            allEdges[edges.length + i] = new double[] {numVertices, i, 0};
        }

        for (int i = 0; i < numVertices; i++) {
            for (double[] edge : allEdges) {
                int u = (int) edge[0];
                int v = (int) edge[1];
                double weight = edge[2];
                if (dist[u] != INF && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }

        for (double[] edge : allEdges) {
            int u = (int) edge[0];
            int v = (int) edge[1];
            double weight = edge[2];
            if (dist[u] + weight < dist[v]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(dist, numVertices);
    }


    public static double[][] reweightGraph(double[][] graph, double[] modifiedWeights) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (graph[i][j] != 0) {
                    reweightedGraph[i][j] = graph[i][j] + modifiedWeights[i] - modifiedWeights[j];
                }
            }
        }

        return reweightedGraph;
    }


    public static double[] dijkstra(double[][] reweightedGraph, int source, double[] modifiedWeights) {
        int numVertices = reweightedGraph.length;
        double[] dist = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        for (int count = 0; count < numVertices - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && reweightedGraph[u][v] != 0 && dist[u] != INF && dist[u] + reweightedGraph[u][v] < dist[v]) {
                    dist[v] = dist[u] + reweightedGraph[u][v];
                }
            }
        }

        for (int i = 0; i < numVertices; i++) {
            if (dist[i] != INF) {
                dist[i] = dist[i] - modifiedWeights[source] + modifiedWeights[i];
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
