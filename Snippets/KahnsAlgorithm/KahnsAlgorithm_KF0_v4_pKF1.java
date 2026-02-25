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

    private final Map<E, ArrayList<E>> adjacencyList;

    /**
     * Constructor to initialize the adjacency list.
     */
    AdjacencyList() {
        adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a directed edge from one vertex to another in the adjacency list.
     * If the vertex does not exist, it will be added to the list.
     *
     * @param sourceVertex      the starting vertex of the directed edge
     * @param destinationVertex the destination vertex of the directed edge
     */
    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyList.putIfAbsent(sourceVertex, new ArrayList<>());
        adjacencyList.get(sourceVertex).add(destinationVertex);
        adjacencyList.putIfAbsent(destinationVertex, new ArrayList<>());
    }

    /**
     * Retrieves the list of adjacent vertices for a given vertex.
     *
     * @param vertex the vertex whose adjacent vertices are to be fetched
     * @return an ArrayList of adjacent vertices for the given vertex
     */
    ArrayList<E> getAdjacentVertices(E vertex) {
        return adjacencyList.get(vertex);
    }

    /**
     * Retrieves the set of all vertices present in the graph.
     *
     * @return a set containing all vertices in the graph
     */
    Set<E> getVertices() {
        return adjacencyList.keySet();
    }
}

/**
 * A class that performs topological sorting on a directed graph using Kahn's algorithm.
 *
 * @param <E> the type of vertices, extending Comparable to ensure that vertices
 *            can be compared
 */
class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> adjacencyList;
    private Map<E, Integer> inDegreeMap;

    /**
     * Constructor to initialize the topological sorting class with a given graph.
     *
     * @param adjacencyList the directed graph represented as an adjacency list
     */
    TopologicalSort(AdjacencyList<E> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    /**
     * Calculates the in-degree of all vertices in the graph. The in-degree is
     * the number of edges directed into a vertex.
     */
    void calculateInDegrees() {
        inDegreeMap = new HashMap<>();
        for (E vertex : adjacencyList.getVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E neighbor : adjacencyList.getAdjacentVertices(vertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.getOrDefault(neighbor, 0) + 1);
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
    ArrayList<E> getTopologicalOrder() {
        calculateInDegrees();
        Queue<E> zeroInDegreeQueue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }

        ArrayList<E> topologicalOrder = new ArrayList<>();
        int processedVertexCount = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E currentVertex = zeroInDegreeQueue.poll();
            topologicalOrder.add(currentVertex);
            processedVertexCount++;

            for (E neighbor : adjacencyList.getAdjacentVertices(currentVertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }

        if (processedVertexCount != adjacencyList.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return topologicalOrder;
    }
}

/**
 * A driver class that sorts a given graph in topological order using Kahn's algorithm.
 */
public final class KahnsAlgorithm {
    private KahnsAlgorithm() {
    }

    public static void main(String[] args) {
        AdjacencyList<String> adjacencyList = new AdjacencyList<>();
        adjacencyList.addEdge("a", "b");
        adjacencyList.addEdge("c", "a");
        adjacencyList.addEdge("a", "d");
        adjacencyList.addEdge("b", "d");
        adjacencyList.addEdge("c", "u");
        adjacencyList.addEdge("u", "b");

        TopologicalSort<String> topologicalSort = new TopologicalSort<>(adjacencyList);

        for (String vertex : topologicalSort.getTopologicalOrder()) {
            System.out.print(vertex + " ");
        }
    }
}