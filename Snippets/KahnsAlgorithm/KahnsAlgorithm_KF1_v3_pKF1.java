package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class DirectedGraph<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyList;

    DirectedGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyList.computeIfAbsent(sourceVertex, key -> new ArrayList<>());
        adjacencyList.get(sourceVertex).add(destinationVertex);
        adjacencyList.computeIfAbsent(destinationVertex, key -> new ArrayList<>());
    }

    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyList.get(vertex);
    }

    Set<E> getVertices() {
        return adjacencyList.keySet();
    }
}

class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> graph;
    private Map<E, Integer> inDegreeByVertex;

    TopologicalSorter(DirectedGraph<E> graph) {
        this.graph = graph;
    }

    void computeInDegrees() {
        inDegreeByVertex = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegreeByVertex.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegreeByVertex.put(neighbor, inDegreeByVertex.getOrDefault(neighbor, 0) + 1);
            }
        }
    }

    ArrayList<E> sort() {
        computeInDegrees();
        Queue<E> verticesWithZeroInDegree = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeByVertex.entrySet()) {
            if (entry.getValue() == 0) {
                verticesWithZeroInDegree.add(entry.getKey());
            }
        }

        ArrayList<E> sortedVertices = new ArrayList<>();
        int processedVertexCount = 0;

        while (!verticesWithZeroInDegree.isEmpty()) {
            E currentVertex = verticesWithZeroInDegree.poll();
            sortedVertices.add(currentVertex);
            processedVertexCount++;

            for (E neighbor : graph.getNeighbors(currentVertex)) {
                inDegreeByVertex.put(neighbor, inDegreeByVertex.get(neighbor) - 1);
                if (inDegreeByVertex.get(neighbor) == 0) {
                    verticesWithZeroInDegree.add(neighbor);
                }
            }
        }

        if (processedVertexCount != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedVertices;
    }
}

public final class TopologicalSortDemo {
    private TopologicalSortDemo() {
    }

    public static void main(String[] args) {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("a", "d");
        graph.addEdge("b", "d");
        graph.addEdge("c", "u");
        graph.addEdge("u", "b");

        TopologicalSorter<String> topologicalSorter = new TopologicalSorter<>(graph);

        for (String vertex : topologicalSorter.sort()) {
            System.out.print(vertex + " ");
        }
    }
}