package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Represents the adjacency list of a directed graph.
 *
 * @param <E> the type of vertices, extending Comparable to ensure that vertices
 *            can be compared
 */
class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    AdjacencyList() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge from {@code from} to {@code to}. If either vertex does
     * not exist, it will be added to the adjacency list.
     *
     * @param from the starting vertex of the directed edge
     * @param to   the destination vertex of the directed edge
     */
    void addEdge(E from, E to) {
        adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacencyMap.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the list of adjacent vertices for the given vertex.
     *
     * @param vertex the vertex whose adjacent vertices are to be fetched
     * @return an ArrayList of adjacent vertices for the given vertex
     */
    ArrayList<E> getAdjacents(E vertex) {
        return adjacencyMap.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Returns the set of all vertices present in the graph.
     *
     * @return a set containing all vertices in the graph
     */
    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

/**
 * Performs topological sorting on a directed graph using Kahn's algorithm.
 *
 * @param <E> the type of vertices, extending Comparable to ensure that vertices
 *            can be compared
 */
class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegree;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    /**
     * Calculates the in-degree (number of incoming edges) for all vertices.
     */
    private void calculateInDegree() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E adjacent : graph.getAdjacents(vertex)) {
                inDegree.merge(adjacent, 1, Integer::sum);
            }
        }
    }

    /**
     * Returns a list of vertices in topological order.
     *
     * @return an ArrayList of vertices in topological order
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

        ArrayList<E> sortedOrder = new ArrayList<>();
        int processedVertices = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E currentVertex = zeroInDegreeQueue.poll();
            sortedOrder.add(currentVertex);
            processedVertices++;

            for (E adjacent : graph.getAdjacents(currentVertex)) {
                int updatedInDegree = inDegree.get(adjacent) - 1;
                inDegree.put(adjacent, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(adjacent);
                }
            }
        }

        if (processedVertices != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedOrder;
    }
}

/**
 * Driver class that sorts a given graph in topological order using Kahn's algorithm.
 */
public final class KahnsAlgorithm {

    private KahnsAlgorithm() {
        // Prevent instantiation
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