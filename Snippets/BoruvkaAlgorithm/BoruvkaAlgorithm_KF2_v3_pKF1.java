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
        int parent;
        int rank;

        DisjointSetNode(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    private static class BoruvkaComputationState {
        List<Edge> minimumSpanningTreeEdges;
        DisjointSetNode[] disjointSetNodes;
        final Graph graph;

        BoruvkaComputationState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSetNodes = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void mergeComponents(final Edge[] cheapestEdgeForComponent) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                final Edge cheapestEdge = cheapestEdgeForComponent[vertex];
                if (cheapestEdge != null) {
                    final int sourceComponent =
                            findSetRepresentative(disjointSetNodes, cheapestEdge.sourceVertex);
                    final int destinationComponent =
                            findSetRepresentative(disjointSetNodes, cheapestEdge.destinationVertex);

                    if (sourceComponent != destinationComponent) {
                        minimumSpanningTreeEdges.add(cheapestEdge);
                        unionSets(disjointSetNodes, sourceComponent, destinationComponent);
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
                        findSetRepresentative(disjointSetNodes, edge.sourceVertex);
                final int destinationComponent =
                        findSetRepresentative(disjointSetNodes, edge.destinationVertex);

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
            DisjointSetNode[] disjointSetNodes = new DisjointSetNode[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSetNodes[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSetNodes;
        }
    }

    static int findSetRepresentative(final DisjointSetNode[] disjointSetNodes, final int vertex) {
        if (disjointSetNodes[vertex].parent != vertex) {
            disjointSetNodes[vertex].parent =
                    findSetRepresentative(disjointSetNodes, disjointSetNodes[vertex].parent);
        }
        return disjointSetNodes[vertex].parent;
    }

    static void unionSets(
            DisjointSetNode[] disjointSetNodes, final int firstVertex, final int secondVertex) {
        final int firstRoot = findSetRepresentative(disjointSetNodes, firstVertex);
        final int secondRoot = findSetRepresentative(disjointSetNodes, secondVertex);

        if (disjointSetNodes[firstRoot].rank < disjointSetNodes[secondRoot].rank) {
            disjointSetNodes[firstRoot].parent = secondRoot;
        } else if (disjointSetNodes[firstRoot].rank > disjointSetNodes[secondRoot].rank) {
            disjointSetNodes[secondRoot].parent = firstRoot;
        } else {
            disjointSetNodes[secondRoot].parent = firstRoot;
            disjointSetNodes[firstRoot].rank++;
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