package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Program description - To find all possible paths from source to destination
 * <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Wikipedia</a>
 *
 * @author <a href="https://github.com/siddhant2002">Siddhant Swarup Mallick</a>
 */
public class AllPathsFromSourceToTarget {

    // Number of vertices in the graph
    private final int numberOfVertices;

    // To store the paths from source to destination
    private static final List<List<Integer>> paths = new ArrayList<>();

    // Adjacency list representation of the graph
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

    // Add directed edge from source to target
    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList[fromVertex].add(toVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visitedVertices = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(sourceVertex);
        depthFirstSearchCollectPaths(sourceVertex, destinationVertex, visitedVertices, currentPath);
    }

    // Recursive DFS to collect all paths from currentVertex to destinationVertex.
    private void depthFirstSearchCollectPaths(
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

        for (Integer neighborVertex : adjacencyList[currentVertex]) {
            if (!visitedVertices[neighborVertex]) {
                currentPath.add(neighborVertex);
                depthFirstSearchCollectPaths(neighborVertex, destinationVertex, visitedVertices, currentPath);
                currentPath.remove(currentPath.size() - 1);
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
            int fromVertex = edge[0];
            int toVertex = edge[1];
            graph.addEdge(fromVertex, toVertex);
        }

        graph.findAllPaths(sourceVertex, destinationVertex);
        return paths;
    }
}