package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Borůvka's algorithm for computing a Minimum Spanning Tree (MST)
 * of a connected, undirected, weighted graph.
 */
final class BoruvkaMST {

    private BoruvkaMST() {
        // Prevent instantiation
    }

    /** Undirected, weighted edge between two vertices. */
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

    /** Graph represented as an edge list. */
    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        /**
         * @param vertexCount number of vertices in the graph (must be non‑negative)
         * @param edges       list of edges (must not be null or empty)
         */
        Graph(final int vertexCount, final List<Edge> edges) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be non‑negative");
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

    /** Disjoint-set (Union-Find) node. */
    private static class DisjointSetNode {
        int parent;
        int rank;

        DisjointSetNode(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /** Helper for Borůvka's algorithm. */
    private static class BoruvkaHelper {
        final Graph graph;
        final DisjointSetNode[] disjointSet;
        final List<Edge> mstEdges;

        BoruvkaHelper(final Graph graph) {
            this.graph = graph;
            this.disjointSet = initializeDisjointSet(graph.vertexCount);
            this.mstEdges = new ArrayList<>();
        }

        /**
         * Adds the cheapest outgoing edge of each component to the MST
         * if it connects two different components.
         */
        void addCheapestEdgesToMst(final Edge[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                final Edge edge = cheapestEdges[vertex];
                if (edge == null) {
                    continue;
                }

                final int set1 = findSet(disjointSet, edge.source);
                final int set2 = findSet(disjointSet, edge.destination);

                if (set1 != set2) {
                    mstEdges.add(edge);
                    unionSets(disjointSet, set1, set2);
                }
            }
        }

        /** Returns true while the MST still needs more edges. */
        boolean isMstIncomplete() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        /** For each component, finds its cheapest outgoing edge. */
        Edge[] findCheapestEdgesPerComponent() {
            Edge[] cheapestEdges = new Edge[graph.vertexCount];

            for (final var edge : graph.edges) {
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
            DisjointSetNode[] disjointSet = new DisjointSetNode[vertexCount];
            for (int vertex = 0; vertex < vertexCount; ++vertex) {
                disjointSet[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSet;
        }
    }

    /**
     * Finds the representative (root) of the set that contains the given vertex
     * using path compression.
     */
    static int findSet(final DisjointSetNode[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent = findSet(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    /** Unites the sets containing the two given vertices using union by rank. */
    static void unionSets(DisjointSetNode[] disjointSet, final int vertex1, final int vertex2) {
        final int root1 = findSet(disjointSet, vertex1);
        final int root2 = findSet(disjointSet, vertex2);

        if (root1 == root2) {
            return;
        }

        if (disjointSet[root1].rank < disjointSet[root2].rank) {
            disjointSet[root1].parent = root2;
        } else if (disjointSet[root1].rank > disjointSet[root2].rank) {
            disjointSet[root2].parent = root1;
        } else {
            disjointSet[root2].parent = root1;
            disjointSet[root1].rank++;
        }
    }

    /**
     * Computes the Minimum Spanning Tree (MST) of the given graph
     * using Borůvka's algorithm.
     *
     * @param graph the input graph
     * @return list of edges that form the MST
     */
    static List<Edge> computeMst(final Graph graph) {
        BoruvkaHelper boruvka = new BoruvkaHelper(graph);

        while (boruvka.isMstIncomplete()) {
            Edge[] cheapestEdges = boruvka.findCheapestEdgesPerComponent();
            boruvka.addCheapestEdgesToMst(cheapestEdges);
        }

        return boruvka.mstEdges;
    }

    /** Validates that a vertex index is within [0, vertexCount). */
    private static void validateVertex(final int vertex, final int vertexCount) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}