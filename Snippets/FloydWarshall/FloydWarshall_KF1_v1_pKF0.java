package com.thealgorithms.datastructures.graphs;

/**
 * Represents a graph and computes all-pairs shortest paths using the Floyd–Warshall algorithm.
 */
public class Class1 {

    /** Adjacency matrix / distance matrix (1-based indexing is used). */
    private int[][] distanceMatrix;

    /** Number of vertices in the graph. */
    private int vertexCount;

    /** A large value representing "infinity" (no direct edge). */
    public static final int INFINITY = 999;

    /**
     * Creates a new instance for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
        this.distanceMatrix = new int[vertexCount + 1][vertexCount + 1];
    }

    /**
     * Runs the Floyd–Warshall algorithm on the given adjacency matrix and
     * stores the resulting all-pairs shortest path distances internally.
     *
     * @param adjacencyMatrix the input adjacency matrix (1-based indexing)
     */
    public void method1(int[][] adjacencyMatrix) {
        // Copy input matrix into internal distance matrix
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distanceMatrix[i], 1, vertexCount);
        }

        // Floyd–Warshall algorithm
        for (int k = 1; k <= vertexCount; k++) {
            for (int i = 1; i <= vertexCount; i++) {
                for (int j = 1; j <= vertexCount; j++) {
                    if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                    }
                }
            }
        }

        printDistanceMatrix();
    }

    /**
     * Prints the current distance matrix to standard output.
     */
    private void printDistanceMatrix() {
        // Header row
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print("\t" + i);
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

    /**
     * Returns the internal distance matrix.
     *
     * @return the distance matrix
     */
    public int[][] method3() {
        return distanceMatrix;
    }
}