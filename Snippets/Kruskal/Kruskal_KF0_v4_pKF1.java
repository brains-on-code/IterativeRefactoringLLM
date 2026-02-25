package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kruskal {

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

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graphAdjacencyList) {
        int vertexCount = graphAdjacencyList.length;

        int[] vertexComponentId = new int[vertexCount];
        HashSet<Integer>[] componentToVertices = new HashSet[vertexCount];
        HashSet<Edge>[] minimumSpanningTreeAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            minimumSpanningTreeAdjacencyList[vertex] = new HashSet<>();
            componentToVertices[vertex] = new HashSet<>();
            componentToVertices[vertex].add(vertex);
            vertexComponentId[vertex] = vertex;
            edgePriorityQueue.addAll(graphAdjacencyList[vertex]);
        }

        int currentComponentVertexCount = 0;

        while (currentComponentVertexCount != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int sourceComponentId = vertexComponentId[currentEdge.sourceVertex];
            int destinationComponentId = vertexComponentId[currentEdge.destinationVertex];

            if (!componentToVertices[sourceComponentId].contains(currentEdge.destinationVertex)
                && !componentToVertices[destinationComponentId].contains(currentEdge.sourceVertex)) {

                componentToVertices[sourceComponentId].addAll(componentToVertices[destinationComponentId]);

                componentToVertices[sourceComponentId].forEach(
                    vertex -> vertexComponentId[vertex] = sourceComponentId
                );

                addEdge(
                    minimumSpanningTreeAdjacencyList,
                    currentEdge.sourceVertex,
                    currentEdge.destinationVertex,
                    currentEdge.weight
                );

                currentComponentVertexCount = componentToVertices[sourceComponentId].size();
            }
        }

        return minimumSpanningTreeAdjacencyList;
    }
}