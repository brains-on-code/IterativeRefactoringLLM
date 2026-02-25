package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    /**
     * Depth-first search on the original graph.
     * Pushes each vertex onto {@code finishOrder} after exploring all its descendants.
     */
    private void dfsFillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visited,
            Stack<Integer> finishOrder,
            int node
    ) {
        visited[node] = VISITED;
        List<Integer> neighbors = graph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsFillOrder(graph, visited, finishOrder, neighbor);
                }
            }
        }

        finishOrder.push(node);
    }

    /**
     * Depth-first search on the reversed graph.
     * Collects all vertices reachable from {@code node} into {@code component}.
     */
    private void dfsCollectComponent(
            HashMap<Integer, List<Integer>> reversedGraph,
            int[] visited,
            int node,
            List<Integer> component
    ) {
        visited[node] = VISITED;
        component.add(node);
        List<Integer> neighbors = reversedGraph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsCollectComponent(reversedGraph, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Computes the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph adjacency list of the directed graph
     * @param n     number of vertices (assumed to be 0..n-1)
     * @return number of strongly connected components
     */
    public int getOutput(HashMap<Integer, List<Integer>> graph, int n) {
        int[] visited = new int[n];
        Arrays.fill(visited, UNVISITED);

        Stack<Integer> finishOrder = new Stack<>();

        // First pass: DFS on the original graph to determine vertex finish order.
        for (int node = 0; node < n; node++) {
            if (visited[node] == UNVISITED) {
                dfsFillOrder(graph, visited, finishOrder, node);
            }
        }

        // Build the reversed graph.
        HashMap<Integer, List<Integer>> reversedGraph = new HashMap<>();
        for (int node = 0; node < n; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < n; node++) {
            List<Integer> neighbors = graph.get(node);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    reversedGraph.get(neighbor).add(node);
                }
            }
        }

        // Second pass: DFS on the reversed graph in decreasing finish time.
        Arrays.fill(visited, UNVISITED);
        int stronglyConnectedComponents = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visited[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsCollectComponent(reversedGraph, visited, node, component);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}