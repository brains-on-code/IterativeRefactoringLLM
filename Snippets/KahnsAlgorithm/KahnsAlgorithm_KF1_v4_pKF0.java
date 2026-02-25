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

    private final Map<E, ArrayList<E>> adjacencyList = new LinkedHashMap<>();

    /**
     * Adds a directed edge from {@code from} to {@code to}.
     * Ensures both vertices exist in the graph.
     */
    void addEdge(E from, E to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>());
    }

    /**
     * Returns the neighbors (outgoing edges) of the given vertex.
     */
    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
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
     * Performs topological sort and returns the vertices in topologically sorted order.
     *
     * @throws IllegalStateException if the graph contains a cycle.
     */
    ArrayList<E> topologicalSort() {
        computeInDegrees();
        Queue<E> zeroInDegreeQueue = initializeZeroInDegreeQueue();

        ArrayList<E> sortedOrder = new ArrayList<>();
        int visitedCount = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E vertex = zeroInDegreeQueue.poll();
            sortedOrder.add(vertex);
            visitedCount++;

            for (E neighbor : graph.getNeighbors(vertex)) {
                int updatedInDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }

        validateAcyclicGraph(visitedCount);

        return sortedOrder;
    }

    /**
     * Computes in-degrees for all vertices in the graph.
     */
    private void computeInDegrees() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegree.merge(neighbor, 1, Integer::sum);
            }
        }
    }

    private Queue<E> initializeZeroInDegreeQueue() {
        Queue<E> zeroInDegreeQueue = new LinkedList<>();
        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }
        return zeroInDegreeQueue;
    }

    private void validateAcyclicGraph(int visitedCount) {
        if (visitedCount != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }
    }
}

/**
 * Example usage of the DirectedGraph and TopologicalSorter.
 */
public final class TopologicalSortExample {

    private TopologicalSortExample() {
        // Utility class
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