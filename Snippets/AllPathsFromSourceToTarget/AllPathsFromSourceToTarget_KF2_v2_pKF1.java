package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class AllPathsFromSourceToTarget {

    private final int numberOfVertices;

    private static final List<List<Integer>> paths = new ArrayList<>();
    private ArrayList<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int numberOfVertices) {
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

    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList[fromVertex].add(toVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visitedVertices = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(sourceVertex);
        depthFirstSearch(sourceVertex, destinationVertex, visitedVertices, currentPath);
    }

    private void depthFirstSearch(
            Integer currentVertex,
            Integer destinationVertex,
            boolean[] visitedVertices,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destinationVertex)) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        visitedVertices[currentVertex] = true;

        for (Integer adjacentVertex : adjacencyList[currentVertex]) {
            if (!visitedVertices[adjacentVertex]) {
                currentPath.add(adjacentVertex);
                depthFirstSearch(adjacentVertex, destinationVertex, visitedVertices, currentPath);
                currentPath.remove(adjacentVertex);
            }
        }

        visitedVertices[currentVertex] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
            int numberOfVertices,
            int[][] edges,
            int sourceVertex,
            int destinationVertex
    ) {
        paths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(numberOfVertices);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.findAllPaths(sourceVertex, destinationVertex);
        return paths;
    }
}