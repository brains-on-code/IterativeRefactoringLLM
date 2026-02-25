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
            System.arraycopy(adjacencyMatrix[source], 1, distanceMatrix[source], 1, vertexCount);
        }
    }

    private void computeAllPairsShortestPaths() {
        for (int intermediate = 1; intermediate <= vertexCount; intermediate++) {
            for (int source = 1; source <= vertexCount; source++) {
                for (int destination = 1; destination <= vertexCount; destination++) {
                    int newDistance =
                        distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination];
                    if (newDistance < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = newDistance;
                    }
                }
            }
        }
    }

    private void printDistanceMatrix() {
        printHeaderRow();
        printRows();
    }

    private void printHeaderRow() {
        System.out.print("\t");
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            System.out.print(vertex + "\t");
        }
        System.out.println();
    }

    private void printRows() {
        for (int source = 1; source <= vertexCount; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= vertexCount; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}