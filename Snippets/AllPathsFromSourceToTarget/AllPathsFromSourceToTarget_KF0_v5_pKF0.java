package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all possible paths from a source vertex to a destination vertex
 * in a directed graph.
 *
 * <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Wikipedia</a>
 *
 * @author <a href="https://github.com/siddhant2002">Siddhant Swarup Mallick</a>
 */
public class AllPathsFromSourceToTarget {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** Adjacency list representation of the graph. */
    private final List<List<Integer>> adjacencyList;

    /** Constructs a graph with the specified number of vertices. */
    public AllPathsFromSourceToTarget(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyList = new ArrayList<>(vertexCount);
        initializeAdjacencyList();
    }

    /** Initializes the adjacency list for each vertex. */
    private void initializeAdjacencyList() {
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    /** Adds a directed edge from {@code from} to {@code to}. */
    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
    }

    /**
     * Computes all paths from {@code source} to {@code destination}.
     *
     * @return list of all paths, each path is a list of vertex indices
     */
    public List<List<Integer>> findAllPaths(int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();

        currentPath.add(source);
        depthFirstSearch(source, destination, visited, currentPath, allPaths);

        return allPaths;
    }

    /**
     * Depth-first search helper to collect all paths.
     */
    private void depthFirstSearch(
            int currentVertex,
            int destination,
            boolean[] visited,
            List<Integer> currentPath,
            List<List<Integer>> allPaths
    ) {
        if (currentVertex == destination) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (int neighbor : adjacencyList.get(currentVertex)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, destination, visited, currentPath, allPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    /**
     * Static helper to build a graph and return all paths from source to destination.
     */
    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertexCount,
            int[][] edges,
            int source,
            int destination
    ) {
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        return graph.findAllPaths(source, destination);
    }
}