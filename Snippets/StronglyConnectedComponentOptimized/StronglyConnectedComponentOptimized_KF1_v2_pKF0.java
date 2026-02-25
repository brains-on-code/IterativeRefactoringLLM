package com.thealgorithms.graph;

import java.util.*;

/**
 * Strongly Connected Components (SCC) counter using Kosaraju's algorithm.
 */
public class StronglyConnectedComponents {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    /**
     * First DFS pass: fills stack with vertices in order of completion time.
     */
    private void fillOrder(
            Map<Integer, List<Integer>> graph,
            int[] visited,
            Deque<Integer> stack,
            int vertex
    ) {
        visited[vertex] = VISITED;
        List<Integer> neighbors = graph.getOrDefault(vertex, Collections.emptyList());

        for (int neighbor : neighbors) {
            if (visited[neighbor] == UNVISITED) {
                fillOrder(graph, visited, stack, neighbor);
            }
        }

        stack.push(vertex);
    }

    /**
     * Second DFS pass on the transposed graph: collects all vertices in the
     * current strongly connected component.
     */
    private void dfsOnTranspose(
            Map<Integer, List<Integer>> transposeGraph,
            int[] visited,
            int vertex,
            List<Integer> component
    ) {
        visited[vertex] = VISITED;
        component.add(vertex);
        List<Integer> neighbors = transposeGraph.getOrDefault(vertex, Collections.emptyList());

        for (int neighbor : neighbors) {
            if (visited[neighbor] == UNVISITED) {
                dfsOnTranspose(transposeGraph, visited, neighbor, component);
            }
        }
    }

    /**
     * Counts the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph the adjacency list of the directed graph
     * @param vertexCount number of vertices in the graph
     * @return number of strongly connected components
     */
    public int countStronglyConnectedComponents(
            Map<Integer, List<Integer>> graph,
            int vertexCount
    ) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, UNVISITED);
        Deque<Integer> stack = new ArrayDeque<>();

        // First pass: fill stack with vertices in order of finishing times
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == UNVISITED) {
                fillOrder(graph, visited, stack, vertex);
            }
        }

        // Build transpose graph
        Map<Integer, List<Integer>> transposeGraph = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposeGraph.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> neighbors = graph.getOrDefault(vertex, Collections.emptyList());
            for (int neighbor : neighbors) {
                transposeGraph.get(neighbor).add(vertex);
            }
        }

        // Second pass: process vertices in order defined by the stack
        Arrays.fill(visited, UNVISITED);
        int sccCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsOnTranspose(transposeGraph, visited, vertex, component);
                sccCount++;
            }
        }

        return sccCount;
    }
}