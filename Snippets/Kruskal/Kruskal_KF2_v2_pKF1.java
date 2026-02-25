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

    static void addEdge(HashSet<Edge>[] adjacencyList, int fromVertex, int toVertex, int weight) {
        adjacencyList[fromVertex].add(new Edge(fromVertex, toVertex, weight));
    }

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;
        int[] componentIdByVertex = new int[vertexCount];
        HashSet<Integer>[] verticesByComponentId = new HashSet[vertexCount];
        HashSet<Edge>[] mstAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeMinHeap =
                new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            mstAdjacencyList[vertex] = new HashSet<>();
            verticesByComponentId[vertex] = new HashSet<>();
            verticesByComponentId[vertex].add(vertex);
            componentIdByVertex[vertex] = vertex;
            edgeMinHeap.addAll(graph[vertex]);
        }

        int verticesInCurrentMstComponent = 0;

        while (verticesInCurrentMstComponent != vertexCount && !edgeMinHeap.isEmpty()) {
            Edge currentEdge = edgeMinHeap.poll();

            int fromComponentId = componentIdByVertex[currentEdge.fromVertex];
            int toComponentId = componentIdByVertex[currentEdge.toVertex];

            if (!verticesByComponentId[fromComponentId].contains(currentEdge.toVertex)
                    && !verticesByComponentId[toComponentId].contains(currentEdge.fromVertex)) {

                verticesByComponentId[fromComponentId].addAll(verticesByComponentId[toComponentId]);

                verticesByComponentId[fromComponentId].forEach(
                        vertex -> componentIdByVertex[vertex] = fromComponentId
                );

                addEdge(mstAdjacencyList, currentEdge.fromVertex, currentEdge.toVertex, currentEdge.weight);

                verticesInCurrentMstComponent = verticesByComponentId[fromComponentId].size();
            }
        }

        return mstAdjacencyList;
    }
}