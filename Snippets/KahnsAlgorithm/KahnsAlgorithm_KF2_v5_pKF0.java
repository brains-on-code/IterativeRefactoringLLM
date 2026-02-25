package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class AdjacencyList<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyMap = new LinkedHashMap<>();

    void addEdge(E from, E to) {
        adjacencyMap.computeIfAbsent(from, key -> new ArrayList<>()).add(to);
        adjacencyMap.computeIfAbsent(to, key -> new ArrayList<>());
    }

    ArrayList<E> getAdjacents(E vertex) {
        return adjacencyMap.getOrDefault(vertex, new ArrayList<>());
    }

    Set<E> getVertices() {
        return adjacencyMap.keySet();
    }
}

class TopologicalSort<E extends Comparable<E>> {

    private final AdjacencyList<E> graph;
    private Map<E, Integer> inDegree;

    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }

    private void calculateInDegree() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E adjacent : graph.getAdjacents(vertex)) {
                inDegree.merge(adjacent, 1, Integer::sum);
            }
        }
    }

    ArrayList<E> topSortOrder() {
        calculateInDegree();
        Queue<E> zeroInDegreeQueue = new LinkedList<>();

        for (Map.Entry<E, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.offer(entry.getKey());
            }
        }

        ArrayList<E> sortedOrder = new ArrayList<>();
        int processedVertices = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E currentVertex = zeroInDegreeQueue.poll();
            sortedOrder.add(currentVertex);
            processedVertices++;

            for (E adjacent : graph.getAdjacents(currentVertex)) {
                int updatedInDegree = inDegree.get(adjacent) - 1;
                inDegree.put(adjacent, updatedInDegree);
                if (updatedInDegree == 0) {
                    zeroInDegreeQueue.offer(adjacent);
                }
            }
        }

        if (processedVertices != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedOrder;
    }
}

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