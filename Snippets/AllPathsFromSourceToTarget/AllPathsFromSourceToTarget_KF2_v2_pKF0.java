package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFromSourceToTarget {

    private final int vertexCount;
    private final List<List<Integer>> allPaths;
    private List<List<Integer>> adjacencyList;

    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        this.allPaths = new ArrayList<>();
        initAdjacencyList();
    }

    private void initAdjacencyList() {
        adjacencyList = new ArrayList<>(vertexCount);
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
        currentPath.add(source);
        depthFirstSearch(source, destination, visited, currentPath);
        return allPaths;
    }

    private void depthFirstSearch(
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

        for (int neighbor : adjacencyList.get(current)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, destination, visited, currentPath);
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
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertices);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph.findAllPaths(source, destination);
    }
}