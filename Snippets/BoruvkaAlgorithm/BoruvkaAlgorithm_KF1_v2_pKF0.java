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
            validateVertexCount(vertexCount);
            validateEdges(edges, vertexCount);

            this.vertexCount = vertexCount;
            this.edges = edges;
        }

        private static void validateVertexCount(final int vertexCount) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
        }

        private static void validateEdges(final List<Edge> edges, final int vertexCount) {
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final Edge edge : edges) {
                validateVertex(edge.source, vertexCount);
                validateVertex(edge.destination, vertexCount);
            }
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
        final Graph graph;
        final DisjointSetNode[] disjointSet;
        final List<Edge> mstEdges;

        BoruvkaMST(final Graph graph) {
            this.graph = graph;
            this.disjointSet = initializeDisjointSet(graph.vertexCount);
            this.mstEdges = new ArrayList<>();
        }

        void addCheapestEdges(final Edge[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; vertex++) {
                final Edge edge = cheapestEdges[vertex];
                if (edge == null) {
                    continue;
                }

                final int set1 = findSet(disjointSet, edge.source);
                final int set2 = findSet(disjointSet, edge.destination);

                if (set1 != set2) {
                    mstEdges.add(edge);
                    union(disjointSet, set1, set2);
                }
            }
        }

        boolean isMSTIncomplete() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        Edge[] findCheapestEdgesForComponents() {
            final Edge[] cheapestEdges = new Edge[graph.vertexCount];

            for (final Edge edge : graph.edges) {
                final int set1 = findSet(disjointSet, edge.source);
                final int set2 = findSet(disjointSet, edge.destination);

                if (set1 == set2) {
                    continue;
                }

                if (cheapestEdges[set1] == null || edge.weight < cheapestEdges[set1].weight) {
                    cheapestEdges[set1] = edge;
                }
                if (cheapestEdges[set2] == null || edge.weight < cheapestEdges[set2].weight) {
                    cheapestEdges[set2] = edge;
                }
            }
            return cheapestEdges;
        }

        private static DisjointSetNode[] initializeDisjointSet(final int vertexCount) {
            final DisjointSetNode[] disjointSet = new DisjointSetNode[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
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

    static void union(final DisjointSetNode[] disjointSet, final int vertex1, final int vertex2) {
        final int root1 = findSet(disjointSet, vertex1);
        final int root2 = findSet(disjointSet, vertex2);

        if (root1 == root2) {
            return;
        }

        final DisjointSetNode node1 = disjointSet[root1];
        final DisjointSetNode node2 = disjointSet[root2];

        if (node1.rank < node2.rank) {
            node1.parent = root2;
        } else if (node1.rank > node2.rank) {
            node2.parent = root1;
        } else {
            node2.parent = root1;
            node1.rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        final BoruvkaMST boruvka = new BoruvkaMST(graph);

        while (boruvka.isMSTIncomplete()) {
            final Edge[] cheapestEdges = boruvka.findCheapestEdgesForComponents();
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