package com.thealgorithms.datastructures.graphs;

/**
 * A Java program for Prim's Minimum Spanning Tree (MST) algorithm.
 * Adjacency matrix representation of the graph.
 */
public class PrimMST {

    private static final int VERTEX_COUNT = 5;
    private static final int NO_PARENT = -1;
    private static final int NO_EDGE = 0;
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param graph adjacency matrix of the graph
     * @return parent array representing the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] minEdgeWeight = new int[VERTEX_COUNT];
        boolean[] isInMst = new boolean[VERTEX_COUNT];

        initializeVertexState(minEdgeWeight, isInMst);

        minEdgeWeight[0] = 0;
        parent[0] = NO_PARENT;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int currentVertex = findMinKeyVertex(minEdgeWeight, isInMst);
            isInMst[currentVertex] = true;
            relaxAdjacentVertices(graph, parent, minEdgeWeight, isInMst, currentVertex);
        }

        return parent;
    }

    /**
     * Finds the vertex with the minimum key value from the set of vertices
     * not yet included in the MST.
     */
    private int findMinKeyVertex(int[] minEdgeWeight, boolean[] isInMst) {
        int minKey = INF;
        int minIndex = NO_PARENT;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!isInMst[vertex] && minEdgeWeight[vertex] < minKey) {
                minKey = minEdgeWeight[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    private void initializeVertexState(int[] minEdgeWeight, boolean[] isInMst) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            minEdgeWeight[i] = INF;
            isInMst[i] = false;
        }
    }

    private void relaxAdjacentVertices(
            int[][] graph,
            int[] parent,
            int[] minEdgeWeight,
            boolean[] isInMst,
            int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            int weight = graph[u][v];

            if (weight != NO_EDGE && !isInMst[v] && weight < minEdgeWeight[v]) {
                parent[v] = u;
                minEdgeWeight[v] = weight;
            }
        }
    }
}