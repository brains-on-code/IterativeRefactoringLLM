package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kruskal {

    static class Edge {

        int fromVertex;
        int toVertex;
        int weight;

        Edge(int fromVertex, int toVertex, int weight) {
            this.fromVertex = fromVertex;
            this.toVertex = toVertex;
            this.weight = weight;
        }
    }

    static void addEdge(HashSet<Edge>[] graph, int fromVertex, int toVertex, int weight) {
        graph[fromVertex].add(new Edge(fromVertex, toVertex, weight));
    }

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;

        int[] componentRepresentative = new int[vertexCount];
        HashSet<Integer>[] componentVertices = new HashSet[vertexCount];
        HashSet<Edge>[] minimumSpanningTree = new HashSet[vertexCount];

        PriorityQueue<Edge> edgePriorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            minimumSpanningTree[vertex] = new HashSet<>();
            componentVertices[vertex] = new HashSet<>();
            componentVertices[vertex].add(vertex);
            componentRepresentative[vertex] = vertex;
            edgePriorityQueue.addAll(graph[vertex]);
        }

        int totalConnectedVertices = 0;

        while (totalConnectedVertices != vertexCount && !edgePriorityQueue.isEmpty()) {
            Edge currentEdge = edgePriorityQueue.poll();

            int fromComponentRep = componentRepresentative[currentEdge.fromVertex];
            int toComponentRep = componentRepresentative[currentEdge.toVertex];

            if (!componentVertices[fromComponentRep].contains(currentEdge.toVertex)
                && !componentVertices[toComponentRep].contains(currentEdge.fromVertex)) {

                componentVertices[fromComponentRep].addAll(componentVertices[toComponentRep]);

                componentVertices[fromComponentRep].forEach(
                    vertex -> componentRepresentative[vertex] = fromComponentRep
                );

                addEdge(
                    minimumSpanningTree,
                    currentEdge.fromVertex,
                    currentEdge.toVertex,
                    currentEdge.weight
                );

                totalConnectedVertices = componentVertices[fromComponentRep].size();
            }
        }

        return minimumSpanningTree;
    }
}