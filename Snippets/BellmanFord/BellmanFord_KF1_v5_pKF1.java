package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFordGraph {

    int vertexCount;
    int edgeCount;
    private Edge[] edges;
    private int edgeInsertionIndex = 0;

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
            int source;
            int destination;
            int weight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter number of vertices and edges:");
            numberOfVertices = scanner.nextInt();
            numberOfEdges = scanner.nextInt();

            Edge[] inputEdges = new Edge[numberOfEdges];
            System.out.println("Input edges (source destination weight):");
            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                source = scanner.nextInt();
                destination = scanner.nextInt();
                weight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(source, destination, weight);
            }

            int[] distances = new int[numberOfVertices];
            int[] parents = new int[numberOfVertices];

            for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
                distances[vertexIndex] = Integer.MAX_VALUE;
                parents[vertexIndex] = -1;
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
                for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
                    System.out.println(vertexIndex + " " + distances[vertexIndex]);
                }
                System.out.println("Paths from source vertex 0:");
                for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
                    System.out.print("0 ");
                    printPath(parents, vertexIndex);
                    System.out.println();
                }
            }
        }
    }

    public void shortestPath(int sourceVertex, int targetVertex, Edge[] graphEdges) {
        int numberOfVertices = vertexCount;
        int numberOfEdges = edgeCount;
        boolean hasNegativeCycle = false;

        double[] distances = new double[numberOfVertices];
        int[] parents = new int[numberOfVertices];

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            distances[vertexIndex] = Integer.MAX_VALUE;
            parents[vertexIndex] = -1;
        }
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                Edge edge = graphEdges[edgeIndex];
                if ((int) distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.destination] > distances[edge.source] + edge.weight) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            Edge edge = graphEdges[edgeIndex];
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

    public void addEdge(int source, int destination, int weight) {
        edges[edgeInsertionIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdges() {
        return edges;
    }
}