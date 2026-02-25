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

    void printPath(int[] parentVertices, int currentVertex) {
        if (parentVertices[currentVertex] == -1) {
            return;
        }
        printPath(parentVertices, parentVertices[currentVertex]);
        System.out.print(currentVertex + " ");
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
            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                edgeWeight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(sourceVertex, destinationVertex, edgeWeight);
            }

            int[] distances = new int[numberOfVertices];
            int[] parentVertices = new int[numberOfVertices];

            for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
                distances[vertexIndex] = Integer.MAX_VALUE;
                parentVertices[vertexIndex] = -1;
            }
            distances[0] = 0;

            for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distances[edge.sourceVertex] != Integer.MAX_VALUE
                            && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                        distances[edge.destinationVertex] = distances[edge.sourceVertex] + edge.weight;
                        parentVertices[edge.destinationVertex] = edge.sourceVertex;
                    }
                }
            }

            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distances[edge.sourceVertex] != Integer.MAX_VALUE
                        && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
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
                    printPath(parentVertices, vertexIndex);
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
        int[] parentVertices = new int[numberOfVertices];

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            distances[vertexIndex] = Integer.MAX_VALUE;
            parentVertices[vertexIndex] = -1;
        }
        distances[sourceVertex] = 0;

        for (int iteration = 0; iteration < numberOfVertices - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
                Edge edge = graphEdges[edgeIndex];
                if ((int) distances[edge.sourceVertex] != Integer.MAX_VALUE
                        && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                    distances[edge.destinationVertex] = distances[edge.sourceVertex] + edge.weight;
                    parentVertices[edge.destinationVertex] = edge.sourceVertex;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            Edge edge = graphEdges[edgeIndex];
            if ((int) distances[edge.sourceVertex] != Integer.MAX_VALUE
                    && distances[edge.destinationVertex] > distances[edge.sourceVertex] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Shortest distance from " + sourceVertex + " to " + targetVertex + " is: " + distances[targetVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parentVertices, targetVertex);
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