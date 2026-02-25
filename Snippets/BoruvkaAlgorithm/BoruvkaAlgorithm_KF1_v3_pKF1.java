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
        final List<Edge> edgeList;

        Graph(final int vertexCount, final List<Edge> edgeList) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (edgeList == null || edgeList.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edgeList) {
                validateVertex(edge.sourceVertex, vertexCount);
                validateVertex(edge.destinationVertex, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edgeList = edgeList;
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
        List<Edge> minimumSpanningTreeEdges;
        DisjointSetNode[] disjointSetForest;
        final Graph graph;

        BoruvkaComputationState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSetForest = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void addCheapestEdges(final Edge[] cheapestEdgesByComponent) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                final Edge cheapestEdge = cheapestEdgesByComponent[vertex];
                if (cheapestEdge != null) {
                    final int sourceComponent = findSet(disjointSetForest, cheapestEdge.sourceVertex);
                    final int destinationComponent = findSet(disjointSetForest, cheapestEdge.destinationVertex);

                    if (sourceComponent != destinationComponent) {
                        minimumSpanningTreeEdges.add(cheapestEdge);
                        unionSets(disjointSetForest, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean isMinimumSpanningTreeIncomplete() {
            return minimumSpanningTreeEdges.size() < graph.vertexCount - 1;
        }

        private Edge[] findCheapestEdgesForComponents() {
            Edge[] cheapestEdgesByComponent = new Edge[graph.vertexCount];
            for (final var edge : graph.edgeList) {
                final int sourceComponent = findSet(disjointSetForest, edge.sourceVertex);
                final int destinationComponent = findSet(disjointSetForest, edge.destinationVertex);

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
            DisjointSetNode[] disjointSetForest = new DisjointSetNode[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSetForest[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSetForest;
        }
    }

    static int findSet(final DisjointSetNode[] disjointSetForest, final int vertexIndex) {
        if (disjointSetForest[vertexIndex].parentIndex != vertexIndex) {
            disjointSetForest[vertexIndex].parentIndex =
                    findSet(disjointSetForest, disjointSetForest[vertexIndex].parentIndex);
        }
        return disjointSetForest[vertexIndex].parentIndex;
    }

    static void unionSets(DisjointSetNode[] disjointSetForest, final int firstVertexIndex, final int secondVertexIndex) {
        final int firstRootIndex = findSet(disjointSetForest, firstVertexIndex);
        final int secondRootIndex = findSet(disjointSetForest, secondVertexIndex);

        if (disjointSetForest[firstRootIndex].rank < disjointSetForest[secondRootIndex].rank) {
            disjointSetForest[firstRootIndex].parentIndex = secondRootIndex;
        } else if (disjointSetForest[firstRootIndex].rank > disjointSetForest[secondRootIndex].rank) {
            disjointSetForest[secondRootIndex].parentIndex = firstRootIndex;
        } else {
            disjointSetForest[secondRootIndex].parentIndex = firstRootIndex;
            disjointSetForest[firstRootIndex].rank++;
        }
    }

    static List<Edge> boruvkaMst(final Graph graph) {
        BoruvkaComputationState computationState = new BoruvkaComputationState(graph);

        while (computationState.isMinimumSpanningTreeIncomplete()) {
            final Edge[] cheapestEdgesByComponent = computationState.findCheapestEdgesForComponents();
            computationState.addCheapestEdges(cheapestEdgesByComponent);
        }
        return computationState.minimumSpanningTreeEdges;
    }

    private static void validateVertex(final int vertexIndex, final int vertexCount) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}