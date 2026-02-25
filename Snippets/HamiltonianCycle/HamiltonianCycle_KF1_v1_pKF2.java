package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Finds a Hamiltonian cycle in an undirected graph represented by an adjacency matrix.
 *
 * The graph is given as a square matrix {@code graph} where:
 * - {@code graph[i][j] == 1} indicates an edge between vertex {@code i} and vertex {@code j}
 * - {@code graph[i][j] == 0} indicates no edge
 *
 * If a Hamiltonian cycle exists, the returned array contains the sequence of vertices
 * in the cycle, with the last element equal to the first (to close the cycle).
 * If no cycle exists, the returned array is filled with {@code -1}.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private int vertexCount;

    /** Current length of the path being constructed. */
    private int pathLength;

    /** Current Hamiltonian path (and final cycle). */
    private int[] path;

    /** Adjacency matrix of the graph (may be modified during search). */
    private int[][] graph;

    /**
     * Attempts to find a Hamiltonian cycle in the given graph.
     *
     * @param graph adjacency matrix of the graph
     * @return an array representing a Hamiltonian cycle if one exists; otherwise an array of -1
     */
    public int[] method1(int[][] graph) {
        if (graph.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = graph.length;
        this.path = new int[this.vertexCount + 1];

        Arrays.fill(this.path, -1);

        this.graph = graph;
        this.path[0] = 0;
        this.pathLength = 1;

        if (!method2(0)) {
            Arrays.fill(this.path, -1);
        } else {
            this.path[this.path.length - 1] = this.path[0];
        }

        return path;
    }

    /**
     * Recursive backtracking step to extend the Hamiltonian path from the given vertex.
     *
     * @param currentVertex the vertex from which to extend the path
     * @return {@code true} if a Hamiltonian cycle is found; {@code false} otherwise
     */
    public boolean method2(int currentVertex) {
        boolean isCycleComplete = this.graph[currentVertex][0] == 1 && this.pathLength == this.vertexCount;
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

                if (!method3(nextVertex)) {
                    return method2(nextVertex);
                }

                this.graph[currentVertex][nextVertex] = 1;
                this.graph[nextVertex][currentVertex] = 1;

                this.path[--this.pathLength] = -1;
            }
        }
        return false;
    }

    /**
     * Checks whether the given vertex is already present in the current path.
     *
     * @param vertex the vertex to check
     * @return {@code true} if the vertex is already in the path; {@code false} otherwise
     */
    public boolean method3(int vertex) {
        for (int i = 0; i < pathLength - 1; i++) {
            if (path[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}