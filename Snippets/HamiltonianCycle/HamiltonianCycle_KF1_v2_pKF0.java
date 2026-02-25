package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Hamiltonian cycle finder for an undirected graph represented
 * by an adjacency matrix.
 */
public class HamiltonianCycleFinder {

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

        initializeSearch(adjacencyMatrix);

        if (!searchFromVertex(0)) {
            Arrays.fill(this.path, -1);
        } else {
            closeCycle();
        }

        return path;
    }

    /**
     * Initializes fields for a new Hamiltonian cycle search.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     */
    private void initializeSearch(int[][] adjacencyMatrix) {
        this.vertexCount = adjacencyMatrix.length;
        this.adjacencyMatrix = adjacencyMatrix;
        this.path = new int[this.vertexCount + 1];
        Arrays.fill(this.path, -1);
        this.path[0] = 0;
        this.pathLength = 1;
    }

    /**
     * Closes the Hamiltonian cycle by repeating the starting vertex
     * at the end of the path.
     */
    private void closeCycle() {
        this.path[this.path.length - 1] = this.path[0];
    }

    /**
     * Recursive backtracking search for a Hamiltonian cycle starting from
     * the given vertex.
     *
     * @param currentVertex the vertex to continue the search from
     * @return true if a Hamiltonian cycle is found, false otherwise
     */
    public boolean searchFromVertex(int currentVertex) {
        if (isCycleComplete(currentVertex)) {
            return true;
        }

        if (pathLength == vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
            if (adjacencyMatrix[currentVertex][nextVertex] == 1) {
                if (tryNextVertex(currentVertex, nextVertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the Hamiltonian cycle is complete.
     *
     * @param currentVertex the current vertex in the path
     * @return true if the cycle is complete, false otherwise
     */
    private boolean isCycleComplete(int currentVertex) {
        return adjacencyMatrix[currentVertex][0] == 1 && pathLength == vertexCount;
    }

    /**
     * Attempts to extend the path by moving to the next vertex.
     *
     * @param currentVertex the current vertex
     * @param nextVertex    the candidate next vertex
     * @return true if a Hamiltonian cycle is found via this extension, false otherwise
     */
    private boolean tryNextVertex(int currentVertex, int nextVertex) {
        addVertexToPath(nextVertex);
        removeEdge(currentVertex, nextVertex);

        boolean foundCycle = false;
        if (!isVertexInCurrentPath(nextVertex)) {
            foundCycle = searchFromVertex(nextVertex);
        }

        if (!foundCycle) {
            restoreEdge(currentVertex, nextVertex);
            removeLastVertexFromPath();
        }

        return foundCycle;
    }

    /**
     * Adds a vertex to the current path.
     *
     * @param vertex the vertex to add
     */
    private void addVertexToPath(int vertex) {
        path[pathLength++] = vertex;
    }

    /**
     * Removes the last vertex from the current path.
     */
    private void removeLastVertexFromPath() {
        path[--pathLength] = -1;
    }

    /**
     * Removes an undirected edge between two vertices from the adjacency matrix.
     *
     * @param u one endpoint of the edge
     * @param v the other endpoint of the edge
     */
    private void removeEdge(int u, int v) {
        adjacencyMatrix[u][v] = 0;
        adjacencyMatrix[v][u] = 0;
    }

    /**
     * Restores an undirected edge between two vertices in the adjacency matrix.
     *
     * @param u one endpoint of the edge
     * @param v the other endpoint of the edge
     */
    private void restoreEdge(int u, int v) {
        adjacencyMatrix[u][v] = 1;
        adjacencyMatrix[v][u] = 1;
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