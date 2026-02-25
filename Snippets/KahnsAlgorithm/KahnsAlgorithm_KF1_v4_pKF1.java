package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class DirectedGraph<E extends Comparable<E>> {

    private final Map<E, ArrayList<E>> adjacencyListByVertex;

    DirectedGraph() {
        this.adjacencyListByVertex = new LinkedHashMap<>();
    }

    void addEdge(E sourceVertex, E destinationVertex) {
        adjacencyListByVertex.computeIfAbsent(sourceVertex, key -> new ArrayList<>());
        adjacencyListByVertex.get(sourceVertex).add(destinationVertex);
        adjacencyListByVertex.computeIfAbsent(destinationVertex, key -> new ArrayList<>());
    }

    ArrayList<E> getAdjacentVertices(E vertex) {
        return adjacencyListByVertex.get(vertex);
    }

    Set<E> getAllVertices() {
        return adjacencyListByVertex.keySet();
    }
}

class TopologicalSorter<E extends Comparable<E>> {

    private final DirectedGraph<E> directedGraph;
    private Map<E, Integer> inDegreeByVertex;

    TopologicalSorter(DirectedGraph<E> directedGraph) {
        this.directedGraph = directedGraph;
    }

    void computeInDegrees() {
        inDegreeByVertex = new HashMap<>();
        for (E vertex : directedGraph.getAllVertices()) {
            inDegreeByVertex.putIfAbsent(vertex, 0);
            for (E adjacentVertex : directedGraph.getAdjacentVertices(vertex)) {
                inDegreeByVertex.put(
                        adjacentVertex,
                        inDegreeByVertex.getOrDefault(adjacentVertex, 0) + 1
                );
            }
        }
    }

    ArrayList<E> sort() {
        computeInDegrees();
        Queue<E> verticesWithZeroInDegree = new LinkedList<>();

        for (Map.Entry<E, Integer> vertexEntry : inDegreeByVertex.entrySet()) {
            if (vertexEntry.getValue() == 0) {
                verticesWithZeroInDegree.add(vertexEntry.getKey());
            }
        }

        ArrayList<E> sortedVertices = new ArrayList<>();
        int processedVertexCount = 0;

        while (!verticesWithZeroInDegree.isEmpty()) {
            E currentVertex = verticesWithZeroInDegree.poll();
            sortedVertices.add(currentVertex);
            processedVertexCount++;

            for (E adjacentVertex : directedGraph.getAdjacentVertices(currentVertex)) {
                int updatedInDegree = inDegreeByVertex.get(adjacentVertex) - 1;
                inDegreeByVertex.put(adjacentVertex, updatedInDegree);
                if (updatedInDegree == 0) {
                    verticesWithZeroInDegree.add(adjacentVertex);
                }
            }
        }

        if (processedVertexCount != directedGraph.getAllVertices().size()) {
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