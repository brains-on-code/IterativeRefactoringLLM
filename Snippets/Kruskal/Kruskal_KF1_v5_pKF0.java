package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Class1 {

    static class Edge {
        final int source;
        final int destination;
        final int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private static void addEdge(Set<Edge>[] graph, int source, int destination, int weight) {
        graph[source].add(new Edge(source, destination, weight));
    }

    public Set<Edge>[] buildMinimumSpanningForest(Set<Edge>[] graph) {
        int vertexCount = graph.length;

        int[] componentIds = new int[vertexCount];

        @SuppressWarnings("unchecked")
        Set<Integer>[] components = new HashSet[vertexCount];

        @SuppressWarnings("unchecked")
        Set<Edge>[] forest = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        initializeStructures(graph, vertexCount, componentIds, components, forest, edgeQueue);

        int mergedVerticesCount = 0;

        while (mergedVerticesCount != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int sourceComponentId = componentIds[edge.source];
            int destinationComponentId = componentIds[edge.destination];

            if (sourceComponentId == destinationComponentId) {
                continue;
            }

            Set<Integer> sourceComponent = components[sourceComponentId];
            Set<Integer> destinationComponent = components[destinationComponentId];

            if (sourceComponent.contains(edge.destination) || destinationComponent.contains(edge.source)) {
                continue;
            }

            mergeComponents(sourceComponent, destinationComponent, componentIds, sourceComponentId);
            addEdge(forest, edge.source, edge.destination, edge.weight);
            mergedVerticesCount = sourceComponent.size();
        }

        return forest;
    }

    private void initializeStructures(
        Set<Edge>[] graph,
        int vertexCount,
        int[] componentIds,
        Set<Integer>[] components,
        Set<Edge>[] forest,
        PriorityQueue<Edge> edgeQueue
    ) {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forest[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentIds[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }
    }

    private void mergeComponents(
        Set<Integer> sourceComponent,
        Set<Integer> destinationComponent,
        int[] componentIds,
        int sourceComponentId
    ) {
        sourceComponent.addAll(destinationComponent);
        for (int vertex : sourceComponent) {
            componentIds[vertex] = sourceComponentId;
        }
    }
}