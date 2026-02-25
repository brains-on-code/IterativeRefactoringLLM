package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private final List<List<Integer>> allPaths;
    private final List<List<Integer>> adjacencyList;

    public AllPathsFromSourceToTarget(int vertexCount) {
        this.vertexCount = vertexCount;
        this.allPaths = new ArrayList<>();
        this.adjacencyList = createEmptyAdjacencyList(vertexCount);
    }

    private List<List<Integer>> createEmptyAdjacencyList(int vertexCount) {
        List<List<Integer>> adjacency = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacency.add(new ArrayList<>());
        }
        return adjacency;
    }

    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
    }

    public List<List<Integer>> findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        dfs(source, destination, visited, currentPath);
        return allPaths;
    }

    private void dfs(
        int currentNode,
        int destination,
        boolean[] visited,
        List<Integer> currentPath
    ) {
        if (currentNode == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentNode] = true;

        for (int neighbor : adjacencyList.get(currentNode)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                dfs(neighbor, destination, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentNode] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
        int vertexCount,
        int[][] edges,
        int source,
        int destination
    ) {
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}