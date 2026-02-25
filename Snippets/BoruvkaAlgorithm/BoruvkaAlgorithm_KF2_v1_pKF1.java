package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaAlgorithm {
    private BoruvkaAlgorithm() {}

    static class Edge {
        final int sourceVertex;
        final int destinationVertex;
        final int weight;

        Edge(final int sourceVertex, final int destinationVertex, final int weight) {
            this.sourceVertex = sourceVertex;
            this.destinationVertex = destinationVertex;
            this.weight = weight;
        }
    }

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
                validateVertexIndex(edge.sourceVertex, vertexCount);
                validateVertexIndex(edge.destinationVertex, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edges = edges;
        }
    }

    private static class Component {
        int parentIndex;
        int rank;

        Component(final int parentIndex, final int rank) {
            this.parentIndex = parentIndex;
            this.rank = rank;
        }
    }

    private static class BoruvkaState {
        List<Edge> minimumSpanningTreeEdges;
        Component[] components;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.components = initializeComponents(graph);
            this.graph = graph;
        }

        void merge(final Edge[] cheapestEdgesPerComponent) {
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                if (cheapestEdgesPerComponent[vertexIndex] != null) {
                    final int componentOfSource =
                            findComponentRepresentative(components, cheapestEdgesPerComponent[vertexIndex].sourceVertex);
                    final int componentOfDestination =
                            findComponentRepresentative(components, cheapestEdgesPerComponent[vertexIndex].destinationVertex);

                    if (componentOfSource != componentOfDestination) {
                        minimumSpanningTreeEdges.add(cheapestEdgesPerComponent[vertexIndex]);
                        unionComponents(components, componentOfSource, componentOfDestination);
                    }
                }
            }
        }

        boolean hasMoreEdgesToAdd() {
            return minimumSpanningTreeEdges.size() < graph.vertexCount - 1;
        }

        private Edge[] computeCheapestEdges() {
            Edge[] cheapestEdgesPerComponent = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponent = findComponentRepresentative(components, edge.sourceVertex);
                final int destinationComponent = findComponentRepresentative(components, edge.destinationVertex);

                if (sourceComponent != destinationComponent) {
                    if (cheapestEdgesPerComponent[sourceComponent] == null
                            || edge.weight < cheapestEdgesPerComponent[sourceComponent].weight) {
                        cheapestEdgesPerComponent[sourceComponent] = edge;
                    }
                    if (cheapestEdgesPerComponent[destinationComponent] == null
                            || edge.weight < cheapestEdgesPerComponent[destinationComponent].weight) {
                        cheapestEdgesPerComponent[destinationComponent] = edge;
                    }
                }
            }
            return cheapestEdgesPerComponent;
        }

        private static Component[] initializeComponents(final Graph graph) {
            Component[] components = new Component[graph.vertexCount];
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                components[vertexIndex] = new Component(vertexIndex, 0);
            }
            return components;
        }
    }

    static int findComponentRepresentative(final Component[] components, final int vertexIndex) {
        if (components[vertexIndex].parentIndex != vertexIndex) {
            components[vertexIndex].parentIndex =
                    findComponentRepresentative(components, components[vertexIndex].parentIndex);
        }
        return components[vertexIndex].parentIndex;
    }

    static void unionComponents(Component[] components, final int firstVertexIndex, final int secondVertexIndex) {
        final int firstRootIndex = findComponentRepresentative(components, firstVertexIndex);
        final int secondRootIndex = findComponentRepresentative(components, secondVertexIndex);

        if (components[firstRootIndex].rank < components[secondRootIndex].rank) {
            components[firstRootIndex].parentIndex = secondRootIndex;
        } else if (components[firstRootIndex].rank > components[secondRootIndex].rank) {
            components[secondRootIndex].parentIndex = firstRootIndex;
        } else {
            components[secondRootIndex].parentIndex = firstRootIndex;
            components[firstRootIndex].rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        var boruvkaState = new BoruvkaState(graph);

        while (boruvkaState.hasMoreEdgesToAdd()) {
            final var cheapestEdgesPerComponent = boruvkaState.computeCheapestEdges();
            boruvkaState.merge(cheapestEdgesPerComponent);
        }
        return boruvkaState.minimumSpanningTreeEdges;
    }

    private static void validateVertexIndex(final int vertexIndex, final int vertexUpperBoundExclusive) {
        if (vertexIndex < 0 || vertexIndex >= vertexUpperBoundExclusive) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}