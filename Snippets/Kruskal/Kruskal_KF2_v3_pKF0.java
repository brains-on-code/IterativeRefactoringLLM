package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Kruskal {

    static class Edge {
        final int from;
        final int to;
        final int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static void addEdge(Set<Edge>[] graph, int from, int to, int weight) {
        graph[from].add(new Edge(from, to, weight));
    }

    public Set<Edge>[] kruskal(Set<Edge>[] graph) {
        int nodeCount = graph.length;

        int[] componentLeader = new int[nodeCount];
        Set<Integer>[] components = new HashSet[nodeCount];
        Set<Edge>[] mstGraph = new HashSet[nodeCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        initializeStructures(graph, nodeCount, componentLeader, components, mstGraph, edgeQueue);

        int connectedNodeCount = 0;

        while (connectedNodeCount != nodeCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int fromLeader = componentLeader[edge.from];
            int toLeader = componentLeader[edge.to];

            if (fromLeader == toLeader) {
                continue;
            }

            mergeComponents(fromLeader, toLeader, components, componentLeader);

            addEdge(mstGraph, edge.from, edge.to, edge.weight);
            connectedNodeCount = components[fromLeader].size();
        }

        return mstGraph;
    }

    private void initializeStructures(
        Set<Edge>[] graph,
        int nodeCount,
        int[] componentLeader,
        Set<Integer>[] components,
        Set<Edge>[] mstGraph,
        PriorityQueue<Edge> edgeQueue
    ) {
        for (int node = 0; node < nodeCount; node++) {
            mstGraph[node] = new HashSet<>();
            components[node] = new HashSet<>();
            components[node].add(node);
            componentLeader[node] = node;
            edgeQueue.addAll(graph[node]);
        }
    }

    private void mergeComponents(
        int fromLeader,
        int toLeader,
        Set<Integer>[] components,
        int[] componentLeader
    ) {
        Set<Integer> fromComponent = components[fromLeader];
        Set<Integer> toComponent = components[toLeader];

        fromComponent.addAll(toComponent);
        for (int node : fromComponent) {
            componentLeader[node] = fromLeader;
        }
    }
}