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
     * @param adjacencyMatrix 1-indexed adjacency matrix of the graph
     */
    public void floydWarshall(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        computeAllPairsShortestPaths();
        printDistanceMatrix();
    }

    /**
     * Initializes the distance matrix from the given adjacency matrix.
     *
     * @param adjacencyMatrix 1-indexed adjacency matrix of the graph
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
     * Computes shortest path distances between all vertex pairs
     * using the Floyd–Warshall dynamic programming recurrence.
     */
    private void computeAllPairsShortestPaths() {
        for (int intermediate = 1; intermediate <= numberOfVertices; intermediate++) {
            for (int source = 1; source <= numberOfVertices; source++) {
                for (int destination = 1; destination <= numberOfVertices; destination++) {
                    int candidateDistance =
                        distanceMatrix[source][intermediate]
                            + distanceMatrix[intermediate][destination];

                    if (candidateDistance < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = candidateDistance;
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
        printDataRows();
    }

    /**
     * Prints the header row containing vertex labels.
     */
    private void printHeaderRow() {
        System.out.print("\t");
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print(vertex + "\t");
        }
        System.out.println();
    }

    /**
     * Prints each row of the distance matrix.
     */
    private void printDataRows() {
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