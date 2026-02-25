package com.thealgorithms.datastructures.graphs;

/**
 * A Java program for Prim's Minimum Spanning Tree (MST) algorithm.
 * Adjacency matrix representation of the graph.
 */
public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the vertex with the minimum key value from the set of vertices
     * not yet included in the MST.
     */
    private int findMinKeyVertex(int[] key, boolean[] inMst) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMst[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

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

        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMst[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < VERTEX_COUNT - 1; count++) {
            int u = findMinKeyVertex(key, inMst);
            inMst[u] = true;

            for (int v = 0; v < VERTEX_COUNT; v++) {
                boolean hasEdge = graph[u][v] != 0;
                boolean notInMst = !inMst[v];
                boolean isBetterEdge = graph[u][v] < key[v];

                if (hasEdge && notInMst && isBetterEdge) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}