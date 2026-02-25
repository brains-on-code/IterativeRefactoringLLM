package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class HamiltonianCycle {

    private int vertexCount;
    private int pathLength;
    private int[] cyclePath;
    private int[][] graph;

    public int[] findHamiltonianCycle(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = adjacencyMatrix.length;
        this.cyclePath = new int[this.vertexCount + 1];
        Arrays.fill(this.cyclePath, -1);

        this.graph = adjacencyMatrix;
        this.cyclePath[0] = 0;
        this.pathLength = 1;

        if (!searchHamiltonianCycleFrom(0)) {
            Arrays.fill(this.cyclePath, -1);
        } else {
            this.cyclePath[this.cyclePath.length - 1] = this.cyclePath[0];
        }

        return cyclePath;
    }

    public boolean searchHamiltonianCycleFrom(int currentVertex) {
        boolean isCycleComplete =
                this.graph[currentVertex][0] == 1 && this.pathLength == this.vertexCount;
        if (isCycleComplete) {
            return true;
        }

        if (this.pathLength == this.vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (this.graph[currentVertex][nextVertex] == 1) {
                this.cyclePath[this.pathLength++] = nextVertex;
                this.graph[currentVertex][nextVertex] = 0;
                this.graph[nextVertex][currentVertex] = 0;

                if (!isVertexInCurrentPath(nextVertex)) {
                    return searchHamiltonianCycleFrom(nextVertex);
                }

                this.graph[currentVertex][nextVertex] = 1;
                this.graph[nextVertex][currentVertex] = 1;

                this.cyclePath[--this.pathLength] = -1;
            }
        }
        return false;
    }

    public boolean isVertexInCurrentPath(int vertex) {
        for (int index = 0; index < pathLength - 1; index++) {
            if (cyclePath[index] == vertex) {
                return true;
            }
        }
        return false;
    }
}