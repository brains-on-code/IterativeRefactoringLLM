package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility to find all paths between two vertices in a directed graph using backtracking.
 */
public class AllPathsInDirectedGraph {

    private final int vertexCount;
    private final List<List<Integer>> adjacencyList;

    public AllPathsInDirectedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyList = new ArrayList<>(vertexCount);
        initializeGraph();
    }

    private void initializeGraph() {
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
    }

    public List<List<Integer>> findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();

        currentPath.add(source);
        explorePaths(source, destination, visited, currentPath, allPaths);

        return allPaths;
    }

    private void explorePaths(
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

        for (int neighbor : adjacencyList.get(currentVertex)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                explorePaths(neighbor, destination, visited, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> getAllPaths(
        int vertexCount,
        int[][] edges,
        int source,
        int destination
    ) {
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}