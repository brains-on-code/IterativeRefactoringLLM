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
        if (vertex == -1) {
            return;
        }
        printPath(parent, parent[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        new BellmanFord(0, 0).runFromInput();
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
            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);

            int sourceVertex = 0;
            distance[sourceVertex] = 0;

            relaxEdges(vertices, edgesCount, inputEdges, distance, parent);

            if (hasNegativeCycle(edgesCount, inputEdges, distance)) {
                System.out.println("Negative cycle detected");
                return;
            }

            System.out.println("Distances from source (" + sourceVertex + "):");
            for (int i = 0; i < vertices; i++) {
                System.out.println(i + " " + distance[i]);
            }

            System.out.println("Paths from source (" + sourceVertex + "):");
            for (int i = 0; i < vertices; i++) {
                System.out.print(sourceVertex + " ");
                printPath(parent, i);
                System.out.println();
            }
        }
    }

    public void show(int source, int destination, Edge[] edges) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;

        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        distance[source] = 0;

        relaxEdges(vertices, edgesCount, edges, distance, parent);

        if (hasNegativeCycle(edgesCount, edges, distance)) {
            System.out.println("Negative cycle detected");
            return;
        }

        System.out.println("Distance from " + source + " to " + destination + " is: " + distance[destination]);
        System.out.println("Path followed:");
        System.out.print(source + " ");
        printPath(parent, destination);
        System.out.println();
    }

    private void relaxEdges(int vertices, int edgesCount, Edge[] edges, int[] distance, int[] parent) {
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

    private boolean hasNegativeCycle(int edgesCount, Edge[] edges, int[] distance) {
        for (int j = 0; j < edgesCount; j++) {
            Edge edge = edges[j];
            if (distance[edge.source] == Integer.MAX_VALUE) {
                continue;
            }
            if (distance[edge.destination] > distance[edge.source] + edge.weight) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(int source, int destination, int weight) {
        if (edgeIndex >= edgeCount) {
            throw new IllegalStateException("Cannot add more edges than the initialized edgeCount");
        }
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdgeArray() {
        return Arrays.copyOf(edges, edgeIndex);
    }
}