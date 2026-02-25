package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    private int vertexCount;
    private int edgeCount;
    private Edge[] edges;
    private int nextEdgeIndex = 0;

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

    void printPath(int[] parents, int currentVertex) {
        if (parents[currentVertex] == -1) {
            return;
        }
        printPath(parents, parents[currentVertex]);
        System.out.print(currentVertex + " ");
    }

    public static void main(String[] args) {
        BellmanFord bellmanFord = new BellmanFord(0, 0);
        bellmanFord.runInteractive();
    }

    public void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int vertexCountInput = scanner.nextInt();
            int edgeCountInput = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgeCountInput];

            System.out.println("Input edges (source destination weight):");
            for (int i = 0; i < edgeCountInput; i++) {
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                inputEdges[i] = new Edge(source, destination, weight);
            }

            int[] distances = new int[vertexCountInput];
            int[] parents = new int[vertexCountInput];

            for (int vertex = 0; vertex < vertexCountInput; vertex++) {
                distances[vertex] = Integer.MAX_VALUE;
                parents[vertex] = -1;
            }
            distances[0] = 0;

            for (int iteration = 0; iteration < vertexCountInput - 1; iteration++) {
                for (int i = 0; i < edgeCountInput; i++) {
                    Edge edge = inputEdges[i];
                    if (distances[edge.source] != Integer.MAX_VALUE
                            && distances[edge.destination] > distances[edge.source] + edge.weight) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                        parents[edge.destination] = edge.source;
                    }
                }
            }

            boolean hasNegativeCycle = false;
            for (int i = 0; i < edgeCountInput; i++) {
                Edge edge = inputEdges[i];
                if (distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source (0):");
                for (int vertex = 0; vertex < vertexCountInput; vertex++) {
                    System.out.println(vertex + " " + distances[vertex]);
                }
                System.out.println("Paths from source (0):");
                for (int vertex = 0; vertex < vertexCountInput; vertex++) {
                    System.out.print("0 ");
                    printPath(parents, vertex);
                    System.out.println();
                }
            }
        }
    }

    public void show(int sourceVertex, int destinationVertex, Edge[] edgeArray) {
        int vertexCountLocal = this.vertexCount;
        int edgeCountLocal = this.edgeCount;
        boolean hasNegativeCycle = false;

        double[] distances = new double[vertexCountLocal];
        int[] parents = new int[vertexCountLocal];

        for (int vertex = 0; vertex < vertexCountLocal; vertex++) {
            distances[vertex] = Integer.MAX_VALUE;
            parents[vertex] = -1;
        }
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertexCountLocal - 1; iteration++) {
            for (int i = 0; i < edgeCountLocal; i++) {
                Edge edge = edgeArray[i];
                if ((int) distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        for (int i = 0; i < edgeCountLocal; i++) {
            Edge edge = edgeArray[i];
            if ((int) distances[edge.source] != Integer.MAX_VALUE
                    && distances[edge.destination] > distances[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println(
                    "Distance from " + sourceVertex + " to " + destinationVertex + " is: " + distances[destinationVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parents, destinationVertex);
            System.out.println();
        }
    }

    public void addEdge(int sourceVertex, int destinationVertex, int weight) {
        edges[nextEdgeIndex++] = new Edge(sourceVertex, destinationVertex, weight);
    }

    public Edge[] getEdges() {
        return edges;
    }
}