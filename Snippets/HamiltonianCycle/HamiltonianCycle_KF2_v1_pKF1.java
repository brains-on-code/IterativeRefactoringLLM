package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class HamiltonianCycle {

    private int numberOfVertices;
    private int currentPathLength;
    private int[] hamiltonianPath;
    private int[][] adjacencyMatrix;

    public int[] findHamiltonianCycle(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length == 1) {
            return new int[] {0, 0};
        }

        this.numberOfVertices = adjacencyMatrix.length;
        this.hamiltonianPath = new int[this.numberOfVertices + 1];

        Arrays.fill(this.hamiltonianPath, -1);

        this.adjacencyMatrix = adjacencyMatrix;
        this.hamiltonianPath[0] = 0;
        this.currentPathLength = 1;

        if (!searchHamiltonianPath(0)) {
            Arrays.fill(this.hamiltonianPath, -1);
        } else {
            this.hamiltonianPath[this.hamiltonianPath.length - 1] = this.hamiltonianPath[0];
        }

        return hamiltonianPath;
    }

    public boolean searchHamiltonianPath(int currentVertex) {
        boolean isCycleComplete =
                this.adjacencyMatrix[currentVertex][0] == 1 && this.currentPathLength == this.numberOfVertices;
        if (isCycleComplete) {
            return true;
        }

        if (this.currentPathLength == this.numberOfVertices) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.numberOfVertices; nextVertex++) {
            if (this.adjacencyMatrix[currentVertex][nextVertex] == 1) {
                this.hamiltonianPath[this.currentPathLength++] = nextVertex;
                this.adjacencyMatrix[currentVertex][nextVertex] = 0;
                this.adjacencyMatrix[nextVertex][currentVertex] = 0;

                if (!isVertexAlreadyInPath(nextVertex)) {
                    return searchHamiltonianPath(nextVertex);
                }

                this.adjacencyMatrix[currentVertex][nextVertex] = 1;
                this.adjacencyMatrix[nextVertex][currentVertex] = 1;

                this.hamiltonianPath[--this.currentPathLength] = -1;
            }
        }
        return false;
    }

    public boolean isVertexAlreadyInPath(int vertex) {
        for (int i = 0; i < currentPathLength - 1; i++) {
            if (hamiltonianPath[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}