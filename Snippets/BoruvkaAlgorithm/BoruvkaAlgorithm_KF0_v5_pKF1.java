package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Boruvka's algorithm to find Minimum Spanning Tree
 * (https://en.wikipedia.org/wiki/Bor%C5%AFvka%27s_algorithm)
 *
 * @author itakurah (https://github.com/itakurah)
 */
final class BoruvkaAlgorithm {

    private BoruvkaAlgorithm() {}

    /**
     * Represents an edge in the graph
     */
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

    /**
     * Represents the graph
     */
    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        /**
         * Constructor for the graph
         *
         * @param vertexCount number of vertices
         * @param edges       list of edges
         */
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

    /**
     * Represents a subset for Union-Find operations
     */
    private static class DisjointSetNode {
        int parentIndex;
        int rank;

        DisjointSetNode(final int parentIndex, final int rank) {
            this.parentIndex = parentIndex;
            this.rank = rank;
        }
    }

    /**
     * Represents the state of Union-Find components and the result list
     */
    private static class BoruvkaComputationState {
        final List<Edge> minimumSpanningTreeEdges;
        final DisjointSetNode[] disjointSetForest;
        final Graph graph;

        BoruvkaComputationState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.disjointSetForest = initializeDisjointSetForest(graph);
            this.graph = graph;
        }

        /**
         * Adds the cheapest edges to the result list and performs Union operation on the subsets.
         *
         * @param cheapestEdgesPerComponent Array containing the cheapest edge for each subset.
         */
        void mergeCheapestEdges(final Edge[] cheapestEdgesPerComponent) {
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                final Edge cheapestEdgeForComponent = cheapestEdgesPerComponent[vertexIndex];
                if (cheapestEdgeForComponent != null) {
                    final int sourceComponentRepresentative =
                        findSetRepresentative(disjointSetForest, cheapestEdgeForComponent.sourceVertex);
                    final int destinationComponentRepresentative =
                        findSetRepresentative(disjointSetForest, cheapestEdgeForComponent.destinationVertex);

                    if (sourceComponentRepresentative != destinationComponentRepresentative) {
                        minimumSpanningTreeEdges.add(cheapestEdgeForComponent);
                        unionSets(disjointSetForest, sourceComponentRepresentative, destinationComponentRepresentative);
                    }
                }
            }
        }

        /**
         * Checks if there are more edges to add to the result list
         *
         * @return true if there are more edges to add, false otherwise
         */
        boolean hasMoreEdgesToAdd() {
            return minimumSpanningTreeEdges.size() < graph.vertexCount - 1;
        }

        /**
         * Computes the cheapest edges for each subset in the Union-Find structure.
         *
         * @return an array containing the cheapest edge for each subset.
         */
        private Edge[] computeCheapestEdges() {
            Edge[] cheapestEdgesPerComponent = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponentRepresentative =
                    findSetRepresentative(disjointSetForest, edge.sourceVertex);
                final int destinationComponentRepresentative =
                    findSetRepresentative(disjointSetForest, edge.destinationVertex);

                if (sourceComponentRepresentative != destinationComponentRepresentative) {
                    if (cheapestEdgesPerComponent[sourceComponentRepresentative] == null
                        || edge.weight < cheapestEdgesPerComponent[sourceComponentRepresentative].weight) {
                        cheapestEdgesPerComponent[sourceComponentRepresentative] = edge;
                    }
                    if (cheapestEdgesPerComponent[destinationComponentRepresentative] == null
                        || edge.weight < cheapestEdgesPerComponent[destinationComponentRepresentative].weight) {
                        cheapestEdgesPerComponent[destinationComponentRepresentative] = edge;
                    }
                }
            }
            return cheapestEdgesPerComponent;
        }

        /**
         * Initializes subsets for Union-Find
         *
         * @param graph the graph
         * @return the initialized subsets
         */
        private static DisjointSetNode[] initializeDisjointSetForest(final Graph graph) {
            DisjointSetNode[] disjointSetForest = new DisjointSetNode[graph.vertexCount];
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                disjointSetForest[vertexIndex] = new DisjointSetNode(vertexIndex, 0);
            }
            return disjointSetForest;
        }
    }

    /**
     * Finds the parent of the subset using path compression
     *
     * @param disjointSetForest array of subsets
     * @param vertexIndex       index of the subset
     * @return the parent of the subset
     */
    static int findSetRepresentative(final DisjointSetNode[] disjointSetForest, final int vertexIndex) {
        if (disjointSetForest[vertexIndex].parentIndex != vertexIndex) {
            disjointSetForest[vertexIndex].parentIndex =
                findSetRepresentative(disjointSetForest, disjointSetForest[vertexIndex].parentIndex);
        }
        return disjointSetForest[vertexIndex].parentIndex;
    }

    /**
     * Performs the Union operation for Union-Find
     *
     * @param disjointSetForest              array of subsets
     * @param firstSetRepresentativeIndex    index of the first subset
     * @param secondSetRepresentativeIndex   index of the second subset
     */
    static void unionSets(
        DisjointSetNode[] disjointSetForest,
        final int firstSetRepresentativeIndex,
        final int secondSetRepresentativeIndex
    ) {
        final int firstRootIndex = findSetRepresentative(disjointSetForest, firstSetRepresentativeIndex);
        final int secondRootIndex = findSetRepresentative(disjointSetForest, secondSetRepresentativeIndex);

        if (disjointSetForest[firstRootIndex].rank < disjointSetForest[secondRootIndex].rank) {
            disjointSetForest[firstRootIndex].parentIndex = secondRootIndex;
        } else if (disjointSetForest[firstRootIndex].rank > disjointSetForest[secondRootIndex].rank) {
            disjointSetForest[secondRootIndex].parentIndex = firstRootIndex;
        } else {
            disjointSetForest[secondRootIndex].parentIndex = firstRootIndex;
            disjointSetForest[firstRootIndex].rank++;
        }
    }

    /**
     * Boruvka's algorithm to find the Minimum Spanning Tree
     *
     * @param graph the graph
     * @return list of edges in the Minimum Spanning Tree
     */
    static List<Edge> boruvkaMST(final Graph graph) {
        BoruvkaComputationState computationState = new BoruvkaComputationState(graph);

        while (computationState.hasMoreEdgesToAdd()) {
            final Edge[] cheapestEdgesPerComponent = computationState.computeCheapestEdges();
            computationState.mergeCheapestEdges(cheapestEdgesPerComponent);
        }
        return computationState.minimumSpanningTreeEdges;
    }

    /**
     * Checks if the edge vertices are in a valid range
     *
     * @param vertexIndex the vertex to check
     * @param vertexCount the upper bound for the vertex range
     */
    private static void validateVertexIndex(final int vertexIndex, final int vertexCount) {
        if (vertexIndex < 0 || vertexIndex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}