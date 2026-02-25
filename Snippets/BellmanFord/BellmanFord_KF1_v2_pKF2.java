package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Scanner;

class BellmanFord {

    private final int vertexCount;
    private final int edgeCapacity;
    private final Edge[] edges;
    private int edgeCount = 0;

    BellmanFord(int vertexCount, int edgeCapacity) {
        this.vertexCount = vertexCount;
        this.edgeCapacity = edgeCapacity;
        this.edges = new Edge[edgeCapacity];
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

            Edge[] inputEdges = new Edge[edgesCount];

            System.out.println("Enter edges as: <source> <destination> <weight>");
            for (int i = 0; i < edgesCount; i++) {
                int source = sc.nextInt();
                int destination = sc.nextInt();
                int weight = sc.nextInt();
                inputEdges[i] = new Edge(source, destination, weight);
            }

            int[] distance = new int[vertices];
            int[] parent = new int[vertices];

            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);

            distance[0] = 0;

            for (int i = 0; i < vertices - 1; i++) {
                for (int j = 0; j < edgesCount; j++) {
                    Edge edge = inputEdges[j];
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

            boolean hasNegativeCycle = false;
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = inputEdges[j];
                if (distance[edge.source] == Integer.MAX_VALUE) {
                    continue;
                }
                if (distance[edge.destination] > distance[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Graph contains a negative-weight cycle.");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Vertex distances from source (0):");
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

    public void shortestPath(int sourceVertex, int targetVertex, Edge[] graphEdges) {
        int vertices = vertexCount;
        int edgesCount = edgeCapacity;

        double[] distance = new double[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        distance[sourceVertex] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (int j = 0; j < edgesCount; j++) {
                Edge edge = graphEdges[j];
                if ((int) distance[edge.source] == Integer.MAX_VALUE) {
                    continue;
                }
                double newDistance = distance[edge.source] + edge.weight;
                if (distance[edge.destination] > newDistance) {
                    distance[edge.destination] = newDistance;
                    parent[edge.destination] = edge.source;
                }
            }
        }

        boolean hasNegativeCycle = false;
        for (int j = 0; j < edgesCount; j++) {
            Edge edge = graphEdges[j];
            if ((int) distance[edge.source] == Integer.MAX_VALUE) {
                continue;
            }
            if (distance[edge.destination] > distance[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Graph contains a negative-weight cycle.");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Shortest distance from " + sourceVertex + " to " + targetVertex + " is: " + distance[targetVertex]);
            System.out.println("Path:");
            System.out.print(sourceVertex + " ");
            printPath(parent, targetVertex);
            System.out.println();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        if (edgeCount >= edgeCapacity) {
            throw new IllegalStateException("Edge capacity exceeded");
        }
        edges[edgeCount++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdges() {
        return Arrays.copyOf(edges, edgeCount);
    }
}