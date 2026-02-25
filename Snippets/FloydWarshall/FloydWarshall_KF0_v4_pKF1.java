package com.thealgorithms.datastructures.graphs;

/**
 * The {@code FloydWarshall} class provides an implementation of the Floyd-Warshall algorithm
 * to compute the shortest paths between all pairs of vertices in a weighted graph.
 * It handles both positive and negative edge weights but does not support negative cycles.
 * The algorithm is based on dynamic programming and runs in O(V^3) time complexity,
 * where V is the number of vertices in the graph.
 *
 * <p>
 * The distance matrix is updated iteratively to find the shortest distance between any two vertices
 * by considering each vertex as an intermediate step.
 * </p>
 *
 * Reference: <a href="https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm">Floyd-Warshall Algorithm</a>
 */
public class FloydWarshall {

    private final int[][] shortestPathDistances;
    private final int numberOfVertices;
    public static final int INFINITY = 999;

    /**
     * Constructs a Floyd-Warshall instance for a graph with the given number of vertices.
     * Initializes the distance matrix for the graph.
     *
     * @param numberOfVertices The number of vertices in the graph.
     */
    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.shortestPathDistances = new int[numberOfVertices + 1][numberOfVertices + 1];
        // The matrix is initialized with 0's by default
    }

    /**
     * Executes the Floyd-Warshall algorithm to compute the shortest path between all pairs of vertices.
     * It uses an adjacency matrix to calculate the distance matrix by considering each vertex as an intermediate point.
     *
     * @param adjacencyMatrix The weighted adjacency matrix representing the graph.
     *                        A value of 0 means no direct edge between the vertices, except for diagonal elements which are 0 (distance to self).
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        // Initialize the distance matrix with the adjacency matrix.
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.arraycopy(
                adjacencyMatrix[sourceVertex],
                1,
                shortestPathDistances[sourceVertex],
                1,
                numberOfVertices
            );
        }

        for (int intermediateVertex = 1; intermediateVertex <= numberOfVertices; intermediateVertex++) {
            for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
                for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                    int candidateDistance =
                        shortestPathDistances[sourceVertex][intermediateVertex]
                            + shortestPathDistances[intermediateVertex][destinationVertex];

                    if (candidateDistance < shortestPathDistances[sourceVertex][destinationVertex]) {
                        shortestPathDistances[sourceVertex][destinationVertex] = candidateDistance;
                    }
                }
            }
        }

        printDistanceMatrix();
    }

    /**
     * Prints the distance matrix representing the shortest paths between all pairs of vertices.
     * The rows and columns correspond to the source and destination vertices.
     */
    private void printDistanceMatrix() {
        // Print header for vertices
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        // Print the distance matrix
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.out.print(sourceVertex + "\t");
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                System.out.print(shortestPathDistances[sourceVertex][destinationVertex] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return shortestPathDistances;
    }
}