package com.thealgorithms.datastructures.graphs;

/**
 * Represents a graph and computes all-pairs shortest paths using
 * the Floyd–Warshall algorithm.
 */
public class FloydWarshall {

    /** Adjacency / distance matrix (1-based indexing is used). */
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
        initializeDistanceMatrix(adjacencyMatrix);
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

    /**
     * Copies the input adjacency matrix into the internal distance matrix.
     *
     * @param adjacencyMatrix the input adjacency matrix
     */
    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int source = 1; source <= vertexCount; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                distanceMatrix[source],
                1,
                vertexCount
            );
        }
    }

    /** Executes the Floyd–Warshall dynamic programming algorithm. */
    private void runFloydWarshall() {
        for (int intermediate = 1; intermediate <= vertexCount; intermediate++) {
            for (int source = 1; source <= vertexCount; source++) {
                for (int destination = 1; destination <= vertexCount; destination++) {
                    int distanceThroughIntermediate =
                        distanceMatrix[source][intermediate]
                            + distanceMatrix[intermediate][destination];

                    if (distanceThroughIntermediate < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = distanceThroughIntermediate;
                    }
                }
            }
        }
    }

    /** Prints the current distance matrix to standard output. */
    private void printDistanceMatrix() {
        printHeaderRow();
        printMatrixRows();
    }

    /** Prints the header row of the distance matrix. */
    private void printHeaderRow() {
        System.out.print("\t");
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            System.out.print(vertex + "\t");
        }
        System.out.println();
    }

    /** Prints all rows of the distance matrix. */
    private void printMatrixRows() {
        for (int source = 1; source <= vertexCount; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= vertexCount; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }
}