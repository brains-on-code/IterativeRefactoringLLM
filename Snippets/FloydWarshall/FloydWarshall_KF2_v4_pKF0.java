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
        for (int source = 1; source <= vertexCount; source++) {
            System.arraycopy(
                adjacencyMatrix[source],
                1,
                distanceMatrix[source],
                1,
                vertexCount
            );
        }
    }

    private void computeAllPairsShortestPaths() {
        for (int via = 1; via <= vertexCount; via++) {
            for (int from = 1; from <= vertexCount; from++) {
                for (int to = 1; to <= vertexCount; to++) {
                    int newDistance = distanceMatrix[from][via] + distanceMatrix[via][to];
                    if (newDistance < distanceMatrix[from][to]) {
                        distanceMatrix[from][to] = newDistance;
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
        for (int from = 1; from <= vertexCount; from++) {
            System.out.print(from + "\t");
            for (int to = 1; to <= vertexCount; to++) {
                System.out.print(distanceMatrix[from][to] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}