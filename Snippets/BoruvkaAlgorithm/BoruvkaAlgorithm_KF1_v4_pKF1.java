package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

final class BoruvkaMinimumSpanningTree {
    private BoruvkaMinimumSpanningTree() {}

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
                validateVertex(edge.source, numberOfVertices);
                validateVertex(edge.destination, numberOfVertices);
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

    private static class BoruvkaState {
        List<Edge> mstEdges;
        DisjointSetNode[] disjointSet;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.mstEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        void addCheapestEdges(final Edge[] cheapestEdgesByComponent) {
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                final Edge cheapestEdge = cheapestEdgesByComponent[vertex];
                if (cheapestEdge != null) {
                    final int sourceComponent = findSet(disjointSet, cheapestEdge.source);
                    final int destinationComponent = findSet(disjointSet, cheapestEdge.destination);

                    if (sourceComponent != destinationComponent) {
                        mstEdges.add(cheapestEdge);
                        unionSets(disjointSet, sourceComponent, destinationComponent);
                    }
                }
            }
        }

        boolean isMstIncomplete() {
            return mstEdges.size() < graph.numberOfVertices - 1;
        }

        private Edge[] findCheapestEdgesForComponents() {
            Edge[] cheapestEdgesByComponent = new Edge[graph.numberOfVertices];
            for (final var edge : graph.edges) {
                final int sourceComponent = findSet(disjointSet, edge.source);
                final int destinationComponent = findSet(disjointSet, edge.destination);

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
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.numberOfVertices];
            for (int vertex = 0; vertex < graph.numberOfVertices; ++vertex) {
                disjointSet[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSet;
        }
    }

    static int findSet(final DisjointSetNode[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent = findSet(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    static void unionSets(DisjointSetNode[] disjointSet, final int firstVertex, final int secondVertex) {
        final int firstRoot = findSet(disjointSet, firstVertex);
        final int secondRoot = findSet(disjointSet, secondVertex);

        if (disjointSet[firstRoot].rank < disjointSet[secondRoot].rank) {
            disjointSet[firstRoot].parent = secondRoot;
        } else if (disjointSet[firstRoot].rank > disjointSet[secondRoot].rank) {
            disjointSet[secondRoot].parent = firstRoot;
        } else {
            disjointSet[secondRoot].parent = firstRoot;
            disjointSet[firstRoot].rank++;
        }
    }

    static List<Edge> boruvkaMst(final Graph graph) {
        BoruvkaState state = new BoruvkaState(graph);

        while (state.isMstIncomplete()) {
            final Edge[] cheapestEdgesByComponent = state.findCheapestEdgesForComponents();
            state.addCheapestEdges(cheapestEdgesByComponent);
        }
        return state.mstEdges;
    }

    private static void validateVertex(final int vertex, final int numberOfVertices) {
        if (vertex < 0 || vertex >= numberOfVertices) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}