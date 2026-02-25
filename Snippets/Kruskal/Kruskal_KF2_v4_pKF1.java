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

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graphAdjacencyList) {
        int vertexCount = graphAdjacencyList.length;

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
            edgeMinHeap.addAll(graphAdjacencyList[vertex]);
        }

        int mstVertexCount = 0;

        while (mstVertexCount != vertexCount && !edgeMinHeap.isEmpty()) {
            Edge currentEdge = edgeMinHeap.poll();

            int sourceComponentId = componentIdByVertex[currentEdge.source];
            int destinationComponentId = componentIdByVertex[currentEdge.destination];

            if (!verticesByComponentId[sourceComponentId].contains(currentEdge.destination)
                    && !verticesByComponentId[destinationComponentId].contains(currentEdge.source)) {

                verticesByComponentId[sourceComponentId].addAll(verticesByComponentId[destinationComponentId]);

                verticesByComponentId[sourceComponentId].forEach(
                        vertex -> componentIdByVertex[vertex] = sourceComponentId
                );

                addEdge(
                        mstAdjacencyList,
                        currentEdge.source,
                        currentEdge.destination,
                        currentEdge.weight
                );

                mstVertexCount = verticesByComponentId[sourceComponentId].size();
            }
        }

        return mstAdjacencyList;
    }
}