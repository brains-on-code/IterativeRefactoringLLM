package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyList;

    AdjacencyList() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyList.computeIfAbsent(sourceVertex, key -> new ArrayList<>());
        adjacencyList.get(sourceVertex).add(destinationVertex);
        adjacencyList.computeIfAbsent(destinationVertex, key -> new ArrayList<>());
    }

    ArrayList<E> getAdjacentVertices(E vertex) {
        return adjacencyList.get(vertex);
    }

    Set<E> getAllVertices() {
        return adjacencyList.keySet();
    }
}

class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegreeMap;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    private void calculateInDegrees() {
        inDegreeMap = new HashMap<>();
        for (E vertex : graph.getAllVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E adjacentVertex : graph.getAdjacentVertices(vertex)) {
                inDegreeMap.put(
                    adjacentVertex,
                    inDegreeMap.getOrDefault(adjacentVertex, 0) + 1
                );
            }
        }
    }

    ArrayList<E> getTopologicalOrder() {
        calculateInDegrees();
        Queue<E> zeroInDegreeQueue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }

        ArrayList<E> topologicalOrder = new ArrayList<>();
        int visitedVertexCount = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E currentVertex = zeroInDegreeQueue.poll();
            topologicalOrder.add(currentVertex);
            visitedVertexCount++;

            for (E adjacentVertex : graph.getAdjacentVertices(currentVertex)) {
                int updatedInDegree = inDegreeMap.get(adjacentVertex) - 1;
                inDegreeMap.put(adjacentVertex, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(adjacentVertex);
                }
            }
        }

        if (visitedVertexCount != graph.getAllVertices().size()) {
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