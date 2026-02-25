package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Finds a Hamiltonian cycle in an undirected graph represented by an adjacency matrix.
 *
 * <p>A Hamiltonian cycle is a cycle that visits every vertex exactly once and
 * returns to the starting vertex.</p>
 *
 * <p>For more details, see the
 * <a href="https://en.wikipedia.org/wiki/Hamiltonian_path">Wikipedia article</a>.
 *
 * @author <a href="https://github.com/itsAkshayDubey">Akshay Dubey</a>
 */
public class HamiltonianCycle {

    /** Number of vertices in the graph. */
    private int vertexCount;

    /** Current length of the path being constructed. */
    private int pathLength;

    /** Stores the Hamiltonian cycle; last element duplicates the start vertex. */
    private int[] cycle;

    /** Adjacency matrix of the graph. */
    private int[][] graph;

    /**
     * Attempts to find a Hamiltonian cycle in the given graph.
     *
     * @param graph adjacency matrix representing the graph G(V, E)
     * @return an array representing the Hamiltonian cycle if found; otherwise,
     *         an array filled with -1
     */
    public int[] findHamiltonianCycle(int[][] graph) {
        if (graph.length == 1) {
            return new int[] {0, 0};
        }

        initializeSearch(graph);

        if (!searchFrom(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            closeCycle();
        }

        return cycle;
    }

    /** Prepares internal state for a new Hamiltonian cycle search. */
    private void initializeSearch(int[][] graph) {
        this.vertexCount = graph.length;
        this.graph = graph;

        this.cycle = new int[this.vertexCount + 1];
        Arrays.fill(this.cycle, -1);

        this.cycle[0] = 0;
        this.pathLength = 1;
    }

    /** Closes the cycle by repeating the starting vertex at the end. */
    private void closeCycle() {
        this.cycle[this.cycle.length - 1] = this.cycle[0];
    }

    /**
     * Recursively searches for a Hamiltonian cycle starting from the given vertex.
     *
     * @param currentVertex the vertex from which to continue the search
     * @return {@code true} if a Hamiltonian cycle is found; {@code false} otherwise
     */
    private boolean searchFrom(int currentVertex) {
        if (isCompleteCycle(currentVertex)) {
            return true;
        }

        if (pathLength == vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (graph[currentVertex][nextVertex] == 1 && tryNextVertex(currentVertex, nextVertex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the current path forms a complete Hamiltonian cycle.
     *
     * @param currentVertex the last vertex in the current path
     * @return {@code true} if all vertices are used and there is an edge back to the start
     */
    private boolean isCompleteCycle(int currentVertex) {
        boolean allVerticesUsed = this.pathLength == this.vertexCount;
        return allVerticesUsed && this.graph[currentVertex][0] == 1;
    }

    /**
     * Attempts to extend the current path with the given next vertex.
     *
     * @param currentVertex the current end of the path
     * @param nextVertex    the candidate vertex to add
     * @return {@code true} if a Hamiltonian cycle is found via this extension
     */
    private boolean tryNextVertex(int currentVertex, int nextVertex) {
        if (isInCurrentPath(nextVertex)) {
            return false;
        }

        addVertexToPath(nextVertex);
        removeEdge(currentVertex, nextVertex);

        boolean foundCycle = searchFrom(nextVertex);

        if (!foundCycle) {
            restoreEdge(currentVertex, nextVertex);
            removeLastVertexFromPath();
        }

        return foundCycle;
    }

    /** Appends a vertex to the current path. */
    private void addVertexToPath(int vertex) {
        this.cycle[this.pathLength++] = vertex;
    }

    /** Removes the last vertex from the current path. */
    private void removeLastVertexFromPath() {
        this.cycle[--this.pathLength] = -1;
    }

    /** Temporarily removes an undirected edge from the graph. */
    private void removeEdge(int u, int v) {
        this.graph[u][v] = 0;
        this.graph[v][u] = 0;
    }

    /** Restores an undirected edge in the graph. */
    private void restoreEdge(int u, int v) {
        this.graph[u][v] = 1;
        this.graph[v][u] = 1;
    }

    /**
     * Checks whether a vertex is already included in the current path.
     *
     * @param vertex the vertex to check
     * @return {@code true} if the vertex is already in the path; {@code false} otherwise
     */
    private boolean isInCurrentPath(int vertex) {
        for (int i = 0; i < pathLength; i++) {
            if (cycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}