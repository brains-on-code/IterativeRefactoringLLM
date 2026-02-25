package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * The Kruskal class implements Kruskal's Algorithm to find the Minimum Spanning Tree (MST)
 * of a connected, undirected graph.
 */
public class Kruskal {

    /**
     * Represents an edge in the graph with a source, destination, and weight.
     */
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

    /**
     * Adds an edge to the graph.
     *
     * @param graph  the adjacency list representing the graph
     * @param from   the source vertex of the edge
     * @param to     the destination vertex of the edge
     * @param weight the weight of the edge
     */
    static void addEdge(HashSet<Edge>[] graph, int from, int to, int weight) {
        graph[from].add(new Edge(from, to, weight));
    }

    /**
     * Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a graph.
     *
     * @param graph the adjacency list representing the input graph
     * @return the adjacency list representing the MST
     */
    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int nodeCount = graph.length;

        int[] componentLeader = new int[nodeCount];
        HashSet<Integer>[] components = new HashSet[nodeCount];
        HashSet<Edge>[] mstGraph = new HashSet[nodeCount];

        PriorityQueue<Edge> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int node = 0; node < nodeCount; node++) {
            mstGraph[node] = new HashSet<>();
            components[node] = new HashSet<>();
            components[node].add(node);
            componentLeader[node] = node;
            edgeQueue.addAll(graph[node]);
        }

        int connectedNodeCount = 0;

        while (connectedNodeCount != nodeCount && !edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();

            int fromLeader = componentLeader[edge.from];
            int toLeader = componentLeader[edge.to];

            // Skip if both vertices are already in the same component (would form a cycle)
            if (fromLeader == toLeader) {
                continue;
            }

            HashSet<Integer> fromComponent = components[fromLeader];
            HashSet<Integer> toComponent = components[toLeader];

            // Merge smaller component into larger one for efficiency
            if (fromComponent.size() < toComponent.size()) {
                HashSet<Integer> tempSet = fromComponent;
                fromComponent = toComponent;
                toComponent = tempSet;

                int tempLeader = fromLeader;
                fromLeader = toLeader;
                toLeader = tempLeader;
            }

            fromComponent.addAll(toComponent);
            for (int node : toComponent) {
                componentLeader[node] = fromLeader;
            }

            addEdge(mstGraph, edge.from, edge.to, edge.weight);
            connectedNodeCount = fromComponent.size();
        }

        return mstGraph;
    }
}