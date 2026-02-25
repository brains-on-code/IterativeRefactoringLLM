package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;
    private static final int NO_PARENT = -1;
    private static final int NO_EDGE = 0;
    private static final int INF = Integer.MAX_VALUE;

    private int findMinKeyVertex(int[] key, boolean[] inMST) {
        int minKey = INF;
        int minIndex = -1;

        for (int v = 0; v < VERTEX_COUNT; v++) {
            if (!inMST[v] && key[v] < minKey) {
                minKey = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        initializeKeysAndFlags(key, inMST);

        key[0] = 0;
        parent[0] = NO_PARENT;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = findMinKeyVertex(key, inMST);
            inMST[u] = true;
            updateAdjacentVertices(graph, parent, key, inMST, u);
        }

        return parent;
    }

    private void initializeKeysAndFlags(int[] key, boolean[] inMST) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = INF;
            inMST[i] = false;
        }
    }

    private void updateAdjacentVertices(
            int[][] graph,
            int[] parent,
            int[] key,
            boolean[] inMST,
            int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            int weight = graph[u][v];
            if (weight != NO_EDGE && !inMST[v] && weight < key[v]) {
                parent[v] = u;
                key[v] = weight;
            }
        }
    }
}