package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Hamiltonian cycle finder for an undirected graph represented
 * by an adjacency matrix.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private int vertexCount;

    /** Current length of the path being explored. */
    private int pathLength;

    /** Stores the Hamiltonian path/cycle. */
    private int[] path;

    /** Adjacency matrix of the graph. */
    private int[][] adjacencyMatrix;

    /**
     * Attempts to find a Hamiltonian cycle in the given graph.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return an array representing a Hamiltonian cycle if one exists;
     *         otherwise an array filled with -1. If a cycle is found,
     *         the last element repeats the starting vertex to close the cycle.
     */
    public int[] findHamiltonianCycle(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = adjacencyMatrix.length;
        this.path = new int[this.vertexCount + 1];
        Arrays.fill(this.path, -1);

        this.adjacencyMatrix = adjacencyMatrix;
        this.path[0] = 0;
        this.pathLength = 1;

        if (!searchFromVertex(0)) {
            Arrays.fill(this.path, -1);
        } else {
            this.path[this.path.length - 1] = this.path[0];
        }

        return path;
    }

    /**
     * Recursive backtracking search for a Hamiltonian cycle starting from
     * the given vertex.
     *
     * @param currentVertex the vertex to continue the search from
     * @return true if a Hamiltonian cycle is found, false otherwise
     */
    public boolean searchFromVertex(int currentVertex) {
        boolean isCycleComplete =
                this.adjacencyMatrix[currentVertex][0] == 1 && this.pathLength == this.vertexCount;
        if (isCycleComplete) {
            return true;
        }

        if (this.pathLength == this.vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (this.adjacencyMatrix[currentVertex][nextVertex] == 1) {
                this.path[this.pathLength++] = nextVertex;
                this.adjacencyMatrix[currentVertex][nextVertex] = 0;
                this.adjacencyMatrix[nextVertex][currentVertex] = 0;

                if (!isVertexInCurrentPath(nextVertex)) {
                    if (searchFromVertex(nextVertex)) {
                        return true;
                    }
                }

                this.adjacencyMatrix[currentVertex][nextVertex] = 1;
                this.adjacencyMatrix[nextVertex][currentVertex] = 1;
                this.path[--this.pathLength] = -1;
            }
        }
        return false;
    }

    /**
     * Checks whether the given vertex is already present in the current path.
     *
     * @param vertex the vertex to check
     * @return true if the vertex is already in the path, false otherwise
     */
    public boolean isVertexInCurrentPath(int vertex) {
        for (int i = 0; i < pathLength - 1; i++) {
            if (path[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}