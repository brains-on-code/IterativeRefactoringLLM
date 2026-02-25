package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Borůvka's algorithm for computing a Minimum Spanning Tree (MST).
 * See: https://en.wikipedia.org/wiki/Bor%C5%AFvka%27s_algorithm
 */
final class BoruvkaAlgorithm {

    private BoruvkaAlgorithm() {
        // Prevent instantiation of utility class.
    }

    /** Undirected, weighted edge between two vertices. */
    static class Edge {
        final int src;
        final int dest;
        final int weight;

        Edge(final int src, final int dest, final int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    /** Graph represented by a vertex count and an edge list. */
    static class Graph {
        final int vertex;
        final List<Edge> edges;

        /**
         * @param vertex number of vertices (0..vertex-1)
         * @param edges  list of edges
         */
        Graph(final int vertex, final List<Edge> edges) {
            if (vertex < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edges) {
                validateVertex(edge.src, vertex);
                validateVertex(edge.dest, vertex);
            }

            this.vertex = vertex;
            this.edges = edges;
        }
    }

    /** Union–Find node (Disjoint Set Union). */
    private static class Component {
        int parent;
        int rank;

        Component(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /** Mutable state used while running Borůvka's algorithm. */
    private static class BoruvkaState {
        final Graph graph;
        final Component[] components;
        final List<Edge> mstEdges;

        BoruvkaState(final Graph graph) {
            this.graph = graph;
            this.components = initializeComponents(graph.vertex);
            this.mstEdges = new ArrayList<>();
        }

        /**
         * Add the current cheapest edges (one per component) to the MST,
         * merging components via union operations.
         */
        void mergeCheapestEdges(final Edge[] cheapest) {
            for (int i = 0; i < graph.vertex; ++i) {
                final Edge edge = cheapest[i];
                if (edge == null) {
                    continue;
                }

                final int component1 = find(components, edge.src);
                final int component2 = find(components, edge.dest);

                if (component1 != component2) {
                    mstEdges.add(edge);
                    union(components, component1, component2);
                }
            }
        }

        /** Returns true while the MST still needs more edges. */
        boolean needsMoreEdges() {
            return mstEdges.size() < graph.vertex - 1;
        }

        /**
         * For each component, find its cheapest outgoing edge.
         *
         * @return array cheapest[c] = cheapest edge leaving component c, or null if none
         */
        private Edge[] computeCheapestEdges() {
            Edge[] cheapest = new Edge[graph.vertex];

            for (final var edge : graph.edges) {
                final int set1 = find(components, edge.src);
                final int set2 = find(components, edge.dest);

                if (set1 == set2) {
                    continue;
                }

                if (cheapest[set1] == null || edge.weight < cheapest[set1].weight) {
                    cheapest[set1] = edge;
                }
                if (cheapest[set2] == null || edge.weight < cheapest[set2].weight) {
                    cheapest[set2] = edge;
                }
            }
            return cheapest;
        }

        /** Initialize each vertex as its own component. */
        private static Component[] initializeComponents(final int vertexCount) {
            Component[] components = new Component[vertexCount];
            for (int v = 0; v < vertexCount; ++v) {
                components[v] = new Component(v, 0);
            }
            return components;
        }
    }

    /**
     * Find representative of set containing i, with path compression.
     *
     * @param components union–find structure
     * @param i          vertex index
     * @return root representative of i
     */
    static int find(final Component[] components, final int i) {
        if (components[i].parent != i) {
            components[i].parent = find(components, components[i].parent);
        }
        return components[i].parent;
    }

    /**
     * Union two sets by rank.
     *
     * @param components union–find structure
     * @param x          representative of first set
     * @param y          representative of second set
     */
    static void union(Component[] components, final int x, final int y) {
        final int xroot = find(components, x);
        final int yroot = find(components, y);

        if (xroot == yroot) {
            return;
        }

        if (components[xroot].rank < components[yroot].rank) {
            components[xroot].parent = yroot;
        } else if (components[xroot].rank > components[yroot].rank) {
            components[yroot].parent = xroot;
        } else {
            components[yroot].parent = xroot;
            components[xroot].rank++;
        }
    }

    /**
     * Compute a Minimum Spanning Tree using Borůvka's algorithm.
     *
     * @param graph input graph
     * @return list of edges in the MST
     */
    static List<Edge> boruvkaMST(final Graph graph) {
        BoruvkaState state = new BoruvkaState(graph);

        while (state.needsMoreEdges()) {
            Edge[] cheapest = state.computeCheapestEdges();
            state.mergeCheapestEdges(cheapest);
        }
        return state.mstEdges;
    }

    /** Validate that a vertex index is within [0, upperBound). */
    private static void validateVertex(final int vertex, final int upperBound) {
        if (vertex < 0 || vertex >= upperBound) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}