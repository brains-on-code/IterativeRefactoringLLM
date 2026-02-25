package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all possible paths from a source vertex to a destination vertex in a directed graph.
 * <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Wikipedia</a>
 *
 * @author <a href="https://github.com/siddhant2002">Siddhant Swarup Mallick</a>
 */
public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private static final List<List<Integer>> allPaths = new ArrayList<>();
    private List<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        initAdjacencyList();
    }

    @SuppressWarnings("unchecked")
    private void initAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    public void storeAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        dfs(source, destination, visited, currentPath);
    }

    private void dfs(
        int current,
        int destination,
        boolean[] visited,
        List<Integer> currentPath
    ) {
        if (current == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (int neighbor : adjacencyList[current]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                dfs(neighbor, destination, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[current] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
        int vertices,
        int[][] edges,
        int source,
        int destination
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertices);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.storeAllPaths(source, destination);
        return allPaths;
    }
}