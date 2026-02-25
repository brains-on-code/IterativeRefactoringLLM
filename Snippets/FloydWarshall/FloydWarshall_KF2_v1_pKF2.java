package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    private final int[][] distanceMatrix;
    private final int numberOfVertices;
    public static final int INFINITY = 999;

    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

    /**
     * Runs the Floyd–Warshall algorithm on the given adjacency matrix.
     * The matrix is assumed to be 1-indexed (indices 1..numberOfVertices).
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     */
    public void floydWarshall(int[][] adjacencyMatrix) {
        // Initialize distance matrix with the given adjacency matrix
        for (int source = 1; source <= numberOfVertices; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                distanceMatrix[source],
                1,
                numberOfVertices
            );
        }

        // Update distances considering each vertex as an intermediate point
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

        printDistanceMatrix();
    }

    /**
     * Prints the distance matrix in a tabular form.
     */
    private void printDistanceMatrix() {
        // Header row
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        // Matrix rows
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