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

        int[] componentId = new int[vertexCount];

        @SuppressWarnings("unchecked")
        Set<Integer>[] components = new HashSet[vertexCount];

        @SuppressWarnings("unchecked")
        Set<Edge>[] forest = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        initializeStructures(graph, vertexCount, componentId, components, forest, edgeQueue);

        int mergedVerticesCount = 0;

        while (mergedVerticesCount != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int sourceComponent = componentId[edge.source];
            int destinationComponent = componentId[edge.destination];

            if (sourceComponent == destinationComponent) {
                continue;
            }

            Set<Integer> sourceSet = components[sourceComponent];
            Set<Integer> destinationSet = components[destinationComponent];

            if (sourceSet.contains(edge.destination) || destinationSet.contains(edge.source)) {
                continue;
            }

            mergeComponents(sourceSet, destinationSet, componentId, sourceComponent);
            addEdge(forest, edge.source, edge.destination, edge.weight);
            mergedVerticesCount = sourceSet.size();
        }

        return forest;
    }

    private void initializeStructures(
        Set<Edge>[] graph,
        int vertexCount,
        int[] componentId,
        Set<Integer>[] components,
        Set<Edge>[] forest,
        PriorityQueue<Edge> edgeQueue
    ) {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forest[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentId[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }
    }

    private void mergeComponents(
        Set<Integer> sourceSet,
        Set<Integer> destinationSet,
        int[] componentId,
        int sourceComponent
    ) {
        sourceSet.addAll(destinationSet);
        for (int vertex : sourceSet) {
            componentId[vertex] = sourceComponent;
        }
    }
}