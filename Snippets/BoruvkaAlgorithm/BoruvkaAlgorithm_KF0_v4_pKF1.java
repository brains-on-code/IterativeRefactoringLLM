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
        final int source;
        final int destination;
        final int weight;

        Edge(final int source, final int destination, final int weight) {
            this.source = source;
            this.destination = destination;
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
                validateVertexIndex(edge.source, vertexCount);
                validateVertexIndex(edge.destination, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edges = edges;
        }
    }

    /**
     * Represents a subset for Union-Find operations
     */
    private static class DisjointSetNode {
        int parent;
        int rank;

        DisjointSetNode(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /**
     * Represents the state of Union-Find components and the result list
     */
    private static class BoruvkaComputationState {
        final List<Edge> mstEdges;
        final DisjointSetNode[] disjointSetForest;
        final Graph graph;

        BoruvkaComputationState(final Graph graph) {
            this.mstEdges = new ArrayList<>();
            this.disjointSetForest = initializeDisjointSetForest(graph);
            this.graph = graph;
        }

        /**
         * Adds the cheapest edges to the result list and performs Union operation on the subsets.
         *
         * @param cheapestEdges Array containing the cheapest edge for each subset.
         */
        void mergeCheapestEdges(final Edge[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                final Edge cheapestEdgeForComponent = cheapestEdges[vertex];
                if (cheapestEdgeForComponent != null) {
                    final int sourceComponent =
                        findSetRepresentative(disjointSetForest, cheapestEdgeForComponent.source);
                    final int destinationComponent =
                        findSetRepresentative(disjointSetForest, cheapestEdgeForComponent.destination);

                    if (sourceComponent != destinationComponent) {
                        mstEdges.add(cheapestEdgeForComponent);
                        unionSets(disjointSetForest, sourceComponent, destinationComponent);
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
            return mstEdges.size() < graph.vertexCount - 1;
        }

        /**
         * Computes the cheapest edges for each subset in the Union-Find structure.
         *
         * @return an array containing the cheapest edge for each subset.
         */
        private Edge[] computeCheapestEdges() {
            Edge[] cheapestEdges = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponent =
                    findSetRepresentative(disjointSetForest, edge.source);
                final int destinationComponent =
                    findSetRepresentative(disjointSetForest, edge.destination);

                if (sourceComponent != destinationComponent) {
                    if (cheapestEdges[sourceComponent] == null
                        || edge.weight < cheapestEdges[sourceComponent].weight) {
                        cheapestEdges[sourceComponent] = edge;
                    }
                    if (cheapestEdges[destinationComponent] == null
                        || edge.weight < cheapestEdges[destinationComponent].weight) {
                        cheapestEdges[destinationComponent] = edge;
                    }
                }
            }
            return cheapestEdges;
        }

        /**
         * Initializes subsets for Union-Find
         *
         * @param graph the graph
         * @return the initialized subsets
         */
        private static DisjointSetNode[] initializeDisjointSetForest(final Graph graph) {
            DisjointSetNode[] disjointSetForest = new DisjointSetNode[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSetForest[vertex] = new DisjointSetNode(vertex, 0);
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
        if (disjointSetForest[vertexIndex].parent != vertexIndex) {
            disjointSetForest[vertexIndex].parent =
                findSetRepresentative(disjointSetForest, disjointSetForest[vertexIndex].parent);
        }
        return disjointSetForest[vertexIndex].parent;
    }

    /**
     * Performs the Union operation for Union-Find
     *
     * @param disjointSetForest       array of subsets
     * @param firstSetRepresentative  index of the first subset
     * @param secondSetRepresentative index of the second subset
     */
    static void unionSets(
        DisjointSetNode[] disjointSetForest,
        final int firstSetRepresentative,
        final int secondSetRepresentative
    ) {
        final int firstRoot = findSetRepresentative(disjointSetForest, firstSetRepresentative);
        final int secondRoot = findSetRepresentative(disjointSetForest, secondSetRepresentative);

        if (disjointSetForest[firstRoot].rank < disjointSetForest[secondRoot].rank) {
            disjointSetForest[firstRoot].parent = secondRoot;
        } else if (disjointSetForest[firstRoot].rank > disjointSetForest[secondRoot].rank) {
            disjointSetForest[secondRoot].parent = firstRoot;
        } else {
            disjointSetForest[secondRoot].parent = firstRoot;
            disjointSetForest[firstRoot].rank++;
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
            final Edge[] cheapestEdges = computationState.computeCheapestEdges();
            computationState.mergeCheapestEdges(cheapestEdges);
        }
        return computationState.mstEdges;
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