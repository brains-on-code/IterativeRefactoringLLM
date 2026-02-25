package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    int numberOfVertices;
    int numberOfEdges;
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
        int edgeWeight;

        /**
         * @param sourceVertex      Source vertex
         * @param destinationVertex Destination vertex
         * @param edgeWeight        Edge weight
         */
        Edge(int sourceVertex, int destinationVertex, int edgeWeight) {
            this.sourceVertex = sourceVertex;
            this.destinationVertex = destinationVertex;
            this.edgeWeight = edgeWeight;
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
            int sourceVertex;
            int destinationVertex;
            int edgeWeight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter no. of vertices and edges please");
            interactiveVertexCount = scanner.nextInt();
            interactiveEdgeCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[interactiveEdgeCount];
            System.out.println("Input edges");
            for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                edgeWeight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(sourceVertex, destinationVertex, edgeWeight);
            }

            int[] distance = new int[interactiveVertexCount];
            int[] parent = new int[interactiveVertexCount];

            for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                distance[vertexIndex] = Integer.MAX_VALUE;
            }
            distance[0] = 0;
            parent[0] = -1;

            for (int iteration = 0; iteration < interactiveVertexCount - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distance[edge.sourceVertex] != Integer.MAX_VALUE
                            && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.edgeWeight) {
                        distance[edge.destinationVertex] = distance[edge.sourceVertex] + edge.edgeWeight;
                        parent[edge.destinationVertex] = edge.sourceVertex;
                    }
                }
            }

            for (int edgeIndex = 0; edgeIndex < interactiveEdgeCount; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distance[edge.sourceVertex] != Integer.MAX_VALUE
                        && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.edgeWeight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle");
                    break;
                }
            }

            if (!hasNegativeCycle) {
                System.out.println("Distances are: ");
                for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                    System.out.println(vertexIndex + " " + distance[vertexIndex]);
                }
                System.out.println("Path followed:");
                for (int vertexIndex = 0; vertexIndex < interactiveVertexCount; vertexIndex++) {
                    System.out.print("0 ");
                    printPath(parent, vertexIndex);
                    System.out.println();
                }
            }
        }
    }

    /**
     * @param sourceVertex      Starting vertex
     * @param destinationVertex Ending vertex
     * @param edges             Array of edges
     */
    public void show(int sourceVertex, int destinationVertex, Edge[] edges) {
        int localVertexCount = this.numberOfVertices;
        int localEdgeCount = this.numberOfEdges;
        boolean hasNegativeCycle = false;

        double[] distance = new double[localVertexCount];
        int[] parent = new int[localVertexCount];

        for (int vertexIndex = 0; vertexIndex < localVertexCount; vertexIndex++) {
            distance[vertexIndex] = Integer.MAX_VALUE;
        }
        distance[sourceVertex] = 0;
        parent[sourceVertex] = -1;

        for (int iteration = 0; iteration < localVertexCount - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < localEdgeCount; edgeIndex++) {
                Edge edge = edges[edgeIndex];
                if ((int) distance[edge.sourceVertex] != Integer.MAX_VALUE
                        && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.edgeWeight) {
                    distance[edge.destinationVertex] = distance[edge.sourceVertex] + edge.edgeWeight;
                    parent[edge.destinationVertex] = edge.sourceVertex;
                }
            }
        }

        for (int edgeIndex = 0; edgeIndex < localEdgeCount; edgeIndex++) {
            Edge edge = edges[edgeIndex];
            if ((int) distance[edge.sourceVertex] != Integer.MAX_VALUE
                    && distance[edge.destinationVertex] > distance[edge.sourceVertex] + edge.edgeWeight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle");
                break;
            }
        }

        if (!hasNegativeCycle) {
            System.out.println("Distance is: " + distance[destinationVertex]);
            System.out.println("Path followed:");
            System.out.print(sourceVertex + " ");
            printPath(parent, destinationVertex);
            System.out.println();
        }
    }

    /**
     * @param sourceVertex      Source vertex
     * @param destinationVertex Destination vertex
     * @param edgeWeight        Edge weight
     */
    public void addEdge(int sourceVertex, int destinationVertex, int edgeWeight) {
        edges[nextEdgeIndex++] = new Edge(sourceVertex, destinationVertex, edgeWeight);
    }

    public Edge[] getEdges() {
        return edges;
    }
}