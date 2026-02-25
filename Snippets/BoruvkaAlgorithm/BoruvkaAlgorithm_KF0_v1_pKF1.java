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

    private BoruvkaAlgorithm() {
    }

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
    private static class Component {
        int parent;
        int rank;

        Component(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /**
     * Represents the state of Union-Find components and the result list
     */
    private static class BoruvkaState {
        final List<Edge> minimumSpanningTreeEdges;
        final Component[] components;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.minimumSpanningTreeEdges = new ArrayList<>();
            this.components = initializeComponents(graph);
            this.graph = graph;
        }

        /**
         * Adds the cheapest edges to the result list and performs Union operation on the subsets.
         *
         * @param cheapestEdges Array containing the cheapest edge for each subset.
         */
        void merge(final Edge[] cheapestEdges) {
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                final Edge cheapestEdgeForComponent = cheapestEdges[vertexIndex];
                if (cheapestEdgeForComponent != null) {
                    final int componentOfSource =
                        findComponentRepresentative(components, cheapestEdgeForComponent.sourceVertex);
                    final int componentOfDestination =
                        findComponentRepresentative(components, cheapestEdgeForComponent.destinationVertex);

                    if (componentOfSource != componentOfDestination) {
                        minimumSpanningTreeEdges.add(cheapestEdgeForComponent);
                        unionComponents(components, componentOfSource, componentOfDestination);
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
            Edge[] cheapestEdges = new Edge[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int sourceComponent = findComponentRepresentative(components, edge.sourceVertex);
                final int destinationComponent = findComponentRepresentative(components, edge.destinationVertex);

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
        private static Component[] initializeComponents(final Graph graph) {
            Component[] components = new Component[graph.vertexCount];
            for (int vertexIndex = 0; vertexIndex < graph.vertexCount; ++vertexIndex) {
                components[vertexIndex] = new Component(vertexIndex, 0);
            }
            return components;
        }
    }

    /**
     * Finds the parent of the subset using path compression
     *
     * @param components array of subsets
     * @param vertex     index of the subset
     * @return the parent of the subset
     */
    static int findComponentRepresentative(final Component[] components, final int vertex) {
        if (components[vertex].parent != vertex) {
            components[vertex].parent = findComponentRepresentative(components, components[vertex].parent);
        }
        return components[vertex].parent;
    }

    /**
     * Performs the Union operation for Union-Find
     *
     * @param components array of subsets
     * @param firstComponentIndex  index of the first subset
     * @param secondComponentIndex index of the second subset
     */
    static void unionComponents(Component[] components, final int firstComponentIndex, final int secondComponentIndex) {
        final int firstRoot = findComponentRepresentative(components, firstComponentIndex);
        final int secondRoot = findComponentRepresentative(components, secondComponentIndex);

        if (components[firstRoot].rank < components[secondRoot].rank) {
            components[firstRoot].parent = secondRoot;
        } else if (components[firstRoot].rank > components[secondRoot].rank) {
            components[secondRoot].parent = firstRoot;
        } else {
            components[secondRoot].parent = firstRoot;
            components[firstRoot].rank++;
        }
    }

    /**
     * Boruvka's algorithm to find the Minimum Spanning Tree
     *
     * @param graph the graph
     * @return list of edges in the Minimum Spanning Tree
     */
    static List<Edge> boruvkaMST(final Graph graph) {
        BoruvkaState boruvkaState = new BoruvkaState(graph);

        while (boruvkaState.hasMoreEdgesToAdd()) {
            final Edge[] cheapestEdges = boruvkaState.computeCheapestEdges();
            boruvkaState.merge(cheapestEdges);
        }
        return boruvkaState.minimumSpanningTreeEdges;
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