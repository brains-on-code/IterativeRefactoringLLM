package com.thealgorithms.datastructures.graphs;

/**
 * Implements the Floyd–Warshall algorithm to compute all-pairs shortest paths
 * on a weighted graph represented by an adjacency matrix.
 *
 * The graph is assumed to be 1-indexed (vertices from 1 to n).
 */
public class FloydWarshall {

    /**
     * Representation of "infinity" for unreachable vertices.
     */
    public static final int INF = 999;

    /**
     * Distance matrix (1-indexed).
     */
    private final int[][] distance;

    /**
     * Number of vertices in the graph.
     */
    private final int vertexCount;

    /**
     * Creates a Floyd–Warshall solver for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public FloydWarshall(int vertexCount) {
        this.vertexCount = vertexCount;
        this.distance = new int[vertexCount + 1][vertexCount + 1];
    }

    /**
     * Runs the Floyd–Warshall algorithm on the given adjacency matrix and
     * prints the resulting all-pairs shortest-path distance matrix.
     *
     * The input matrix is expected to be 1-indexed and of size
     * (vertexCount + 1) x (vertexCount + 1).
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        runFloydWarshall();
        printDistanceMatrix();
    }

    /**
     * Returns the computed all-pairs shortest-path distance matrix.
     *
     * @return the distance matrix
     */
    public int[][] getDistanceMatrix() {
        return distance;
    }

    /**
     * Copies the input adjacency matrix into the internal distance matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     */
    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distance[i], 1, vertexCount);
        }
    }

    /**
     * Core Floyd–Warshall dynamic programming triple loop.
     *
     * Updates the distance matrix with the shortest paths between all pairs
     * of vertices.
     */
    private void runFloydWarshall() {
        for (int intermediate = 1; intermediate <= vertexCount; intermediate++) {
            for (int source = 1; source <= vertexCount; source++) {
                for (int target = 1; target <= vertexCount; target++) {
                    int pathThroughIntermediate = distance[source][intermediate] + distance[intermediate][target];
                    if (pathThroughIntermediate < distance[source][target]) {
                        distance[source][target] = pathThroughIntermediate;
                    }
                }
            }
        }
    }

    /**
     * Prints the current distance matrix in a tabular form.
     */
    private void printDistanceMatrix() {
        for (int column = 1; column <= vertexCount; column++) {
            System.out.print("\t" + column);
        }
        System.out.println();

        for (int row = 1; row <= vertexCount; row++) {
            System.out.print(row + "\t");
            for (int column = 1; column <= vertexCount; column++) {
                System.out.print(distance[row][column] + "\t");
            }
            System.out.println();
        }
    }
}