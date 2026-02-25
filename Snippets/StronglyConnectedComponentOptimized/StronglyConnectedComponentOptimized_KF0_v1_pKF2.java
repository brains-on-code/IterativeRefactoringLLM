package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Computes the number of strongly connected components (SCCs) in a directed graph
 * using Kosaraju's algorithm.
 */
public class StronglyConnectedComponentOptimized {

    /**
     * First DFS pass: fills the stack with vertices ordered by finish time.
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
     * Second DFS pass on the reversed graph: collects all nodes in the current SCC.
     */
    private void dfsCollectScc(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visited,
            int currentNode,
            List<Integer> currentScc
    ) {
        visited[currentNode] = 1;
        currentScc.add(currentNode);
        List<Integer> neighbors = reversedAdjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    dfsCollectScc(reversedAdjacencyList, visited, neighbor, currentScc);
                }
            }
        }
    }

    /**
     * Returns the number of strongly connected components in the given directed graph.
     *
     * @param adjacencyList adjacency list representation of the graph
     * @param nodeCount     number of nodes in the graph (nodes are assumed to be 0..nodeCount-1)
     * @return number of strongly connected components
     */
    public int getOutput(HashMap<Integer, List<Integer>> adjacencyList, int nodeCount) {
        int[] visited = new int[nodeCount];
        Arrays.fill(visited, -1);
        Stack<Integer> finishOrder = new Stack<>();

        // First pass: fill stack with vertices ordered by finish time
        for (int node = 0; node < nodeCount; node++) {
            if (visited[node] == -1) {
                dfsFillOrder(adjacencyList, visited, finishOrder, node);
            }
        }

        // Build reversed graph
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

        // Second pass: process nodes in decreasing finish time to find SCCs
        Arrays.fill(visited, -1);
        int stronglyConnectedComponents = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visited[node] == -1) {
                List<Integer> currentScc = new ArrayList<>();
                dfsCollectScc(reversedAdjacencyList, visited, node, currentScc);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}