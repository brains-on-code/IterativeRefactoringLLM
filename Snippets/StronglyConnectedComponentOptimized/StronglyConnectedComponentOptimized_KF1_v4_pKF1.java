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

    private void dfsFillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visitState,
            Stack<Integer> finishOrder,
            int vertex
    ) {
        visitState[vertex] = VISITED;
        List<Integer> neighbors = graph.get(vertex);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == UNVISITED) {
                    dfsFillOrder(graph, visitState, finishOrder, neighbor);
                }
            }
        }
        finishOrder.push(vertex);
    }

    private void dfsCollectComponent(
            HashMap<Integer, List<Integer>> graph,
            int[] visitState,
            int vertex,
            List<Integer> component
    ) {
        visitState[vertex] = VISITED;
        component.add(vertex);
        List<Integer> neighbors = graph.get(vertex);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visitState[neighbor] == UNVISITED) {
                    dfsCollectComponent(graph, visitState, neighbor, component);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> graph, int vertexCount) {
        int[] visitState = new int[vertexCount];
        Arrays.fill(visitState, UNVISITED);
        Stack<Integer> finishOrder = new Stack<>();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visitState[vertex] == UNVISITED) {
                dfsFillOrder(graph, visitState, finishOrder, vertex);
            }
        }

        HashMap<Integer, List<Integer>> transposedGraph = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposedGraph.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> neighbors = graph.get(vertex);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    transposedGraph.get(neighbor).add(vertex);
                }
            }
        }

        Arrays.fill(visitState, UNVISITED);
        int stronglyConnectedComponentsCount = 0;

        while (!finishOrder.isEmpty()) {
            int vertex = finishOrder.pop();
            if (visitState[vertex] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsCollectComponent(transposedGraph, visitState, vertex, component);
                stronglyConnectedComponentsCount++;
            }
        }

        return stronglyConnectedComponentsCount;
    }
}