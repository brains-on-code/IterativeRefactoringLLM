package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    int vertexCount;
    int edgeCount;
    private Edge[] edges;
    private int edgeInsertionIndex = 0;

    BellmanFord(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.edges = new Edge[edgeCount];
    }

    class Edge {

        int source;
        int destination;
        int weight;

        /**
         * @param source      Source vertex
         * @param destination Destination vertex
         * @param weight      Edge weight
         */
        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * @param parent Parent array which shows updates in edges
     * @param vertex Current vertex under consideration
     */
    void printPath(int[] parent, int vertex) {
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
            int interactiveVertexCount;
            int interactiveEdgeCount;
            int source;
            int destination;
            int weight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter number of vertices and edges:");
            interactiveVertexCount = scanner.nextInt();
            interactiveEdgeCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[interactiveEdgeCount];
            System.out.println("Input edges (source destination weight):");
            for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                source = scanner.nextInt();
                destination = scanner.nextInt();
                weight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(source, destination, weight);
            }

            int[] distance = new int[interactiveVertexCount];
            int[] parent = new int[interactiveVertexCount];

            for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                distance[vertexIndex] = Integer.MAX_VALUE;
                parent[vertexIndex] = -1;
            }
            distance[0] = 0;

            for (int iteration = 0; iteration < interactiveVertexCount - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distance[edge.source] != Integer.MAX_VALUE
                            && distance[edge.destination] > distance[edge.source] + edge.weight) {
                        distance[edge.destination] = distance[edge.source] + edge.weight;
                        parent[edge.destination] = edge.source;
                    }
                }
            }

            for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle detected");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances from source vertex 0:");
                for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                    System.out.println(vertexIndex + " " + distance[vertexIndex]);
                }
                System.out.println("Paths from source vertex 0:");
                for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                    System.out.print("0 ");
                    printPath(parent, vertexIndex);
                    System.out.println();
                }
            }
        }
    }

    /**
     * @param source      Starting vertex
     * @param destination Ending vertex
     * @param edges       Array of edges
     */
    public void show(int source, int destination, Edge[] edges) {
        int localVertexCount = this.vertexCount;
        int localEdgeCount = this.edgeCount;
        boolean hasNegativeCycle = false;

        double[] distance = new double[localVertexCount];
        int[] parent = new int[localVertexCount];

        for (int vertexIndex = 0; vertexIndex < localVertexCount; vertexIndex++) {
            distance[vertexIndex] = Integer.MAX_VALUE;
            parent[vertexIndex] = -1;
        }
        distance[source] = 0;

        for (int iteration = 0; iteration < localVertexCount - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < localEdgeCount; edgeIndex++) {
                Edge edge = edges[edgeIndex];
                if ((int) distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                    parent[edge.destination] = edge.source;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < localEdgeCount; edgeIndex++) {
            Edge edge = edges[edgeIndex];
            if ((int) distance[edge.source] != Integer.MAX_VALUE
                    && distance[edge.destination] > distance[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle detected");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Distance from " + source + " to " + destination + " is: " + distance[destination]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parent, destination);
            System.out.println();
        }
    }

    /**
     * @param source      Source vertex
     * @param destination Destination vertex
     * @param weight      Edge weight
     */
    public void addEdge(int source, int destination, int weight) {
        edges[edgeInsertionIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdges() {
        return edges;
    }
}