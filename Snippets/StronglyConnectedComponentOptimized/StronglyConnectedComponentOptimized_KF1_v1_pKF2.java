package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Utility class for working with directed graphs.
 *
 * <p>Provides a method to compute the number of strongly connected components
 * (SCCs) in a directed graph using Kosaraju's algorithm.</p>
 */
public class Class1 {

    /**
     * Depth-first search that fills the stack with vertices ordered by
     * finishing time (first phase of Kosaraju's algorithm).
     *
     * @param graph   adjacency list of the original graph
     * @param visited visitation state array (-1 = unvisited, 1 = visited)
     * @param stack   stack to store vertices by finishing time
     * @param node    current vertex
     */
    public void fillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visited,
            Stack<Integer> stack,
            int node
    ) {
        visited[node] = 1;
        List<Integer> neighbors = graph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    fillOrder(graph, visited, stack, neighbor);
                }
            }
        }
        stack.add(node);
    }

    /**
     * Depth-first search on the transposed graph to collect all vertices
     * in a single strongly connected component (second phase of Kosaraju's algorithm).
     *
     * @param transposedGraph adjacency list of the transposed graph
     * @param visited         visitation state array (-1 = unvisited, 1 = visited)
     * @param node            current vertex
     * @param component       list to collect vertices in the current SCC
     */
    public void dfsOnTransposed(
            HashMap<Integer, List<Integer>> transposedGraph,
            int[] visited,
            int node,
            List<Integer> component
    ) {
        visited[node] = 1;
        component.add(node);
        List<Integer> neighbors = transposedGraph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == -1) {
                    dfsOnTransposed(transposedGraph, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Computes the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph      adjacency list of the original graph
     * @param vertexCount number of vertices in the graph (vertices are 0-based indexed)
     * @return number of strongly connected components
     */
    public int countStronglyConnectedComponents(
            HashMap<Integer, List<Integer>> graph,
            int vertexCount
    ) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, -1);
        Stack<Integer> stack = new Stack<>();

        // First pass: fill stack with vertices ordered by finishing time
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == -1) {
                fillOrder(graph, visited, stack, vertex);
            }
        }

        // Build the transposed graph
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

        // Second pass: process vertices in order defined by the stack
        Arrays.fill(visited, -1);
        int sccCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == -1) {
                List<Integer> component = new ArrayList<>();
                dfsOnTransposed(transposedGraph, visited, vertex, component);
                sccCount++;
            }
        }

        return sccCount;
    }
}