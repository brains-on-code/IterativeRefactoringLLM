package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Class1 {

    static class Edge {

        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static void addEdge(HashSet<Edge>[] graph, int source, int destination, int weight) {
        graph[source].add(new Edge(source, destination, weight));
    }

    public HashSet<Edge>[] buildMinimumSpanningForest(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;
        int[] componentId = new int[vertexCount];
        HashSet<Integer>[] components = new HashSet[vertexCount];
        HashSet<Edge>[] result = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            result[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentId[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }

        int mergedVertices = 0;

        while (mergedVertices != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();

            int sourceComponent = componentId[edge.source];
            int destinationComponent = componentId[edge.destination];

            if (!components[sourceComponent].contains(edge.destination)
                && !components[destinationComponent].contains(edge.source)) {

                components[sourceComponent].addAll(components[destinationComponent]);

                components[sourceComponent].forEach(
                    vertex -> componentId[vertex] = sourceComponent
                );

                addEdge(result, edge.source, edge.destination, edge.weight);

                mergedVertices = components[sourceComponent].size();
            }
        }

        return result;
    }
}