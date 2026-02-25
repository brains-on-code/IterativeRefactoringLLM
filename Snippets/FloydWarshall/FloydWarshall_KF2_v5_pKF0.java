package com.thealgorithms.datastructures.graphs;

public class FloydWarshall {

    public static final int INFINITY = 999;

    private final int[][] distanceMatrix;
    private final int vertexCount;

    public FloydWarshall(int vertexCount) {
        this.vertexCount = vertexCount;
        this.distanceMatrix = new int[vertexCount + 1][vertexCount + 1];
    }

    public void floydWarshall(int[][] adjacencyMatrix) {
        initializeDistanceMatrix(adjacencyMatrix);
        computeAllPairsShortestPaths();
        printDistanceMatrix();
    }

    private void initializeDistanceMatrix(int[][] adjacencyMatrix) {
        for (int sourceVertex = 1; sourceVertex <= vertexCount; sourceVertex++) {
            System.arraycopy(
                adjacencyMatrix[sourceVertex],
                1,
                distanceMatrix[sourceVertex],
                1,
                vertexCount
            );
        }
    }

    private void computeAllPairsShortestPaths() {
        for (int intermediateVertex = 1; intermediateVertex <= vertexCount; intermediateVertex++) {
            for (int sourceVertex = 1; sourceVertex <= vertexCount; sourceVertex++) {
                for (int destinationVertex = 1; destinationVertex <= vertexCount; destinationVertex++) {
                    int newDistance =
                        distanceMatrix[sourceVertex][intermediateVertex]
                            + distanceMatrix[intermediateVertex][destinationVertex];

                    if (newDistance < distanceMatrix[sourceVertex][destinationVertex]) {
                        distanceMatrix[sourceVertex][destinationVertex] = newDistance;
                    }
                }
            }
        }
    }

    private void printDistanceMatrix() {
        printHeaderRow();
        printMatrixRows();
    }

    private void printHeaderRow() {
        System.out.print("\t");
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            System.out.print(vertex + "\t");
        }
        System.out.println();
    }

    private void printMatrixRows() {
        for (int sourceVertex = 1; sourceVertex <= vertexCount; sourceVertex++) {
            System.out.print(sourceVertex + "\t");
            for (int destinationVertex = 1; destinationVertex <= vertexCount; destinationVertex++) {
                System.out.print(distanceMatrix[sourceVertex][destinationVertex] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}