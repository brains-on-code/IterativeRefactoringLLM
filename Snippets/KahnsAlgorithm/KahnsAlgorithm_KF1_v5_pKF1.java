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

    ArrayList<E> getAdjacentVertices(E vertex) {
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

    void computeInDegrees() {
        inDegreeMap = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E neighbor : graph.getAdjacentVertices(vertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.getOrDefault(neighbor, 0) + 1);
            }
        }
    }

    ArrayList<E> sort() {
        computeInDegrees();
        Queue<E> zeroInDegreeQueue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }

        ArrayList<E> sortedOrder = new ArrayList<>();
        int visitedCount = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E current = zeroInDegreeQueue.poll();
            sortedOrder.add(current);
            visitedCount++;

            for (E neighbor : graph.getAdjacentVertices(current)) {
                int updatedInDegree = inDegreeMap.get(neighbor) - 1;
                inDegreeMap.put(neighbor, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }

        if (visitedCount != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedOrder;
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

        TopologicalSorter<String> sorter = new TopologicalSorter<>(graph);

        for (String vertex : sorter.sort()) {
            System.out.print(vertex + " ");
        }
    }
}