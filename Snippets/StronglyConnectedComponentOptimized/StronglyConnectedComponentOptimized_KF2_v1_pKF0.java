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
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visited,
            Stack<Integer> nodeStack,
            int currentNode
    ) {
        visited[currentNode] = VISITED;
        List<Integer> neighbors = adjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    fillOrder(adjacencyList, visited, nodeStack, neighbor);
                }
            }
        }

        nodeStack.push(currentNode);
    }

    private void dfsOnReversedGraph(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visited,
            int currentNode,
            List<Integer> currentComponent
    ) {
        visited[currentNode] = VISITED;
        currentComponent.add(currentNode);
        List<Integer> neighbors = reversedAdjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsOnReversedGraph(reversedAdjacencyList, visited, neighbor, currentComponent);
                }
            }
        }
    }

    public int getOutput(HashMap<Integer, List<Integer>> adjacencyList, int nodeCount) {
        int[] visited = new int[nodeCount];
        Arrays.fill(visited, UNVISITED);
        Stack<Integer> nodeStack = new Stack<>();

        for (int node = 0; node < nodeCount; node++) {
            if (visited[node] == UNVISITED) {
                fillOrder(adjacencyList, visited, nodeStack, node);
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

        Arrays.fill(visited, UNVISITED);
        int stronglyConnectedComponents = 0;

        while (!nodeStack.isEmpty()) {
            int node = nodeStack.pop();
            if (visited[node] == UNVISITED) {
                List<Integer> currentComponent = new ArrayList<>();
                dfsOnReversedGraph(reversedAdjacencyList, visited, node, currentComponent);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}