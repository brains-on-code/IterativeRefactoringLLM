package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
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

    private static class Edge {
        final int source;
        final int destination;
        final int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private void printPath(int[] parent, int vertex) {
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
            int vertexCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgeCount];

            System.out.println("Enter edges as: <source> <destination> <weight>");
            for (int i = 0; i < edgeCount; i++) {
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                inputEdges[i] = new Edge(source, destination, weight);
            }

            int[] distances = new int[vertexCount];
            int[] parents = new int[vertexCount];
            Arrays.fill(distances, Integer.MAX_VALUE);
            Arrays.fill(parents, -1);

            int sourceVertex = 0;
            distances[sourceVertex] = 0;

            for (int i = 0; i < vertexCount - 1; i++) {
                for (int j = 0; j < edgeCount; j++) {
                    Edge edge = inputEdges[j];
                    if (distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                        parents[edge.destination] = edge.source;
                    }
                }
            }

            boolean hasNegativeCycle = false;
            for (int j = 0; j < edgeCount; j++) {
                Edge edge = inputEdges[j];
                if (distances[edge.source] != Integer.MAX_VALUE
                    && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Graph contains a negative weight cycle.");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Shortest distances from source vertex " + sourceVertex + ":");
                for (int i = 0; i < vertexCount; i++) {
                    System.out.println(i + " -> " + distances[i]);
                }

                System.out.println("Paths from source vertex " + sourceVertex + ":");
                for (int i = 0; i < vertexCount; i++) {
                    System.out.print(sourceVertex + " ");
                    printPath(parents, i);
                    System.out.println();
                }
            }
        }
    }

    public void show(int source, int destination, Edge[] graphEdges) {
        int v = vertexCount;
        int e = edgeCount;

        double[] distances = new double[v];
        int[] parents = new int[v];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);

        distances[source] = 0;

        for (int i = 0; i < v - 1; i++) {
            for (int j = 0; j < e; j++) {
                Edge edge = graphEdges[j];
                if ((int) distances[edge.source] != Integer.MAX_VALUE
                    && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        boolean hasNegativeCycle = false;
        for (int j = 0; j < e; j++) {
            Edge edge = graphEdges[j];
            if ((int) distances[edge.source] != Integer.MAX_VALUE
                && distances[edge.destination] > distances[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Graph contains a negative weight cycle.");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Shortest distance from " + source + " to " + destination + " is: " + distances[destination]);
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