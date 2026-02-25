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

        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    void printPath(int[] parents, int vertex) {
        if (parents[vertex] == -1) {
            return;
        }
        printPath(parents, parents[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        BellmanFordGraph graph = new BellmanFordGraph(0, 0);
        graph.runInteractive();
    }

    public void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            int numberOfVertices;
            int numberOfEdges;
            int sourceVertex;
            int destinationVertex;
            int edgeWeight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter number of vertices and edges:");
            numberOfVertices = scanner.nextInt();
            numberOfEdges = scanner.nextInt();

            Edge[] inputEdges = new Edge[numberOfEdges];
            System.out.println("Input edges (source destination weight):");
            for (int i = 0; i < numberOfEdges; i++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                edgeWeight = scanner.nextInt();
                inputEdges[i] = new Edge(sourceVertex, destinationVertex, edgeWeight);
            }

            int[] distances = new int[numberOfVertices];
            int[] parents = new int[numberOfVertices];

            for (int vertex = 0; vertex < numberOfVertices; vertex++) {
                distances[vertex] = Integer.MAX_VALUE;
                parents[vertex] = -1;
            }
            distances[0] = 0;

            for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distances[edge.source] != Integer.MAX_VALUE
                            && distances[edge.destination] > distances[edge.source] + edge.weight) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                        parents[edge.destination] = edge.source;
                    }
                }
            }

            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source vertex 0:");
                for (int vertex = 0; vertex < numberOfVertices; vertex++) {
                    System.out.println(vertex + " " + distances[vertex]);
                }
                System.out.println("Paths from source vertex 0:");
                for (int vertex = 0; vertex < numberOfVertices; vertex++) {
                    System.out.print("0 ");
                    printPath(parents, vertex);
                    System.out.println();
                }
            }
        }
    }

    public void shortestPath(int sourceVertex, int targetVertex, Edge[] edges) {
        int numberOfVertices = vertexCount;
        int numberOfEdges = edgeCount;
        boolean hasNegativeCycle = false;

        double[] distances = new double[numberOfVertices];
        int[] parents = new int[numberOfVertices];

        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            distances[vertex] = Integer.MAX_VALUE;
            parents[vertex] = -1;
        }
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                Edge edge = edges[edgeIndex];
                if ((int) distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            Edge edge = edges[edgeIndex];
            if ((int) distances[edge.source] != Integer.MAX_VALUE
                    && distances[edge.destination] > distances[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Shortest distance from " + sourceVertex + " to " + targetVertex + " is: " + distances[targetVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parents, targetVertex);
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