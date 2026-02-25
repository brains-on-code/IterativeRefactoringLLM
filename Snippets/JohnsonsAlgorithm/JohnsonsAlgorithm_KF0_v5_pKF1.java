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

        double[][] reweightedAdjacencyMatrix = reweightGraph(adjacencyMatrix, vertexPotentials);

        double[][] allPairsShortestPaths = new double[vertexCount][vertexCount];
        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            allPairsShortestPaths[sourceVertex] =
                dijkstra(reweightedAdjacencyMatrix, sourceVertex, vertexPotentials);
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
                double weight = adjacencyMatrix[sourceVertex][targetVertex];
                if (sourceVertex != targetVertex && !Double.isInfinite(weight)) {
                    edgeList.add(new double[] {sourceVertex, targetVertex, weight});
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
        double[] distancesFromSuperSource = new double[vertexCount + 1];
        Arrays.fill(distancesFromSuperSource, INFINITY);

        int superSourceVertex = vertexCount;
        distancesFromSuperSource[superSourceVertex] = 0;

        double[][] extendedEdgeList = Arrays.copyOf(edgeList, edgeList.length + vertexCount);
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            extendedEdgeList[edgeList.length + vertexIndex] =
                new double[] {superSourceVertex, vertexIndex, 0};
        }

        for (int iteration = 0; iteration < vertexCount; iteration++) {
            for (double[] edge : extendedEdgeList) {
                int sourceVertex = (int) edge[0];
                int targetVertex = (int) edge[1];
                double weight = edge[2];

                if (distancesFromSuperSource[sourceVertex] != INFINITY
                    && distancesFromSuperSource[sourceVertex] + weight
                        < distancesFromSuperSource[targetVertex]) {
                    distancesFromSuperSource[targetVertex] =
                        distancesFromSuperSource[sourceVertex] + weight;
                }
            }
        }

        for (double[] edge : extendedEdgeList) {
            int sourceVertex = (int) edge[0];
            int targetVertex = (int) edge[1];
            double weight = edge[2];

            if (distancesFromSuperSource[sourceVertex] + weight
                < distancesFromSuperSource[targetVertex]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return Arrays.copyOf(distancesFromSuperSource, vertexCount);
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
        double[][] reweightedAdjacencyMatrix = new double[vertexCount][vertexCount];

        for (int sourceVertex = 0; sourceVertex < vertexCount; sourceVertex++) {
            for (int targetVertex = 0; targetVertex < vertexCount; targetVertex++) {
                double originalWeight = adjacencyMatrix[sourceVertex][targetVertex];
                if (originalWeight != 0) {
                    reweightedAdjacencyMatrix[sourceVertex][targetVertex] =
                        originalWeight
                            + vertexPotentials[sourceVertex]
                            - vertexPotentials[targetVertex];
                }
            }
        }

        return reweightedAdjacencyMatrix;
    }

    /**
     * Implements Dijkstra's algorithm for finding shortest paths from a source vertex.
     *
     * @param reweightedAdjacencyMatrix The reweighted graph to run Dijkstra's on.
     * @param sourceVertex The source vertex.
     * @param vertexPotentials The modified weights (potentials) from Bellman-Ford.
     * @return An array of shortest distances from the source to all other vertices.
     */
    public static double[] dijkstra(
        double[][] reweightedAdjacencyMatrix,
        int sourceVertex,
        double[] vertexPotentials
    ) {
        int vertexCount = reweightedAdjacencyMatrix.length;
        double[] shortestDistances = new double[vertexCount];
        boolean[] visitedVertices = new boolean[vertexCount];

        Arrays.fill(shortestDistances, INFINITY);
        shortestDistances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            int currentVertex = findMinDistanceVertex(shortestDistances, visitedVertices);
            visitedVertices[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < vertexCount; neighborVertex++) {
                double edgeWeight = reweightedAdjacencyMatrix[currentVertex][neighborVertex];

                if (!visitedVertices[neighborVertex]
                    && edgeWeight != 0
                    && shortestDistances[currentVertex] != INFINITY
                    && shortestDistances[currentVertex] + edgeWeight
                        < shortestDistances[neighborVertex]) {
                    shortestDistances[neighborVertex] =
                        shortestDistances[currentVertex] + edgeWeight;
                }
            }
        }

        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            if (shortestDistances[vertexIndex] != INFINITY) {
                shortestDistances[vertexIndex] =
                    shortestDistances[vertexIndex]
                        - vertexPotentials[sourceVertex]
                        + vertexPotentials[vertexIndex];
            }
        }

        return shortestDistances;
    }

    /**
     * Finds the vertex with the minimum distance value from the set of vertices
     * not yet included in the shortest path tree.
     *
     * @param distances Array of distances.
     * @param visitedVertices Array of visited vertices.
     * @return The index of the vertex with minimum distance.
     */
    public static int findMinDistanceVertex(double[] distances, boolean[] visitedVertices) {
        double minimumDistance = INFINITY;
        int vertexWithMinimumDistance = -1;

        for (int vertexIndex = 0; vertexIndex < distances.length; vertexIndex++) {
            if (!visitedVertices[vertexIndex] && distances[vertexIndex] <= minimumDistance) {
                minimumDistance = distances[vertexIndex];
                vertexWithMinimumDistance = vertexIndex;
            }
        }

        return vertexWithMinimumDistance;
    }
}