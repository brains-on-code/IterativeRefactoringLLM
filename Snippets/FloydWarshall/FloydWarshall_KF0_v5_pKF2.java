package com.thealgorithms.datastructures.graphs;

/**
 * Floyd–Warshall algorithm for all-pairs shortest paths in a weighted graph.
 *
 * <p>Supports negative edge weights but not negative cycles.</p>
 *
 * <p>Time complexity: O(V^3), where V is the number of vertices.</p>
 *
 * <p>Reference:
 * <a href="https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm">
 * Floyd–Warshall Algorithm</a></p>
 */
public class FloydWarshall {

    public static final int INFINITY = 999;

    private final int[][] distanceMatrix;
    private final int numberOfVertices;

    /**
     * Constructs a FloydWarshall instance for a graph with the given number of vertices.
     *
     * @param numberOfVertices number of vertices in the graph
     */
    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

    /**
     * Executes the Floyd–Warshall algorithm on the given adjacency matrix.
     *
     * <p>The matrix is assumed to be 1-indexed:
     * indices 1..numberOfVertices are used, and index 0 is ignored.</p>
     *
     * <p>A value of 0 indicates no direct edge between two distinct vertices.
     * Diagonal entries (i == j) should be 0.</p>
     *
     * @param adjacencyMatrix weighted adjacency matrix of the graph
     */
    public void floydWarshall(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        computeAllPairsShortestPaths();
        printDistanceMatrix();
    }

    /**
     * Copies the given adjacency matrix into the internal distance matrix.
     *
     * @param adjacencyMatrix weighted adjacency matrix of the graph
     */
    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int source = 1; source <= numberOfVertices; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                distanceMatrix[source],
                1,
                numberOfVertices
            );
        }
    }

    /**
     * Computes all-pairs shortest paths using dynamic programming.
     *
     * <p>Each vertex is considered as an intermediate vertex between all pairs
     * of vertices, and distances are relaxed accordingly.</p>
     */
    private void computeAllPairsShortestPaths() {
        for (int intermediate = 1; intermediate <= numberOfVertices; intermediate++) {
            for (int source = 1; source <= numberOfVertices; source++) {
                for (int destination = 1; destination <= numberOfVertices; destination++) {
                    int throughIntermediate =
                        distanceMatrix[source][intermediate]
                            + distanceMatrix[intermediate][destination];

                    if (throughIntermediate < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = throughIntermediate;
                    }
                }
            }
        }
    }

    /**
     * Prints the distance matrix of shortest paths between all pairs of vertices.
     */
    private void printDistanceMatrix() {
        System.out.print("\t");
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print(vertex + "\t");
        }
        System.out.println();

        for (int source = 1; source <= numberOfVertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberOfVertices; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Returns the computed distance matrix.
     *
     * @return 2D array of shortest path distances
     */
    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}