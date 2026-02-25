package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class HamiltonianCycleFinder {

    private int numberOfVertices;
    private int currentPathSize;
    private int[] hamiltonianCycle;
    private int[][] adjacencyMatrix;

    public int[] findHamiltonianCycle(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length == 1) {
            return new int[] {0, 0};
        }

        this.numberOfVertices = adjacencyMatrix.length;
        this.hamiltonianCycle = new int[this.numberOfVertices + 1];

        Arrays.fill(this.hamiltonianCycle, -1);

        this.adjacencyMatrix = adjacencyMatrix;
        this.hamiltonianCycle[0] = 0;
        this.currentPathSize = 1;

        if (!searchHamiltonianCycle(0)) {
            Arrays.fill(this.hamiltonianCycle, -1);
        } else {
            this.hamiltonianCycle[this.hamiltonianCycle.length - 1] = this.hamiltonianCycle[0];
        }

        return hamiltonianCycle;
    }

    public boolean searchHamiltonianCycle(int currentVertex) {
        boolean isCycleComplete =
                this.adjacencyMatrix[currentVertex][0] == 1
                        && this.currentPathSize == this.numberOfVertices;
        if (isCycleComplete) {
            return true;
        }

        if (this.currentPathSize == this.numberOfVertices) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.numberOfVertices; nextVertex++) {
            if (this.adjacencyMatrix[currentVertex][nextVertex] == 1) {
                this.hamiltonianCycle[this.currentPathSize++] = nextVertex;
                this.adjacencyMatrix[currentVertex][nextVertex] = 0;
                this.adjacencyMatrix[nextVertex][currentVertex] = 0;

                if (!isVertexInCurrentPath(nextVertex)) {
                    return searchHamiltonianCycle(nextVertex);
                }

                this.adjacencyMatrix[currentVertex][nextVertex] = 1;
                this.adjacencyMatrix[nextVertex][currentVertex] = 1;

                this.hamiltonianCycle[--this.currentPathSize] = -1;
            }
        }
        return false;
    }

    public boolean isVertexInCurrentPath(int vertex) {
        for (int index = 0; index < currentPathSize - 1; index++) {
            if (hamiltonianCycle[index] == vertex) {
                return true;
            }
        }
        return false;
    }
}