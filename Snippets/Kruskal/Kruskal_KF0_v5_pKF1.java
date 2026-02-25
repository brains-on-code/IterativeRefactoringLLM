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

        int[] vertexToComponent = new int[vertexCount];
        HashSet<Integer>[] componentVertices = new HashSet[vertexCount];
        HashSet<Edge>[] mstAdjacencyList = new HashSet[vertexCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            mstAdjacencyList[vertex] = new HashSet<>();
            componentVertices[vertex] = new HashSet<>();
            componentVertices[vertex].add(vertex);
            vertexToComponent[vertex] = vertex;
            edgeQueue.addAll(graphAdjacencyList[vertex]);
        }

        int largestComponentSize = 0;

        while (largestComponentSize != vertexCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();

            int sourceComponent = vertexToComponent[edge.source];
            int destinationComponent = vertexToComponent[edge.destination];

            if (!componentVertices[sourceComponent].contains(edge.destination)
                && !componentVertices[destinationComponent].contains(edge.source)) {

                componentVertices[sourceComponent].addAll(componentVertices[destinationComponent]);

                componentVertices[sourceComponent].forEach(
                    vertex -> vertexToComponent[vertex] = sourceComponent
                );

                addEdge(
                    mstAdjacencyList,
                    edge.source,
                    edge.destination,
                    edge.weight
                );

                largestComponentSize = componentVertices[sourceComponent].size();
            }
        }

        return mstAdjacencyList;
    }
}