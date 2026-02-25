package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    private int[][] shortestPathDistances;
    private int vertexCount;
    public static final int INFINITY = 999;

    public FloydWarshall(int vertexCount) {
        this.shortestPathDistances = new int[vertexCount + 1][vertexCount + 1];
        this.vertexCount = vertexCount;
    }

    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        runFloydWarshallAlgorithm();
        printDistanceMatrix();
    }

    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int source = 1; source <= vertexCount; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                shortestPathDistances[source],
                1,
                vertexCount
            );
        }
    }

    private void runFloydWarshallAlgorithm() {
        for (int intermediate = 1; intermediate <= vertexCount; intermediate++) {
            for (int source = 1; source <= vertexCount; source++) {
                for (int destination = 1; destination <= vertexCount; destination++) {
                    int candidateDistance =
                        shortestPathDistances[source][intermediate]
                            + shortestPathDistances[intermediate][destination];

                    if (candidateDistance < shortestPathDistances[source][destination]) {
                        shortestPathDistances[source][destination] = candidateDistance;
                    }
                }
            }
        }
    }

    private void printDistanceMatrix() {
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        for (int source = 1; source <= vertexCount; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= vertexCount; destination++) {
                System.out.print(shortestPathDistances[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return shortestPathDistances;
    }
}