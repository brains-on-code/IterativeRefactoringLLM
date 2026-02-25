package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Computes the number of strongly connected components (SCCs) in a directed
 * graph using Kosaraju's algorithm.
 */
public class StronglyConnectedComponents {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    /**
     * Depth-first search on the original graph.
     *
     * Visits all vertices reachable from {@code node} and pushes each vertex
     * onto {@code stack} after exploring all its descendants. The resulting
     * stack is ordered by decreasing finishing time.
     */
    private void fillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visited,
            Stack<Integer> stack,
            int node
    ) {
        visited[node] = VISITED;
        List<Integer> neighbors = graph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    fillOrder(graph, visited, stack, neighbor);
                }
            }
        }

        stack.push(node);
    }

    /**
     * Depth-first search on the transposed graph.
     *
     * Starting from {@code node}, collects all reachable vertices in
     * {@code component}. These vertices form a single strongly connected
     * component.
     */
    private void dfsOnTransposed(
            HashMap<Integer, List<Integer>> transposedGraph,
            int[] visited,
            int node,
            List<Integer> component
    ) {
        visited[node] = VISITED;
        component.add(node);
        List<Integer> neighbors = transposedGraph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsOnTransposed(transposedGraph, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Computes the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph       adjacency list of the original graph
     * @param vertexCount number of vertices in the graph (0-based indexed)
     * @return number of strongly connected components
     */
    public int countStronglyConnectedComponents(
            HashMap<Integer, List<Integer>> graph,
            int vertexCount
    ) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, UNVISITED);
        Stack<Integer> stack = new Stack<>();

        // First pass: DFS on the original graph to order vertices by finish time.
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == UNVISITED) {
                fillOrder(graph, visited, stack, vertex);
            }
        }

        // Build the transposed graph (reverse all edges).
        HashMap<Integer, List<Integer>> transposedGraph = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposedGraph.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> neighbors = graph.get(vertex);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    transposedGraph.get(neighbor).add(vertex);
                }
            }
        }

        // Second pass: DFS on the transposed graph in stack order to count SCCs.
        Arrays.fill(visited, UNVISITED);
        int sccCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsOnTransposed(transposedGraph, visited, vertex, component);
                sccCount++;
            }
        }

        return sccCount;
    }
}