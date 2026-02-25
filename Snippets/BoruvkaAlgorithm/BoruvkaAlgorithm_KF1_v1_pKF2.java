package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Borůvka's algorithm to find a Minimum Spanning Tree (MST)
 * of a connected, undirected, weighted graph.
 */
final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents an undirected, weighted edge between two vertices.
     */
    static class Class2 {
        final int source;
        final int destination;
        final int weight;

        Class2(final int source, final int destination, final int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Represents a graph using an edge list.
     */
    static class Class3 {
        final int vertexCount;
        final List<Class2> edges;

        /**
         * Creates a graph.
         *
         * @param vertexCount number of vertices in the graph (must be positive)
         * @param edges       list of edges (must not be null or empty)
         */
        Class3(final int vertexCount, final List<Class2> edges) {
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var edge : edges) {
                validateVertex(edge.source, vertexCount);
                validateVertex(edge.destination, vertexCount);
            }

            this.vertexCount = vertexCount;
            this.edges = edges;
        }
    }

    /**
     * Disjoint-set (Union-Find) node with parent and rank.
     */
    private static class Class4 {
        int parent;
        int rank;

        Class4(final int parent, final int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    /**
     * Borůvka's algorithm implementation helper.
     */
    private static class Class5 {
        List<Class2> mstEdges;
        Class4[] disjointSet;
        final Class3 graph;

        Class5(final Class3 graph) {
            this.mstEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        /**
         * Adds the cheapest edges for each component to the MST if they connect
         * different components.
         *
         * @param cheapestEdges array where each index represents a component and
         *                      stores its current cheapest outgoing edge
         */
        void addCheapestEdgesToMst(final Class2[] cheapestEdges) {
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                if (cheapestEdges[vertex] != null) {
                    final int set1 = findSet(disjointSet, cheapestEdges[vertex].source);
                    final int set2 = findSet(disjointSet, cheapestEdges[vertex].destination);

                    if (set1 != set2) {
                        mstEdges.add(cheapestEdges[vertex]);
                        unionSets(disjointSet, set1, set2);
                    }
                }
            }
        }

        /**
         * Checks if the MST is complete. For a connected graph, an MST has
         * exactly (V - 1) edges.
         *
         * @return true if the MST is not yet complete; false otherwise
         */
        boolean isMstIncomplete() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        /**
         * For each component, finds the cheapest outgoing edge.
         *
         * @return array of cheapest edges, indexed by component representative
         */
        private Class2[] findCheapestEdgesPerComponent() {
            Class2[] cheapestEdges = new Class2[graph.vertexCount];
            for (final var edge : graph.edges) {
                final int set1 = findSet(disjointSet, edge.source);
                final int set2 = findSet(disjointSet, edge.destination);

                if (set1 != set2) {
                    if (cheapestEdges[set1] == null || edge.weight < cheapestEdges[set1].weight) {
                        cheapestEdges[set1] = edge;
                    }
                    if (cheapestEdges[set2] == null || edge.weight < cheapestEdges[set2].weight) {
                        cheapestEdges[set2] = edge;
                    }
                }
            }
            return cheapestEdges;
        }

        /**
         * Initializes the disjoint-set structure for all vertices.
         *
         * @param graph the graph
         * @return initialized disjoint-set array
         */
        private static Class4[] initializeDisjointSet(final Class3 graph) {
            Class4[] disjointSet = new Class4[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSet[vertex] = new Class4(vertex, 0);
            }
            return disjointSet;
        }
    }

    /**
     * Finds the representative (root) of the set that contains the given vertex,
     * with path compression.
     *
     * @param disjointSet disjoint-set array
     * @param vertex      vertex index
     * @return representative of the set containing the vertex
     */
    static int findSet(final Class4[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent = findSet(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    /**
     * Unites the sets containing the two given vertices using union by rank.
     *
     * @param disjointSet disjoint-set array
     * @param vertex1     first vertex
     * @param vertex2     second vertex
     */
    static void unionSets(Class4[] disjointSet, final int vertex1, final int vertex2) {
        final int root1 = findSet(disjointSet, vertex1);
        final int root2 = findSet(disjointSet, vertex2);

        if (disjointSet[root1].rank < disjointSet[root2].rank) {
            disjointSet[root1].parent = root2;
        } else if (disjointSet[root1].rank > disjointSet[root2].rank) {
            disjointSet[root2].parent = root1;
        } else {
            disjointSet[root2].parent = root1;
            disjointSet[root1].rank++;
        }
    }

    /**
     * Computes the Minimum Spanning Tree (MST) of the given graph using
     * Borůvka's algorithm.
     *
     * @param graph the input graph
     * @return list of edges that form the MST
     */
    static List<Class2> method7(final Class3 graph) {
        var boruvka = new Class5(graph);

        while (boruvka.isMstIncomplete()) {
            final var cheapestEdges = boruvka.findCheapestEdgesPerComponent();
            boruvka.addCheapestEdgesToMst(cheapestEdges);
        }
        return boruvka.mstEdges;
    }

    /**
     * Validates that a vertex index is within the valid range.
     *
     * @param vertex      vertex index
     * @param vertexCount total number of vertices
     */
    private static void validateVertex(final int vertex, final int vertexCount) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}