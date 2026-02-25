package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for finding all paths between two vertices in a directed graph
 * using backtracking.
 */
public class AllPathsInDirectedGraph {

    private final int vertexCount;

    private static final List<List<Integer>> allPaths = new ArrayList<>();

    private List<Integer>[] adjacencyList;

    public AllPathsInDirectedGraph(int vertexCount) {
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

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(sourceVertex);
        depthFirstSearch(sourceVertex, destinationVertex, visited, currentPath);
    }

    private void depthFirstSearch(
        int currentVertex,
        int destinationVertex,
        boolean[] visited,
        List<Integer> currentPath
    ) {
        if (currentVertex == destinationVertex) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (int neighborVertex : adjacencyList[currentVertex]) {
            if (!visited[neighborVertex]) {
                currentPath.add(neighborVertex);
                depthFirstSearch(neighborVertex, destinationVertex, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> findAllPathsInGraph(
        int vertexCount,
        int[][] directedEdges,
        int sourceVertex,
        int destinationVertex
    ) {
        allPaths.clear();
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(vertexCount);
        for (int[] edge : directedEdges) {
            int fromVertex = edge[0];
            int toVertex = edge[1];
            graph.addEdge(fromVertex, toVertex);
        }
        graph.findAllPaths(sourceVertex, destinationVertex);
        return allPaths;
    }
}