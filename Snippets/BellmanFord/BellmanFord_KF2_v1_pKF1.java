package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    private int vertexCount;
    private int edgeCount;
    private Edge[] edges;
    private int edgeIndex = 0;

    BellmanFord(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.edges = new Edge[edgeCount];
    }

    class Edge {

        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    void printPath(int[] parent, int vertex) {
        if (parent[vertex] == -1) {
            return;
        }
        printPath(parent, parent[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        BellmanFord bellmanFord = new BellmanFord(0, 0);
        bellmanFord.runInteractive();
    }

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

            for (int i = 0; i < vertices; i++) {
                distances[i] = Integer.MAX_VALUE;
            }
            distances[0] = 0;
            parents[0] = -1;

            for (int i = 0; i < vertices - 1; i++) {
                for (int j = 0; j < edgesCount; j++) {
                    Edge edge = inputEdges[j];
                    if (distances[edge.source] != Integer.MAX_VALUE
                            && distances[edge.destination] > distances[edge.source] + edge.weight) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                        parents[edge.destination] = edge.source;
                    }
                }
            }

            boolean hasNegativeCycle = false;
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = inputEdges[j];
                if (distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source (0):");
                for (int i = 0; i < vertices; i++) {
                    System.out.println(i + " " + distances[i]);
                }
                System.out.println("Paths from source (0):");
                for (int i = 0; i < vertices; i++) {
                    System.out.print("0 ");
                    printPath(parents, i);
                    System.out.println();
                }
            }
        }
    }

    public void show(int source, int destination, Edge[] edgeArray) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;
        boolean hasNegativeCycle = false;

        double[] distances = new double[vertices];
        int[] parents = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[source] = 0;
        parents[source] = -1;

        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = edgeArray[j];
                if ((int) distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        for (int j = 0; j < edgesCount; j++) {
            Edge edge = edgeArray[j];
            if ((int) distances[edge.source] != Integer.MAX_VALUE
                    && distances[edge.destination] > distances[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Distance from " + source + " to " + destination + " is: " + distances[destination]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parents, destination);
            System.out.println();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}