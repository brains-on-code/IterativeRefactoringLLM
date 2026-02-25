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

    static void addEdge(HashSet<Edge>[] adjacencyList, int source, int destination, int weight) {
        adjacencyList[source].add(new Edge(source, destination, weight));
    }

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;

        int[] componentId = new int[vertexCount];
        HashSet<Integer>[] componentVertices = new HashSet[vertexCount];
        HashSet<Edge>[] minimumSpanningTree = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            minimumSpanningTree[vertex] = new HashSet<>();
            componentVertices[vertex] = new HashSet<>();
            componentVertices[vertex].add(vertex);
            componentId[vertex] = vertex;
            edgePriorityQueue.addAll(graph[vertex]);
        }

        int totalVerticesInCurrentComponent = 0;

        while (totalVerticesInCurrentComponent != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int sourceComponentId = componentId[currentEdge.source];
            int destinationComponentId = componentId[currentEdge.destination];

            if (!componentVertices[sourceComponentId].contains(currentEdge.destination)
                && !componentVertices[destinationComponentId].contains(currentEdge.source)) {

                componentVertices[sourceComponentId].addAll(componentVertices[destinationComponentId]);

                componentVertices[sourceComponentId].forEach(
                    vertex -> componentId[vertex] = sourceComponentId
                );

                addEdge(
                    minimumSpanningTree,
                    currentEdge.source,
                    currentEdge.destination,
                    currentEdge.weight
                );

                totalVerticesInCurrentComponent = componentVertices[sourceComponentId].size();
            }
        }

        return minimumSpanningTree;
    }
}