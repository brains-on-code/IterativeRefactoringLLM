package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Directed graph implementation using adjacency lists.
 */
class DirectedGraph<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyList;

    DirectedGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge from {@code from} to {@code to}.
     * Ensures both vertices exist in the graph.
     */
    void addEdge(E from, E to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>());
        adjacencyList.get(from).add(to);
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the neighbors (outgoing edges) of the given vertex.
     */
    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyList.get(vertex);
    }

    /**
     * Returns the set of all vertices in the graph.
     */
    Set<E> getVertices() {
        return adjacencyList.keySet();
    }
}

/**
 * Topological sort for a directed acyclic graph (DAG).
 */
class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> graph;
    private Map<E, Integer> inDegree;

    TopologicalSorter(DirectedGraph<E> graph) {
        this.graph = graph;
    }

    /**
     * Computes in-degrees for all vertices in the graph.
     */
    private void computeInDegrees() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }
    }

    /**
     * Performs topological sort and returns the vertices in topologically sorted order.
     *
     * @throws IllegalStateException if the graph contains a cycle.
     */
    ArrayList<E> topologicalSort() {
        computeInDegrees();
        Queue<E> queue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        ArrayList<E> sortedOrder = new ArrayList<>();
        int visitedCount = 0;

        while (!queue.isEmpty()) {
            E vertex = queue.poll();
            sortedOrder.add(vertex);
            visitedCount++;

            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (visitedCount != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedOrder;
    }
}

/**
 * Example usage of the DirectedGraph and TopologicalSorter.
 */
public final class TopologicalSortExample {

    private TopologicalSortExample() {
    }

    public static void main(String[] args) {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("a", "d");
        graph.addEdge("b", "d");
        graph.addEdge("c", "u");
        graph.addEdge("u", "b");

        TopologicalSorter<String> sorter = new TopologicalSorter<>(graph);

        for (String vertex : sorter.topologicalSort()) {
            System.out.print(vertex + " ");
        }
    }
}