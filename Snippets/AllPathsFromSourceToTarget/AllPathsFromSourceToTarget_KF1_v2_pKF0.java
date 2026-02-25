package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility to find all paths between two vertices in a directed graph using backtracking.
 */
public class AllPathsInDirectedGraph {

    private final int vertexCount;
    private final List<Integer>[] adjacencyList;

    @SuppressWarnings("unchecked")
    public AllPathsInDirectedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyList = new ArrayList[vertexCount];
        initializeGraph();
    }

    private void initializeGraph() {
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
        List<List<Integer>> allPaths = new ArrayList<>();

        currentPath.add(source);
        backtrackPaths(source, destination, visited, currentPath, allPaths);

        return allPaths;
    }

    private void backtrackPaths(int current,
                                int destination,
                                boolean[] visited,
                                List<Integer> currentPath,
                                List<List<Integer>> allPaths) {

        if (current == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (int neighbor : adjacencyList[current]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                backtrackPaths(neighbor, destination, visited, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[current] = false;
    }

    public static List<List<Integer>> getAllPaths(int vertexCount,
                                                  int[][] edges,
                                                  int source,
                                                  int destination) {
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}