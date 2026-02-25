package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Utility class for graph-related operations.
 */
public class Class1 {

    /**
     * Represents a weighted directed edge in a graph.
     *
     * <p>source: source vertex</p>
     * <p>destination: destination vertex</p>
     * <p>weight: edge weight</p>
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
     * Adds a directed edge to the adjacency list.
     *
     * @param adjacencyList adjacency list of edges
     * @param source        source vertex
     * @param destination   destination vertex
     * @param weight        edge weight
     */
    static void addEdge(HashSet<Class2>[] adjacencyList, int source, int destination, int weight) {
        adjacencyList[source].add(new Class2(source, destination, weight));
    }

    /**
     * Constructs a minimum spanning forest using a variant of Kruskal's algorithm.
     *
     * @param graph adjacency list of the original graph
     * @return adjacency list representing the minimum spanning forest
     */
    public HashSet<Class2>[] buildMinimumSpanningForest(HashSet<Class2>[] graph) {
        int vertexCount = graph.length;

        // parent[i] is the representative (root) of the component containing vertex i
        int[] parent = new int[vertexCount];

        // components[i] is the set of vertices in the component whose representative is i
        HashSet<Integer>[] components = new HashSet[vertexCount];

        // Resulting adjacency list for the minimum spanning forest
        HashSet<Class2>[] forest = new HashSet[vertexCount];

        // Priority queue of all edges, ordered by weight
        PriorityQueue<Class2> edgeQueue =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        // Initialize disjoint sets and result adjacency list; collect all edges
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            forest[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            parent[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }

        int largestComponentSize = 0;

        // Process edges in non-decreasing order of weight
        while (largestComponentSize != vertexCount && !edgeQueue.isEmpty()) {
            Class2 edge = edgeQueue.poll();

            int rootSource = parent[edge.source];
            int rootDestination = parent[edge.destination];

            // If vertices are in different components, add the edge and union the sets
            if (!components[rootSource].contains(edge.destination)
                && !components[rootDestination].contains(edge.source)) {

                // Merge component of destination into component of source
                components[rootSource].addAll(components[rootDestination]);

                // Update parent references for all vertices in the merged component
                components[rootSource].forEach(vertex -> parent[vertex] = rootSource);

                // Add edge to the resulting minimum spanning forest
                addEdge(forest, edge.source, edge.destination, edge.weight);

                // Update size of the current largest component
                largestComponentSize = components[rootSource].size();
            }
        }
        return forest;
    }
}