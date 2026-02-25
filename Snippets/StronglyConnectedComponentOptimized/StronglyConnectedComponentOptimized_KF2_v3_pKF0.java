package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visitState,
            Stack<Integer> stack,
            int node
    ) {
        visitState[node] = VISITED;
        List<Integer> neighbors = graph.get(node);

        if (neighbors == null) {
            stack.push(node);
            return;
        }

        for (int neighbor : neighbors) {
            if (visitState[neighbor] == UNVISITED) {
                fillOrder(graph, visitState, stack, neighbor);
            }
        }

        stack.push(node);
    }

    private void dfsOnReversedGraph(
            HashMap<Integer, List<Integer>> reversedGraph,
            int[] visitState,
            int node,
            List<Integer> component
    ) {
        visitState[node] = VISITED;
        component.add(node);
        List<Integer> neighbors = reversedGraph.get(node);

        if (neighbors == null) {
            return;
        }

        for (int neighbor : neighbors) {
            if (visitState[neighbor] == UNVISITED) {
                dfsOnReversedGraph(reversedGraph, visitState, neighbor, component);
            }
        }
    }

    private HashMap<Integer, List<Integer>> buildReversedGraph(
            HashMap<Integer, List<Integer>> graph,
            int nodeCount
    ) {
        HashMap<Integer, List<Integer>> reversedGraph = new HashMap<>();

        for (int node = 0; node < nodeCount; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < nodeCount; node++) {
            List<Integer> neighbors = graph.get(node);
            if (neighbors == null) {
                continue;
            }
            List<Integer> reversedNeighbors;
            for (int neighbor : neighbors) {
                reversedNeighbors = reversedGraph.get(neighbor);
                if (reversedNeighbors != null) {
                    reversedNeighbors.add(node);
                }
            }
        }

        return reversedGraph;
    }

    public int getOutput(HashMap<Integer, List<Integer>> adjacencyList, int nodeCount) {
        int[] visitState = new int[nodeCount];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> stack = new Stack<>();

        for (int node = 0; node < nodeCount; node++) {
            if (visitState[node] == UNVISITED) {
                fillOrder(adjacencyList, visitState, stack, node);
            }
        }

        HashMap<Integer, List<Integer>> reversedGraph = buildReversedGraph(adjacencyList, nodeCount);
        Arrays.fill(visitState, UNVISITED);

        int stronglyConnectedComponents = 0;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visitState[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsOnReversedGraph(reversedGraph, visitState, node, component);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}