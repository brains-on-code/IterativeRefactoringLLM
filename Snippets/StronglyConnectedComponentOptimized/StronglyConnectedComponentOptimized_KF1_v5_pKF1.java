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

    private void depthFirstSearchFillOrder(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitStatus,
            Stack<Integer> finishOrderStack,
            int currentVertex
    ) {
        visitStatus[currentVertex] = VISITED;
        List<Integer> adjacentVertices = adjacencyList.get(currentVertex);

        if (adjacentVertices != null) {
            for (int adjacentVertex : adjacentVertices) {
                if (visitStatus[adjacentVertex] == UNVISITED) {
                    depthFirstSearchFillOrder(adjacencyList, visitStatus, finishOrderStack, adjacentVertex);
                }
            }
        }

        finishOrderStack.push(currentVertex);
    }

    private void depthFirstSearchCollectComponent(
            HashMap<Integer, List<Integer>> adjacencyList,
            int[] visitStatus,
            int currentVertex,
            List<Integer> stronglyConnectedComponent
    ) {
        visitStatus[currentVertex] = VISITED;
        stronglyConnectedComponent.add(currentVertex);
        List<Integer> adjacentVertices = adjacencyList.get(currentVertex);

        if (adjacentVertices != null) {
            for (int adjacentVertex : adjacentVertices) {
                if (visitStatus[adjacentVertex] == UNVISITED) {
                    depthFirstSearchCollectComponent(
                            adjacencyList,
                            visitStatus,
                            adjacentVertex,
                            stronglyConnectedComponent
                    );
                }
            }
        }
    }

    public int countStronglyConnectedComponents(HashMap<Integer, List<Integer>> adjacencyList, int vertexCount) {
        int[] visitStatus = new int[vertexCount];
        Arrays.fill(visitStatus, UNVISITED);
        Stack<Integer> finishOrderStack = new Stack<>();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visitStatus[vertex] == UNVISITED) {
                depthFirstSearchFillOrder(adjacencyList, visitStatus, finishOrderStack, vertex);
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

        Arrays.fill(visitStatus, UNVISITED);
        int stronglyConnectedComponentsCount = 0;

        while (!finishOrderStack.isEmpty()) {
            int vertex = finishOrderStack.pop();
            if (visitStatus[vertex] == UNVISITED) {
                List<Integer> stronglyConnectedComponent = new ArrayList<>();
                depthFirstSearchCollectComponent(
                        transposedAdjacencyList,
                        visitStatus,
                        vertex,
                        stronglyConnectedComponent
                );
                stronglyConnectedComponentsCount++;
            }
        }

        return stronglyConnectedComponentsCount;
    }
}