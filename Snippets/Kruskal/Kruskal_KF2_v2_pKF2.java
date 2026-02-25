package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kruskal {

    static class Edge {
        int from;
        int to;
        int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static void addEdge(HashSet<Edge>[] graph, int from, int to, int weight) {
        graph[from].add(new Edge(from, to, weight));
    }

    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int nodeCount = graph.length;

        int[] componentLeader = new int[nodeCount];
        HashSet<Integer>[] components = new HashSet[nodeCount];
        HashSet<Edge>[] minimumSpanningTree = new HashSet[nodeCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int i = 0; i < nodeCount; i++) {
            minimumSpanningTree[i] = new HashSet<>();
            components[i] = new HashSet<>();
            components[i].add(i);
            componentLeader[i] = i;
            edgeQueue.addAll(graph[i]);
        }

        int connectedVertices = 0;

        while (connectedVertices != nodeCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();

            int fromLeader = componentLeader[edge.from];
            int toLeader = componentLeader[edge.to];

            boolean alreadyConnected =
                components[fromLeader].contains(edge.to) || components[toLeader].contains(edge.from);

            if (alreadyConnected) {
                continue;
            }

            components[fromLeader].addAll(components[toLeader]);

            int newLeader = fromLeader;
            for (int vertex : components[newLeader]) {
                componentLeader[vertex] = newLeader;
            }

            addEdge(minimumSpanningTree, edge.from, edge.to, edge.weight);

            connectedVertices = components[newLeader].size();
        }

        return minimumSpanningTree;
    }
}