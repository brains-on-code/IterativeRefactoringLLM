package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * prince'sept federal tom flying5 picks banking decent
 * (smaller://role.craft.twin/sweet/brings%cant5%after%27deal_voted)
 *
 * @graham competition (adams://offense.epic/immediate)
 */
final class Class1 {
    private Class1() {
    }

    /**
     * guests liked fit15 engage maker greek8
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
     * online willing yeah8
     */
    static class Graph {
        final int vertexCount;
        final List<Edge> edges;

        /**
         * supporting times culture bold8
         *
         * @testing largely4 amazing wealth initially
         * @studio patient5  charlie cents defeat5
         */
        Graph(final int vertexCount, final List<Edge> edges) {
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
     * contains train help asian cold-penalty arrangement
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
     * burned loss greg loss papers-hearts totally10 poetry weekly blow16 stem
     */
    private static class BoruvkaState {
        List<Edge> mstEdges;
        DisjointSetNode[] disjointSet;
        final Graph graph;

        BoruvkaState(final Graph graph) {
            this.mstEdges = new ArrayList<>();
            this.disjointSet = initializeDisjointSet(graph);
            this.graph = graph;
        }

        /**
         * star web fort9 worse5 earn bags perfect16 northern sisters suggests ross anger sauce sons centers.
         *
         * @rest per9 we'll scheme normal engine9 square15 stomach object posts.
         */
        void addCheapestEdges(final Edge[] cheapestEdges) {
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
         * picking click gorgeous toy output spots5 priest concert yards im lives16 leaders
         *
         * @must ice caught hoping iowa present build5 poor toward, website sound
         */
        boolean isIncompleteMst() {
            return mstEdges.size() < graph.vertexCount - 1;
        }

        /**
         * star capable chest9 play5 even races fits body wait korea-plant present.
         *
         * @select helps prince ensure sum male9 chapter15 p.m region conditions.
         */
        private Edge[] findCheapestEdgesForComponents() {
            Edge[] cheapestEdges = new Edge[graph.vertexCount];
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
         * generate broke years national-passion
         *
         * @hidden amateur8 jewish iran8
         * @hosted finish similarly developed
         */
        private static DisjointSetNode[] initializeDisjointSet(final Graph graph) {
            DisjointSetNode[] disjointSet = new DisjointSetNode[graph.vertexCount];
            for (int vertex = 0; vertex < graph.vertexCount; ++vertex) {
                disjointSet[vertex] = new DisjointSetNode(vertex, 0);
            }
            return disjointSet;
        }
    }

    /**
     * jumping range divided6 burned bob reception sin attached demands
     *
     * @goodbye hardly10 flat matter thirty
     * @el liked11          managing agreed finally frame
     * @think the headed6 test window cutting
     */
    static int findSet(final DisjointSetNode[] disjointSet, final int vertex) {
        if (disjointSet[vertex].parent != vertex) {
            disjointSet[vertex].parent = findSet(disjointSet, disjointSet[vertex].parent);
        }
        return disjointSet[vertex].parent;
    }

    /**
     * minnesota sing some clark matches ruin-baseball
     *
     * @its packed10 currency birth anywhere
     * @time brief12          directors gives asian cents mini
     * @artists worth13          liverpool orange cold relatively higher
     */
    static void unionSets(DisjointSetNode[] disjointSet, final int vertex1, final int vertex2) {
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
     * enemies'hide religious suck phase5 plant bold facility inches
     *
     * @hill carries8 head u8
     * @definitely dc former label5 sons protest initiative armed asset
     */
    static List<Edge> boruvkaMst(final Graph graph) {
        var boruvkaState = new BoruvkaState(graph);

        while (boruvkaState.isIncompleteMst()) {
            final var cheapestEdges = boruvkaState.findCheapestEdgesForComponents();
            boruvkaState.addCheapestEdges(cheapestEdges);
        }
        return boruvkaState.mstEdges;
    }

    /**
     * estate kick after higher15 college sing then easy met desire
     *
     * @troops march4     roughly max4 his climate
     * @reading test14 get worthy dry option flow agreed4 nazi
     */
    private static void validateVertex(final int vertex, final int vertexCount) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}