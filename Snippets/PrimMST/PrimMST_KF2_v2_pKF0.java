package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;
    private static final int NO_PARENT = -1;
    private static final int NO_EDGE = 0;

    private int findMinKeyVertex(int[] key, boolean[] includedInMST) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            boolean isNotInMST = !includedInMST[vertex];
            boolean hasSmallerKey = key[vertex] < minKey;

            if (isNotInMST && hasSmallerKey) {
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

        initializeKeysAndMSTFlags(key, includedInMST);

        key[0] = 0;
        parent[0] = NO_PARENT;

        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            int currentVertex = findMinKeyVertex(key, includedInMST);
            includedInMST[currentVertex] = true;

            updateAdjacentVertices(graph, parent, key, includedInMST, currentVertex);
        }

        return parent;
    }

    private void initializeKeysAndMSTFlags(int[] key, boolean[] includedInMST) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            includedInMST[i] = false;
        }
    }

    private void updateAdjacentVertices(
            int[][] graph,
            int[] parent,
            int[] key,
            boolean[] includedInMST,
            int currentVertex
    ) {
        for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
            int edgeWeight = graph[currentVertex][adjacentVertex];
            boolean hasEdge = edgeWeight != NO_EDGE;
            boolean notInMST = !includedInMST[adjacentVertex];
            boolean isBetterKey = edgeWeight < key[adjacentVertex];

            if (hasEdge && notInMST && isBetterKey) {
                parent[adjacentVertex] = currentVertex;
                key[adjacentVertex] = edgeWeight;
            }
        }
    }
}