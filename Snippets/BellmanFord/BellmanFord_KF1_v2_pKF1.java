package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFordGraph {

    int vertexCount;
    int edgeCount;
    private Edge[] edges;
    private int nextEdgeIndex = 0;

    BellmanFordGraph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.edges = new Edge[edgeCount];
    }

    class Edge {

        int sourceVertex;
        int destinationVertex;
        int weight;

        Edge(int sourceVertex, int destinationVertex, int weight) {
            this.sourceVertex = sourceVertex;
            this.destinationVertex = destinationVertex;
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
        try (Scanner scanner = new Scanner(System.in)) {
            int vertices;
            int edgesCount;
            int sourceVertex;
            int destinationVertex;
            int weight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter number of vertices and edges:");
            vertices = scanner.nextInt();
            edgesCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgesCount];
            System.out.println("Input edges (source destination weight):");
            for (int i = 0; i < edgesCount; i++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                weight = scanner.nextInt();
                inputEdges[i] = new Edge(sourceVertex, destinationVertex, weight);
            }

            int[] distance = new int[vertices];
            int[] parent = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
                parent[i] = -1;
            }
            distance[0] = 0;

            for (int iteration = 0; iteration < vertices - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < edgesCount; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distance[edge.sourceVertex] != Integer.MAX_VALUE
                            && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.weight) {
                        distance[edge.destinationVertex] = distance[edge.sourceVertex] + edge.weight;
                        parent[edge.destinationVertex] = edge.sourceVertex;
                    }
                }
            }

            for (int edgeIndex = 0; edgeIndex < edgesCount; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distance[edge.sourceVertex] != Integer.MAX_VALUE
                        && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source vertex 0:");
                for (int vertex = 0; vertex < vertices; vertex++) {
                    System.out.println(vertex + " " + distance[vertex]);
                }
                System.out.println("Paths from source vertex 0:");
                for (int vertex = 0; vertex < vertices; vertex++) {
                    System.out.print("0 ");
                    printPath(parent, vertex);
                    System.out.println();
                }
            }
        }
    }

    public void shortestPath(int sourceVertex, int targetVertex, Edge[] edges) {
        int vertices = vertexCount;
        int edgesCount = edgeCount;
        boolean hasNegativeCycle = false;

        double[] distance = new double[vertices];
        int[] parent = new int[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            distance[vertex] = Integer.MAX_VALUE;
            parent[vertex] = -1;
        }
        distance[sourceVertex] = 0;

        for (int iteration = 0; iteration < vertices - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < edgesCount; edgeIndex++) {
                Edge edge = edges[edgeIndex];
                if ((int) distance[edge.sourceVertex] != Integer.MAX_VALUE
                        && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.weight) {
                    distance[edge.destinationVertex] = distance[edge.sourceVertex] + edge.weight;
                    parent[edge.destinationVertex] = edge.sourceVertex;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < edgesCount; edgeIndex++) {
            Edge edge = edges[edgeIndex];
            if ((int) distance[edge.sourceVertex] != Integer.MAX_VALUE
                    && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Shortest distance from " + sourceVertex + " to " + targetVertex + " is: " + distance[targetVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parent, targetVertex);
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