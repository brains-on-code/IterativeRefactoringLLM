package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A class representing the adjacency list of a directed graph. The adjacency list
 * maintains a mapping of vertices to their adjacent vertices.
 *
 * @param <E> the type of vertices, extending Comparable to ensure that vertices
 *            can be compared
 */
class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    /**
     * Constructor to initialize the adjacency list.
     */
    AdjacencyList() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge from one vertex to another in the adjacency list.
     * If the vertex does not exist, it will be added to the list.
     *
     * @param from the starting vertex of the directed edge
     * @param to   the destination vertex of the directed edge
     */
    void addEdge(E from, E to) {
        adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacencyMap.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Retrieves the list of adjacent vertices for a given vertex.
     *
     * @param vertex the vertex whose adjacent vertices are to be fetched
     * @return an ArrayList of adjacent vertices for the given vertex
     */
    ArrayList<E> getAdjacents(E vertex) {
        return adjacencyMap.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Retrieves the set of all vertices present in the graph.
     *
     * @return a set containing all vertices in the graph
     */
    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

/**
 * A class that performs topological sorting on a directed graph using Kahn's algorithm.
 *
 * @param <E> the type of vertices, extending Comparable to ensure that vertices
 *            can be compared
 */
class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegree;

    /**
     * Constructor to initialize the topological sorting class with a given graph.
     *
     * @param graph the directed graph represented as an adjacency list
     */
    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    /**
     * Calculates the in-degree of all vertices in the graph. The in-degree is
     * the number of edges directed into a vertex.
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
     * Returns an ArrayList containing the vertices of the graph arranged in
     * topological order. Topological sorting ensures that for any directed edge
     * (u, v), vertex u appears before vertex v in the ordering.
     *
     * @return an ArrayList of vertices in topological order
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

        ArrayList<E> sortedOrder = new ArrayList<>();
        int processedVertices = 0;

        while (!queue.isEmpty()) {
            E current = queue.poll();
            sortedOrder.add(current);
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

        return sortedOrder;
    }
}

/**
 * A driver class that sorts a given graph in topological order using Kahn's algorithm.
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