package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

public class HamiltonianCycle {

    private int vertexCount;
    private int pathLength;
    private int[] cycle;
    private int[][] graph;

    public int[] findHamiltonianCycle(int[][] graph) {
        if (graph.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = graph.length;
        this.cycle = new int[this.vertexCount + 1];
        Arrays.fill(this.cycle, -1);

        this.graph = graph;
        this.cycle[0] = 0;
        this.pathLength = 1;

        if (!searchPath(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            this.cycle[this.cycle.length - 1] = this.cycle[0];
        }

        return cycle;
    }

    private boolean searchPath(int currentVertex) {
        boolean canCloseCycle =
                this.graph[currentVertex][0] == 1 && this.pathLength == this.vertexCount;
        if (canCloseCycle) {
            return true;
        }

        if (this.pathLength == this.vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (this.graph[currentVertex][nextVertex] == 1) {
                this.cycle[this.pathLength++] = nextVertex;
                this.graph[currentVertex][nextVertex] = 0;
                this.graph[nextVertex][currentVertex] = 0;

                if (!isInCurrentPath(nextVertex) && searchPath(nextVertex)) {
                    return true;
                }

                this.graph[currentVertex][nextVertex] = 1;
                this.graph[nextVertex][currentVertex] = 1;
                this.cycle[--this.pathLength] = -1;
            }
        }
        return false;
    }

    private boolean isInCurrentPath(int vertex) {
        for (int i = 0; i < pathLength - 1; i++) {
            if (cycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}