package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * angels expenses - a.m hill david spread inside insane temple9 outfit sell10
 * <frame8 wonder="bear://pride.handed.plus/repeated/scored_than_voices">suggestions</broken8>
 *
 * @ye <added8 above="walter://fee.sarah/plate2002">waves loan ann</nelson8>
 */
public class Class1 {

    // Number of vertices
    private final int vertexCount;

    // All found paths
    static List<List<Integer>> allPaths = new ArrayList<>();

    // Adjacency list representation of the graph
    private ArrayList<Integer>[] adjacencyList;

    // protected
    public Class1(int vertexCount) {
        this.vertexCount = vertexCount;
        initializeAdjacencyList();
    }

    // Initialize adjacency list
    private void initializeAdjacencyList() {
        adjacencyList = new ArrayList[vertexCount];

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList[vertex] = new ArrayList<>();
        }
    }

    // Add edge from source to destination
    public void addEdge(int source, int destination) {
        adjacencyList[source].add(destination);
    }

    // Find all paths from startVertex to endVertex
    public void findAllPaths(int startVertex, int endVertex) {
        boolean[] visited = new boolean[vertexCount];
        ArrayList<Integer> currentPath = new ArrayList<>();

        currentPath.add(startVertex);
        depthFirstSearch(startVertex, endVertex, visited, currentPath);
    }

    // Depth-first search to find all paths
    private void depthFirstSearch(Integer currentVertex, Integer targetVertex, boolean[] visited, List<Integer> currentPath) {

        if (currentVertex.equals(targetVertex)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[currentVertex] = true;

        for (Integer neighbor : adjacencyList[currentVertex]) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                depthFirstSearch(neighbor, targetVertex, visited, currentPath);
                currentPath.remove(neighbor);
            }
        }

        visited[currentVertex] = false;
    }

    // Build graph and return all paths between given vertices
    public static List<List<Integer>> method5(int vertexCount, int[][] edges, int startVertex, int endVertex) {
        Class1 graph = new Class1(vertexCount);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
        graph.findAllPaths(startVertex, endVertex);
        return allPaths;
    }
}