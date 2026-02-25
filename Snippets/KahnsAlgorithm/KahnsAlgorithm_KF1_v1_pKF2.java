package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A simple directed graph implementation using an adjacency list.
 *
 * @param <E> the type of vertices in the graph
 */
class DirectedGraph<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyList;

    /**
     * Constructs an empty directed graph.
     */
    DirectedGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge from {@code from} to {@code to}. If the vertices do not
     * exist in the graph, they are added.
     *
     * @param from the source vertex
     * @param to   the destination vertex
     */
    void addEdge(E from, E to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>());
        adjacencyList.get(from).add(to);
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the list of neighbors (outgoing edges) for the given vertex.
     *
     * @param vertex the vertex whose neighbors are requested
     * @return the list of neighbors, or {@code null} if the vertex is not present
     */
    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyList.get(vertex);
    }

    /**
     * Returns the set of all vertices in the graph.
     *
     * @return a set of vertices
     */
    Set<E> getVertices() {
        return adjacencyList.keySet();
    }
}

/**
 * Performs a topological sort on a directed acyclic graph (DAG).
 *
 * @param <E> the type of vertices in the graph
 */
class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> graph;
    private Map<E, Integer> inDegree;

    /**
     * Constructs a topological sorter for the given graph.
     *
     * @param graph the directed graph to sort
     */
    TopologicalSorter(DirectedGraph<E> graph) {
        this.graph = graph;
    }

    /**
     * Computes the in-degree (number of incoming edges) for each vertex in the graph.
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
     * Performs a topological sort of the graph using Kahn's algorithm.
     *
     * @return a list of vertices in topologically sorted order
     * @throws IllegalStateException if the graph contains a cycle
     */
    ArrayList<E> topologicalSort() {
        computeInDegrees();
        Queue<E> queue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        ArrayList<E> sorted = new ArrayList<>();
        int visitedCount = 0;

        while (!queue.isEmpty()) {
            E vertex = queue.poll();
            sorted.add(vertex);
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

        return sorted;
    }
}

/**
 * Demonstrates topological sorting on a sample directed graph.
 */
public final class TopologicalSortDemo {

    private TopologicalSortDemo() {
        // Utility class; prevent instantiation.
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