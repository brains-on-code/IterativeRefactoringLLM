package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;

/**
 * Java program to find a Hamiltonian Cycle in a graph.
 * A Hamiltonian Cycle is a cycle that visits every vertex exactly once
 * and returns to the starting vertex.
 *
 * <p>For more details, see the
 * <a href="https://en.wikipedia.org/wiki/Hamiltonian_path">Wikipedia article</a>.
 *
 * @author  <a href="https://github.com/itsAkshayDubey">Akshay Dubey</a>
 */
public class HamiltonianCycle {

    private int vertexCount;
    private int visitedVertexCount;
    private int[] hamiltonianCycle;
    private int[][] adjacencyMatrix;

    /**
     * Finds a Hamiltonian Cycle for the given graph.
     *
     * @param graph Adjacency matrix representing the graph G(V, E), where V is
     *              the set of vertices and E is the set of edges.
     * @return An array representing the Hamiltonian cycle if found, otherwise an
     *         array filled with -1 indicating no Hamiltonian cycle exists.
     */
    public int[] findHamiltonianCycle(int[][] graph) {
        if (graph.length == 1) {
            return new int[] {0, 0};
        }

        this.vertexCount = graph.length;
        this.hamiltonianCycle = new int[this.vertexCount + 1];

        Arrays.fill(this.hamiltonianCycle, -1);

        this.adjacencyMatrix = graph;
        this.hamiltonianCycle[0] = 0;
        this.visitedVertexCount = 1;

        if (!searchHamiltonianPath(0)) {
            Arrays.fill(this.hamiltonianCycle, -1);
        } else {
            this.hamiltonianCycle[this.hamiltonianCycle.length - 1] = this.hamiltonianCycle[0];
        }

        return hamiltonianCycle;
    }

    /**
     * Recursively searches for a Hamiltonian cycle from the given vertex.
     *
     * @param currentVertex The current vertex from which to explore paths.
     * @return {@code true} if a Hamiltonian cycle is found, otherwise {@code false}.
     */
    public boolean searchHamiltonianPath(int currentVertex) {
        boolean isLastVertexConnectedToStart =
                this.adjacencyMatrix[currentVertex][0] == 1 && this.visitedVertexCount == this.vertexCount;
        if (isLastVertexConnectedToStart) {
            return true;
        }

        if (this.visitedVertexCount == this.vertexCount) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (this.adjacencyMatrix[currentVertex][nextVertex] == 1) {
                this.hamiltonianCycle[this.visitedVertexCount++] = nextVertex;
                this.adjacencyMatrix[currentVertex][nextVertex] = 0;
                this.adjacencyMatrix[nextVertex][currentVertex] = 0;

                if (!isVertexInCurrentPath(nextVertex)) {
                    return searchHamiltonianPath(nextVertex);
                }

                this.adjacencyMatrix[currentVertex][nextVertex] = 1;
                this.adjacencyMatrix[nextVertex][currentVertex] = 1;

                this.hamiltonianCycle[--this.visitedVertexCount] = -1;
            }
        }
        return false;
    }

    /**
     * Checks if a vertex is already part of the current Hamiltonian path.
     *
     * @param vertex The vertex to check.
     * @return {@code true} if the vertex is already in the path, otherwise {@code false}.
     */
    public boolean isVertexInCurrentPath(int vertex) {
        for (int i = 0; i < visitedVertexCount - 1; i++) {
            if (hamiltonianCycle[i] == vertex) {
                return true;
            }
        }
        return false;
    }
}