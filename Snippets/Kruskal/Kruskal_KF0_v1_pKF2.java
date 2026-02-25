package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Implements Kruskal's algorithm to find the Minimum Spanning Tree (MST)
 * of a connected, undirected graph.
 *
 * <p>Time Complexity: O(E log V), where E is the number of edges and V is the number of vertices.</p>
 */
public class Kruskal {

    /** Represents an edge in the graph. */
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
     * Adds a directed edge to the adjacency list.
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
     * Runs Kruskal's algorithm to compute an MST.
     *
     * @param graph adjacency list of the input graph
     * @return adjacency list of the resulting MST
     */
    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int nodes = graph.length;

        // captain[i] is the representative (leader) of the component containing i
        int[] captain = new int[nodes];

        // connectedGroups[c] is the set of vertices in the component whose leader is c
        HashSet<Integer>[] connectedGroups = new HashSet[nodes];

        // Resulting MST adjacency list
        HashSet<Edge>[] minGraph = new HashSet[nodes];

        // All edges sorted by weight (ascending)
        PriorityQueue<Edge> edges =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int i = 0; i < nodes; i++) {
            minGraph[i] = new HashSet<>();
            connectedGroups[i] = new HashSet<>();
            connectedGroups[i].add(i);
            captain[i] = i;
            edges.addAll(graph[i]);
        }

        int connectedElements = 0;

        while (connectedElements != nodes && !edges.isEmpty()) {
            Edge edge = edges.poll();

            int fromLeader = captain[edge.from];
            int toLeader = captain[edge.to];

            // Skip if both endpoints are already in the same component (would form a cycle)
            if (fromLeader == toLeader) {
                continue;
            }

            // Merge the two components
            connectedGroups[fromLeader].addAll(connectedGroups[toLeader]);

            // Update leader for all vertices in the merged component
            for (int vertex : connectedGroups[fromLeader]) {
                captain[vertex] = fromLeader;
            }

            // Add the edge to the MST
            addEdge(minGraph, edge.from, edge.to, edge.weight);

            connectedElements = connectedGroups[fromLeader].size();
        }

        return minGraph;
    }
}