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

    void addEdge(E source, E destination) {
        adjacencyList.computeIfAbsent(source, key -> new ArrayList<>());
        adjacencyList.get(source).add(destination);
        adjacencyList.computeIfAbsent(destination, key -> new ArrayList<>());
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
    private Map<E, Integer> inDegreeMap;

    TopologicalSorter(DirectedGraph<E> graph) {
        this.graph = graph;
    }

    private void computeInDegrees() {
        inDegreeMap = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getNeighbors(vertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.getOrDefault(neighbor, 0) + 1);
            }
        }
    }

    ArrayList<E> getTopologicalOrder() {
        computeInDegrees();
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

            for (E neighbor : graph.getNeighbors(currentVertex)) {
                int updatedInDegree = inDegreeMap.get(neighbor) - 1;
                inDegreeMap.put(neighbor, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }

        if (visitedVertexCount != graph.getVertices().size()) {
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