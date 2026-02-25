package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaAlgorithm {

    private BoruvkaAlgorithm() {}

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

    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        Graph(final int vertexCount, final List<Edge> edges) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be non-negative");
            }
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edges) {
                validateVertex(edge.src, vertexCount);
                validateVertex(edge.dest, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edges = edges;
        }
    }

    private static class Component {
        int parent;
        int rank;

        Component(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    private static class BoruvkaState {
        final Graph graph;
        final Component[] components;
        final List<Edge> mstEdges;

        BoruvkaState(final Graph graph) {
            this.graph = graph;
            this.components = initializeComponents(graph.vertexCount);
            this.mstEdges = new ArrayList<>();
        }

        boolean hasMoreEdgesToAdd() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        Edge[] computeCheapestEdges() {
            Edge[] cheapest = new Edge[graph.vertexCount];

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

        void merge(final Edge[] cheapest) {
            for (int i = 0; i < graph.vertexCount; ++i) {
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

        private static Component[] initializeComponents(final int vertexCount) {
            Component[] components = new Component[vertexCount];
            for (int v = 0; v < vertexCount; ++v) {
                components[v] = new Component(v, 0);
            }
            return components;
        }
    }

    static int find(final Component[] components, final int i) {
        if (components[i].parent != i) {
            components[i].parent = find(components, components[i].parent);
        }
        return components[i].parent;
    }

    static void union(final Component[] components, final int x, final int y) {
        final int xRoot = find(components, x);
        final int yRoot = find(components, y);

        if (xRoot == yRoot) {
            return;
        }

        if (components[xRoot].rank < components[yRoot].rank) {
            components[xRoot].parent = yRoot;
        } else if (components[xRoot].rank > components[yRoot].rank) {
            components[yRoot].parent = xRoot;
        } else {
            components[yRoot].parent = xRoot;
            components[xRoot].rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        BoruvkaState state = new BoruvkaState(graph);

        while (state.hasMoreEdgesToAdd()) {
            Edge[] cheapest = state.computeCheapestEdges();
            state.merge(cheapest);
        }

        return state.mstEdges;
    }

    private static void validateVertex(final int vertex, final int upperBound) {
        if (vertex < 0 || vertex >= upperBound) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}