package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MinimumSpanningForestBuilder {

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

    static void addEdge(HashSet<Edge>[] adjacencyList, int source, int destination, int weight) {
        adjacencyList[source].add(new Edge(source, destination, weight));
    }

    public HashSet<Edge>[] buildMinimumSpanningForest(HashSet<Edge>[] graphAdjacencyList) {
        int vertexCount = graphAdjacencyList.length;
        int[] vertexToComponent = new int[vertexCount];
        HashSet<Integer>[] componentToVertices = new HashSet[vertexCount];
        HashSet<Edge>[] forestAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forestAdjacencyList[vertex] = new HashSet<>();
            componentToVertices[vertex] = new HashSet<>();
            componentToVertices[vertex].add(vertex);
            vertexToComponent[vertex] = vertex;
            edgeQueue.addAll(graphAdjacencyList[vertex]);
        }

        int mergedVertexCount = 0;

        while (mergedVertexCount != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();

            int sourceComponent = vertexToComponent[edge.source];
            int destinationComponent = vertexToComponent[edge.destination];

            if (!componentToVertices[sourceComponent].contains(edge.destination)
                && !componentToVertices[destinationComponent].contains(edge.source)) {

                componentToVertices[sourceComponent].addAll(componentToVertices[destinationComponent]);

                componentToVertices[sourceComponent].forEach(
                    vertex -> vertexToComponent[vertex] = sourceComponent
                );

                addEdge(
                    forestAdjacencyList,
                    edge.source,
                    edge.destination,
                    edge.weight
                );

                mergedVertexCount = componentToVertices[sourceComponent].size();
            }
        }

        return forestAdjacencyList;
    }
}