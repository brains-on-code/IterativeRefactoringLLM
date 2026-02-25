package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

/**
 * Implementation of the Bellman-Ford algorithm to detect negative cycles and
 * compute shortest paths in a weighted directed graph.
 *
 * <p>Vertices are labeled from 0 to (vertexCount - 1), inclusive.</p>
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

    /**
     * Represents a directed, weighted edge (u -> v) with weight w.
     */
    class Edge {
        int u; // source vertex
        int v; // destination vertex
        int w; // edge weight

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    /**
     * Recursively prints the path from the source to vertex {@code i} using the
     * parent array.
     *
     * @param parent parent[i] is the predecessor of vertex i in the shortest path tree
     * @param i      current vertex
     */
    void printPath(int[] parent, int i) {
        if (parent[i] == -1) {
            return;
        }
        printPath(parent, parent[i]);
        System.out.print(i + " ");
    }

    public static void main(String[] args) {
        // Dummy object to call non-static methods
        BellmanFord obj = new BellmanFord(0, 0);
        obj.go();
    }

    /**
     * Interactive run of Bellman-Ford for demonstration.
     * Assumes source vertex is 0.
     */
    public void go() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int v = sc.nextInt();
            int e = sc.nextInt();

            Edge[] arr = new Edge[e];
            System.out.println("Input edges (u v w):");
            for (int i = 0; i < e; i++) {
                int u = sc.nextInt();
                int ve = sc.nextInt();
                int w = sc.nextInt();
                arr[i] = new Edge(u, ve, w);
            }

            int[] dist = new int[v]; // dist[i] = shortest distance from source (0) to i
            int[] parent = new int[v]; // parent[i] = predecessor of i in the shortest path

            for (int i = 0; i < v; i++) {
                dist[i] = Integer.MAX_VALUE;
                parent[i] = -1;
            }
            dist[0] = 0;

            // Relax edges (v - 1) times
            for (int i = 0; i < v - 1; i++) {
                for (int j = 0; j < e; j++) {
                    int u = arr[j].u;
                    int ve = arr[j].v;
                    int w = arr[j].w;

                    if (dist[u] != Integer.MAX_VALUE && dist[ve] > dist[u] + w) {
                        dist[ve] = dist[u] + w;
                        parent[ve] = u;
                    }
                }
            }

            // Check for negative-weight cycles
            boolean hasNegativeCycle = false;
            for (int j = 0; j < e; j++) {
                int u = arr[j].u;
                int ve = arr[j].v;
                int w = arr[j].w;

                if (dist[u] != Integer.MAX_VALUE && dist[ve] > dist[u] + w) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source (0):");
                for (int i = 0; i < v; i++) {
                    System.out.println(i + " " + dist[i]);
                }

                System.out.println("Paths from source (0):");
                for (int i = 0; i < v; i++) {
                    System.out.print("0 ");
                    printPath(parent, i);
                    System.out.println();
                }
            }
        }
    }

    /**
     * Runs Bellman-Ford on the current graph and prints the shortest distance and
     * path from {@code source} to {@code end}.
     *
     * @param source starting vertex
     * @param end    ending vertex
     * @param arr    array of edges representing the graph
     */
    public void show(int source, int end, Edge[] arr) {
        int v = vertexCount;
        int e = edgeCount;

        double[] dist = new double[v]; // dist[i] = shortest distance from source to i
        int[] parent = new int[v];     // parent[i] = predecessor of i in the shortest path

        for (int i = 0; i < v; i++) {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        dist[source] = 0;

        // Relax edges (v - 1) times
        for (int i = 0; i < v - 1; i++) {
            for (int j = 0; j < e; j++) {
                int u = arr[j].u;
                int ve = arr[j].v;
                int w = arr[j].w;

                if ((int) dist[u] != Integer.MAX_VALUE && dist[ve] > dist[u] + w) {
                    dist[ve] = dist[u] + w;
                    parent[ve] = u;
                }
            }
        }

        // Check for negative-weight cycles
        boolean hasNegativeCycle = false;
        for (int j = 0; j < e; j++) {
            int u = arr[j].u;
            int ve = arr[j].v;
            int w = arr[j].w;

            if ((int) dist[u] != Integer.MAX_VALUE && dist[ve] > dist[u] + w) {
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
     * Adds a directed edge (x -> y) with weight z to the internal edge list.
     *
     * @param x source vertex
     * @param y destination vertex
     * @param z weight
     */
    public void addEdge(int x, int y, int z) {
        edges[edgeIndex++] = new Edge(x, y, z);
    }

    /**
     * Returns the internal array of edges.
     *
     * @return array of edges
     */
    public Edge[] getEdgeArray() {
        return edges;
    }
}