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

    private static class DisjointSetNode {
        int parentIndex;
        int rank;

        DisjointSetNode(final int parentIndex, final int rank) {
            this.parentIndex = parentIndex;
            this.rank = rank;
        }
    }

    private static class BoruvkaComputationState {
        final Graph graph;
        final List<Edge> minimumSpanningTreeEdges;
        final DisjointSetNode[] disjointSet;

        BoruvkaComputationState(final Graph graph) {
            this.graph = graph;
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
        }

        void mergeComponents(final Edge[] cheapestEdgeForComponent) {
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                final Edge cheapestEdge = cheapestEdgeForComponent[vertexIndex];
                if (cheapestEdge != null) {
                    final int sourceComponent =
                            findSetRepresentative(disjointSet, cheapestEdge.sourceVertex);
                    final int destinationComponent =
                            findSetRepresentative(disjointSet, cheapestEdge.destinationVertex);

                    if (sourceComponent != destinationComponent) {
                        minimumSpanningTreeEdges.add(cheapestEdge);
                        unionSets(disjointSet, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean needsMoreEdges() {
            return minimumSpanningTreeEdges.size() < graph.vertexCount - 1;
        }

        private Edge[] computeCheapestEdgesForComponents() {
            Edge[] cheapestEdgeForComponent = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponent =
                        findSetRepresentative(disjointSet, edge.sourceVertex);
                final int destinationComponent =
                        findSetRepresentative(disjointSet, edge.destinationVertex);

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
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.vertexCount];
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                disjointSet[vertexIndex] = new DisjointSetNode(vertexIndex, 0);
            }
            return disjointSet;
        }
    }

    static int findSetRepresentative(final DisjointSetNode[] disjointSet, final int vertexIndex) {
        if (disjointSet[vertexIndex].parentIndex != vertexIndex) {
            disjointSet[vertexIndex].parentIndex =
                    findSetRepresentative(disjointSet, disjointSet[vertexIndex].parentIndex);
        }
        return disjointSet[vertexIndex].parentIndex;
    }

    static void unionSets(
            DisjointSetNode[] disjointSet, final int firstVertexIndex, final int secondVertexIndex) {
        final int firstRootIndex = findSetRepresentative(disjointSet, firstVertexIndex);
        final int secondRootIndex = findSetRepresentative(disjointSet, secondVertexIndex);

        if (disjointSet[firstRootIndex].rank < disjointSet[secondRootIndex].rank) {
            disjointSet[firstRootIndex].parentIndex = secondRootIndex;
        } else if (disjointSet[firstRootIndex].rank > disjointSet[secondRootIndex].rank) {
            disjointSet[secondRootIndex].parentIndex = firstRootIndex;
        } else {
            disjointSet[secondRootIndex].parentIndex = firstRootIndex;
            disjointSet[firstRootIndex].rank++;
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

    private static void validateVertexIndex(final int vertexIndex, final int vertexUpperBoundExclusive) {
        if (vertexIndex < 0 || vertexIndex >= vertexUpperBoundExclusive) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}