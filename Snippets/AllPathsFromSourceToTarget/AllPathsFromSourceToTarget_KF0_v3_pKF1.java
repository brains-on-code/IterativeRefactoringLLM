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
    private static final List<List<Integer>> pathsFromSourceToTarget = new ArrayList<>();

    // Adjacency list representation of the graph
    private ArrayList<Integer>[] adjacencyList;

    // Constructor
    public AllPathsFromSourceToTarget(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        initializeAdjacencyList();
    }

    // Utility method to initialize adjacency list
    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[numberOfVertices];
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            adjacencyList[vertexIndex] = new ArrayList<>();
        }
    }

    // Add directed edge from sourceVertex to targetVertex
    public void addEdge(int sourceVertex, int targetVertex) {
        adjacencyList[sourceVertex].add(targetVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visited = new boolean[numberOfVertices];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(sourceVertex);
        depthFirstSearchCollectPaths(sourceVertex, destinationVertex, visited, currentPath);
    }

    // A recursive function to store all paths from 'currentVertex' to 'destinationVertex'.
    // visited[] keeps track of vertices in current path.
    // currentPath stores actual vertices in the current path.
    private void depthFirstSearchCollectPaths(
            Integer currentVertex,
            Integer destinationVertex,
            boolean[] visited,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destinationVertex)) {
            pathsFromSourceToTarget.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (Integer neighborVertex : adjacencyList[currentVertex]) {
            if (!visited[neighborVertex]) {
                currentPath.add(neighborVertex);
                depthFirstSearchCollectPaths(neighborVertex, destinationVertex, visited, currentPath);
                currentPath.remove(neighborVertex);
            }
        }

        visited[currentVertex] = false;
    }

    // Driver method
    public static List<List<Integer>> allPathsFromSourceToTarget(
            int numberOfVertices,
            int[][] edges,
            int sourceVertex,
            int destinationVertex
    ) {
        pathsFromSourceToTarget.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(numberOfVertices);

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        graph.findAllPaths(sourceVertex, destinationVertex);
        return pathsFromSourceToTarget;
    }
}