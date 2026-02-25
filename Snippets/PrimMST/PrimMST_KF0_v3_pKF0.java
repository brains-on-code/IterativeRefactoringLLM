package com.thealgorithms.datastructures.graphs;

/**
 * A Java program for Prim's Minimum Spanning Tree (MST) algorithm.
 * Adjacency matrix representation of the graph.
 */
public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param graph adjacency matrix of the graph
     * @return parent array representing the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMst = new boolean[VERTEX_COUNT];

        initializeKeysAndMstFlags(key, inMst);

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = findMinKeyVertex(key, inMst);
            inMst[u] = true;
            updateAdjacentVertices(graph, parent, key, inMst, u);
        }

        return parent;
    }

    /**
     * Finds the vertex with the minimum key value from the set of vertices
     * not yet included in the MST.
     */
    private int findMinKeyVertex(int[] key, boolean[] inMst) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            boolean isNotInMst = !inMst[vertex];
            boolean hasSmallerKey = key[vertex] < minKey;

            if (isNotInMst && hasSmallerKey) {
                minKey = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    private void initializeKeysAndMstFlags(int[] key, boolean[] inMst) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMst[i] = false;
        }
    }

    private void updateAdjacentVertices(
            int[][] graph,
            int[] parent,
            int[] key,
            boolean[] inMst,
            int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            int weight = graph[u][v];
            boolean isEdgePresent = weight != 0;
            boolean isNotInMst = !inMst[v];
            boolean hasSmallerWeight = weight < key[v];

            if (isEdgePresent && isNotInMst && hasSmallerWeight) {
                parent[v] = u;
                key[v] = weight;
            }
        }
    }
}