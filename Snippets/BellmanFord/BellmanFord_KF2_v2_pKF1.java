package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    private int numberOfVertices;
    private int numberOfEdges;
    private Edge[] edges;
    private int nextEdgeIndex = 0;

    BellmanFord(int numberOfVertices, int numberOfEdges) {
        this.numberOfVertices = numberOfVertices;
        this.numberOfEdges = numberOfEdges;
        this.edges = new Edge[numberOfEdges];
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

    void printPath(int[] parentVertices, int currentVertex) {
        if (parentVertices[currentVertex] == -1) {
            return;
        }
        printPath(parentVertices, parentVertices[currentVertex]);
        System.out.print(currentVertex + " ");
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

            System.out.println("Input edges (source destination weight):");
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                int sourceVertex = scanner.nextInt();
                int destinationVertex = scanner.nextInt();
                int weight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(sourceVertex, destinationVertex, weight);
            }

            int[] distances = new int[vertexCount];
            int[] parentVertices = new int[vertexCount];

            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                distances[vertexIndex] = Integer.MAX_VALUE;
            }
            distances[0] = 0;
            parentVertices[0] = -1;

            for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distances[edge.sourceVertex] != Integer.MAX_VALUE
                            && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                        distances[edge.destinationVertex] = distances[edge.sourceVertex] + edge.weight;
                        parentVertices[edge.destinationVertex] = edge.sourceVertex;
                    }
                }
            }

            boolean hasNegativeCycle = false;
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distances[edge.sourceVertex] != Integer.MAX_VALUE
                        && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source (0):");
                for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                    System.out.println(vertexIndex + " " + distances[vertexIndex]);
                }
                System.out.println("Paths from source (0):");
                for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                    System.out.print("0 ");
                    printPath(parentVertices, vertexIndex);
                    System.out.println();
                }
            }
        }
    }

    public void show(int sourceVertex, int destinationVertex, Edge[] edgeArray) {
        int vertexCount = numberOfVertices;
        int edgeCount = numberOfEdges;
        boolean hasNegativeCycle = false;

        double[] distances = new double[vertexCount];
        int[] parentVertices = new int[vertexCount];

        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            distances[vertexIndex] = Integer.MAX_VALUE;
        }
        distances[sourceVertex] = 0;
        parentVertices[sourceVertex] = -1;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                Edge edge = edgeArray[edgeIndex];
                if ((int) distances[edge.sourceVertex] != Integer.MAX_VALUE
                        && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                    distances[edge.destinationVertex] = distances[edge.sourceVertex] + edge.weight;
                    parentVertices[edge.destinationVertex] = edge.sourceVertex;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            Edge edge = edgeArray[edgeIndex];
            if ((int) distances[edge.sourceVertex] != Integer.MAX_VALUE
                    && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
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
            printPath(parentVertices, destinationVertex);
            System.out.println();
        }
    }

    public void addEdge(int sourceVertex, int destinationVertex, int weight) {
        edges[nextEdgeIndex++] = new Edge(sourceVertex, destinationVertex, weight);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}