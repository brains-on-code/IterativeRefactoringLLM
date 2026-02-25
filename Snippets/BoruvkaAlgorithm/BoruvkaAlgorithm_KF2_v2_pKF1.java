package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaAlgorithm {
    private BoruvkaAlgorithm() {}

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

    static class Graph {
        final int numberOfVertices;
        final List<Edge> edges;

        Graph(final int numberOfVertices, final List<Edge> edges) {
            if (numberOfVertices < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edges) {
                validateVertexIndex(edge.source, numberOfVertices);
                validateVertexIndex(edge.destination, numberOfVertices);
            }

            this.numberOfVertices = numberOfVertices;
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
        List<Edge> mstEdges;
        Component[] components;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.mstEdges = new ArrayList<>();
            this.components = initializeComponents(graph);
            this.graph = graph;
        }

        void merge(final Edge[] cheapestEdgeForComponent) {
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                final Edge cheapestEdge = cheapestEdgeForComponent[vertex];
                if (cheapestEdge != null) {
                    final int sourceComponent =
                            findComponentRepresentative(components, cheapestEdge.source);
                    final int destinationComponent =
                            findComponentRepresentative(components, cheapestEdge.destination);

                    if (sourceComponent != destinationComponent) {
                        mstEdges.add(cheapestEdge);
                        unionComponents(components, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean hasMoreEdgesToAdd() {
            return mstEdges.size() < graph.numberOfVertices - 1;
        }

        private Edge[] computeCheapestEdges() {
            Edge[] cheapestEdgeForComponent = new Edge[graph.numberOfVertices];
            for (final var edge : graph.edges) {
                final int sourceComponent = findComponentRepresentative(components, edge.source);
                final int destinationComponent = findComponentRepresentative(components, edge.destination);

                if (sourceComponent != destinationComponent) {
                    if (cheapestEdgeForComponent[sourceComponent] == null
                            || edge.weight < cheapestEdgeForComponent[sourceComponent].weight) {
                        cheapestEdgeForComponent[sourceComponent] = edge;
                    }
                    if (cheapestEdgeForComponent[destinationComponent] == null
                            || edge.weight < cheapestEdgeForComponent[destinationComponent].weight) {
                        cheapestEdgeForComponent[destinationComponent] = edge;
                    }
                }
            }
            return cheapestEdgeForComponent;
        }

        private static Component[] initializeComponents(final Graph graph) {
            Component[] components = new Component[graph.numberOfVertices];
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                components[vertex] = new Component(vertex, 0);
            }
            return components;
        }
    }

    static int findComponentRepresentative(final Component[] components, final int vertex) {
        if (components[vertex].parent != vertex) {
            components[vertex].parent =
                    findComponentRepresentative(components, components[vertex].parent);
        }
        return components[vertex].parent;
    }

    static void unionComponents(Component[] components, final int firstVertex, final int secondVertex) {
        final int firstRoot = findComponentRepresentative(components, firstVertex);
        final int secondRoot = findComponentRepresentative(components, secondVertex);

        if (components[firstRoot].rank < components[secondRoot].rank) {
            components[firstRoot].parent = secondRoot;
        } else if (components[firstRoot].rank > components[secondRoot].rank) {
            components[secondRoot].parent = firstRoot;
        } else {
            components[secondRoot].parent = firstRoot;
            components[firstRoot].rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        var state = new BoruvkaState(graph);

        while (state.hasMoreEdgesToAdd()) {
            final var cheapestEdgeForComponent = state.computeCheapestEdges();
            state.merge(cheapestEdgeForComponent);
        }
        return state.mstEdges;
    }

    private static void validateVertexIndex(final int vertex, final int vertexUpperBoundExclusive) {
        if (vertex < 0 || vertex >= vertexUpperBoundExclusive) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}