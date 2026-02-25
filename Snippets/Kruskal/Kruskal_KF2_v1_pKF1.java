package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kruskal {

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

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int numberOfVertices = graph.length;
        int[] componentRepresentative = new int[numberOfVertices];
        HashSet<Integer>[] components = new HashSet[numberOfVertices];
        HashSet<Edge>[] minimumSpanningTree = new HashSet[numberOfVertices];

        PriorityQueue<Edge> edgePriorityQueue =
                new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            minimumSpanningTree[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentRepresentative[vertex] = vertex;
            edgePriorityQueue.addAll(graph[vertex]);
        }

        int connectedVertexCount = 0;

        while (connectedVertexCount != numberOfVertices && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int sourceComponent = componentRepresentative[currentEdge.source];
            int destinationComponent = componentRepresentative[currentEdge.destination];

            if (!components[sourceComponent].contains(currentEdge.destination)
                    && !components[destinationComponent].contains(currentEdge.source)) {

                components[sourceComponent].addAll(components[destinationComponent]);

                components[sourceComponent].forEach(
                        vertex -> componentRepresentative[vertex] = sourceComponent
                );

                addEdge(minimumSpanningTree, currentEdge.source, currentEdge.destination, currentEdge.weight);

                connectedVertexCount = components[sourceComponent].size();
            }
        }

        return minimumSpanningTree;
    }
}