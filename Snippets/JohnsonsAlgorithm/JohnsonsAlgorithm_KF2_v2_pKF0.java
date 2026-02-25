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
                if (from == to || Double.isInfinite(weight)) {
                    continue;
                }
                edgeList.add(new double[] {from, to, weight});
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    private static double[] bellmanFord(double[][] edges, int numVertices) {
        double[] distances = new double[numVertices + 1];
        Arrays.fill(distances, INF);

        int superSource = numVertices;
        distances[superSource] = 0;

        double[][] allEdges = buildEdgesWithSuperSource(edges, numVertices, superSource);

        relaxEdges(numVertices, distances, allEdges);
        detectNegativeCycle(distances, allEdges);

        return Arrays.copyOf(distances, numVertices);
    }

    private static double[][] buildEdgesWithSuperSource(double[][] edges, int numVertices, int superSource) {
        double[][] allEdges = Arrays.copyOf(edges, edges.length + numVertices);
        for (int vertex = 0; vertex < numVertices; vertex++) {
            allEdges[edges.length + vertex] = new double[] {superSource, vertex, 0};
        }
        return allEdges;
    }

    private static void relaxEdges(int numVertices, double[] distances, double[][] allEdges) {
        for (int i = 0; i < numVertices; i++) {
            for (double[] edge : allEdges) {
                int from = (int) edge[0];
                int to = (int) edge[1];
                double weight = edge[2];

                if (distances[from] == INF) {
                    continue;
                }

                double newDistance = distances[from] + weight;
                if (newDistance < distances[to]) {
                    distances[to] = newDistance;
                }
            }
        }
    }

    private static void detectNegativeCycle(double[] distances, double[][] allEdges) {
        for (double[] edge : allEdges) {
            int from = (int) edge[0];
            int to = (int) edge[1];
            double weight = edge[2];

            if (distances[from] == INF) {
                continue;
            }

            if (distances[from] + weight < distances[to]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }
    }

    public static double[][] reweightGraph(double[][] graph, double[] vertexPotentials) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                double weight = graph[from][to];

                if (weight == 0 || Double.isInfinite(weight)) {
                    reweightedGraph[from][to] = weight;
                    continue;
                }

                reweightedGraph[from][to] =
                    weight + vertexPotentials[from] - vertexPotentials[to];
            }
        }

        return reweightedGraph;
    }

    public static double[] dijkstra(double[][] reweightedGraph, int source, double[] vertexPotentials) {
        int numVertices = reweightedGraph.length;
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];

        Arrays.fill(distances, INF);
        distances[source] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int currentVertex = minDistance(distances, visited);
            if (currentVertex == -1) {
                break;
            }
            visited[currentVertex] = true;

            updateNeighborDistances(reweightedGraph, distances, visited, currentVertex);
        }

        adjustDistancesWithPotentials(distances, vertexPotentials, source);

        return distances;
    }

    private static void updateNeighborDistances(
        double[][] reweightedGraph,
        double[] distances,
        boolean[] visited,
        int currentVertex
    ) {
        int numVertices = reweightedGraph.length;

        for (int neighbor = 0; neighbor < numVertices; neighbor++) {
            double weight = reweightedGraph[currentVertex][neighbor];

            if (visited[neighbor] || weight == 0 || Double.isInfinite(weight)) {
                continue;
            }

            if (distances[currentVertex] == INF) {
                continue;
            }

            double newDistance = distances[currentVertex] + weight;
            if (newDistance < distances[neighbor]) {
                distances[neighbor] = newDistance;
            }
        }
    }

    private static void adjustDistancesWithPotentials(
        double[] distances,
        double[] vertexPotentials,
        int source
    ) {
        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (distances[vertex] == INF) {
                continue;
            }
            distances[vertex] =
                distances[vertex] - vertexPotentials[source] + vertexPotentials[vertex];
        }
    }

    public static int minDistance(double[] distances, boolean[] visited) {
        double minDistance = INF;
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