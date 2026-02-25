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
        int weight;

        /**
         * @param sourceVertex      Source Vertex
         * @param destinationVertex End vertex
         * @param weight            Weight
         */
        Edge(int sourceVertex, int destinationVertex, int weight) {
            this.sourceVertex = sourceVertex;
            this.destinationVertex = destinationVertex;
            this.weight = weight;
        }
    }

    /**
     * @param parent Parent array which shows updates in edges
     * @param vertex Current vertex under consideration
     */
    void printPath(int[] parent, int vertex) {
        if (parent[vertex] == -1) { // Found the path back to parent
            return;
        }
        printPath(parent, parent[vertex]);
        System.out.print(vertex + " ");
    }

    public static void main(String[] args) {
        BellmanFord bellmanFord = new BellmanFord(0, 0); // Dummy object to call nonstatic variables
        bellmanFord.runInteractive();
    }

    public void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            int vertexCount;
            int edgeCount;
            int sourceVertex;
            int destinationVertex;
            int weight;
            boolean hasNegativeCycle = false;

            System.out.println("Enter no. of vertices and edges please");
            vertexCount = scanner.nextInt();
            edgeCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgeCount]; // Array of edges
            System.out.println("Input edges");
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                weight = scanner.nextInt();
                inputEdges[edgeIndex] = new Edge(sourceVertex, destinationVertex, weight);
            }

            int[] distanceToVertex = new int[vertexCount]; // Distance array for holding the finalized shortest path distance
                                                           // between source and all vertices
            int[] parentVertex = new int[vertexCount]; // Parent array for holding the paths

            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                distanceToVertex[vertexIndex] = Integer.MAX_VALUE; // Initializing distance values
            }
            distanceToVertex[0] = 0;
            parentVertex[0] = -1;

            for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
                for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                    Edge edge = inputEdges[edgeIndex];
                    if (distanceToVertex[edge.sourceVertex] != Integer.MAX_VALUE
                            && distanceToVertex[edge.destinationVertex]
                                    > distanceToVertex[edge.sourceVertex] + edge.weight) {
                        distanceToVertex[edge.destinationVertex] =
                                distanceToVertex[edge.sourceVertex] + edge.weight; // Update
                        parentVertex[edge.destinationVertex] = edge.sourceVertex;
                    }
                }
            }

            // Final cycle for negative checking
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                Edge edge = inputEdges[edgeIndex];
                if (distanceToVertex[edge.sourceVertex] != Integer.MAX_VALUE
                        && distanceToVertex[edge.destinationVertex]
                                > distanceToVertex[edge.sourceVertex] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle");
                    break;
                }
            }

            if (!hasNegativeCycle) { // Go ahead and show results of computation
                System.out.println("Distances are: ");
                for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                    System.out.println(vertexIndex + " " + distanceToVertex[vertexIndex]);
                }
                System.out.println("Path followed:");
                for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                    System.out.print("0 ");
                    printPath(parentVertex, vertexIndex);
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
        int vertexCount = this.numberOfVertices;
        int edgeCount = this.numberOfEdges;
        boolean hasNegativeCycle = false;

        double[] distanceToVertex = new double[vertexCount]; // Distance array for holding the finalized shortest path
                                                             // distance between source and all vertices
        int[] parentVertex = new int[vertexCount]; // Parent array for holding the paths

        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            distanceToVertex[vertexIndex] = Integer.MAX_VALUE; // Initializing distance values
        }
        distanceToVertex[source] = 0;
        parentVertex[source] = -1;

        for (int iteration = 0; iteration < vertexCount - 1; iteration++) {
            for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
                Edge edge = edges[edgeIndex];
                if ((int) distanceToVertex[edge.sourceVertex] != Integer.MAX_VALUE
                        && distanceToVertex[edge.destinationVertex]
                                > distanceToVertex[edge.sourceVertex] + edge.weight) {
                    distanceToVertex[edge.destinationVertex] =
                            distanceToVertex[edge.sourceVertex] + edge.weight; // Update
                    parentVertex[edge.destinationVertex] = edge.sourceVertex;
                }
            }
        }

        // Final cycle for negative checking
        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            Edge edge = edges[edgeIndex];
            if ((int) distanceToVertex[edge.sourceVertex] != Integer.MAX_VALUE
                    && distanceToVertex[edge.destinationVertex]
                            > distanceToVertex[edge.sourceVertex] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle");
                break;
            }
        }

        if (!hasNegativeCycle) { // Go ahead and show results of computation
            System.out.println("Distance is: " + distanceToVertex[destination]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parentVertex, destination);
            System.out.println();
        }
    }

    /**
     * @param sourceVertex      Source Vertex
     * @param destinationVertex End vertex
     * @param weight            Weight
     */
    public void addEdge(int sourceVertex, int destinationVertex, int weight) { // Adds unidirectional edge
        edges[nextEdgeIndex++] = new Edge(sourceVertex, destinationVertex, weight);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}