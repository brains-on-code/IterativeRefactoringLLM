package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int NUM_VERTICES = 5;

    private int getMinKeyVertex(int[] key, boolean[] includedInMST) {
        int minKey = Integer.MAX_VALUE;
        int minVertex = -1;

        for (int vertex = 0; vertex < NUM_VERTICES; vertex++) {
            if (!includedInMST[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minVertex = vertex;
            }
        }

        return minVertex;
    }

    public int[] computePrimMST(int[][] graph) {
        int[] parent = new int[NUM_VERTICES];
        int[] key = new int[NUM_VERTICES];
        boolean[] includedInMST = new boolean[NUM_VERTICES];

        for (int vertex = 0; vertex < NUM_VERTICES; vertex++) {
            key[vertex] = Integer.MAX_VALUE;
            includedInMST[vertex] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int edge = 0; edge < NUM_VERTICES - 1; edge++) {
            int u = getMinKeyVertex(key, includedInMST);
            includedInMST[u] = true;

            for (int v = 0; v < NUM_VERTICES; v++) {
                if (graph[u][v] != 0
                        && !includedInMST[v]
                        && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}