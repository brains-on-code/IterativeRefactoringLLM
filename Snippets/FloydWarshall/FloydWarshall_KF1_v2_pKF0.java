package com.thealgorithms.datastructures.graphs;

/**
 * Represents a graph and computes all-pairs shortest paths using the Floyd–Warshall algorithm.
 */
public class FloydWarshall {

    /** Adjacency matrix / distance matrix (1-based indexing is used). */
    private final int[][] distanceMatrix;

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** A large value representing "infinity" (no direct edge). */
    public static final int INFINITY = 999;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public FloydWarshall(int vertexCount) {
        this.vertexCount = vertexCount;
        this.distanceMatrix = new int[vertexCount + 1][vertexCount + 1];
    }

    /**
     * Runs the Floyd–Warshall algorithm on the given adjacency matrix and
     * stores the resulting all-pairs shortest path distances internally.
     *
     * @param adjacencyMatrix the input adjacency matrix (1-based indexing)
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        copyAdjacencyMatrix(adjacencyMatrix);
        runFloydWarshall();
        printDistanceMatrix();
    }

    /**
     * Returns the internal distance matrix.
     *
     * @return the distance matrix
     */
    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /** Copies the input adjacency matrix into the internal distance matrix. */
    private void copyAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distanceMatrix[i], 1, vertexCount);
        }
    }

    /** Executes the Floyd–Warshall dynamic programming algorithm. */
    private void runFloydWarshall() {
        for (int k = 1; k <= vertexCount; k++) {
            for (int i = 1; i <= vertexCount; i++) {
                for (int j = 1; j <= vertexCount; j++) {
                    int throughK = distanceMatrix[i][k] + distanceMatrix[k][j];
                    if (throughK < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = throughK;
                    }
                }
            }
        }
    }

    /**
     * Prints the current distance matrix to standard output.
     */
    private void printDistanceMatrix() {
        // Header row
        System.out.print("\t");
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        // Matrix rows
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print(i + "\t");
            for (int j = 1; j <= vertexCount; j++) {
                System.out.print(distanceMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}