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
    private int pathLength;
    private int[] cycle;
    private int[][] graph;

    /**
     * Finds a Hamiltonian Cycle for the given graph.
     *
     * @param graph Adjacency matrix representing the graph G(V, E), where V is
     *              the set of vertices and E is the set of edges.
     * @return An array representing the Hamiltonian cycle if found, otherwise an
     *         array filled with -1 indicating no Hamiltonian cycle exists.
     */
    public int[] findHamiltonianCycle(int[][] graph) {
        if (!isValidGraph(graph)) {
            return new int[] {-1};
        }

        if (isSingleVertexGraph(graph)) {
            return new int[] {0, 0};
        }

        initializeState(graph);

        if (!searchHamiltonianCycleFrom(0)) {
            Arrays.fill(this.cycle, -1);
        } else {
            closeCycle();
        }

        return cycle;
    }

    private boolean isValidGraph(int[][] graph) {
        return graph != null && graph.length > 0;
    }

    private boolean isSingleVertexGraph(int[][] graph) {
        return graph.length == 1;
    }

    private void initializeState(int[][] graph) {
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

    /**
     * Recursively searches for a Hamiltonian cycle from the given vertex.
     *
     * @param currentVertex The current vertex from which to explore paths.
     * @return {@code true} if a Hamiltonian cycle is found, otherwise {@code false}.
     */
    private boolean searchHamiltonianCycleFrom(int currentVertex) {
        if (isCompletePath()) {
            return canCloseCycleFrom(currentVertex);
        }

        for (int nextVertex = 0; nextVertex < this.vertexCount; nextVertex++) {
            if (!isValidNextVertex(currentVertex, nextVertex)) {
                continue;
            }

            addVertexToPath(currentVertex, nextVertex);

            if (searchHamiltonianCycleFrom(nextVertex)) {
                return true;
            }

            removeVertexFromPath(currentVertex, nextVertex);
        }

        return false;
    }

    private boolean isCompletePath() {
        return this.pathLength == this.vertexCount;
    }

    private boolean canCloseCycleFrom(int currentVertex) {
        return this.graph[currentVertex][0] == 1;
    }

    private boolean isValidNextVertex(int currentVertex, int nextVertex) {
        return isAdjacent(currentVertex, nextVertex) && !isInCurrentPath(nextVertex);
    }

    private boolean isAdjacent(int fromVertex, int toVertex) {
        return this.graph[fromVertex][toVertex] == 1;
    }

    /**
     * Adds a vertex to the current Hamiltonian path and removes the corresponding edge.
     */
    private void addVertexToPath(int fromVertex, int toVertex) {
        this.cycle[this.pathLength++] = toVertex;
        setEdge(fromVertex, toVertex, 0);
    }

    /**
     * Removes a vertex from the current Hamiltonian path and restores the corresponding edge.
     */
    private void removeVertexFromPath(int fromVertex, int toVertex) {
        setEdge(fromVertex, toVertex, 1);
        this.cycle[--this.pathLength] = -1;
    }

    private void setEdge(int fromVertex, int toVertex, int value) {
        this.graph[fromVertex][toVertex] = value;
        this.graph[toVertex][fromVertex] = value;
    }

    /**
     * Checks if a vertex is already part of the current Hamiltonian path.
     *
     * @param vertex The vertex to check.
     * @return {@code true} if the vertex is already in the path, otherwise {@code false}.
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