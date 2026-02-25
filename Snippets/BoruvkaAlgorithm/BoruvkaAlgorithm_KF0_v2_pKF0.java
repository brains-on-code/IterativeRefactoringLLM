package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Boruvka's algorithm to find Minimum Spanning Tree
 * (https://en.wikipedia.org/wiki/Bor%C5%AFvka%27s_algorithm)
 *
 * @author itakurah (https://github.com/itakurah)
 */
final class BoruvkaAlgorithm {

    private BoruvkaAlgorithm() {
        // Utility class
    }

    /**
     * Represents an edge in the graph.
     */
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

    /**
     * Represents the graph.
     */
    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        /**
         * Constructor for the graph.
         *
         * @param vertexCount number of vertices
         * @param edges       list of edges
         */
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
                validateVertex(edge.src, vertexCount);
                validateVertex(edge.dest, vertexCount);
            }
        }
    }

    /**
     * Represents a subset for Union-Find operations.
     */
    private static class Component {
        int parent;
        int rank;

        Component(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /**
     * Represents the state of Union-Find components and the result list.
     */
    private static class BoruvkaState {
        final Graph graph;
        final Component[] components;
        final List<Edge> result;

        BoruvkaState(final Graph graph) {
            this.graph = graph;
            this.components = initializeComponents(graph.vertexCount);
            this.result = new ArrayList<>();
        }

        /**
         * Adds the cheapest edges to the result list and performs Union operation on the subsets.
         *
         * @param cheapest array containing the cheapest edge for each subset
         */
        void merge(final Edge[] cheapest) {
            for (int i = 0; i < graph.vertexCount; i++) {
                final Edge edge = cheapest[i];
                if (edge == null) {
                    continue;
                }

                final int component1 = find(components, edge.src);
                final int component2 = find(components, edge.dest);

                if (component1 == component2) {
                    continue;
                }

                result.add(edge);
                union(components, component1, component2);
            }
        }

        /**
         * Checks if there are more edges to add to the result list.
         *
         * @return true if there are more edges to add, false otherwise
         */
        boolean hasMoreEdgesToAdd() {
            return result.size() < graph.vertexCount - 1;
        }

        /**
         * Computes the cheapest edges for each subset in the Union-Find structure.
         *
         * @return an array containing the cheapest edge for each subset
         */
        Edge[] computeCheapestEdges() {
            final Edge[] cheapest = new Edge[graph.vertexCount];

            for (final Edge edge : graph.edges) {
                final int set1 = find(components, edge.src);
                final int set2 = find(components, edge.dest);

                if (set1 == set2) {
                    continue;
                }

                updateCheapestEdge(cheapest, set1, edge);
                updateCheapestEdge(cheapest, set2, edge);
            }

            return cheapest;
        }

        private static void updateCheapestEdge(final Edge[] cheapest, final int set, final Edge edge) {
            if (cheapest[set] == null || edge.weight < cheapest[set].weight) {
                cheapest[set] = edge;
            }
        }

        /**
         * Initializes subsets for Union-Find.
         *
         * @param vertexCount number of vertices in the graph
         * @return the initialized subsets
         */
        private static Component[] initializeComponents(final int vertexCount) {
            final Component[] components = new Component[vertexCount];
            for (int v = 0; v < vertexCount; v++) {
                components[v] = new Component(v, 0);
            }
            return components;
        }
    }

    /**
     * Finds the parent of the subset using path compression.
     *
     * @param components array of subsets
     * @param index      index of the subset
     * @return the parent of the subset
     */
    static int find(final Component[] components, final int index) {
        if (components[index].parent != index) {
            components[index].parent = find(components, components[index].parent);
        }
        return components[index].parent;
    }

    /**
     * Performs the Union operation for Union-Find.
     *
     * @param components array of subsets
     * @param x          index of the first subset
     * @param y          index of the second subset
     */
    static void union(final Component[] components, final int x, final int y) {
        final int xRoot = find(components, x);
        final int yRoot = find(components, y);

        if (xRoot == yRoot) {
            return;
        }

        final Component xComponent = components[xRoot];
        final Component yComponent = components[yRoot];

        if (xComponent.rank < yComponent.rank) {
            xComponent.parent = yRoot;
        } else if (xComponent.rank > yComponent.rank) {
            yComponent.parent = xRoot;
        } else {
            yComponent.parent = xRoot;
            xComponent.rank++;
        }
    }

    /**
     * Boruvka's algorithm to find the Minimum Spanning Tree.
     *
     * @param graph the graph
     * @return list of edges in the Minimum Spanning Tree
     */
    static List<Edge> boruvkaMST(final Graph graph) {
        final BoruvkaState state = new BoruvkaState(graph);

        while (state.hasMoreEdgesToAdd()) {
            final Edge[] cheapest = state.computeCheapestEdges();
            state.merge(cheapest);
        }

        return state.result;
    }

    /**
     * Checks if the edge vertices are in a valid range.
     *
     * @param vertex     the vertex to check
     * @param upperBound the upper bound for the vertex range
     */
    private static void validateVertex(final int vertex, final int upperBound) {
        if (vertex < 0 || vertex >= upperBound) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}