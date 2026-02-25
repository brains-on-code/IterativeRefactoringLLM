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

        this.vertexCount = graph.length;
        this.cycle = new int[this.vertexCount + 1];
        Arrays.fill(this.cycle, -1);

        this.graph = graph;
        this.cycle[0] = 0;
        this.pathLength = 1;

        if (!searchFrom(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            this.cycle[this.cycle.length - 1] = this.cycle[0];
        }

        return cycle;
    }

    /**
     * Recursively searches for a Hamiltonian cycle starting from the given vertex.
     *
     * @param currentVertex the vertex from which to continue the search
     * @return {@code true} if a Hamiltonian cycle is found; {@code false} otherwise
     */
    private boolean searchFrom(int currentVertex) {
        boolean allVerticesUsed = this.pathLength == this.vertexCount;
        boolean closesCycleToStart = this.graph[currentVertex][0] == 1 && allVerticesUsed;

        if (closesCycleToStart) {
            return true;
        }

        if (allVerticesUsed) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (this.graph[currentVertex][nextVertex] == 1) {
                this.cycle[this.pathLength++] = nextVertex;

                this.graph[currentVertex][nextVertex] = 0;
                this.graph[nextVertex][currentVertex] = 0;

                if (!isInCurrentPath(nextVertex) && searchFrom(nextVertex)) {
                    return true;
                }

                this.graph[currentVertex][nextVertex] = 1;
                this.graph[nextVertex][currentVertex] = 1;

                this.cycle[--this.pathLength] = -1;
            }
        }
        return false;
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