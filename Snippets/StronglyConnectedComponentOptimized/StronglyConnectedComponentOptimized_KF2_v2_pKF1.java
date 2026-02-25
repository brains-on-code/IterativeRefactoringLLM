package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillFinishOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visitState,
            Stack<Integer> finishOrder,
            int node
    ) {
        visitState[node] = VISITED;
        List<Integer> neighbors = graph.get(node);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == UNVISITED) {
                    fillFinishOrder(graph, visitState, finishOrder, neighbor);
                }
            }
        }
        finishOrder.push(node);
    }

    private void depthFirstSearchReversed(
            HashMap<Integer, List<Integer>> reversedGraph,
            int[] visitState,
            int node,
            List<Integer> component
    ) {
        visitState[node] = VISITED;
        component.add(node);
        List<Integer> neighbors = reversedGraph.get(node);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == UNVISITED) {
                    depthFirstSearchReversed(reversedGraph, visitState, neighbor, component);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> graph, int nodeCount) {
        int[] visitState = new int[nodeCount];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        for (int node = 0; node < nodeCount; node++) {
            if (visitState[node] == UNVISITED) {
                fillFinishOrder(graph, visitState, finishOrder, node);
            }
        }

        HashMap<Integer, List<Integer>> reversedGraph = new HashMap<>();
        for (int node = 0; node < nodeCount; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < nodeCount; node++) {
            List<Integer> neighbors = graph.get(node);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    reversedGraph.get(neighbor).add(node);
                }
            }
        }

        Arrays.fill(visitState, UNVISITED);
        int stronglyConnectedComponentCount = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visitState[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                depthFirstSearchReversed(reversedGraph, visitState, node, component);
                stronglyConnectedComponentCount++;
            }
        }

        return stronglyConnectedComponentCount;
    }
}