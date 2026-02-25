package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for finding all paths between two vertices in a directed graph
 * using backtracking.
 */
public class AllPathsInDirectedGraph {

    private final int numberOfVertices;

    private static final List<List<Integer>> paths = new ArrayList<>();

    private List<Integer>[] adjacencyList;

    public AllPathsInDirectedGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        initializeAdjacencyList();
    }

    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[numberOfVertices];
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            adjacencyList[vertexIndex] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int target) {
        adjacencyList[source].add(target);
    }

    public void findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        depthFirstSearch(source, destination, visited, currentPath);
    }

    private void depthFirstSearch(
        int currentVertex,
        int destinationVertex,
        boolean[] visited,
        List<Integer> currentPath
    ) {
        if (currentVertex == destinationVertex) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (int neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, destinationVertex, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> findAllPathsInGraph(
        int numberOfVertices,
        int[][] directedEdges,
        int source,
        int destination
    ) {
        paths.clear();
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(numberOfVertices);
        for (int[] edge : directedEdges) {
            int sourceVertex = edge[0];
            int targetVertex = edge[1];
            graph.addEdge(sourceVertex, targetVertex);
        }
        graph.findAllPaths(source, destination);
        return paths;
    }
}