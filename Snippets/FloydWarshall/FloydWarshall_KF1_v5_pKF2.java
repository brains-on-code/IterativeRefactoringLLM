package com.thealgorithms.datastructures.graphs;

/**
 * Floyd–Warshall algorithm for computing all-pairs shortest paths on a weighted graph
 * represented by a 1-indexed adjacency matrix.
 */
public class FloydWarshall {

    /** Representation of "infinity" for unreachable vertices. */
    public static final int INF = 999;

    /** All-pairs shortest-path distance matrix (1-indexed). */
    private final int[][] distance;

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /**
     * Constructs a Floyd–Warshall solver for a graph with the given number of vertices.
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
     * The input matrix must be 1-indexed and of size (vertexCount + 1) x (vertexCount + 1).
     *
     * @param adjacencyMatrix 1-indexed adjacency matrix of the graph
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        copyAdjacencyToDistance(adjacencyMatrix);
        runFloydWarshall();
        printDistanceMatrix();
    }

    /**
     * Returns the computed all-pairs shortest-path distance matrix.
     *
     * @return 1-indexed distance matrix
     */
    public int[][] getDistanceMatrix() {
        return distance;
    }

    /**
     * Copies the input adjacency matrix into the internal distance matrix.
     *
     * @param adjacencyMatrix 1-indexed adjacency matrix of the graph
     */
    private void copyAdjacencyToDistance(int[][] adjacencyMatrix) {
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distance[i], 1, vertexCount);
        }
    }

    /**
     * Core Floyd–Warshall dynamic programming loop.
     *
     * Updates the distance matrix with the shortest paths between all pairs of vertices.
     */
    private void runFloydWarshall() {
        for (int k = 1; k <= vertexCount; k++) {
            for (int i = 1; i <= vertexCount; i++) {
                for (int j = 1; j <= vertexCount; j++) {
                    int pathThroughK = distance[i][k] + distance[k][j];
                    if (pathThroughK < distance[i][j]) {
                        distance[i][j] = pathThroughK;
                    }
                }
            }
        }
    }

    /** Prints the current distance matrix in a tabular form. */
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