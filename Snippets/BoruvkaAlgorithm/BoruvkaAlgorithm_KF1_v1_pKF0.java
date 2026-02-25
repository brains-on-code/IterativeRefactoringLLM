package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class Class1 {

    private Class1() {
        // Utility class
    }

    /**
     * Represents an undirected weighted edge between two vertices.
     */
    static class Edge {
        final int source;
        final int destination;
        final int weight;

        Edge(final int source, final int destination, final int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Represents a graph with a fixed number of vertices and a list of edges.
     */
    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        Graph(final int vertexCount, final List<Edge> edges) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edges) {
                validateVertex(edge.source, vertexCount);
                validateVertex(edge.destination, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edges = edges;
        }
    }

    /**
     * Disjoint-set (Union-Find) node.
     */
    private static class DisjointSetNode {
        int parent;
        int rank;

        DisjointSetNode(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /**
     * Helper for Borůvka's MST algorithm.
     */
    private static class BoruvkaMST {
        List<Edge> mstEdges;
        DisjointSetNode[] disjointSet;
        final Graph graph;

        BoruvkaMST(final Graph graph) {
            this.mstEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void addCheapestEdges(final Edge[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                if (cheapestEdges[vertex] != null) {
                    final int set1 = findSet(disjointSet, cheapestEdges[vertex].source);
                    final int set2 = findSet(disjointSet, cheapestEdges[vertex].destination);

                    if (set1 != set2) {
                        mstEdges.add(cheapestEdges[vertex]);
                        union(disjointSet, set1, set2);
                    }
                }
            }
        }

        boolean isMSTComplete() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        private Edge[] findCheapestEdgesForComponents() {
            Edge[] cheapestEdges = new Edge[graph.vertexCount];

            for (final var edge : graph.edges) {
                final int set1 = findSet(disjointSet, edge.source);
                final int set2 = findSet(disjointSet, edge.destination);

                if (set1 != set2) {
                    if (cheapestEdges[set1] == null || edge.weight < cheapestEdges[set1].weight) {
                        cheapestEdges[set1] = edge;
                    }
                    if (cheapestEdges[set2] == null || edge.weight < cheapestEdges[set2].weight) {
                        cheapestEdges[set2] = edge;
                    }
                }
            }
            return cheapestEdges;
        }

        private static DisjointSetNode[] initializeDisjointSet(final Graph graph) {
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSet[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSet;
        }
    }

    static int findSet(final DisjointSetNode[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent = findSet(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    static void union(DisjointSetNode[] disjointSet, final int vertex1, final int vertex2) {
        final int root1 = findSet(disjointSet, vertex1);
        final int root2 = findSet(disjointSet, vertex2);

        if (disjointSet[root1].rank < disjointSet[root2].rank) {
            disjointSet[root1].parent = root2;
        } else if (disjointSet[root1].rank > disjointSet[root2].rank) {
            disjointSet[root2].parent = root1;
        } else {
            disjointSet[root2].parent = root1;
            disjointSet[root1].rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        var boruvka = new BoruvkaMST(graph);

        while (boruvka.isMSTComplete()) {
            final var cheapestEdges = boruvka.findCheapestEdgesForComponents();
            boruvka.addCheapestEdges(cheapestEdges);
        }
        return boruvka.mstEdges;
    }

    private static void validateVertex(final int vertex, final int vertexCount) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}