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

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private JohnsonsAlgorithm() {
    }

    /**
     * Executes Johnson's algorithm on the given graph.
     * Steps:
     * 1. Add a new vertex to the graph and run Bellman-Ford to compute modified weights
     * 2. Reweight the graph using the modified weights
     * 3. Run Dijkstra's algorithm for each vertex to compute the shortest paths
     * The final result is a 2D array of shortest distances between all pairs of vertices.
     *
     * @param adjacencyMatrix The input graph represented as an adjacency matrix.
     * @return A 2D array representing the shortest distances between all pairs of vertices.
     */
    public static double[][] johnsonAlgorithm(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        double[][] edgeList = convertToEdgeList(adjacencyMatrix);

        double[] vertexPotentials = bellmanFord(edgeList, vertexCount);

        double[][] reweightedGraph = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[vertexCount][vertexCount];
        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            allPairsShortestPaths[sourceVertex] = dijkstra(reweightedGraph, sourceVertex, vertexPotentials);
        }

        return allPairsShortestPaths;
    }

    /**
     * Converts the adjacency matrix representation of the graph to an edge list.
     *
     * @param adjacencyMatrix The input graph as an adjacency matrix.
     * @return An array of edges, where each edge is represented as [from, to, weight].
     */
    public static double[][] convertToEdgeList(double[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        List<double[]> edgeList = new ArrayList<>();

        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < vertexCount; targetVertex++) {
                if (sourceVertex != targetVertex && !Double.isInfinite(adjacencyMatrix[sourceVertex][targetVertex])) {
                    // Only add edges that are not self-loops and have a finite weight
                    edgeList.add(new double[] {sourceVertex, targetVertex, adjacencyMatrix[sourceVertex][targetVertex]});
                }
            }
        }

        return edgeList.toArray(new double[0][]);
    }

    /**
     * Implements the Bellman-Ford algorithm to compute the shortest paths from a new vertex
     * to all other vertices. This is used to calculate the weight function h(v) for reweighting.
     *
     * @param edgeList The edge list of the graph.
     * @param vertexCount The number of vertices in the original graph.
     * @return An array of modified weights (potentials) for each vertex.
     */
    private static double[] bellmanFord(double[][] edgeList, int vertexCount) {
        double[] distances = new double[vertexCount + 1];
        Arrays.fill(distances, INFINITY);
        int superSourceVertex = vertexCount;
        distances[superSourceVertex] = 0;

        // Add edges from the new vertex to all original vertices
        double[][] extendedEdgeList = Arrays.copyOf(edgeList, edgeList.length + vertexCount);
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            extendedEdgeList[edgeList.length + vertex] = new double[] {superSourceVertex, vertex, 0};
        }

        // Relax all edges V times
        for (int iteration = 0; iteration < vertexCount; iteration++) {
            for (double[] edge : extendedEdgeList) {
                int sourceVertex = (int) edge[0];
                int targetVertex = (int) edge[1];
                double weight = edge[2];
                if (distances[sourceVertex] != INFINITY && distances[sourceVertex] + weight < distances[targetVertex]) {
                    distances[targetVertex] = distances[sourceVertex] + weight;
                }
            }
        }

        // Check for negative weight cycles
        for (double[] edge : extendedEdgeList) {
            int sourceVertex = (int) edge[0];
            int targetVertex = (int) edge[1];
            double weight = edge[2];
            if (distances[sourceVertex] + weight < distances[targetVertex]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distances, vertexCount);
    }

    /**
     * Reweights the graph using the modified weights computed by Bellman-Ford.
     *
     * @param adjacencyMatrix The original graph.
     * @param vertexPotentials The modified weights (potentials) from Bellman-Ford.
     * @return The reweighted graph.
     */
    public static double[][] reweightGraph(double[][] adjacencyMatrix, double[] vertexPotentials) {
        int vertexCount = adjacencyMatrix.length;
        double[][] reweightedGraph = new double[vertexCount][vertexCount];

        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < vertexCount; targetVertex++) {
                if (adjacencyMatrix[sourceVertex][targetVertex] != 0) {
                    // New weight = original weight + h(u) - h(v)
                    reweightedGraph[sourceVertex][targetVertex] =
                        adjacencyMatrix[sourceVertex][targetVertex]
                            + vertexPotentials[sourceVertex]
                            - vertexPotentials[targetVertex];
                }
            }
        }

        return reweightedGraph;
    }

    /**
     * Implements Dijkstra's algorithm for finding shortest paths from a source vertex.
     *
     * @param reweightedGraph The reweighted graph to run Dijkstra's on.
     * @param sourceVertex The source vertex.
     * @param vertexPotentials The modified weights (potentials) from Bellman-Ford.
     * @return An array of shortest distances from the source to all other vertices.
     */
    public static double[] dijkstra(double[][] reweightedGraph, int sourceVertex, double[] vertexPotentials) {
        int vertexCount = reweightedGraph.length;
        double[] distances = new double[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Arrays.fill(distances, INFINITY);
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int nearestVertex = findMinDistanceVertex(distances, visited);
            visited[nearestVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                if (!visited[neighborVertex]
                    && reweightedGraph[nearestVertex][neighborVertex] != 0
                    && distances[nearestVertex] != INFINITY
                    && distances[nearestVertex] + reweightedGraph[nearestVertex][neighborVertex]
                        < distances[neighborVertex]) {
                    distances[neighborVertex] =
                        distances[nearestVertex] + reweightedGraph[nearestVertex][neighborVertex];
                }
            }
        }

        // Adjust distances back to the original graph weights
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (distances[vertex] != INFINITY) {
                distances[vertex] = distances[vertex] - vertexPotentials[sourceVertex] + vertexPotentials[vertex];
            }
        }

        return distances;
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices
     * not yet included in the shortest path tree.
     *
     * @param distances Array of distances.
     * @param visited Array of visited vertices.
     * @return The index of the vertex with minimum distance.
     */
    public static int findMinDistanceVertex(double[] distances, boolean[] visited) {
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