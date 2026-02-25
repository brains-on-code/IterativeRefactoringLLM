package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

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

    private static void addEdge(HashSet<Edge>[] graph, int source, int destination, int weight) {
        graph[source].add(new Edge(source, destination, weight));
    }

    public HashSet<Edge>[] buildMinimumSpanningForest(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;

        int[] componentId = new int[vertexCount];
        @SuppressWarnings("unchecked")
        HashSet<Integer>[] components = new HashSet[vertexCount];
        @SuppressWarnings("unchecked")
        HashSet<Edge>[] forest = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forest[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentId[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }

        int mergedVertexCount = 0;

        while (mergedVertexCount != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int sourceComponent = componentId[edge.source];
            int destinationComponent = componentId[edge.destination];

            if (sourceComponent == destinationComponent) {
                continue;
            }

            HashSet<Integer> sourceSet = components[sourceComponent];
            HashSet<Integer> destinationSet = components[destinationComponent];

            if (!sourceSet.contains(edge.destination) && !destinationSet.contains(edge.source)) {
                sourceSet.addAll(destinationSet);
                for (int vertex : sourceSet) {
                    componentId[vertex] = sourceComponent;
                }

                addEdge(forest, edge.source, edge.destination, edge.weight);
                mergedVertexCount = sourceSet.size();
            }
        }

        return forest;
    }
}