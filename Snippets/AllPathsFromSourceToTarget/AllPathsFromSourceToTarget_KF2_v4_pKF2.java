package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Computes all paths from a source vertex to a destination vertex in a directed graph.
 */
public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private final List<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        this.adjacencyList = createAdjacencyList(vertices);
    }

    @SuppressWarnings("unchecked")
    private List<Integer>[] createAdjacencyList(int vertices) {
        List<Integer>[] list = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            list[i] = new ArrayList<>();
        }
        return list;
    }

    /**
     * Adds a directed edge from {@code from} to {@code to}.
     */
    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    /**
     * Returns all paths from {@code source} to {@code destination}.
     */
    public List<List<Integer>> findAllPaths(int source, int destination) {
        List<List<Integer>> allPaths = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(source);
        depthFirstSearch(source, destination, visited, currentPath, allPaths);

        return allPaths;
    }

    /**
     * Depth-first search with backtracking to collect all paths from the current
     * vertex to the destination.
     */
    private void depthFirstSearch(
            int currentVertex,
            int destination,
            boolean[] visited,
            List<Integer> currentPath,
            List<List<Integer>> allPaths
    ) {
        if (currentVertex == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (int neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, destination, visited, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    /**
     * Builds a graph and returns all paths from {@code source} to {@code destination}.
     */
    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertices,
            int[][] edges,
            int source,
            int destination
    ) {
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertices);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}