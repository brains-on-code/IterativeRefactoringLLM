package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentOptimized {

    /**
     * First DFS pass (on the original graph).
     * Fills the stack with vertices ordered by finish time.
     */
    private void dfsFillOrder(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visited,
            Stack<Integer> finishOrder,
            int currentNode
    ) {
        visited[currentNode] = 1;
        List<Integer> neighbors = adjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    dfsFillOrder(adjacencyList, visited, finishOrder, neighbor);
                }
            }
        }

        finishOrder.push(currentNode);
    }

    /**
     * Second DFS pass (on the reversed graph).
     * Collects all vertices in the current strongly connected component.
     */
    private void dfsCollectComponent(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visited,
            int currentNode,
            List<Integer> component
    ) {
        visited[currentNode] = 1;
        component.add(currentNode);
        List<Integer> neighbors = reversedAdjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    dfsCollectComponent(reversedAdjacencyList, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Returns the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param adjacencyList adjacency list of the directed graph
     * @param n             number of vertices (assumed to be 0..n-1)
     * @return number of strongly connected components
     */
    public int getOutput(HashMap<Integer, List<Integer>> adjacencyList, int n) {
        int[] visited = new int[n];
        Arrays.fill(visited, -1);

        Stack<Integer> finishOrder = new Stack<>();

        // First pass: fill stack with vertices in order of completion time
        for (int i = 0; i < n; i++) {
            if (visited[i] == -1) {
                dfsFillOrder(adjacencyList, visited, finishOrder, i);
            }
        }

        // Build reversed graph
        HashMap<Integer, List<Integer>> reversedAdjacencyList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            reversedAdjacencyList.put(i, new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            List<Integer> neighbors = adjacencyList.get(i);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    reversedAdjacencyList.get(neighbor).add(i);
                }
            }
        }

        // Second pass: process vertices in decreasing finish time on reversed graph
        Arrays.fill(visited, -1);
        int stronglyConnectedComponents = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visited[node] == -1) {
                List<Integer> component = new ArrayList<>();
                dfsCollectComponent(reversedAdjacencyList, visited, node, component);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}