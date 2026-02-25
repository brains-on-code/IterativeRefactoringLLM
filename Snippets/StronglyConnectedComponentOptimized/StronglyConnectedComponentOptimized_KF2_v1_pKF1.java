package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentOptimized {

    private void fillOrder(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitState,
            Stack<Integer> finishOrder,
            int currentNode
    ) {
        visitState[currentNode] = 1;
        List<Integer> neighbors = adjacencyList.get(currentNode);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == -1) {
                    fillOrder(adjacencyList, visitState, finishOrder, neighbor);
                }
            }
        }
        finishOrder.push(currentNode);
    }

    private void dfsOnReversedGraph(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visitState,
            int currentNode,
            List<Integer> currentComponent
    ) {
        visitState[currentNode] = 1;
        currentComponent.add(currentNode);
        List<Integer> neighbors = reversedAdjacencyList.get(currentNode);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == -1) {
                    dfsOnReversedGraph(reversedAdjacencyList, visitState, neighbor, currentComponent);
                }
            }
        }
    }

    public int getOutput(HashMap<Integer, List<Integer>> adjacencyList, int nodeCount) {
        int[] visitState = new int[nodeCount];
        Arrays.fill(visitState, -1);
        Stack<Integer> finishOrder = new Stack<>();

        for (int node = 0; node < nodeCount; node++) {
            if (visitState[node] == -1) {
                fillOrder(adjacencyList, visitState, finishOrder, node);
            }
        }

        HashMap<Integer, List<Integer>> reversedAdjacencyList = new HashMap<>();
        for (int node = 0; node < nodeCount; node++) {
            reversedAdjacencyList.put(node, new ArrayList<>());
        }

        for (int node = 0; node < nodeCount; node++) {
            List<Integer> neighbors = adjacencyList.get(node);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    reversedAdjacencyList.get(neighbor).add(node);
                }
            }
        }

        Arrays.fill(visitState, -1);
        int stronglyConnectedComponents = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visitState[node] == -1) {
                List<Integer> currentComponent = new ArrayList<>();
                dfsOnReversedGraph(reversedAdjacencyList, visitState, node, currentComponent);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}