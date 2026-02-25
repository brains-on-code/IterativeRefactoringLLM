package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Bellman-Ford algorithm for shortest paths and negative-cycle detection
 * in a weighted directed graph.
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
     * Directed, weighted edge: source -> destination with a given weight.
     */
    static class Edge {
        final int source;
        final int destination;
        final int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Recursively prints the path from the source to {@code vertex}
     * using the {@code parent} array.
     *
     * @param parent parent[v] is the predecessor of v in the shortest-path tree
     * @param vertex current vertex
     */
    void printPath(int[] parent, int vertex) {
        if (vertex == -1) {
            return;
        }
        printPath(parent, parent[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        BellmanFord bellmanFord = new BellmanFord(0, 0);
        bellmanFord.runInteractive();
    }

    /**
     * Interactive demonstration of Bellman-Ford.
     * Assumes source vertex is 0.
     */
    public void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int vertices = scanner.nextInt();
            int edgesCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgesCount];
            System.out.println("Input edges (source destination weight):");
            for (int i = 0; i < edgesCount; i++) {
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                inputEdges[i] = new Edge(source, destination, weight);
            }

            int[] distances = new int[vertices];
            int[] parents = new int[vertices];

            Arrays.fill(distances, Integer.MAX_VALUE);
            Arrays.fill(parents, -1);
            distances[0] = 0;

            relaxEdges(vertices, edgesCount, inputEdges, distances, parents);

            boolean hasNegativeCycle = detectNegativeCycle(edgesCount, inputEdges, distances);

            if (!hasNegativeCycle) {
                printDistancesFromSource(0, vertices, distances);
                printAllPathsFromSource(0, vertices, parents);
            }
        }
    }

    /**
     * Runs Bellman-Ford on the current graph and prints the shortest distance and
     * path from {@code source} to {@code target}.
     *
     * @param source    starting vertex
     * @param target    ending vertex
     * @param edgeArray array of edges representing the graph
     */
    public void show(int source, int target, Edge[] edgeArray) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;

        double[] distances = new double[vertices];
        int[] parents = new int[vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        distances[source] = 0;

        relaxEdges(vertices, edgesCount, edgeArray, distances, parents);

        boolean hasNegativeCycle = detectNegativeCycle(edgesCount, edgeArray, distances);

        if (!hasNegativeCycle) {
            System.out.println("Distance from " + source + " to " + target + " is: " + distances[target]);
            System.out.println("Path followed:");
            printPath(parents, source);
            printPath(parents, target);
            System.out.println();
        }
    }

    /**
     * Adds a directed edge (source -> destination) with the given weight
     * to the internal edge list.
     *
     * @param source      source vertex
     * @param destination destination vertex
     * @param weight      edge weight
     */
    public void addEdge(int source, int destination, int weight) {
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    /**
     * Returns the internal array of edges.
     *
     * @return array of edges
     */
    public Edge[] getEdgeArray() {
        return edges;
    }

    private void relaxEdges(int vertices, int edgesCount, Edge[] edgeArray, double[] distances, int[] parents) {
        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                int source = edgeArray[j].source;
                int destination = edgeArray[j].destination;
                int weight = edgeArray[j].weight;

                if ((int) distances[source] != Integer.MAX_VALUE
                        && distances[destination] > distances[source] + weight) {
                    distances[destination] = distances[source] + weight;
                    parents[destination] = source;
                }
            }
        }
    }

    private void relaxEdges(int vertices, int edgesCount, Edge[] edgeArray, int[] distances, int[] parents) {
        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                int source = edgeArray[j].source;
                int destination = edgeArray[j].destination;
                int weight = edgeArray[j].weight;

                if (distances[source] != Integer.MAX_VALUE
                        && distances[destination] > distances[source] + weight) {
                    distances[destination] = distances[source] + weight;
                    parents[destination] = source;
                }
            }
        }
    }

    private boolean detectNegativeCycle(int edgesCount, Edge[] edgeArray, double[] distances) {
        for (int j = 0; j < edgesCount; j++) {
            int source = edgeArray[j].source;
            int destination = edgeArray[j].destination;
            int weight = edgeArray[j].weight;

            if ((int) distances[source] != Integer.MAX_VALUE
                    && distances[destination] > distances[source] + weight) {
                System.out.println("Negative cycle detected");
                return true;
            }
        }
        return false;
    }

    private boolean detectNegativeCycle(int edgesCount, Edge[] edgeArray, int[] distances) {
        for (int j = 0; j < edgesCount; j++) {
            int source = edgeArray[j].source;
            int destination = edgeArray[j].destination;
            int weight = edgeArray[j].weight;

            if (distances[source] != Integer.MAX_VALUE
                    && distances[destination] > distances[source] + weight) {
                System.out.println("Negative cycle detected");
                return true;
            }
        }
        return false;
    }

    private void printDistancesFromSource(int source, int vertices, int[] distances) {
        System.out.println("Distances from source (" + source + "):");
        for (int i = 0; i < vertices; i++) {
            System.out.println(i + " " + distances[i]);
        }
    }

    private void printAllPathsFromSource(int source, int vertices, int[] parents) {
        System.out.println("Paths from source (" + source + "):");
        for (int i = 0; i < vertices; i++) {
            System.out.print(source + " ");
            printPath(parents, i);
            System.out.println();
        }
    }
}