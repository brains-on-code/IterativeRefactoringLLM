package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    public static final int INFINITY = 999;

    private final int[][] distanceMatrix;
    private final int numberOfVertices;

    public FloydWarshall(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
    }

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
                    int newDistance = distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination];
                    if (newDistance < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = newDistance;
                    }
                }
            }
        }
    }

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