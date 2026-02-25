package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaMinimumSpanningTree {
    private BoruvkaMinimumSpanningTree() {}

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
                validateVertex(edge.sourceVertex, vertexCount);
                validateVertex(edge.destinationVertex, vertexCount);
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

    private static class BoruvkaState {
        List<Edge> minimumSpanningTreeEdges;
        DisjointSetNode[] disjointSet;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void addCheapestEdges(final Edge[] cheapestEdgesByComponent) {
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                final Edge cheapestEdge = cheapestEdgesByComponent[vertexIndex];
                if (cheapestEdge != null) {
                    final int sourceComponent = findSet(disjointSet, cheapestEdge.sourceVertex);
                    final int destinationComponent = findSet(disjointSet, cheapestEdge.destinationVertex);

                    if (sourceComponent != destinationComponent) {
                        minimumSpanningTreeEdges.add(cheapestEdge);
                        unionSets(disjointSet, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean isMstIncomplete() {
            return minimumSpanningTreeEdges.size() < graph.vertexCount - 1;
        }

        private Edge[] findCheapestEdgesForComponents() {
            Edge[] cheapestEdgesByComponent = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponent = findSet(disjointSet, edge.sourceVertex);
                final int destinationComponent = findSet(disjointSet, edge.destinationVertex);

                if (sourceComponent != destinationComponent) {
                    if (cheapestEdgesByComponent[sourceComponent] == null
                            || edge.weight < cheapestEdgesByComponent[sourceComponent].weight) {
                        cheapestEdgesByComponent[sourceComponent] = edge;
                    }
                    if (cheapestEdgesByComponent[destinationComponent] == null
                            || edge.weight < cheapestEdgesByComponent[destinationComponent].weight) {
                        cheapestEdgesByComponent[destinationComponent] = edge;
                    }
                }
            }
            return cheapestEdgesByComponent;
        }

        private static DisjointSetNode[] initializeDisjointSet(final Graph graph) {
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.vertexCount];
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                disjointSet[vertexIndex] = new DisjointSetNode(vertexIndex, 0);
            }
            return disjointSet;
        }
    }

    static int findSet(final DisjointSetNode[] disjointSet, final int vertexIndex) {
        if (disjointSet[vertexIndex].parentIndex != vertexIndex) {
            disjointSet[vertexIndex].parentIndex =
                    findSet(disjointSet, disjointSet[vertexIndex].parentIndex);
        }
        return disjointSet[vertexIndex].parentIndex;
    }

    static void unionSets(DisjointSetNode[] disjointSet, final int firstVertexIndex, final int secondVertexIndex) {
        final int firstRootIndex = findSet(disjointSet, firstVertexIndex);
        final int secondRootIndex = findSet(disjointSet, secondVertexIndex);

        if (disjointSet[firstRootIndex].rank < disjointSet[secondRootIndex].rank) {
            disjointSet[firstRootIndex].parentIndex = secondRootIndex;
        } else if (disjointSet[firstRootIndex].rank > disjointSet[secondRootIndex].rank) {
            disjointSet[secondRootIndex].parentIndex = firstRootIndex;
        } else {
            disjointSet[secondRootIndex].parentIndex = firstRootIndex;
            disjointSet[firstRootIndex].rank++;
        }
    }

    static List<Edge> boruvkaMst(final Graph graph) {
        BoruvkaState state = new BoruvkaState(graph);

        while (state.isMstIncomplete()) {
            final Edge[] cheapestEdgesByComponent = state.findCheapestEdgesForComponents();
            state.addCheapestEdges(cheapestEdgesByComponent);
        }
        return state.minimumSpanningTreeEdges;
    }

    private static void validateVertex(final int vertexIndex, final int vertexCount) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}