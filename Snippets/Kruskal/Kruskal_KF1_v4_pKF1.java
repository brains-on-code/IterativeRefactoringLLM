package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MinimumSpanningForestBuilder {

    static class Edge {

        int sourceVertex;
        int destinationVertex;
        int weight;

        Edge(int sourceVertex, int destinationVertex, int weight) {
            this.sourceVertex = sourceVertex;
            this.destinationVertex = destinationVertex;
            this.weight = weight;
        }
    }

    static void addEdge(HashSet<Edge>[] adjacencyList, int sourceVertex, int destinationVertex, int weight) {
        adjacencyList[sourceVertex].add(new Edge(sourceVertex, destinationVertex, weight));
    }

    public HashSet<Edge>[] buildMinimumSpanningForest(HashSet<Edge>[] graphAdjacencyList) {
        int vertexCount = graphAdjacencyList.length;
        int[] vertexToComponentId = new int[vertexCount];
        HashSet<Integer>[] componentIdToVertices = new HashSet[vertexCount];
        HashSet<Edge>[] forestAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forestAdjacencyList[vertex] = new HashSet<>();
            componentIdToVertices[vertex] = new HashSet<>();
            componentIdToVertices[vertex].add(vertex);
            vertexToComponentId[vertex] = vertex;
            edgePriorityQueue.addAll(graphAdjacencyList[vertex]);
        }

        int mergedVerticesInLargestComponent = 0;

        while (mergedVerticesInLargestComponent != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int sourceComponentId = vertexToComponentId[currentEdge.sourceVertex];
            int destinationComponentId = vertexToComponentId[currentEdge.destinationVertex];

            if (!componentIdToVertices[sourceComponentId].contains(currentEdge.destinationVertex)
                && !componentIdToVertices[destinationComponentId].contains(currentEdge.sourceVertex)) {

                componentIdToVertices[sourceComponentId].addAll(componentIdToVertices[destinationComponentId]);

                componentIdToVertices[sourceComponentId].forEach(
                    vertex -> vertexToComponentId[vertex] = sourceComponentId
                );

                addEdge(
                    forestAdjacencyList,
                    currentEdge.sourceVertex,
                    currentEdge.destinationVertex,
                    currentEdge.weight
                );

                mergedVerticesInLargestComponent = componentIdToVertices[sourceComponentId].size();
            }
        }

        return forestAdjacencyList;
    }
}