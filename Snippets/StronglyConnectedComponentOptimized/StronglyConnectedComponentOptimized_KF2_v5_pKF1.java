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
            int currentNode
    ) {
        visitState[currentNode] = VISITED;
        List<Integer> adjacentNodes = graph.get(currentNode);
        if (adjacentNodes != null) {
            for (int adjacentNode : adjacentNodes) {
                if (visitState[adjacentNode] == UNVISITED) {
                    fillFinishOrder(graph, visitState, finishOrder, adjacentNode);
                }
            }
        }
        finishOrder.push(currentNode);
    }

    private void depthFirstSearchReversed(
            HashMap<Integer, List<Integer>> reversedGraph,
            int[] visitState,
            int currentNode,
            List<Integer> component
    ) {
        visitState[currentNode] = VISITED;
        component.add(currentNode);
        List<Integer> adjacentNodes = reversedGraph.get(currentNode);
        if (adjacentNodes != null) {
            for (int adjacentNode : adjacentNodes) {
                if (visitState[adjacentNode] == UNVISITED) {
                    depthFirstSearchReversed(reversedGraph, visitState, adjacentNode, component);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> graph, int totalNodes) {
        int[] visitState = new int[totalNodes];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        for (int node = 0; node < totalNodes; node++) {
            if (visitState[node] == UNVISITED) {
                fillFinishOrder(graph, visitState, finishOrder, node);
            }
        }

        HashMap<Integer, List<Integer>> reversedGraph = new HashMap<>();
        for (int node = 0; node < totalNodes; node++) {
            reversedGraph.put(node, new ArrayList<>());
        }

        for (int node = 0; node < totalNodes; node++) {
            List<Integer> adjacentNodes = graph.get(node);
            if (adjacentNodes != null) {
                for (int adjacentNode : adjacentNodes) {
                    reversedGraph.get(adjacentNode).add(node);
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