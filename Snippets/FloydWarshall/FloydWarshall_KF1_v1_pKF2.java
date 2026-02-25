package com.thealgorithms.datastructures.graphs;

/**
 * Implementation of the Floyd–Warshall algorithm for all-pairs shortest paths
 * on a weighted graph represented by an adjacency matrix.
 *
 * The graph is assumed to be 1-indexed (vertices from 1 to n).
 */
public class Class1 {

    /** Distance matrix (1-indexed). */
    private int[][] distance;

    /** Number of vertices in the graph. */
    private int vertexCount;

    /** Representation of "infinity" for unreachable vertices. */
    public static final int INF = 999;

    /**
     * Creates a Floyd–Warshall solver for a graph with the given number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    public Class1(int vertexCount) {
        this.distance = new int[vertexCount + 1][vertexCount + 1];
        this.vertexCount = vertexCount;
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
    public void method1(int[][] adjacencyMatrix) {
        // Copy input adjacency matrix into the internal distance matrix
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distance[i], 1, vertexCount);
        }

        // Core Floyd–Warshall dynamic programming triple loop
        for (int k = 1; k <= vertexCount; k++) {
            for (int i = 1; i <= vertexCount; i++) {
                for (int j = 1; j <= vertexCount; j++) {
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                    }
                }
            }
        }

        printDistanceMatrix();
    }

    /**
     * Prints the current distance matrix in a tabular form.
     */
    private void printDistanceMatrix() {
        // Print column headers
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();

        // Print each row with its row header
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print(i + "\t");
            for (int j = 1; j <= vertexCount; j++) {
                System.out.print(distance[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Returns the computed all-pairs shortest-path distance matrix.
     *
     * @return the distance matrix
     */
    public int[][] method3() {
        return distance;
    }
}