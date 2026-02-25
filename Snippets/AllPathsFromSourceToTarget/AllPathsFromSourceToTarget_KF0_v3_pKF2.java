package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for finding all paths from a source vertex to a destination vertex
 * in a directed graph.
 *
 * <p>See: <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Shortest path problem</a></p>
 */
public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private final List<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        this.adjacencyList = createAdjacencyList(vertices);
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] createAdjacencyList(int vertices) {
        List<Integer>[] list = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            list[i] = new ArrayList<>();
        }
        return list;
    }

    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    /**
     * Returns all paths from {@code source} to {@code destination} using DFS.
     */
    public List<List<Integer>> findAllPaths(int source, int destination) {
        List<List<Integer>> paths = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(source);
        dfs(source, destination, visited, currentPath, paths);

        return paths;
    }

    private void dfs(
        int current,
        int destination,
        boolean[] visited,
        List<Integer> currentPath,
        List<List<Integer>> paths
    ) {
        if (current == destination) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (int neighbor : adjacencyList[current]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                dfs(neighbor, destination, visited, currentPath, paths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[current] = false;
    }

    /**
     * Convenience method to build a graph and return all paths from
     * {@code source} to {@code destination}.
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