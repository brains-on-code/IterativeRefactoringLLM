package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Adjacency list representation of a directed graph.
 *
 * @param <E> vertex type
 */
class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    AdjacencyList() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge {@code from -> to}. Missing vertices are created.
     */
    void addEdge(E from, E to) {
        adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacencyMap.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the adjacency list of {@code vertex}.
     */
    ArrayList<E> getAdjacents(E vertex) {
        return adjacencyMap.get(vertex);
    }

    /**
     * Returns all vertices in the graph.
     */
    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

/**
 * Topological sort using Kahn's algorithm.
 *
 * @param <E> vertex type
 */
class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegree;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    /**
     * Computes in-degree for every vertex.
     */
    private void calculateInDegree() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E adjacent : graph.getAdjacents(vertex)) {
                inDegree.put(adjacent, inDegree.getOrDefault(adjacent, 0) + 1);
            }
        }
    }

    /**
     * Returns vertices in topological order.
     *
     * @throws IllegalStateException if the graph contains a cycle
     */
    ArrayList<E> topSortOrder() {
        calculateInDegree();
        Queue<E> queue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        ArrayList<E> order = new ArrayList<>();
        int processedVertices = 0;

        while (!queue.isEmpty()) {
            E current = queue.poll();
            order.add(current);
            processedVertices++;

            for (E adjacent : graph.getAdjacents(current)) {
                int updatedInDegree = inDegree.get(adjacent) - 1;
                inDegree.put(adjacent, updatedInDegree);
                if (updatedInDegree == 0) {
                    queue.add(adjacent);
                }
            }
        }

        if (processedVertices != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return order;
    }
}

/**
 * Demonstrates topological sorting using Kahn's algorithm.
 */
public final class KahnsAlgorithm {

    private KahnsAlgorithm() {
        // Utility class
    }

    public static void main(String[] args) {
        AdjacencyList<String> graph = new AdjacencyList<>();
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("a", "d");
        graph.addEdge("b", "d");
        graph.addEdge("c", "u");
        graph.addEdge("u", "b");

        TopologicalSort<String> topSort = new TopologicalSort<>(graph);

        for (String vertex : topSort.topSortOrder()) {
            System.out.print(vertex + " ");
        }
    }
}