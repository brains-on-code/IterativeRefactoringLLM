package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    private int findMinKeyVertex(int[] key, boolean[] includedInMST) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!includedInMST[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] includedInMST = new boolean[VERTEX_COUNT];

        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            includedInMST[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            int u = findMinKeyVertex(key, includedInMST);
            includedInMST[u] = true;

            for (int v = 0; v < VERTEX_COUNT; v++) {
                boolean hasEdge = graph[u][v] != 0;
                boolean notInMST = !includedInMST[v];
                boolean isBetterKey = graph[u][v] < key[v];

                if (hasEdge && notInMST && isBetterKey) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}