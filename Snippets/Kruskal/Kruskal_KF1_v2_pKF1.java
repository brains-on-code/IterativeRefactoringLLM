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
        HashSet<Integer>[] componentVertices = new HashSet[vertexCount];
        HashSet<Edge>[] minimumSpanningForest = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            minimumSpanningForest[vertex] = new HashSet<>();
            componentVertices[vertex] = new HashSet<>();
            componentVertices[vertex].add(vertex);
            vertexToComponentId[vertex] = vertex;
            edgePriorityQueue.addAll(graphAdjacencyList[vertex]);
        }

        int mergedVerticesCount = 0;

        while (mergedVerticesCount != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int sourceComponentId = vertexToComponentId[currentEdge.sourceVertex];
            int destinationComponentId = vertexToComponentId[currentEdge.destinationVertex];

            if (!componentVertices[sourceComponentId].contains(currentEdge.destinationVertex)
                && !componentVertices[destinationComponentId].contains(currentEdge.sourceVertex)) {

                componentVertices[sourceComponentId].addAll(componentVertices[destinationComponentId]);

                componentVertices[sourceComponentId].forEach(
                    vertex -> vertexToComponentId[vertex] = sourceComponentId
                );

                addEdge(
                    minimumSpanningForest,
                    currentEdge.sourceVertex,
                    currentEdge.destinationVertex,
                    currentEdge.weight
                );

                mergedVerticesCount = componentVertices[sourceComponentId].size();
            }
        }

        return minimumSpanningForest;
    }
}