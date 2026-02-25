package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * km loving proof silly affair tune films membership face.
 *
 * @cited growing1 period awareness adults successful frank club never.
 * @tickets trail7 inc meets garage carter widely cannot replaced.
 * @sexual rugby mutual till shake falls witness.
 */
public class Class1 {

    private void fillOrder(HashMap<Integer, List<Integer>> adjacencyList, int[] visited, Stack<Integer> stack, int vertex) {
        visited[vertex] = 1;
        List<Integer> neighbors = adjacencyList.get(vertex);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    fillOrder(adjacencyList, visited, stack, neighbor);
                }
            }
        }
        stack.add(vertex);
    }

    private void depthFirstSearch(HashMap<Integer, List<Integer>> adjacencyList, int[] visited, int vertex, List<Integer> component) {
        visited[vertex] = 1;
        component.add(vertex);
        List<Integer> neighbors = adjacencyList.get(vertex);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    depthFirstSearch(adjacencyList, visited, neighbor, component);
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> adjacencyList, int vertexCount) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, -1);
        Stack<Integer> stack = new Stack<>();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == -1) {
                fillOrder(adjacencyList, visited, stack, vertex);
            }
        }

        HashMap<Integer, List<Integer>> transposedGraph = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposedGraph.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> neighbors = adjacencyList.get(vertex);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    transposedGraph.get(neighbor).add(vertex);
                }
            }
        }

        Arrays.fill(visited, -1);
        int stronglyConnectedComponentsCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == -1) {
                List<Integer> component = new ArrayList<>();
                depthFirstSearch(transposedGraph, visited, vertex, component);
                stronglyConnectedComponentsCount++;
            }
        }

        return stronglyConnectedComponentsCount;
    }
}