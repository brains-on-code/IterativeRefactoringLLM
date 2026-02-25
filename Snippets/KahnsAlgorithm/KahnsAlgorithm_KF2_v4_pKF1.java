package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class DirectedGraph<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    DirectedGraph() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyMap.computeIfAbsent(sourceVertex, key -> new ArrayList<>());
        adjacencyMap.get(sourceVertex).add(destinationVertex);
        adjacencyMap.computeIfAbsent(destinationVertex, key -> new ArrayList<>());
    }

    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyMap.get(vertex);
    }

    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> graph;
    private Map<E, Integer> inDegreeByVertex;

    TopologicalSorter(DirectedGraph<E> graph) {
        this.graph = graph;
    }

    private void computeInDegrees() {
        inDegreeByVertex = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegreeByVertex.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegreeByVertex.put(
                    neighbor,
                    inDegreeByVertex.getOrDefault(neighbor, 0) + 1
                );
            }
        }
    }

    ArrayList<E> getTopologicalOrder() {
        computeInDegrees();
        Queue<E> zeroInDegreeVertices = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeByVertex.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeVertices.add(entry.getKey());
            }
        }

        ArrayList<E> topologicalOrder = new ArrayList<>();
        int processedVertexCount = 0;

        while (!zeroInDegreeVertices.isEmpty()) {
            E currentVertex = zeroInDegreeVertices.poll();
            topologicalOrder.add(currentVertex);
            processedVertexCount++;

            for (E neighbor : graph.getNeighbors(currentVertex)) {
                int updatedInDegree = inDegreeByVertex.get(neighbor) - 1;
                inDegreeByVertex.put(neighbor, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeVertices.add(neighbor);
                }
            }
        }

        if (processedVertexCount != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return topologicalOrder;
    }
}

public final class KahnsAlgorithm {
    private KahnsAlgorithm() {
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

        for (String vertex : topologicalSorter.getTopologicalOrder()) {
            System.out.print(vertex + " ");
        }
    }
}