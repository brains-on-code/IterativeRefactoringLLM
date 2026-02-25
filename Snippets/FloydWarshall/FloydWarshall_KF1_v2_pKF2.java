package com.thealgorithms.datastructures.graphs;

/**
 * Floyd–Warshall algorithm for all-pairs shortest paths
 * on a weighted graph represented by an adjacency matrix.
 *
 * The graph is assumed to be 1-indexed (vertices from 1 to n).
 */
public class FloydWarshall {

    /** Distance matrix (1-indexed). */
    private final int[][] distance;

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** Representation of "infinity" for unreachable vertices. */
    public static final int INF = 999;

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
        copyAdjacencyToDistance(adjacencyMatrix);
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

    /** Copies the input adjacency matrix into the internal distance matrix. */
    private void copyAdjacencyToDistance(int[][] adjacencyMatrix) {
        for (int i = 1; i <= vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 1, distance[i], 1, vertexCount);
        }
    }

    /** Core Floyd–Warshall dynamic programming triple loop. */
    private void runFloydWarshall() {
        for (int k = 1; k <= vertexCount; k++) {
            for (int i = 1; i <= vertexCount; i++) {
                for (int j = 1; j <= vertexCount; j++) {
                    int throughK = distance[i][k] + distance[k][j];
                    if (throughK < distance[i][j]) {
                        distance[i][j] = throughK;
                    }
                }
            }
        }
    }

    /** Prints the current distance matrix in a tabular form. */
    private void printDistanceMatrix() {
        for (int i = 1; i <= vertexCount; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();

        for (int i = 1; i <= vertexCount; i++) {
            System.out.print(i + "\t");
            for (int j = 1; j <= vertexCount; j++) {
                System.out.print(distance[i][j] + "\t");
            }
            System.out.println();
        }
    }
}