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

    private int numberOfVertices;
    private int visitedVerticesCount;
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

        this.numberOfVertices = graph.length;
        this.hamiltonianCycle = new int[this.numberOfVertices + 1];

        Arrays.fill(this.hamiltonianCycle, -1);

        this.adjacencyMatrix = graph;
        this.hamiltonianCycle[0] = 0;
        this.visitedVerticesCount = 1;

        if (!searchHamiltonianCycleFromVertex(0)) {
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
    public boolean searchHamiltonianCycleFromVertex(int currentVertex) {
        boolean isCycleCompleteAndConnectedToStart =
                this.adjacencyMatrix[currentVertex][0] == 1 && this.visitedVerticesCount == this.numberOfVertices;
        if (isCycleCompleteAndConnectedToStart) {
            return true;
        }

        if (this.visitedVerticesCount == this.numberOfVertices) {
            return false;
        }

        for (int nextVertex = 0; nextVertex < this.numberOfVertices; nextVertex++) {
            if (this.adjacencyMatrix[currentVertex][nextVertex] == 1) {
                this.hamiltonianCycle[this.visitedVerticesCount++] = nextVertex;
                this.adjacencyMatrix[currentVertex][nextVertex] = 0;
                this.adjacencyMatrix[nextVertex][currentVertex] = 0;

                if (!isVertexAlreadyInPath(nextVertex)) {
                    return searchHamiltonianCycleFromVertex(nextVertex);
                }

                this.adjacencyMatrix[currentVertex][nextVertex] = 1;
                this.adjacencyMatrix[nextVertex][currentVertex] = 1;

                this.hamiltonianCycle[--this.visitedVerticesCount] = -1;
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
    public boolean isVertexAlreadyInPath(int vertex) {
        for (int pathIndex = 0; pathIndex < visitedVerticesCount - 1; pathIndex++) {
            if (hamiltonianCycle[pathIndex] == vertex) {
                return true;
            }
        }
        return false;
    }
}