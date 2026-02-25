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

    private static class DisjointSetNode {
        int parent;
        int rank;

        DisjointSetNode(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    private static class BoruvkaComputationState {
        List<Edge> minimumSpanningTreeEdges;
        DisjointSetNode[] disjointSet;
        final Graph graph;

        BoruvkaComputationState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void mergeComponents(final Edge[] cheapestEdgeForComponent) {
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                final Edge cheapestEdge = cheapestEdgeForComponent[vertex];
                if (cheapestEdge != null) {
                    final int sourceComponent =
                            findSetRepresentative(disjointSet, cheapestEdge.source);
                    final int destinationComponent =
                            findSetRepresentative(disjointSet, cheapestEdge.destination);

                    if (sourceComponent != destinationComponent) {
                        minimumSpanningTreeEdges.add(cheapestEdge);
                        unionSets(disjointSet, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean needsMoreEdges() {
            return minimumSpanningTreeEdges.size() < graph.numberOfVertices - 1;
        }

        private Edge[] computeCheapestEdgesForComponents() {
            Edge[] cheapestEdgeForComponent = new Edge[graph.numberOfVertices];
            for (final var edge : graph.edges) {
                final int sourceComponent =
                        findSetRepresentative(disjointSet, edge.source);
                final int destinationComponent =
                        findSetRepresentative(disjointSet, edge.destination);

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

        private static DisjointSetNode[] initializeDisjointSet(final Graph graph) {
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.numberOfVertices];
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                disjointSet[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSet;
        }
    }

    static int findSetRepresentative(final DisjointSetNode[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent =
                    findSetRepresentative(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    static void unionSets(
            DisjointSetNode[] disjointSet, final int firstVertex, final int secondVertex) {
        final int firstRoot = findSetRepresentative(disjointSet, firstVertex);
        final int secondRoot = findSetRepresentative(disjointSet, secondVertex);

        if (disjointSet[firstRoot].rank < disjointSet[secondRoot].rank) {
            disjointSet[firstRoot].parent = secondRoot;
        } else if (disjointSet[firstRoot].rank > disjointSet[secondRoot].rank) {
            disjointSet[secondRoot].parent = firstRoot;
        } else {
            disjointSet[secondRoot].parent = firstRoot;
            disjointSet[firstRoot].rank++;
        }
    }

    static List<Edge> boruvkaMST(final Graph graph) {
        var computationState = new BoruvkaComputationState(graph);

        while (computationState.needsMoreEdges()) {
            final var cheapestEdgeForComponent =
                    computationState.computeCheapestEdgesForComponents();
            computationState.mergeComponents(cheapestEdgeForComponent);
        }
        return computationState.minimumSpanningTreeEdges;
    }

    private static void validateVertexIndex(final int vertex, final int vertexUpperBoundExclusive) {
        if (vertex < 0 || vertex >= vertexUpperBoundExclusive) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}