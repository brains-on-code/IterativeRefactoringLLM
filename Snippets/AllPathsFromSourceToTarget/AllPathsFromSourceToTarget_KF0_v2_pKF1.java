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

    // Number of vertices in graph
    private final int vertexCount;

    // To store the paths from source to destination
    private static final List<List<Integer>> allPaths = new ArrayList<>();

    // Adjacency list representation of the graph
    private ArrayList<Integer>[] adjacencyList;

    // Constructor
    public AllPathsFromSourceToTarget(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeAdjacencyList();
    }

    // Utility method to initialize adjacency list
    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList[vertex] = new ArrayList<>();
        }
    }

    // Add directed edge from sourceVertex to targetVertex
    public void addEdge(int sourceVertex, int targetVertex) {
        adjacencyList[sourceVertex].add(targetVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visitedVertices = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(sourceVertex);
        collectAllPaths(sourceVertex, destinationVertex, visitedVertices, currentPath);
    }

    // A recursive function to store all paths from 'currentVertex' to 'destinationVertex'.
    // visitedVertices[] keeps track of vertices in current path.
    // currentPath stores actual vertices in the current path.
    private void collectAllPaths(
            Integer currentVertex,
            Integer destinationVertex,
            boolean[] visitedVertices,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destinationVertex)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visitedVertices[currentVertex] = true;

        for (Integer neighborVertex : adjacencyList[currentVertex]) {
            if (!visitedVertices[neighborVertex]) {
                currentPath.add(neighborVertex);
                collectAllPaths(neighborVertex, destinationVertex, visitedVertices, currentPath);
                currentPath.remove(neighborVertex);
            }
        }

        visitedVertices[currentVertex] = false;
    }

    // Driver method
    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertexCount,
            int[][] edges,
            int sourceVertex,
            int destinationVertex
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        graph.findAllPaths(sourceVertex, destinationVertex);
        return allPaths;
    }
}