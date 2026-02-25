package com.thealgorithms.graph;

import java.util.*;

/**
 * Finds the strongly connected components in a directed graph.
 */
public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillOrder(
            Map<Integer, List<Integer>> graph,
            int[] visited,
            Deque<Integer> stack,
            int node
    ) {
        visited[node] = VISITED;

        List<Integer> neighbors = graph.getOrDefault(node, Collections.emptyList());
        for (int neighbor : neighbors) {
            if (visited[neighbor] == UNVISITED) {
                fillOrder(graph, visited, stack, neighbor);
            }
        }

        stack.push(node);
    }

    private void dfsOnReversed(
            Map<Integer, List<Integer>> reversedGraph,
            int[] visited,
            int node,
            List<Integer> component
    ) {
        visited[node] = VISITED;
        component.add(node);

        List<Integer> neighbors = reversedGraph.getOrDefault(node, Collections.emptyList());
        for (int neighbor : neighbors) {
            if (visited[neighbor] == UNVISITED) {
                dfsOnReversed(reversedGraph, visited, neighbor, component);
            }
        }
    }

    private Map<Integer, List<Integer>> buildReversedGraph(
            Map<Integer, List<Integer>> adjList,
            int n
    ) {
        Map<Integer, List<Integer>> reversedGraph = new HashMap<>(n);
        for (int node = 0; node < n; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < n; node++) {
            List<Integer> neighbors = adjList.getOrDefault(node, Collections.emptyList());
            for (int neighbor : neighbors) {
                reversedGraph.get(neighbor).add(node);
            }
        }

        return reversedGraph;
    }

    public int getOutput(Map<Integer, List<Integer>> adjList, int n) {
        int[] visited = new int[n];
        Arrays.fill(visited, UNVISITED);

        Deque<Integer> stack = new ArrayDeque<>();
        for (int node = 0; node < n; node++) {
            if (visited[node] == UNVISITED) {
                fillOrder(adjList, visited, stack, node);
            }
        }

        Map<Integer, List<Integer>> reversedGraph = buildReversedGraph(adjList, n);
        Arrays.fill(visited, UNVISITED);

        int stronglyConnectedComponents = 0;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsOnReversed(reversedGraph, visited, node, component);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}