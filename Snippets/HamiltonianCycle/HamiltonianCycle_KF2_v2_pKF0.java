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

        initialize(graph);

        if (!searchPathFrom(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            closeCycle();
        }

        return cycle;
    }

    private void initialize(int[][] graph) {
        this.vertexCount = graph.length;
        this.graph = graph;
        this.cycle = new int[this.vertexCount + 1];
        Arrays.fill(this.cycle, -1);
        this.cycle[0] = 0;
        this.pathLength = 1;
    }

    private void closeCycle() {
        this.cycle[this.cycle.length - 1] = this.cycle[0];
    }

    private boolean searchPathFrom(int currentVertex) {
        if (isCycleComplete(currentVertex)) {
            return true;
        }

        if (pathLength == vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
            if (graph[currentVertex][nextVertex] == 1) {
                if (tryNextVertex(currentVertex, nextVertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCycleComplete(int currentVertex) {
        return graph[currentVertex][0] == 1 && pathLength == vertexCount;
    }

    private boolean tryNextVertex(int currentVertex, int nextVertex) {
        addVertexToPath(currentVertex, nextVertex);

        boolean foundCycle =
                !isVertexInCurrentPath(nextVertex) && searchPathFrom(nextVertex);

        if (foundCycle) {
            return true;
        }

        removeVertexFromPath(currentVertex, nextVertex);
        return false;
    }

    private void addVertexToPath(int currentVertex, int nextVertex) {
        cycle[pathLength++] = nextVertex;
        graph[currentVertex][nextVertex] = 0;
        graph[nextVertex][currentVertex] = 0;
    }

    private void removeVertexFromPath(int currentVertex, int nextVertex) {
        graph[currentVertex][nextVertex] = 1;
        graph[nextVertex][currentVertex] = 1;
        cycle[--pathLength] = -1;
    }

    private boolean isVertexInCurrentPath(int vertex) {
        for (int i = 0; i < pathLength - 1; i++) {
            if (cycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}