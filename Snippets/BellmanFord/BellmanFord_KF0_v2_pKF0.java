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
     * @param parent  parent array
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
        new BellmanFord(0, 0).interactiveRun();
    }

    /**
     * Interactive run for understanding the algorithm.
     * Assumes source vertex is 0.
     */
    public void interactiveRun() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int vertexCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            Edge[] inputEdges = readEdges(scanner, edgeCount);

            int source = 0;
            int[] parent = new int[vertexCount];
            int[] dist = runBellmanFord(vertexCount, inputEdges, source, parent);

            if (dist == null) {
                System.out.println("Negative cycle detected");
                return;
            }

            printDistances(source, dist);
            printAllPaths(source, parent);
        }
    }

    private Edge[] readEdges(Scanner scanner, int edgeCount) {
        Edge[] inputEdges = new Edge[edgeCount];
        System.out.println("Input edges (from to weight):");
        for (int i = 0; i < edgeCount; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            inputEdges[i] = new Edge(from, to, weight);
        }
        return inputEdges;
    }

    private int[] runBellmanFord(int vertexCount, Edge[] edges, int source, int[] parent) {
        int[] dist = new int[vertexCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        relaxEdges(vertexCount, edges, dist, parent);

        if (hasNegativeCycle(edges, dist)) {
            return null;
        }
        return dist;
    }

    private void relaxEdges(int vertexCount, Edge[] edges, int[] dist, int[] parent) {
        for (int i = 0; i < vertexCount - 1; i++) {
            for (Edge edge : edges) {
                if (dist[edge.from] == Integer.MAX_VALUE) {
                    continue;
                }
                int newDist = dist[edge.from] + edge.weight;
                if (dist[edge.to] > newDist) {
                    dist[edge.to] = newDist;
                    parent[edge.to] = edge.from;
                }
            }
        }
    }

    private boolean hasNegativeCycle(Edge[] edges, int[] dist) {
        for (Edge edge : edges) {
            if (dist[edge.from] == Integer.MAX_VALUE) {
                continue;
            }
            if (dist[edge.to] > dist[edge.from] + edge.weight) {
                return true;
            }
        }
        return false;
    }

    private void printDistances(int source, int[] dist) {
        System.out.println("Distances from source " + source + ":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + " " + dist[i]);
        }
    }

    private void printAllPaths(int source, int[] parent) {
        System.out.println("Paths from source " + source + ":");
        for (int i = 0; i < parent.length; i++) {
            System.out.print(source + " ");
            printPath(parent, i);
            System.out.println();
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