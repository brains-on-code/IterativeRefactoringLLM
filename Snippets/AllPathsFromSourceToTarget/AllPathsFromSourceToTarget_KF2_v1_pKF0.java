package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private final List<List<Integer>> allPaths = new ArrayList<>();
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

    public List<List<Integer>> findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        dfs(source, destination, visited, currentPath);
        return allPaths;
    }

    private void dfs(int current, int destination, boolean[] visited, List<Integer> currentPath) {
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

    public static List<List<Integer>> allPathsFromSourceToTarget(int vertices, int[][] edges, int source, int destination) {
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertices);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}