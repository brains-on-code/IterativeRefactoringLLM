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

    void addEdge(E fromVertex, E toVertex) {
        adjacencyMap.computeIfAbsent(fromVertex, k -> new ArrayList<>());
        adjacencyMap.get(fromVertex).add(toVertex);
        adjacencyMap.computeIfAbsent(toVertex, k -> new ArrayList<>());
    }

    ArrayList<E> getAdjacentVertices(E vertex) {
        return adjacencyMap.get(vertex);
    }

    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegreeMap;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    private void calculateInDegree() {
        inDegreeMap = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E adjacentVertex : graph.getAdjacentVertices(vertex)) {
                inDegreeMap.put(adjacentVertex, inDegreeMap.getOrDefault(adjacentVertex, 0) + 1);
            }
        }
    }

    ArrayList<E> getTopologicalOrder() {
        calculateInDegree();
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

            for (E adjacentVertex : graph.getAdjacentVertices(currentVertex)) {
                inDegreeMap.put(adjacentVertex, inDegreeMap.get(adjacentVertex) - 1);
                if (inDegreeMap.get(adjacentVertex) == 0) {
                    zeroInDegreeQueue.add(adjacentVertex);
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