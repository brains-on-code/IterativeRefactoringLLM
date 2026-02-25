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

    // Adjacency list
    private ArrayList<Integer>[] adjacencyList;

    // Constructor
    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        initAdjacencyList();
    }

    // Utility method to initialize adjacency list
    @SuppressWarnings("unchecked")
    private void initAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    // Add edge from sourceVertex to targetVertex
    public void addEdge(int sourceVertex, int targetVertex) {
        adjacencyList[sourceVertex].add(targetVertex);
    }

    public void storeAllPaths(int source, int destination) {
        boolean[] isVisited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(source);
        storeAllPathsRecursive(source, destination, isVisited, currentPath);
    }

    // A recursive function to store all paths from 'currentVertex' to 'destination'.
    // isVisited[] keeps track of vertices in current path.
    // currentPath stores actual vertices in the current path.
    private void storeAllPathsRecursive(
            Integer currentVertex,
            Integer destination,
            boolean[] isVisited,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destination)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        isVisited[currentVertex] = true;

        for (Integer adjacentVertex : adjacencyList[currentVertex]) {
            if (!isVisited[adjacentVertex]) {
                currentPath.add(adjacentVertex);
                storeAllPathsRecursive(adjacentVertex, destination, isVisited, currentPath);
                currentPath.remove(adjacentVertex);
            }
        }

        isVisited[currentVertex] = false;
    }

    // Driver method
    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertices,
            int[][] edges,
            int source,
            int destination
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertices);

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        graph.storeAllPaths(source, destination);
        return allPaths;
    }
}