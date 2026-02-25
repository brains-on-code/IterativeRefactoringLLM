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

                updateCheapestEdge(cheapestEdges, set1, edge);
                updateCheapestEdge(cheapestEdges, set2, edge);
            }

            return cheapestEdges;
        }

        private static void updateCheapestEdge(
            final Edge[] cheapestEdges,
            final int setIndex,
            final Edge candidateEdge
        ) {
            final Edge currentCheapest = cheapestEdges[setIndex];
            if (currentCheapest == null || candidateEdge.weight < currentCheapest.weight) {
                cheapestEdges[setIndex] = candidateEdge;
            }
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
        final Component component = components[index];
        if (component.parent != index) {
            component.parent = find(components, component.parent);
        }
        return component.parent;
    }

    static void union(final Component[] components, final int firstIndex, final int secondIndex) {
        final int firstRoot = find(components, firstIndex);
        final int secondRoot = find(components, secondIndex);

        if (firstRoot == secondRoot) {
            return;
        }

        final Component firstComponent = components[firstRoot];
        final Component secondComponent = components[secondRoot];

        if (firstComponent.rank < secondComponent.rank) {
            firstComponent.parent = secondRoot;
        } else if (firstComponent.rank > secondComponent.rank) {
            secondComponent.parent = firstRoot;
        } else {
            secondComponent.parent = firstRoot;
            firstComponent.rank++;
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