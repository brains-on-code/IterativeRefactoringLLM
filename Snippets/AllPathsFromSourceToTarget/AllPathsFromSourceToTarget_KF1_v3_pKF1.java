package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for finding all paths between two vertices in a directed graph
 * using backtracking.
 */
public class AllPathsInDirectedGraph {

    private final int numberOfVertices;

    private static final List<List<Integer>> collectedPaths = new ArrayList<>();

    private List<Integer>[] adjacencyLists;

    public AllPathsInDirectedGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        initializeAdjacencyLists();
    }

    @SuppressWarnings("unchecked")
    private void initializeAdjacencyLists() {
        adjacencyLists = new ArrayList[numberOfVertices];
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            adjacencyLists[vertexIndex] = new ArrayList<>();
        }
    }

    public void addEdge(int sourceVertex, int destinationVertex) {
        adjacencyLists[sourceVertex].add(destinationVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visitedVertices = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(sourceVertex);
        depthFirstSearch(sourceVertex, destinationVertex, visitedVertices, currentPath);
    }

    private void depthFirstSearch(
        int currentVertex,
        int targetVertex,
        boolean[] visitedVertices,
        List<Integer> currentPath
    ) {
        if (currentVertex == targetVertex) {
            collectedPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visitedVertices[currentVertex] = true;

        for (int adjacentVertex : adjacencyLists[currentVertex]) {
            if (!visitedVertices[adjacentVertex]) {
                currentPath.add(adjacentVertex);
                depthFirstSearch(adjacentVertex, targetVertex, visitedVertices, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visitedVertices[currentVertex] = false;
    }

    public static List<List<Integer>> findAllPathsInGraph(
        int numberOfVertices,
        int[][] directedEdges,
        int sourceVertex,
        int destinationVertex
    ) {
        collectedPaths.clear();
        AllPathsInDirectedGraph graph = new AllPathsInDirectedGraph(numberOfVertices);
        for (int[] edge : directedEdges) {
            int fromVertex = edge[0];
            int toVertex = edge[1];
            graph.addEdge(fromVertex, toVertex);
        }
        graph.findAllPaths(sourceVertex, destinationVertex);
        return collectedPaths;
    }
}