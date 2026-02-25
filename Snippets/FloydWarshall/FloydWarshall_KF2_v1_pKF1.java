package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    private int[][] distanceMatrix;
    private int numberOfVertices;
    public static final int INFINITY = 999;

    public FloydWarshall(int numberOfVertices) {
        this.distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
        this.numberOfVertices = numberOfVertices;
    }

    public void computeAllPairsShortestPaths(int[][] adjacencyMatrix) {
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.arraycopy(
                adjacencyMatrix[sourceVertex],
                1,
                distanceMatrix[sourceVertex],
                1,
                numberOfVertices
            );
        }

        for (int intermediateVertex = 1; intermediateVertex <= numberOfVertices; intermediateVertex++) {
            for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
                for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                    int newDistance =
                        distanceMatrix[sourceVertex][intermediateVertex]
                            + distanceMatrix[intermediateVertex][destinationVertex];

                    if (newDistance < distanceMatrix[sourceVertex][destinationVertex]) {
                        distanceMatrix[sourceVertex][destinationVertex] = newDistance;
                    }
                }
            }
        }

        printDistanceMatrix();
    }

    private void printDistanceMatrix() {
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            System.out.print("\t" + vertex);
        }
        System.out.println();

        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.out.print(sourceVertex + "\t");
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                System.out.print(distanceMatrix[sourceVertex][destinationVertex] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}