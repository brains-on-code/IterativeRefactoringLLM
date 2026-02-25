package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * The Kruskal class implements Kruskal's Algorithm to find the Minimum Spanning Tree (MST)
 * of a connected, undirected graph. The algorithm constructs the MST by selecting edges
 * with the least weight, ensuring no cycles are formed, and using union-find to track the
 * connected components.
 *
 * <p><strong>Key Features:</strong></p>
 * <ul>
 *   <li>The graph is represented using an adjacency list, where each node points to a set of edges.</li>
 *   <li>Each edge is processed in ascending order of weight using a priority queue.</li>
 *   <li>The algorithm stops when all nodes are connected or no more edges are available.</li>
 * </ul>
 *
 * <p><strong>Time Complexity:</strong> O(E log V), where E is the number of edges and V is the number of vertices.</p>
 */
public class Kruskal {

    /**
     * Represents an edge in the graph with a source, destination, and weight.
     */
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

    /**
     * Adds an edge to the graph.
     *
     * @param graph the adjacency list representing the graph
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @param weight the weight of the edge
     */
    static void addEdge(HashSet<Edge>[] graph, int source, int destination, int weight) {
        graph[source].add(new Edge(source, destination, weight));
    }

    /**
     * Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a graph.
     *
     * @param graph the adjacency list representing the input graph
     * @return the adjacency list representing the MST
     */
    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int vertexCount = graph.length;
        int[] componentLeader = new int[vertexCount]; // Stores the leader of each vertex's connected component
        HashSet<Integer>[] components = new HashSet[vertexCount];
        HashSet<Edge>[] minimumSpanningTree = new HashSet[vertexCount];
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            minimumSpanningTree[vertex] = new HashSet<>();
            components[vertex] = new HashSet<>();
            components[vertex].add(vertex);
            componentLeader[vertex] = vertex;
            edgeQueue.addAll(graph[vertex]);
        }

        int connectedVertexCount = 0;

        while (connectedVertexCount != vertexCount && !edgeQueue.isEmpty()) {
            Edge currentEdge = edgeQueue.poll();

            int sourceLeader = componentLeader[currentEdge.source];
            int destinationLeader = componentLeader[currentEdge.destination];

            // Avoid forming cycles by checking if the vertices belong to different connected components
            if (!components[sourceLeader].contains(currentEdge.destination)
                && !components[destinationLeader].contains(currentEdge.source)) {

                // Merge the two sets of vertices connected by the edge
                components[sourceLeader].addAll(components[destinationLeader]);

                // Update the leader for each merged vertex
                components[sourceLeader].forEach(vertex -> componentLeader[vertex] = sourceLeader);

                // Add the edge to the resulting MST graph
                addEdge(minimumSpanningTree, currentEdge.source, currentEdge.destination, currentEdge.weight);

                // Update the count of connected vertices
                connectedVertexCount = components[sourceLeader].size();
            }
        }

        return minimumSpanningTree;
    }
}