package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Strongly Connected Components (SCC) counter using Kosaraju's algorithm.
 */
public class Class1 {

    /**
     * First DFS pass: fills stack with vertices in order of completion time.
     */
    public void fillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visited,
            Stack<Integer> stack,
            int vertex
    ) {
        visited[vertex] = 1;
        List<Integer> neighbors = graph.get(vertex);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    fillOrder(graph, visited, stack, neighbor);
                }
            }
        }

        stack.push(vertex);
    }

    /**
     * Second DFS pass on the transposed graph: collects all vertices in the
     * current strongly connected component.
     */
    public void dfsOnTranspose(
            HashMap<Integer, List<Integer>> transposeGraph,
            int[] visited,
            int vertex,
            List<Integer> component
    ) {
        visited[vertex] = 1;
        component.add(vertex);
        List<Integer> neighbors = transposeGraph.get(vertex);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    dfsOnTranspose(transposeGraph, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Counts the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph the adjacency list of the directed graph
     * @param vertexCount number of vertices in the graph
     * @return number of strongly connected components
     */
    public int countStronglyConnectedComponents(
            HashMap<Integer, List<Integer>> graph,
            int vertexCount
    ) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, -1);
        Stack<Integer> stack = new Stack<>();

        // First pass: fill stack with vertices in order of finishing times
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == -1) {
                fillOrder(graph, visited, stack, vertex);
            }
        }

        // Build transpose graph
        HashMap<Integer, List<Integer>> transposeGraph = new HashMap<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            transposeGraph.put(vertex, new ArrayList<>());
        }

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            List<Integer> neighbors = graph.get(vertex);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    transposeGraph.get(neighbor).add(vertex);
                }
            }
        }

        // Second pass: process vertices in order defined by the stack
        Arrays.fill(visited, -1);
        int sccCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == -1) {
                List<Integer> component = new ArrayList<>();
                dfsOnTranspose(transposeGraph, visited, vertex, component);
                sccCount++;
            }
        }

        return sccCount;
    }
}