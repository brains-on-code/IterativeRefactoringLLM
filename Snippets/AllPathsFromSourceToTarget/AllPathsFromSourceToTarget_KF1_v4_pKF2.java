package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all paths between two vertices in a directed graph using backtracking.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** Adjacency list representation of the graph. */
    private final List<Integer>[] adjacencyList;

    /** Stores all paths found between source and destination. */
    private static List<List<Integer>> allPaths = new ArrayList<>();

    /**
     * Constructs a graph with the specified number of vertices.
     *
     * @param vertexCount number of vertices in the graph
     */
    @SuppressWarnings("unchecked")
    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyList = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    /**
     * Adds a directed edge from {@code from} to {@code to}.
     *
     * @param from source vertex
     * @param to   destination vertex
     */
    public void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    /**
     * Finds all paths from {@code source} to {@code destination}.
     *
     * @param source      starting vertex
     * @param destination target vertex
     */
    public void findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(source);
        backtrackPaths(source, destination, visited, currentPath);
    }

    /**
     * Uses backtracking to explore all paths from the current vertex to the destination.
     *
     * @param current     current vertex
     * @param destination target vertex
     * @param visited     visited vertices tracker
     * @param path        current path being explored
     */
    private void backtrackPaths(int current, int destination, boolean[] visited, List<Integer> path) {
        if (current == destination) {
            allPaths.add(new ArrayList<>(path));
            return;
        }

        visited[current] = true;

        for (int neighbor : adjacencyList[current]) {
            if (!visited[neighbor]) {
                path.add(neighbor);
                backtrackPaths(neighbor, destination, visited, path);
                path.remove(path.size() - 1);
            }
        }

        visited[current] = false;
    }

    /**
     * Returns all paths between two vertices in a directed graph.
     *
     * @param vertexCount number of vertices
     * @param edges       list of directed edges, each as [from, to]
     * @param source      starting vertex
     * @param destination target vertex
     * @return list of all paths from source to destination
     */
    public static List<List<Integer>> method5(int vertexCount, int[][] edges, int source, int destination) {
        allPaths = new ArrayList<>();
        Class1 graph = new Class1(vertexCount);

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        graph.findAllPaths(source, destination);
        return allPaths;
    }
}