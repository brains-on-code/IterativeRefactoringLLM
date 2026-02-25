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

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    /**
     * Depth-first search on the original graph.
     * Populates {@code finishOrder} so that vertices appear in order of completion time.
     */
    private void dfsFillOrder(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visited,
            Stack<Integer> finishOrder,
            int currentNode
    ) {
        visited[currentNode] = VISITED;
        List<Integer> neighbors = adjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsFillOrder(adjacencyList, visited, finishOrder, neighbor);
                }
            }
        }

        finishOrder.push(currentNode);
    }

    /**
     * Depth-first search on the reversed graph.
     * Collects all vertices belonging to the same strongly connected component as {@code currentNode}.
     */
    private void dfsCollectScc(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visited,
            int currentNode,
            List<Integer> currentScc
    ) {
        visited[currentNode] = VISITED;
        currentScc.add(currentNode);
        List<Integer> neighbors = reversedAdjacencyList.get(currentNode);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
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
        Arrays.fill(visited, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        // First pass: DFS on the original graph to compute vertex finish times.
        for (int node = 0; node < nodeCount; node++) {
            if (visited[node] == UNVISITED) {
                dfsFillOrder(adjacencyList, visited, finishOrder, node);
            }
        }

        // Build the reversed graph.
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

        // Second pass: DFS on the reversed graph in decreasing order of finish time.
        Arrays.fill(visited, UNVISITED);
        int stronglyConnectedComponents = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visited[node] == UNVISITED) {
                List<Integer> currentScc = new ArrayList<>();
                dfsCollectScc(reversedAdjacencyList, visited, node, currentScc);
                stronglyConnectedComponents++;
            }
        }

        return stronglyConnectedComponents;
    }
}