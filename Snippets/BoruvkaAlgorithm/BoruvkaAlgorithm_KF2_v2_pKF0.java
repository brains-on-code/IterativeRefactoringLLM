package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaAlgorithm {

    private BoruvkaAlgorithm() {
        // Utility class
    }

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

        private static void validateVertex(final int vertex, final int upperBound) {
            if (vertex < 0 || vertex >= upperBound) {
                throw new IllegalArgumentException("Edge vertex out of range");
            }
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
        final List<Edge> result;

        BoruvkaState(final Graph graph) {
            this.graph = graph;
            this.components = initializeComponents(graph.vertexCount);
            this.result = new ArrayList<>();
        }

        void merge(final Edge[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; vertex++) {
                final Edge edge = cheapestEdges[vertex];
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

        boolean hasMoreEdgesToAdd() {
            return result.size() < graph.vertexCount - 1;
        }

        Edge[] computeCheapestEdges() {
            final Edge[] cheapestEdges = new Edge[graph.vertexCount];

            for (final Edge edge : graph.edges) {
                final int set1 = find(components, edge.src);
                final int set2 = find(components, edge.dest);

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

        private static Component[] initializeComponents(final int vertexCount) {
            final Component[] components = new Component[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                components[vertex] = new Component(vertex, 0);
            }
            return components;
        }
    }

    static int find(final Component[] components, final int index) {
        if (components[index].parent != index) {
            components[index].parent = find(components, components[index].parent);
        }
        return components[index].parent;
    }

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

    static List<Edge> boruvkaMST(final Graph graph) {
        final BoruvkaState boruvkaState = new BoruvkaState(graph);

        while (boruvkaState.hasMoreEdgesToAdd()) {
            final Edge[] cheapestEdges = boruvkaState.computeCheapestEdges();
            boruvkaState.merge(cheapestEdges);
        }

        return boruvkaState.result;
    }
}