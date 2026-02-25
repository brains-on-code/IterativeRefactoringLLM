package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Scanner;

class BellmanFord {

    private final int vertexCount;
    private final int edgeCount;
    private final Edge[] edges;
    private int edgeIndex = 0;

    BellmanFord(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.edges = new Edge[edgeCount];
    }

    private static class Edge {
        final int source;
        final int destination;
        final int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private void printPath(int[] parent, int vertex) {
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
            System.out.println("Enter number of vertices and edges:");
            int vertexCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            Edge[] inputEdges = readEdges(scanner, edgeCount);

            int sourceVertex = 0;
            int[] distances = initializeDistances(vertexCount, sourceVertex);
            int[] parents = initializeParents(vertexCount);

            runBellmanFord(vertexCount, edgeCount, inputEdges, distances, parents);

            if (containsNegativeCycle(edgeCount, inputEdges, distances)) {
                System.out.println("Graph contains a negative weight cycle.");
                return;
            }

            printAllDistances(sourceVertex, distances);
            printAllPaths(sourceVertex, parents);
        }
    }

    private Edge[] readEdges(Scanner scanner, int edgeCount) {
        Edge[] inputEdges = new Edge[edgeCount];
        System.out.println("Enter edges as: <source> <destination> <weight>");
        for (int i = 0; i < edgeCount; i++) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            inputEdges[i] = new Edge(source, destination, weight);
        }
        return inputEdges;
    }

    private int[] initializeDistances(int vertexCount, int sourceVertex) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[sourceVertex] = 0;
        return distances;
    }

    private int[] initializeParents(int vertexCount) {
        int[] parents = new int[vertexCount];
        Arrays.fill(parents, -1);
        return parents;
    }

    private void runBellmanFord(int vertexCount, int edgeCount, Edge[] edges, int[] distances, int[] parents) {
        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = 0; j < edgeCount; j++) {
                Edge edge = edges[j];
                if (distances[edge.source] == Integer.MAX_VALUE) {
                    continue;
                }
                int newDistance = distances[edge.source] + edge.weight;
                if (distances[edge.destination] > newDistance) {
                    distances[edge.destination] = newDistance;
                    parents[edge.destination] = edge.source;
                }
            }
        }
    }

    private boolean containsNegativeCycle(int edgeCount, Edge[] edges, int[] distances) {
        for (int j = 0; j < edgeCount; j++) {
            Edge edge = edges[j];
            if (distances[edge.source] == Integer.MAX_VALUE) {
                continue;
            }
            int newDistance = distances[edge.source] + edge.weight;
            if (distances[edge.destination] > newDistance) {
                return true;
            }
        }
        return false;
    }

    private void printAllDistances(int sourceVertex, int[] distances) {
        System.out.println("Shortest distances from source vertex " + sourceVertex + ":");
        for (int i = 0; i < distances.length; i++) {
            System.out.println(i + " -> " + distances[i]);
        }
    }

    private void printAllPaths(int sourceVertex, int[] parents) {
        System.out.println("Paths from source vertex " + sourceVertex + ":");
        for (int i = 0; i < parents.length; i++) {
            System.out.print(sourceVertex + " ");
            printPath(parents, i);
            System.out.println();
        }
    }

    public void show(int source, int destination, Edge[] graphEdges) {
        double[] distances = new double[vertexCount];
        int[] parents = new int[vertexCount];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);

        distances[source] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = 0; j < edgeCount; j++) {
                Edge edge = graphEdges[j];
                if ((int) distances[edge.source] == Integer.MAX_VALUE) {
                    continue;
                }
                double newDistance = distances[edge.source] + edge.weight;
                if (distances[edge.destination] > newDistance) {
                    distances[edge.destination] = newDistance;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        if (hasNegativeCycle(graphEdges, distances)) {
            System.out.println("Graph contains a negative weight cycle.");
            return;
        }

        System.out.println("Shortest distance from " + source + " to " + destination + " is: " + distances[destination]);
        System.out.println("Path followed:");
        System.out.print(source + " ");
        printPath(parents, destination);
        System.out.println();
    }

    private boolean hasNegativeCycle(Edge[] graphEdges, double[] distances) {
        for (int j = 0; j < edgeCount; j++) {
            Edge edge = graphEdges[j];
            if ((int) distances[edge.source] == Integer.MAX_VALUE) {
                continue;
            }
            double newDistance = distances[edge.source] + edge.weight;
            if (distances[edge.destination] > newDistance) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(int source, int destination, int weight) {
        edges[edgeIndex++] = new Edge(source, destination, weight);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}