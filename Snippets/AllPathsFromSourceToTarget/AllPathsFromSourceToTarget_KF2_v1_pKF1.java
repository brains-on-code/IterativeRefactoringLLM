package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFromSourceToTarget {

    private final int vertexCount;

    private static final List<List<Integer>> allPaths = new ArrayList<>();
    private ArrayList<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeAdjacencyList();
    }

    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList[vertex] = new ArrayList<>();
        }
    }

    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList[fromVertex].add(toVertex);
    }

    public void storeAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        storeAllPathsRecursive(source, destination, visited, currentPath);
    }

    private void storeAllPathsRecursive(
            Integer currentVertex,
            Integer destinationVertex,
            boolean[] visited,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destinationVertex)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (Integer neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                storeAllPathsRecursive(neighbor, destinationVertex, visited, currentPath);
                currentPath.remove(neighbor);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertexCount,
            int[][] edges,
            int source,
            int destination
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.storeAllPaths(source, destination);
        return allPaths;
    }
}