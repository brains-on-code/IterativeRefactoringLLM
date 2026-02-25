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

    public void findAllPaths(int sourceVertex, int targetVertex) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(sourceVertex);
        depthFirstSearch(sourceVertex, targetVertex, visited, currentPath);
    }

    private void depthFirstSearch(
            Integer currentVertex,
            Integer targetVertex,
            boolean[] visited,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(targetVertex)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (Integer neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, targetVertex, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertexCount,
            int[][] edges,
            int sourceVertex,
            int targetVertex
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.findAllPaths(sourceVertex, targetVertex);
        return allPaths;
    }
}