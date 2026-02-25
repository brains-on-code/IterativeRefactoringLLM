package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Directed graph represented with an adjacency list.
 *
 * @param <E> vertex type
 */
class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    AdjacencyList() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge {@code from -> to}.
     * Ensures both vertices exist in the adjacency map.
     *
     * @param from source vertex
     * @param to   destination vertex
     */
    void addEdge(E from, E to) {
        adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>());
        adjacencyMap.get(from).add(to);
        adjacencyMap.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the adjacency list of {@code vertex},
     * or {@code null} if the vertex is not present.
     *
     * @param vertex vertex whose adjacency list is requested
     * @return list of adjacent vertices or {@code null} if absent
     */
    ArrayList<E> getAdjacents(E vertex) {
        return adjacencyMap.get(vertex);
    }

    /**
     * Returns all vertices in the graph.
     *
     * @return set of vertices
     */
    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

/**
 * Topological sort for a directed acyclic graph (DAG)
 * using Kahn's algorithm.
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
     * Computes in-degree (number of incoming edges) for each vertex.
     */
    private void calculateInDegree() {
        inDegree = new HashMap<>();

        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);

            ArrayList<E> adjacents = graph.getAdjacents(vertex);
            if (adjacents == null) {
                continue;
            }

            for (E adjacent : adjacents) {
                inDegree.put(adjacent, inDegree.getOrDefault(adjacent, 0) + 1);
            }
        }
    }

    /**
     * Returns vertices in topological order.
     *
     * @return list of vertices in topological order
     * @throws IllegalStateException if the graph contains a cycle
     */
    ArrayList<E> topSortOrder() {
        calculateInDegree();
        Queue<E> zeroInDegreeQueue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }

        ArrayList<E> order = new ArrayList<>();
        int processedVertices = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E current = zeroInDegreeQueue.poll();
            order.add(current);
            processedVertices++;

            ArrayList<E> adjacents = graph.getAdjacents(current);
            if (adjacents == null) {
                continue;
            }

            for (E adjacent : adjacents) {
                int updatedInDegree = inDegree.get(adjacent) - 1;
                inDegree.put(adjacent, updatedInDegree);

                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(adjacent);
                }
            }
        }

        if (processedVertices != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle; topological sort not possible.");
        }

        return order;
    }
}

/**
 * Demonstrates Kahn's algorithm for topological sorting.
 */
public final class KahnsAlgorithm {

    private KahnsAlgorithm() {
        throw new UnsupportedOperationException("Utility class");
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