package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Builds a minimum spanning forest for a directed, weighted graph.
 */
public class Class1 {

    /**
     * Represents a directed, weighted edge in the graph.
     */
    static class Class2 {
        int source;
        int destination;
        int weight;

        Class2(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Adds a directed edge to the given adjacency list.
     */
    static void addEdge(HashSet<Class2>[] adjacencyList, int source, int destination, int weight) {
        adjacencyList[source].add(new Class2(source, destination, weight));
    }

    /**
     * Builds a minimum spanning forest using a Kruskal-like algorithm.
     *
     * @param graph adjacency list of the original graph
     * @return adjacency list representing the minimum spanning forest
     */
    public HashSet<Class2>[] buildMinimumSpanningForest(HashSet<Class2>[] graph) {
        int vertexCount = graph.length;

        // parent[v] is the representative (root) of the component containing v
        int[] parent = new int[vertexCount];

        // components[r] is the set of vertices whose representative is r
        @SuppressWarnings("unchecked")
        HashSet<Integer>[] components = new HashSet[vertexCount];

        // adjacency list of the resulting forest
        @SuppressWarnings("unchecked")
        HashSet<Class2>[] forest = new HashSet[vertexCount];

        // all edges ordered by non-decreasing weight
        PriorityQueue<Class2> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        // initialize components, parents, forest, and collect all edges
        for (int v = 0; v < vertexCount; v++) {
            forest[v] = new HashSet<>();
            components[v] = new HashSet<>();
            components[v].add(v);
            parent[v] = v;
            edgeQueue.addAll(graph[v]);
        }

        int largestComponentSize = 0;

        // process edges in order of increasing weight
        while (largestComponentSize != vertexCount && !edgeQueue.isEmpty()) {
            Class2 edge = edgeQueue.poll();

            int rootSource = parent[edge.source];
            int rootDestination = parent[edge.destination];

            // skip if both endpoints are already in the same component
            if (components[rootSource].contains(edge.destination)
                || components[rootDestination].contains(edge.source)) {
                continue;
            }

            // merge destination's component into source's component
            components[rootSource].addAll(components[rootDestination]);

            // update parent for all vertices in the merged component
            for (int vertex : components[rootSource]) {
                parent[vertex] = rootSource;
            }

            // add edge to the forest
            addEdge(forest, edge.source, edge.destination, edge.weight);

            // update size of the largest component
            largestComponentSize = components[rootSource].size();
        }

        return forest;
    }
}