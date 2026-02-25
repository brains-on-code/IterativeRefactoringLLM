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

    public void addEdge(int sourceVertex, int destinationVertex) {
        adjacencyList[sourceVertex].add(destinationVertex);
    }

    public void findAllPaths(int sourceVertex, int targetVertex) {
        boolean[] visitedVertices = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(sourceVertex);
        depthFirstSearch(sourceVertex, targetVertex, visitedVertices, currentPath);
    }

    private void depthFirstSearch(
            Integer currentVertex,
            Integer targetVertex,
            boolean[] visitedVertices,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(targetVertex)) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        visitedVertices[currentVertex] = true;

        for (Integer adjacentVertex : adjacencyList[currentVertex]) {
            if (!visitedVertices[adjacentVertex]) {
                currentPath.add(adjacentVertex);
                depthFirstSearch(adjacentVertex, targetVertex, visitedVertices, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visitedVertices[currentVertex] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
            int numberOfVertices,
            int[][] edges,
            int sourceVertex,
            int targetVertex
    ) {
        paths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(numberOfVertices);
        for (int[] edge : edges) {
            int source = edge[0];
            int destination = edge[1];
            graph.addEdge(source, destination);
        }
        graph.findAllPaths(sourceVertex, targetVertex);
        return paths;
    }
}