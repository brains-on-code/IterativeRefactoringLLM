package com.thealgorithms.datastructures.graphs;

/**
 * Implementation of the Floyd–Warshall algorithm for computing
 * all-pairs shortest paths in a weighted graph.
 */
public class FloydWarshall {

    private final int[][] distanceMatrix;
    private final int vertexCount;
    public static final int INFINITY = 999;

    /**
     * Constructs a FloydWarshall instance for a graph with the given number of vertices.
     *
     * @param vertexCount the number of vertices in the graph
     */
    public FloydWarshall(int vertexCount) {
        this.vertexCount = vertexCount;
        this.distanceMatrix = new int[vertexCount + 1][vertexCount + 1];
    }

    /**
     * Computes all-pairs shortest paths using the Floyd–Warshall algorithm.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     */
    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        for (int source = 1; source <= vertexCount; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                distanceMatrix[source],
                1,
                vertexCount
            );
        }

        for (int intermediate = 1; intermediate <= vertexCount; intermediate++) {
            for (int source = 1; source <= vertexCount; source++) {
                for (int destination = 1; destination <= vertexCount; destination++) {
                    int candidateDistance =
                        distanceMatrix[source][intermediate]
                            + distanceMatrix[intermediate][destination];

                    if (candidateDistance < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = candidateDistance;
                    }
                }
            }
        }

        printDistanceMatrix();
    }

    /**
     * Prints the distance matrix to standard output.
     */
    private void printDistanceMatrix() {
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        for (int source = 1; source <= vertexCount; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= vertexCount; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getShortestPathDistances() {
        return distanceMatrix;
    }
}