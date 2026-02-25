package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    public static final int INFINITY = 999;

    private final int[][] distanceMatrix;
    private final int numberOfVertices;

    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

    /**
     * Runs the Floyd–Warshall all-pairs shortest path algorithm.
     * The input matrix is assumed to be 1-indexed (1..numberOfVertices).
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     */
    public void floydWarshall(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        computeAllPairsShortestPaths();
        printDistanceMatrix();
    }

    /**
     * Copies the given adjacency matrix into the internal distance matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
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
     * Core Floyd–Warshall dynamic programming routine.
     * Updates distanceMatrix with the shortest path distances between all pairs.
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
     * Prints the distance matrix in a tabular form.
     */
    private void printDistanceMatrix() {
        printHeaderRow();
        printRows();
    }

    /**
     * Prints the header row containing vertex labels.
     */
    private void printHeaderRow() {
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();
    }

    /**
     * Prints each row of the distance matrix.
     */
    private void printRows() {
        for (int source = 1; source <= numberOfVertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberOfVertices; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}