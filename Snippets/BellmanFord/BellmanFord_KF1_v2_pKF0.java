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

    private void printPath(int[] parent, int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            return;
        }
        if (parent[vertex] == -1) {
            return;
        }
        printPath(parent, parent[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        new BellmanFord(0, 0).runFromInput();
    }

    public void runFromInput() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter number of vertices and edges:");
            int vertices = sc.nextInt();
            int edgesCount = sc.nextInt();

            Edge[] inputEdges = readEdges(sc, edgesCount);

            int[] distance = new int[vertices];
            int[] parent = new int[vertices];

            initializeSingleSource(distance, parent, 0);

            relaxEdges(vertices, edgesCount, inputEdges, distance, parent);

            if (detectNegativeCycle(edgesCount, inputEdges, distance)) {
                System.out.println("Negative cycle detected");
                return;
            }

            printAllDistances(0, distance);
            printAllPaths(0, parent);
        }
    }

    public void shortestPath(int source, int destination, Edge[] edges) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;

        int[] distance = new int[vertices];
        int[] parent = new int[vertices];

        initializeSingleSource(distance, parent, source);

        relaxEdges(vertices, edgesCount, edges, distance, parent);

        if (detectNegativeCycle(edgesCount, edges, distance)) {
            System.out.println("Negative cycle detected");
            return;
        }

        System.out.println("Distance from " + source + " to " + destination + " is: " + distance[destination]);
        System.out.println("Path followed:");
        System.out.print(source + " ");
        printPath(parent, destination);
        System.out.println();
    }

    public void addEdge(int source, int destination, int weight) {
        if (edgeIndex >= edges.length) {
            throw new IllegalStateException("Cannot add more edges than initialized: " + edgeCount);
        }
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdges() {
        return Arrays.copyOf(edges, edgeIndex);
    }

    private static Edge[] readEdges(Scanner sc, int edgesCount) {
        System.out.println("Input edges (source destination weight):");
        Edge[] inputEdges = new Edge[edgesCount];
        for (int i = 0; i < edgesCount; i++) {
            int source = sc.nextInt();
            int destination = sc.nextInt();
            int weight = sc.nextInt();
            inputEdges[i] = new Edge(source, destination, weight);
        }
        return inputEdges;
    }

    private static void initializeSingleSource(int[] distance, int[] parent, int source) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distance[source] = 0;
    }

    private static void relaxEdges(int vertices, int edgesCount, Edge[] edges, int[] distance, int[] parent) {
        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = edges[j];
                if (distance[edge.source] == Integer.MAX_VALUE) {
                    continue;
                }
                int newDistance = distance[edge.source] + edge.weight;
                if (distance[edge.destination] > newDistance) {
                    distance[edge.destination] = newDistance;
                    parent[edge.destination] = edge.source;
                }
            }
        }
    }

    private static boolean detectNegativeCycle(int edgesCount, Edge[] edges, int[] distance) {
        for (int i = 0; i < edgesCount; i++) {
            Edge edge = edges[i];
            if (distance[edge.source] == Integer.MAX_VALUE) {
                continue;
            }
            if (distance[edge.destination] > distance[edge.source] + edge.weight) {
                return true;
            }
        }
        return false;
    }

    private void printAllDistances(int source, int[] distance) {
        System.out.println("Distances from source " + source + ":");
        for (int i = 0; i < distance.length; i++) {
            System.out.println(i + " " + distance[i]);
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
}