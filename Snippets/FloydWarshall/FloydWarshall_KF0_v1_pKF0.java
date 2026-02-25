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

    public static final int INFINITY = 999;

    private final int[][] distanceMatrix;
    private final int numberOfVertices;

    /**
     * Constructs a Floyd-Warshall instance for a graph with the given number of vertices.
     * Initializes the distance matrix for the graph.
     *
     * @param numberOfVertices The number of vertices in the graph.
     */
    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

    /**
     * Executes the Floyd-Warshall algorithm to compute the shortest path between all pairs of vertices.
     * It uses an adjacency matrix to calculate the distance matrix by considering each vertex as an intermediate point.
     *
     * @param adjacencyMatrix The weighted adjacency matrix representing the graph.
     *                        A value of 0 means no direct edge between the vertices, except for diagonal elements which are 0 (distance to self).
     */
    public void floydWarshall(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        computeAllPairsShortestPaths();
        printDistanceMatrix();
    }

    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int source = 1; source <= numberOfVertices; source++) {
            System.arraycopy(adjacencyMatrix[source], 1, distanceMatrix[source], 1, numberOfVertices);
        }
    }

    private void computeAllPairsShortestPaths() {
        for (int intermediate = 1; intermediate <= numberOfVertices; intermediate++) {
            for (int source = 1; source <= numberOfVertices; source++) {
                for (int destination = 1; destination <= numberOfVertices; destination++) {
                    int newDistance =
                        distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination];
                    if (newDistance < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = newDistance;
                    }
                }
            }
        }
    }

    /**
     * Prints the distance matrix representing the shortest paths between all pairs of vertices.
     * The rows and columns correspond to the source and destination vertices.
     */
    private void printDistanceMatrix() {
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
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

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}