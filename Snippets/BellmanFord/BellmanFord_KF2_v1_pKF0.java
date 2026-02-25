package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

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
        bellmanFord.runFromInput();
    }

    public void runFromInput() {
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

            int[] distance = new int[vertices];
            int[] parent = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
                parent[i] = -1;
            }

            distance[0] = 0;

            for (int i = 0; i < vertices - 1; i++) {
                for (int j = 0; j < edgesCount; j++) {
                    Edge edge = inputEdges[j];
                    if (distance[edge.source] != Integer.MAX_VALUE
                            && distance[edge.destination] > distance[edge.source] + edge.weight) {
                        distance[edge.destination] = distance[edge.source] + edge.weight;
                        parent[edge.destination] = edge.source;
                    }
                }
            }

            boolean hasNegativeCycle = false;
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = inputEdges[j];
                if (distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source (0):");
                for (int i = 0; i < vertices; i++) {
                    System.out.println(i + " " + distance[i]);
                }

                System.out.println("Paths from source (0):");
                for (int i = 0; i < vertices; i++) {
                    System.out.print("0 ");
                    printPath(parent, i);
                    System.out.println();
                }
            }
        }
    }

    public void show(int source, int destination, Edge[] edges) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;

        double[] distance = new double[vertices];
        int[] parent = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        distance[source] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = edges[j];
                if ((int) distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                    parent[edge.destination] = edge.source;
                }
            }
        }

        boolean hasNegativeCycle = false;
        for (int j = 0; j < edgesCount; j++) {
            Edge edge = edges[j];
            if ((int) distance[edge.source] != Integer.MAX_VALUE
                    && distance[edge.destination] > distance[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Distance from " + source + " to " + destination + " is: " + distance[destination]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parent, destination);
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