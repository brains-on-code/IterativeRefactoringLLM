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
    private final int vertexCount;

    // To store the paths from source to destination
    private static final List<List<Integer>> allPaths = new ArrayList<>();

    // Adjacency list representation of the graph
    private ArrayList<Integer>[] adjacencyList;

    public AllPathsFromSourceToTarget(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeAdjacencyList();
    }

    @SuppressWarnings("unchecked")
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList[vertex] = new ArrayList<>();
        }
    }

    // Add directed edge from source to target
    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList[fromVertex].add(toVertex);
    }

    public void findAllPaths(int sourceVertex, int destinationVertex) {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> currentPath = new ArrayList<>();

        currentPath.add(sourceVertex);
        collectPathsDepthFirst(sourceVertex, destinationVertex, visited, currentPath);
    }

    // Recursive DFS to collect all paths from currentVertex to destinationVertex.
    private void collectPathsDepthFirst(
            Integer currentVertex,
            Integer destinationVertex,
            boolean[] visited,
            List<Integer> currentPath
    ) {
        if (currentVertex.equals(destinationVertex)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (Integer neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                collectPathsDepthFirst(neighbor, destinationVertex, visited, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[currentVertex] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(
            int vertexCount,
            int[][] edges,
            int sourceVertex,
            int destinationVertex
    ) {
        allPaths.clear();
        AllPathsFromSourceToTarget graph = new AllPathsFromSourceToTarget(vertexCount);

        for (int[] edge : edges) {
            int fromVertex = edge[0];
            int toVertex = edge[1];
            graph.addEdge(fromVertex, toVertex);
        }

        graph.findAllPaths(sourceVertex, destinationVertex);
        return allPaths;
    }
}