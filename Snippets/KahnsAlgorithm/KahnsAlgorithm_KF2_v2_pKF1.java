package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap;

    AdjacencyList() {
        this.adjacencyMap = new LinkedHashMap<>();
    }

    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyMap.computeIfAbsent(sourceVertex, key -> new ArrayList<>());
        adjacencyMap.get(sourceVertex).add(destinationVertex);
        adjacencyMap.computeIfAbsent(destinationVertex, key -> new ArrayList<>());
    }

    ArrayList<E> getAdjacentVertices(E vertex) {
        return adjacencyMap.get(vertex);
    }

    Set<E> getAllVertices() {
        return adjacencyMap.keySet();
    }
}

class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegreeByVertex;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    private void calculateInDegrees() {
        inDegreeByVertex = new HashMap<>();
        for (E vertex : graph.getAllVertices()) {
            inDegreeByVertex.putIfAbsent(vertex, 0);
            for (E adjacentVertex : graph.getAdjacentVertices(vertex)) {
                inDegreeByVertex.put(
                    adjacentVertex,
                    inDegreeByVertex.getOrDefault(adjacentVertex, 0) + 1
                );
            }
        }
    }

    ArrayList<E> getTopologicalOrder() {
        calculateInDegrees();
        Queue<E> zeroInDegreeVertices = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeByVertex.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeVertices.add(entry.getKey());
            }
        }

        ArrayList<E> topologicalOrder = new ArrayList<>();
        int processedVerticesCount = 0;

        while (!zeroInDegreeVertices.isEmpty()) {
            E currentVertex = zeroInDegreeVertices.poll();
            topologicalOrder.add(currentVertex);
            processedVerticesCount++;

            for (E adjacentVertex : graph.getAdjacentVertices(currentVertex)) {
                int updatedInDegree = inDegreeByVertex.get(adjacentVertex) - 1;
                inDegreeByVertex.put(adjacentVertex, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeVertices.add(adjacentVertex);
                }
            }
        }

        if (processedVerticesCount != graph.getAllVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return topologicalOrder;
    }
}

public final class KahnsAlgorithm {
    private KahnsAlgorithm() {
    }

    public static void main(String[] args) {
        AdjacencyList<String> graph = new AdjacencyList<>();
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("a", "d");
        graph.addEdge("b", "d");
        graph.addEdge("c", "u");
        graph.addEdge("u", "b");

        TopologicalSort<String> topologicalSort = new TopologicalSort<>(graph);

        for (String vertex : topologicalSort.getTopologicalOrder()) {
            System.out.print(vertex + " ");
        }
    }
}