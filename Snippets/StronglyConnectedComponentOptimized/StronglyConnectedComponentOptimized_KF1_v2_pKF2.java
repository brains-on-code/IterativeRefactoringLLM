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

    private static final int UNVISITED = -1;
    private static final int VISITED = 1;

    /**
     * First DFS pass of Kosaraju's algorithm.
     *
     * <p>Performs a depth-first search on the original graph and pushes each
     * vertex onto the stack after exploring all its descendants. The stack
     * will then contain vertices ordered by decreasing finishing time.</p>
     *
     * @param graph   adjacency list of the original graph
     * @param visited visitation state array (UNVISITED or VISITED)
     * @param stack   stack to store vertices by finishing time
     * @param node    current vertex
     */
    public void fillOrder(
            HashMap<Integer, List<Integer>> graph,
            int[] visited,
            Stack<Integer> stack,
            int node
    ) {
        visited[node] = VISITED;
        List<Integer> neighbors = graph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    fillOrder(graph, visited, stack, neighbor);
                }
            }
        }
        stack.push(node);
    }

    /**
     * Second DFS pass of Kosaraju's algorithm on the transposed graph.
     *
     * <p>Starting from a given vertex, this DFS collects all vertices reachable
     * in the transposed graph, which together form a single strongly connected
     * component.</p>
     *
     * @param transposedGraph adjacency list of the transposed graph
     * @param visited         visitation state array (UNVISITED or VISITED)
     * @param node            current vertex
     * @param component       list to collect vertices in the current SCC
     */
    public void dfsOnTransposed(
            HashMap<Integer, List<Integer>> transposedGraph,
            int[] visited,
            int node,
            List<Integer> component
    ) {
        visited[node] = VISITED;
        component.add(node);
        List<Integer> neighbors = transposedGraph.get(node);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (visited[neighbor] == UNVISITED) {
                    dfsOnTransposed(transposedGraph, visited, neighbor, component);
                }
            }
        }
    }

    /**
     * Computes the number of strongly connected components in a directed graph
     * using Kosaraju's algorithm.
     *
     * @param graph       adjacency list of the original graph
     * @param vertexCount number of vertices in the graph (vertices are 0-based indexed)
     * @return number of strongly connected components
     */
    public int countStronglyConnectedComponents(
            HashMap<Integer, List<Integer>> graph,
            int vertexCount
    ) {
        int[] visited = new int[vertexCount];
        Arrays.fill(visited, UNVISITED);
        Stack<Integer> stack = new Stack<>();

        // First pass: run DFS on the original graph to fill the stack
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (visited[vertex] == UNVISITED) {
                fillOrder(graph, visited, stack, vertex);
            }
        }

        // Build the transposed graph (reverse all edges)
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

        // Second pass: run DFS on the transposed graph in stack order
        Arrays.fill(visited, UNVISITED);
        int sccCount = 0;

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex] == UNVISITED) {
                List<Integer> component = new ArrayList<>();
                dfsOnTransposed(transposedGraph, visited, vertex, component);
                sccCount++;
            }
        }

        return sccCount;
    }
}