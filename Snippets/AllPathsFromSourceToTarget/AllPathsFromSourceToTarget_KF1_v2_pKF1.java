package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for finding all paths between two vertices in a directed graph
 * using backtracking.
 */
public class AllPathsInDirectedGraph {

    // Number of vertices in the graph
    private final int vertexCount;

    // All found paths (shared across invocations of method5)
    private static final List<List<Integer>> allPaths = new ArrayList<>();

    // Adjacency list representation of the graph
    private ArrayList<Integer>[] adjacencyList;

    public AllPathsInDirectedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeAdjacencyList();
    }

    // Initialize adjacency list
    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList[vertex] = new ArrayList<>();
        }
    }

    // Add directed edge from source to destination
    public void addEdge(int sourceVertex, int destinationVertex) {
        adjacencyList[sourceVertex].add(destinationVertex);
    }

    // Find all paths from startVertex to endVertex
    public void findAllPaths(int startVertex, int endVertex) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(startVertex);
        depthFirstSearch(startVertex, endVertex, visited, currentPath);
    }

    // Depth-first search to find all paths
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

        for (Integer neighborVertex : adjacencyList[currentVertex]) {
            if (!visited[neighborVertex]) {
                currentPath.add(neighborVertex);
                depthFirstSearch(neighborVertex, targetVertex, visited, currentPath);
                currentPath.remove(neighborVertex);
            }
        }

        visited[currentVertex] = false;
    }

    // Build graph and return all paths between given vertices
    public static List<List<Integer>> findAllPathsInGraph(
        int vertexCount,
        int[][] edges,
        int startVertex,
        int endVertex
    ) {
        allPaths.clear();
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.findAllPaths(startVertex, endVertex);
        return allPaths;
    }
}