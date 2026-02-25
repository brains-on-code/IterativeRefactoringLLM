package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Implementation of Bellman-Ford to detect negative cycles and compute shortest
 * paths in a weighted directed graph.
 *
 * Vertices are labeled from 0 to vertexCount - 1 (inclusive).
 */
class BellmanFord {

    private final int vertexCount;
    private final int edgeCount;
    private final Edge[] edges;
    private int edgeIndex = 0;

    BellmanFord(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.edges = new Edge[edgeCount];
    }

    static class Edge {
        final int from;
        final int to;
        final int weight;

        /**
         * @param from   source vertex
         * @param to     destination vertex
         * @param weight edge weight
         */
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Recursively prints the path from the source to vertex {@code current}
     * using the parent array.
     *
     * @param parent parent array
     * @param current current vertex
     */
    private void printPath(int[] parent, int current) {
        if (parent[current] == -1) {
            return;
        }
        printPath(parent, parent[current]);
        System.out.print(current + " ");
    }

    public static void main(String[] args) {
        BellmanFord dummy = new BellmanFord(0, 0); // Dummy object to call instance method
        dummy.interactiveRun();
    }

    /**
     * Interactive run for understanding the algorithm.
     * Assumes source vertex is 0.
     */
    public void interactiveRun() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int v = scanner.nextInt();
            int e = scanner.nextInt();

            Edge[] inputEdges = new Edge[e];
            System.out.println("Input edges (from to weight):");
            for (int i = 0; i < e; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int weight = scanner.nextInt();
                inputEdges[i] = new Edge(from, to, weight);
            }

            int source = 0;
            int[] dist = new int[v];
            int[] parent = new int[v];

            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);

            dist[source] = 0;

            // Relax edges |V| - 1 times
            for (int i = 0; i < v - 1; i++) {
                for (Edge edge : inputEdges) {
                    if (dist[edge.from] != Integer.MAX_VALUE
                        && dist[edge.to] > dist[edge.from] + edge.weight) {
                        dist[edge.to] = dist[edge.from] + edge.weight;
                        parent[edge.to] = edge.from;
                    }
                }
            }

            // Check for negative-weight cycles
            boolean hasNegativeCycle = false;
            for (Edge edge : inputEdges) {
                if (dist[edge.from] != Integer.MAX_VALUE
                    && dist[edge.to] > dist[edge.from] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source " + source + ":");
                for (int i = 0; i < v; i++) {
                    System.out.println(i + " " + dist[i]);
                }

                System.out.println("Paths from source " + source + ":");
                for (int i = 0; i < v; i++) {
                    System.out.print(source + " ");
                    printPath(parent, i);
                    System.out.println();
                }
            }
        }
    }

    /**
     * Shows the shortest path and distance from {@code source} to {@code end}
     * using the given edge array. Assumes the graph has {@code vertexCount}
     * vertices and {@code edgeCount} edges.
     *
     * @param source starting vertex
     * @param end    ending vertex
     * @param edges  array of edges
     */
    public void show(int source, int end, Edge[] edges) {
        int v = vertexCount;
        int e = edgeCount;

        double[] dist = new double[v];
        int[] parent = new int[v];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        // Relax edges |V| - 1 times
        for (int i = 0; i < v - 1; i++) {
            for (int j = 0; j < e; j++) {
                Edge edge = edges[j];
                if ((int) dist[edge.from] != Integer.MAX_VALUE
                    && dist[edge.to] > dist[edge.from] + edge.weight) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
                    parent[edge.to] = edge.from;
                }
            }
        }

        // Check for negative-weight cycles
        boolean hasNegativeCycle = false;
        for (int j = 0; j < e; j++) {
            Edge edge = edges[j];
            if ((int) dist[edge.from] != Integer.MAX_VALUE
                && dist[edge.to] > dist[edge.from] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Distance from " + source + " to " + end + " is: " + dist[end]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parent, end);
            System.out.println();
        }
    }

    /**
     * Adds a directed edge to the internal edge array.
     *
     * @param from   source vertex
     * @param to     destination vertex
     * @param weight edge weight
     */
    public void addEdge(int from, int to, int weight) {
        if (edgeIndex >= edges.length) {
            throw new IllegalStateException("Cannot add more edges than initialized: " + edges.length);
        }
        edges[edgeIndex++] = new Edge(from, to, weight);
    }

    public Edge[] getEdgeArray() {
        return Arrays.copyOf(edges, edgeIndex);
    }
}