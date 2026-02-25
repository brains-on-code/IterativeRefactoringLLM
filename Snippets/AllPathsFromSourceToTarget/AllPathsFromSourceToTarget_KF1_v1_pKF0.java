package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility to find all paths between two vertices in a directed graph using backtracking.
 */
public class Class1 {

    private final int vertexCount;
    private static List<List<Integer>> allPaths = new ArrayList<>();
    private ArrayList<Integer>[] adjacencyList;

    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeGraph();
    }

    @SuppressWarnings("unchecked")
    private void initializeGraph() {
        adjacencyList = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    public void findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        ArrayList<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        backtrackPaths(source, destination, visited, currentPath);
    }

    private void backtrackPaths(Integer current,
                                Integer destination,
                                boolean[] visited,
                                List<Integer> currentPath) {

        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (Integer neighbor : adjacencyList[current]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                backtrackPaths(neighbor, destination, visited, currentPath);
                currentPath.remove(neighbor);
            }
        }

        visited[current] = false;
    }

    public static List<List<Integer>> getAllPaths(int vertexCount,
                                                  int[][] edges,
                                                  int source,
                                                  int destination) {
        allPaths = new ArrayList<>();
        Class1 graph = new Class1(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.findAllPaths(source, destination);
        return allPaths;
    }
}