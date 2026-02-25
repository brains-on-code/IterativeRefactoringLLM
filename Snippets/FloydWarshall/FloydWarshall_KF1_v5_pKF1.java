package com.thealgorithms.datastructures.graphs;

/**
 * Implementation of the Floyd–Warshall algorithm for computing
 * all-pairs shortest paths in a weighted graph.
 */
public class FloydWarshall {

    private final int[][] shortestPathMatrix;
    private final int numberOfVertices;
    public static final int INFINITY = 999;

    /**
     * Constructs a FloydWarshall instance for a graph with the given number of vertices.
     *
     * @param numberOfVertices the number of vertices in the graph
     */
    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.shortestPathMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

    /**
     * Computes all-pairs shortest paths using the Floyd–Warshall algorithm.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.arraycopy(
                adjacencyMatrix[sourceVertex],
                1,
                shortestPathMatrix[sourceVertex],
                1,
                numberOfVertices
            );
        }

        for (int intermediateVertex = 1; intermediateVertex <= numberOfVertices; intermediateVertex++) {
            for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
                for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                    int candidateDistance =
                        shortestPathMatrix[sourceVertex][intermediateVertex]
                            + shortestPathMatrix[intermediateVertex][destinationVertex];

                    if (candidateDistance < shortestPathMatrix[sourceVertex][destinationVertex]) {
                        shortestPathMatrix[sourceVertex][destinationVertex] = candidateDistance;
                    }
                }
            }
        }

        printShortestPathMatrix();
    }

    /**
     * Prints the shortest path matrix to standard output.
     */
    private void printShortestPathMatrix() {
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.out.print(sourceVertex + "\t");
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                System.out.print(shortestPathMatrix[sourceVertex][destinationVertex] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getShortestPathDistances() {
        return shortestPathMatrix;
    }
}