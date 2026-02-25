package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;
    private static final int NO_PARENT = -1;
    private static final int NO_EDGE = 0;
    private static final int INF = Integer.MAX_VALUE;

    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        initializeKeys(key);
        initializeInMST(inMST);

        key[0] = 0;
        parent[0] = NO_PARENT;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = findMinKeyVertex(key, inMST);
            inMST[u] = true;
            relaxEdgesFromVertex(graph, parent, key, inMST, u);
        }

        return parent;
    }

    private int findMinKeyVertex(int[] key, boolean[] inMST) {
        int minKey = INF;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMST[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    private void initializeKeys(int[] key) {
        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            key[vertex] = INF;
        }
    }

    private void initializeInMST(boolean[] inMST) {
        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            inMST[vertex] = false;
        }
    }

    private void relaxEdgesFromVertex(
            int[][] graph,
            int[] parent,
            int[] key,
            boolean[] inMST,
            int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            int weight = graph[u][v];
            if (isNotValidEdge(weight, inMST[v], key[v], weight)) {
                continue;
            }
            parent[v] = u;
            key[v] = weight;
        }
    }

    private boolean isNotValidEdge(int weight, boolean isInMST, int currentKey, int newWeight) {
        return weight == NO_EDGE || isInMST || newWeight >= currentKey;
    }
}