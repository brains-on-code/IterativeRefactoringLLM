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

        int[] vertexComponentIds = new int[vertexCount];
        HashSet<Integer>[] componentVertices = new HashSet[vertexCount];
        HashSet<Edge>[] forestAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forestAdjacencyList[vertex] = new HashSet<>();
            componentVertices[vertex] = new HashSet<>();
            componentVertices[vertex].add(vertex);
            vertexComponentIds[vertex] = vertex;
            edgePriorityQueue.addAll(graphAdjacencyList[vertex]);
        }

        int largestComponentSize = 0;

        while (largestComponentSize != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge edge = edgePriorityQueue.poll();

            int sourceComponentId = vertexComponentIds[edge.source];
            int destinationComponentId = vertexComponentIds[edge.destination];

            if (!componentVertices[sourceComponentId].contains(edge.destination)
                && !componentVertices[destinationComponentId].contains(edge.source)) {

                componentVertices[sourceComponentId].addAll(componentVertices[destinationComponentId]);

                componentVertices[sourceComponentId].forEach(
                    vertex -> vertexComponentIds[vertex] = sourceComponentId
                );

                addEdge(
                    forestAdjacencyList,
                    edge.source,
                    edge.destination,
                    edge.weight
                );

                largestComponentSize = componentVertices[sourceComponentId].size();
            }
        }

        return forestAdjacencyList;
    }
}