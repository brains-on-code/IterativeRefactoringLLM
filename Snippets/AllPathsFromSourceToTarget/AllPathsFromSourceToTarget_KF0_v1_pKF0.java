package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Program description - To find all possible paths from source to destination
 * <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Wikipedia</a>
 *
 * @author <a href="https://github.com/siddhant2002">Siddhant Swarup Mallick</a>
 */
public class AllPathsFromSourceToTarget {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** Adjacency list representation of the graph. */
    private final List<List<Integer>> adjacencyList;

    /** Constructor. */
    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        this.adjacencyList = new ArrayList<>(vertices);
        initAdjacencyList();
    }

    /** Initialize adjacency list. */
    private void initAdjacencyList() {
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    /** Add a directed edge from {@code from} to {@code to}. */
    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
    }

    /**
     * Compute all paths from {@code source} to {@code destination}.
     *
     * @return list of all paths, each path is a list of vertex indices
     */
    public List<List<Integer>> findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();

        currentPath.add(source);
        dfs(source, destination, visited, currentPath, allPaths);

        return allPaths;
    }

    /**
     * Depth-first search helper to collect all paths.
     */
    private void dfs(
            int current,
            int destination,
            boolean[] visited,
            List<Integer> currentPath,
            List<List<Integer>> allPaths
    ) {
        if (current == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (int neighbor : adjacencyList.get(current)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                dfs(neighbor, destination, visited, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[current] = false;
    }

    /**
     * Static helper to build a graph and return all paths from source to destination.
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