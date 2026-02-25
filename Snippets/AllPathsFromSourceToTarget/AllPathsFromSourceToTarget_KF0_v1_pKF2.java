package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all possible paths from a source vertex to a destination vertex in a directed graph.
 * <a href="https://en.wikipedia.org/wiki/Shortest_path_problem">Wikipedia</a>
 *
 * @author <a href="https://github.com/siddhant2002">Siddhant Swarup Mallick</a>
 */
public class AllPathsFromSourceToTarget {

    /** Number of vertices in the graph. */
    private final int vertexCount;

    /** Stores all paths found from source to destination. */
    private static final List<List<Integer>> allPaths = new ArrayList<>();

    /** Adjacency list representation of the graph. */
    private ArrayList<Integer>[] adjacencyList;

    /**
     * Creates a graph with the specified number of vertices.
     *
     * @param vertices number of vertices
     */
    public AllPathsFromSourceToTarget(int vertices) {
        this.vertexCount = vertices;
        initAdjacencyList();
    }

    /**
     * Initializes the adjacency list for all vertices.
     */
    @SuppressWarnings("unchecked")
    private void initAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
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
     * Computes and stores all paths from {@code source} to {@code destination}.
     *
     * @param source      starting vertex
     * @param destination target vertex
     */
    public void storeAllPaths(int source, int destination) {
        boolean[] isVisited = new boolean[vertexCount];
        List<Integer> path = new ArrayList<>();
        path.add(source);
        storeAllPathsUtil(source, destination, isVisited, path);
    }

    /**
     * Recursive helper that explores all paths from the current vertex to the destination.
     *
     * @param current     current vertex
     * @param destination target vertex
     * @param isVisited   marks vertices already in the current path
     * @param path        current path being explored
     */
    private void storeAllPathsUtil(
        Integer current,
        Integer destination,
        boolean[] isVisited,
        List<Integer> path
    ) {
        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(path));
            return;
        }

        isVisited[current] = true;

        for (Integer neighbor : adjacencyList[current]) {
            if (!isVisited[neighbor]) {
                path.add(neighbor);
                storeAllPathsUtil(neighbor, destination, isVisited, path);
                path.remove(path.size() - 1);
            }
        }

        isVisited[current] = false;
    }

    /**
     * Builds a graph from the given edges and returns all paths from {@code source} to {@code destination}.
     *
     * @param vertices    number of vertices
     * @param edges       edge list where each element is [from, to]
     * @param source      starting vertex
     * @param destination target vertex
     * @return list of all paths from source to destination
     */
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