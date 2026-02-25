package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;


public class HamiltonianCycle {

    private int vertex;
    private int pathCount;
    private int[] cycle;
    private int[][] graph;


    public int[] findHamiltonianCycle(int[][] graph) {
        if (graph.length == 1) {
            return new int[] {0, 0};
        }

        this.vertex = graph.length;
        this.cycle = new int[this.vertex + 1];

        Arrays.fill(this.cycle, -1);

        this.graph = graph;
        this.cycle[0] = 0;
        this.pathCount = 1;
        if (!isPathFound(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            this.cycle[this.cycle.length - 1] = this.cycle[0];
        }

        return cycle;
    }


    public boolean isPathFound(int vertex) {
        boolean isLastVertexConnectedToStart = this.graph[vertex][0] == 1 && this.pathCount == this.vertex;
        if (isLastVertexConnectedToStart) {
            return true;
        }

        if (this.pathCount == this.vertex) {
            return false;
        }

        for (int v = 0; v < this.vertex; v++) {
            if (this.graph[vertex][v] == 1) {
                this.cycle[this.pathCount++] = v;
                this.graph[vertex][v] = 0;
                this.graph[v][vertex] = 0;

                if (!isPresent(v)) {
                    return isPathFound(v);
                }

                this.graph[vertex][v] = 1;
                this.graph[v][vertex] = 1;

                this.cycle[--this.pathCount] = -1;
            }
        }
        return false;
    }


    public boolean isPresent(int vertex) {
        for (int i = 0; i < pathCount - 1; i++) {
            if (cycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}
