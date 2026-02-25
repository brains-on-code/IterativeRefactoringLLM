package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord {

    int vertexCount;
    int edgeCount;
    private Edge[] edges;
    private int edgeIndex = 0;

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
         * @param source      Source Vertex
         * @param destination End vertex
         * @param weight      Weight
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
        // shows distance to all vertices
        // Interactive run for understanding the
        // class first time. Assumes source vertex is 0 and
        try (Scanner scanner = new Scanner(System.in)) {
            int vertexCount;
            int edgeCount;
            int sourceVertex;
            int destinationVertex;
            int weight;
            int i;
            int j;
            boolean hasNegativeCycle = false;

            System.out.println("Enter no. of vertices and edges please");
            vertexCount = scanner.nextInt();
            edgeCount = scanner.nextInt();

            Edge[] inputEdges = new Edge[edgeCount]; // Array of edges
            System.out.println("Input edges");
            for (i = 0; i < edgeCount; i++) {
                sourceVertex = scanner.nextInt();
                destinationVertex = scanner.nextInt();
                weight = scanner.nextInt();
                inputEdges[i] = new Edge(sourceVertex, destinationVertex, weight);
            }

            int[] distance = new int[vertexCount]; // Distance array for holding the finalized shortest path distance
                                                   // between source and all vertices
            int[] parent = new int[vertexCount]; // Parent array for holding the paths

            for (i = 0; i < vertexCount; i++) {
                distance[i] = Integer.MAX_VALUE; // Initializing distance values
            }
            distance[0] = 0;
            parent[0] = -1;

            for (i = 0; i < vertexCount - 1; i++) {
                for (j = 0; j < edgeCount; j++) {
                    Edge edge = inputEdges[j];
                    if (distance[edge.source] != Integer.MAX_VALUE
                            && distance[edge.destination] > distance[edge.source] + edge.weight) {
                        distance[edge.destination] = distance[edge.source] + edge.weight; // Update
                        parent[edge.destination] = edge.source;
                    }
                }
            }

            // Final cycle for negative checking
            for (j = 0; j < edgeCount; j++) {
                Edge edge = inputEdges[j];
                if (distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    hasNegativeCycle = true;
                    System.out.println("Negative cycle");
                    break;
                }
            }

            if (!hasNegativeCycle) { // Go ahead and show results of computation
                System.out.println("Distances are: ");
                for (i = 0; i < vertexCount; i++) {
                    System.out.println(i + " " + distance[i]);
                }
                System.out.println("Path followed:");
                for (i = 0; i < vertexCount; i++) {
                    System.out.print("0 ");
                    printPath(parent, i);
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
        // Just shows results of computation, if graph is passed to it. The graph should
        // be created by using addEdge() method and passed by calling getEdgeArray()
        int vertexCount = this.vertexCount;
        int edgeCount = this.edgeCount;
        boolean hasNegativeCycle = false;

        double[] distance = new double[vertexCount]; // Distance array for holding the finalized shortest path
                                                     // distance between source and all vertices
        int[] parent = new int[vertexCount]; // Parent array for holding the paths

        for (int i = 0; i < vertexCount; i++) {
            distance[i] = Integer.MAX_VALUE; // Initializing distance values
        }
        distance[source] = 0;
        parent[source] = -1;

        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = 0; j < edgeCount; j++) {
                Edge edge = edges[j];
                if ((int) distance[edge.source] != Integer.MAX_VALUE
                        && distance[edge.destination] > distance[edge.source] + edge.weight) {
                    distance[edge.destination] = distance[edge.source] + edge.weight; // Update
                    parent[edge.destination] = edge.source;
                }
            }
        }

        // Final cycle for negative checking
        for (int j = 0; j < edgeCount; j++) {
            Edge edge = edges[j];
            if ((int) distance[edge.source] != Integer.MAX_VALUE
                    && distance[edge.destination] > distance[edge.source] + edge.weight) {
                hasNegativeCycle = true;
                System.out.println("Negative cycle");
                break;
            }
        }

        if (!hasNegativeCycle) { // Go ahead and show results of computation
            System.out.println("Distance is: " + distance[destination]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(parent, destination);
            System.out.println();
        }
    }

    /**
     * @param source      Source Vertex
     * @param destination End vertex
     * @param weight      Weight
     */
    public void addEdge(int source, int destination, int weight) { // Adds unidirectional edge
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}