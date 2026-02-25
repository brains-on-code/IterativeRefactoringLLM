package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class HamiltonianCycle {

    private int vertexCount;
    private int pathLength;
    private int[] path;
    private int[][] graph;

    public int[] findHamiltonianCycle(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = adjacencyMatrix.length;
        this.path = new int[this.vertexCount + 1];
        Arrays.fill(this.path, -1);

        this.graph = adjacencyMatrix;
        this.path[0] = 0;
        this.pathLength = 1;

        if (!searchHamiltonianPath(0)) {
            Arrays.fill(this.path, -1);
        } else {
            this.path[this.path.length - 1] = this.path[0];
        }

        return path;
    }

    public boolean searchHamiltonianPath(int currentVertex) {
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
                this.path[this.pathLength++] = nextVertex;
                this.graph[currentVertex][nextVertex] = 0;
                this.graph[nextVertex][currentVertex] = 0;

                if (!isVertexInPath(nextVertex)) {
                    return searchHamiltonianPath(nextVertex);
                }

                this.graph[currentVertex][nextVertex] = 1;
                this.graph[nextVertex][currentVertex] = 1;

                this.path[--this.pathLength] = -1;
            }
        }
        return false;
    }

    public boolean isVertexInPath(int vertex) {
        for (int i = 0; i < pathLength - 1; i++) {
            if (path[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}