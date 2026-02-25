package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Kosaraju's algorithm for counting strongly connected components in a directed graph.
 */
public class StronglyConnectedComponentsCounter {

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    private void fillVerticesByFinishTime(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitState,
            Stack<Integer> finishOrder,
            int currentVertex
    ) {
        visitState[currentVertex] = VISITED;
        List<Integer> adjacentVertices = adjacencyList.get(currentVertex);
        if (adjacentVertices != null) {
            for (int adjacentVertex : adjacentVertices) {
                if (visitState[adjacentVertex] == UNVISITED) {
                    fillVerticesByFinishTime(adjacencyList, visitState, finishOrder, adjacentVertex);
                }
            }
        }
        finishOrder.push(currentVertex);
    }

    private void exploreComponent(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitState,
            int currentVertex,
            List<Integer> componentVertices
    ) {
        visitState[currentVertex] = VISITED;
        componentVertices.add(currentVertex);
        List<Integer> adjacentVertices = adjacencyList.get(currentVertex);
        if (adjacentVertices != null) {
            for (int adjacentVertex : adjacentVertices) {
                if (visitState[adjacentVertex] == UNVISITED) {
                    exploreComponent(adjacencyList, visitState, adjacentVertex, componentVertices);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> adjacencyList, int vertexCount) {
        int[] visitState = new int[vertexCount];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visitState[vertex] == UNVISITED) {
                fillVerticesByFinishTime(adjacencyList, visitState, finishOrder, vertex);
            }
        }

        HashMap<Integer, List<Integer>> transposedAdjacencyList = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposedAdjacencyList.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> adjacentVertices = adjacencyList.get(vertex);
            if (adjacentVertices != null) {
                for (int adjacentVertex : adjacentVertices) {
                    transposedAdjacencyList.get(adjacentVertex).add(vertex);
                }
            }
        }

        Arrays.fill(visitState, UNVISITED);
        int stronglyConnectedComponentsCount = 0;

        while (!finishOrder.isEmpty()) {
            int vertex = finishOrder.pop();
            if (visitState[vertex] == UNVISITED) {
                List<Integer> componentVertices = new ArrayList<>();
                exploreComponent(transposedAdjacencyList, visitState, vertex, componentVertices);
                stronglyConnectedComponentsCount++;
            }
        }

        return stronglyConnectedComponentsCount;
    }
}