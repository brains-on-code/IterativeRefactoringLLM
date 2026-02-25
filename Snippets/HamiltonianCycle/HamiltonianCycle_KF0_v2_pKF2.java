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

        initialize(graph);

        if (!searchFrom(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            closeCycle();
        }

        return cycle;
    }

    /** Initializes internal state for a new search. */
    private void initialize(int[][] graph) {
        this.vertexCount = graph.length;
        this.graph = graph;

        this.cycle = new int[this.vertexCount + 1];
        Arrays.fill(this.cycle, -1);

        this.cycle[0] = 0;
        this.pathLength = 1;
    }

    /** Sets the last element of the cycle to the starting vertex. */
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
            if (graph[currentVertex][nextVertex] == 1) {
                if (tryNextVertex(currentVertex, nextVertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns true if all vertices are used and there is an edge back to the start. */
    private boolean isCompleteCycle(int currentVertex) {
        boolean allVerticesUsed = this.pathLength == this.vertexCount;
        boolean closesCycleToStart = this.graph[currentVertex][0] == 1 && allVerticesUsed;
        return closesCycleToStart;
    }

    /**
     * Attempts to extend the current path with the given next vertex.
     *
     * @return {@code true} if a Hamiltonian cycle is found via this extension
     */
    private boolean tryNextVertex(int currentVertex, int nextVertex) {
        addVertexToPath(nextVertex);
        removeEdge(currentVertex, nextVertex);

        boolean foundCycle = false;
        if (!isInCurrentPath(nextVertex) && searchFrom(nextVertex)) {
            foundCycle = true;
        }

        if (!foundCycle) {
            restoreEdge(currentVertex, nextVertex);
            removeVertexFromPath();
        }

        return foundCycle;
    }

    /** Adds a vertex to the current path. */
    private void addVertexToPath(int vertex) {
        this.cycle[this.pathLength++] = vertex;
    }

    /** Removes the last vertex from the current path. */
    private void removeVertexFromPath() {
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
        for (int i = 0; i < pathLength - 1; i++) {
            if (cycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}