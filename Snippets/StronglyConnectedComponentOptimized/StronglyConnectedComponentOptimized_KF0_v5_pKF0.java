package com.thealgorithms.graph;

import java.util.*;

/**
 * Finds the strongly connected components in a directed graph using Kosaraju's algorithm.
 */
public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillOrder(
            Map<Integer, List<Integer>> graph,
            int[] visitState,
            Deque<Integer> stack,
            int node
    ) {
        visitState[node] = VISITED;

        for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
            if (visitState[neighbor] == UNVISITED) {
                fillOrder(graph, visitState, stack, neighbor);
            }
        }

        stack.push(node);
    }

    private void collectComponent(
            Map<Integer, List<Integer>> reversedGraph,
            int[] visitState,
            int node,
            List<Integer> component
    ) {
        visitState[node] = VISITED;
        component.add(node);

        for (int neighbor : reversedGraph.getOrDefault(node, Collections.emptyList())) {
            if (visitState[neighbor] == UNVISITED) {
                collectComponent(reversedGraph, visitState, neighbor, component);
            }
        }
    }

    private Map<Integer, List<Integer>> buildReversedGraph(
            Map<Integer, List<Integer>> adjList,
            int nodeCount
    ) {
        Map<Integer, List<Integer>> reversedGraph = new HashMap<>(nodeCount);

        for (int node = 0; node < nodeCount; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < nodeCount; node++) {
            for (int neighbor : adjList.getOrDefault(node, Collections.emptyList())) {
                reversedGraph.get(neighbor).add(node);
            }
        }

        return reversedGraph;
    }

    public int getOutput(Map<Integer, List<Integer>> adjList, int nodeCount) {
        int[] visitState = new int[nodeCount];
        Arrays.fill(visitState, UNVISITED);

        Deque<Integer> stack = new ArrayDeque<>();
        for (int node = 0; node < nodeCount; node++) {
            if (visitState[node] == UNVISITED) {
                fillOrder(adjList, visitState, stack, node);
            }
        }

        Map<Integer, List<Integer>> reversedGraph = buildReversedGraph(adjList, nodeCount);
        Arrays.fill(visitState, UNVISITED);

        int stronglyConnectedComponents = 0;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visitState[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                collectComponent(reversedGraph, visitState, node, component);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}