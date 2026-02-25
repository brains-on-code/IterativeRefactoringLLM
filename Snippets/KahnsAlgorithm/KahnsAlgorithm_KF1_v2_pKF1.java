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
        adjacencyList.computeIfAbsent(source, k -> new ArrayList<>());
        adjacencyList.get(source).add(destination);
        adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>());
    }

    ArrayList<E> getNeighbors(E vertex) {
        return adjacencyList.get(vertex);
    }

    Set<E> getVertices() {
        return adjacencyList.keySet();
    }
}

class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> directedGraph;
    private Map<E, Integer> inDegreeMap;

    TopologicalSorter(DirectedGraph<E> directedGraph) {
        this.directedGraph = directedGraph;
    }

    void computeInDegrees() {
        inDegreeMap = new HashMap<>();
        for (E vertex : directedGraph.getVertices()) {
            inDegreeMap.putIfAbsent(vertex, 0);
            for (E neighbor : directedGraph.getNeighbors(vertex)) {
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

        ArrayList<E> sortedVertices = new ArrayList<>();
        int visitedVerticesCount = 0;

        while (!zeroInDegreeQueue.isEmpty()) {
            E currentVertex = zeroInDegreeQueue.poll();
            sortedVertices.add(currentVertex);
            visitedVerticesCount++;

            for (E neighbor : directedGraph.getNeighbors(currentVertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }

        if (visitedVerticesCount != directedGraph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return sortedVertices;
    }
}

public final class TopologicalSortDemo {
    private TopologicalSortDemo() {
    }

    public static void main(String[] args) {
        DirectedGraph<String> directedGraph = new DirectedGraph<>();
        directedGraph.addEdge("a", "b");
        directedGraph.addEdge("c", "a");
        directedGraph.addEdge("a", "d");
        directedGraph.addEdge("b", "d");
        directedGraph.addEdge("c", "u");
        directedGraph.addEdge("u", "b");

        TopologicalSorter<String> topologicalSorter = new TopologicalSorter<>(directedGraph);

        for (String vertex : topologicalSorter.sort()) {
            System.out.print(vertex + " ");
        }
    }
}