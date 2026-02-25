package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Finds the strongly connected components in a directed graph.
 *
 * @param adjacencyList The adjacency list representation of the graph.
 * @param nodeCount The number of nodes in the graph.
 * @return The number of strongly connected components.
 */
public class StronglyConnectedComponentOptimized {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillFinishOrder(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitState,
            Stack<Integer> finishOrder,
            int currentNode
    ) {
        visitState[currentNode] = VISITED;
        List<Integer> adjacentNodes = adjacencyList.get(currentNode);

        if (adjacentNodes != null) {
            for (int adjacentNode : adjacentNodes) {
                if (visitState[adjacentNode] == UNVISITED) {
                    fillFinishOrder(adjacencyList, visitState, finishOrder, adjacentNode);
                }
            }
        }

        finishOrder.push(currentNode);
    }

    private void depthFirstSearchReversed(
            HashMap<Integer, List<Integer>> reversedAdjacencyList,
            int[] visitState,
            int currentNode,
            List<Integer> component
    ) {
        visitState[currentNode] = VISITED;
        component.add(currentNode);
        List<Integer> adjacentNodes = reversedAdjacencyList.get(currentNode);

        if (adjacentNodes != null) {
            for (int adjacentNode : adjacentNodes) {
                if (visitState[adjacentNode] == UNVISITED) {
                    depthFirstSearchReversed(reversedAdjacencyList, visitState, adjacentNode, component);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> adjacencyList, int nodeCount) {
        int[] visitState = new int[nodeCount];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        for (int node = 0; node < nodeCount; node++) {
            if (visitState[node] == UNVISITED) {
                fillFinishOrder(adjacencyList, visitState, finishOrder, node);
            }
        }

        HashMap<Integer, List<Integer>> reversedAdjacencyList = new HashMap<>();
        for (int node = 0; node < nodeCount; node++) {
            reversedAdjacencyList.put(node, new ArrayList<>());
        }

        for (int node = 0; node < nodeCount; node++) {
            List<Integer> adjacentNodes = adjacencyList.get(node);
            if (adjacentNodes != null) {
                for (int adjacentNode : adjacentNodes) {
                    reversedAdjacencyList.get(adjacentNode).add(node);
                }
            }
        }

        Arrays.fill(visitState, UNVISITED);
        int stronglyConnectedComponentsCount = 0;

        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (visitState[node] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                depthFirstSearchReversed(reversedAdjacencyList, visitState, node, component);
                stronglyConnectedComponentsCount++;
            }
        }

        return stronglyConnectedComponentsCount;
    }
}