package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFordGraph {

    int vertexCount;
    int edgeCount;
    private Edge[] edges;
    private int edgeIndex = 0;

    BellmanFordGraph(int vertexCount, int edgeCount) {
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
        BellmanFordGraph graph = new BellmanFordGraph(0, 0);
        graph.runInteractive();
    }

    public void runInteractive() {
        try (Scanner sc = new Scanner(System.in)) {
            int vertices;
            int edgesCount;
            int source;
            int destination;
            int weight;
            int hasNegativeCycle = 0;

            System.out.println("Enter no. of vertices and edges please");
            vertices = sc.nextInt();
            edgesCount = sc.nextInt();

            Edge[] inputEdges = new Edge[edgesCount];
            System.out.println("Input edges");
            for (int i = 0; i < edgesCount; i++) {
                source = sc.nextInt();
                destination = sc.nextInt();
                weight = sc.nextInt();
                inputEdges[i] = new Edge(source, destination, weight);
            }

            int[] distance = new int[vertices];
            int[] parent = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
            }
            distance[0] = 0;
            parent[0] = -1;

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

            for (int j = 0; j < edgesCount; j++) {
                Edge edge = inputEdges[j];
                if (distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    hasNegativeCycle = 1;
                    System.out.println("Negative cycle");
                    break;
                }
            }

            if (hasNegativeCycle == 0) {
                System.out.println("Distances are: ");
                for (int i = 0; i < vertices; i++) {
                    System.out.println(i + " " + distance[i]);
                }
                System.out.println("Path followed:");
                for (int i = 0; i < vertices; i++) {
                    System.out.print("0 ");
                    printPath(parent, i);
                    System.out.println();
                }
            }
        }
    }

    public void shortestPath(int sourceVertex, int targetVertex, Edge[] edges) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;
        int hasNegativeCycle = 0;

        double[] distance = new double[vertices];
        int[] parent = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[sourceVertex] = 0;
        parent[sourceVertex] = -1;

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

        for (int j = 0; j < edgesCount; j++) {
            Edge edge = edges[j];
            if ((int) distance[edge.source] != Integer.MAX_VALUE
                    && distance[edge.destination] > distance[edge.source] + edge.weight) {
                hasNegativeCycle = 1;
                System.out.println("Negative cycle");
                break;
            }
        }

        if (hasNegativeCycle == 0) {
            System.out.println("Distance is: " + distance[targetVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parent, targetVertex);
            System.out.println();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdges() {
        return edges;
    }
}