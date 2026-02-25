package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Kruskal's algorithm for computing the Minimum Spanning Tree (MST)
 * of a connected, undirected graph.
 *
 * <p>Time Complexity: O(E log V), where E is the number of edges and V is the number of vertices.</p>
 */
public class Kruskal {

    /**
     * Represents an edge in a graph.
     */
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

    /**
     * Adds a directed edge to the given adjacency list.
     *
     * @param graph  adjacency list of the graph
     * @param from   source vertex
     * @param to     destination vertex
     * @param weight edge weight
     */
    static void addEdge(HashSet<Edge>[] graph, int from, int to, int weight) {
        graph[from].add(new Edge(from, to, weight));
    }

    /**
     * Computes a Minimum Spanning Tree using Kruskal's algorithm.
     *
     * @param graph adjacency list of the input graph
     * @return adjacency list of the resulting MST
     */
    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int nodeCount = graph.length;

        // captain[v] is the representative (leader) of the component containing vertex v
        int[] captain = new int[nodeCount];

        // componentVertices[leader] is the set of vertices in the component whose leader is `leader`
        @SuppressWarnings("unchecked")
        HashSet<Integer>[] componentVertices = new HashSet[nodeCount];

        // MST adjacency list
        @SuppressWarnings("unchecked")
        HashSet<Edge>[] mst = new HashSet[nodeCount];

        // All edges sorted by non-decreasing weight
        PriorityQueue<Edge> edges =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int v = 0; v < nodeCount; v++) {
            mst[v] = new HashSet<>();
            componentVertices[v] = new HashSet<>();
            componentVertices[v].add(v);
            captain[v] = v;
            edges.addAll(graph[v]);
        }

        int verticesInSingleComponent = 0;

        while (verticesInSingleComponent != nodeCount && !edges.isEmpty()) {
            Edge edge = edges.poll();

            int fromLeader = captain[edge.from];
            int toLeader = captain[edge.to];

            // Skip edges whose endpoints are already in the same component (would create a cycle)
            if (fromLeader == toLeader) {
                continue;
            }

            // Merge the component of `toLeader` into the component of `fromLeader`
            componentVertices[fromLeader].addAll(componentVertices[toLeader]);

            // Update leader for all vertices in the merged component
            for (int vertex : componentVertices[fromLeader]) {
                captain[vertex] = fromLeader;
            }

            // Add the selected edge to the MST
            addEdge(mst, edge.from, edge.to, edge.weight);

            verticesInSingleComponent = componentVertices[fromLeader].size();
        }

        return mst;
    }
}