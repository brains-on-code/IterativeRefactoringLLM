package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements Johnson's algorithm for finding all-pairs shortest paths in a weighted,
 * directed graph that may contain negative edge weights.
 *
 * Johnson's algorithm works by using the Bellman-Ford algorithm to compute a transformation of the
 * input graph that removes all negative weights, allowing Dijkstra's algorithm to be used for
 * efficient shortest path computations.
 *
 * Time Complexity: O(V^2 * log(V) + V*E)
 * Space Complexity: O(V^2)
 *
 * Where V is the number of vertices and E is the number of edges in the graph.
 *
 * For more information, please visit {@link https://en.wikipedia.org/wiki/Johnson%27s_algorithm}
 */
public final class JohnsonsAlgorithm {

    private static final double INF = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
        // Utility class; prevent instantiation
    }

    /**
     * Executes Johnson's algorithm on the given graph.
     *
     * Steps:
     * 1. Add a new vertex to the graph and run Bellman-Ford to compute modified weights.
     * 2. Reweight the graph using the modified weights.
     * 3. Run Dijkstra's algorithm for each vertex to compute the shortest paths.
     *
     * @param graph The input graph represented as an adjacency matrix.
     * @return A 2D array representing the shortest distances between all pairs of vertices.
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
     * Converts the adjacency matrix representation of the graph to an edge list.
     *
     * @param graph The input graph as an adjacency matrix.
     * @return An array of edges, where each edge is represented as [from, to, weight].
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
     * Implements the Bellman-Ford algorithm to compute the shortest paths from a new vertex
     * to all other vertices. This is used to calculate the weight function h(v) for reweighting.
     *
     * @param edges The edge list of the graph.
     * @param numVertices The number of vertices in the original graph.
     * @return An array of modified weights for each vertex.
     */
    private static double[] bellmanFord(double[][] edges, int numVertices) {
        int extendedVertex = numVertices;
        double[] distances = new double[numVertices + 1];
        Arrays.fill(distances, INF);
        distances[extendedVertex] = 0;

        double[][] allEdges = Arrays.copyOf(edges, edges.length + numVertices);
        for (int vertex = 0; vertex < numVertices; vertex++) {
            allEdges[edges.length + vertex] = new double[] {extendedVertex, vertex, 0};
        }

        relaxEdges(numVertices, distances, allEdges);
        detectNegativeCycle(distances, allEdges);

        return Arrays.copyOf(distances, numVertices);
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

    /**
     * Reweights the graph using the modified weights computed by Bellman-Ford.
     *
     * @param graph The original graph.
     * @param potentials The modified weights (potential function) from Bellman-Ford.
     * @return The reweighted graph.
     */
    public static double[][] reweightGraph(double[][] graph, double[] potentials) {
        int numVertices = graph.length;
        double[][] reweightedGraph = new double[numVertices][numVertices];

        for (int from = 0; from < numVertices; from++) {
            for (int to = 0; to < numVertices; to++) {
                double weight = graph[from][to];
                if (weight != 0 && !Double.isInfinite(weight)) {
                    reweightedGraph[from][to] = weight + potentials[from] - potentials[to];
                } else {
                    reweightedGraph[from][to] = weight;
                }
            }
        }

        return reweightedGraph;
    }

    /**
     * Implements Dijkstra's algorithm for finding shortest paths from a source vertex.
     *
     * @param reweightedGraph The reweighted graph to run Dijkstra's on.
     * @param source The source vertex.
     * @param potentials The modified weights from Bellman-Ford.
     * @return An array of shortest distances from the source to all other vertices.
     */
    public static double[] dijkstra(double[][] reweightedGraph, int source, double[] potentials) {
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

            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                double weight = reweightedGraph[currentVertex][neighbor];
                if (isRelaxationCandidate(visited, distances, currentVertex, neighbor, weight)) {
                    distances[neighbor] = distances[currentVertex] + weight;
                }
            }
        }

        adjustDistancesWithPotentials(distances, source, potentials);

        return distances;
    }

    private static boolean isRelaxationCandidate(
            boolean[] visited, double[] distances, int from, int to, double weight) {
        if (visited[to]) {
            return false;
        }
        if (weight == 0 || Double.isInfinite(weight)) {
            return false;
        }
        if (distances[from] == INF) {
            return false;
        }
        return distances[from] + weight < distances[to];
    }

    private static void adjustDistancesWithPotentials(
            double[] distances, int source, double[] potentials) {
        for (int vertex = 0; vertex < distances.length; vertex++) {
            if (distances[vertex] != INF) {
                distances[vertex] = distances[vertex] - potentials[source] + potentials[vertex];
            }
        }
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices
     * not yet included in the shortest path tree.
     *
     * @param distances Array of distances.
     * @param visited Array of visited vertices.
     * @return The index of the vertex with minimum distance.
     */
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