package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int NUM_VERTICES = 5;

    private int getMinKeyVertex(int[] keyValues, Boolean[] isInMST) {
        int minKey = Integer.MAX_VALUE;
        int minKeyVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < NUM_VERTICES; vertexIndex++) {
            if (!isInMST[vertexIndex] && keyValues[vertexIndex] < minKey) {
                minKey = keyValues[vertexIndex];
                minKeyVertexIndex = vertexIndex;
            }
        }

        return minKeyVertexIndex;
    }

    public int[] computePrimMST(int[][] adjacencyMatrix) {
        int[] parent = new int[NUM_VERTICES];
        int[] keyValues = new int[NUM_VERTICES];
        Boolean[] isInMST = new Boolean[NUM_VERTICES];

        for (int vertexIndex = 0; vertexIndex < NUM_VERTICES; vertexIndex++) {
            keyValues[vertexIndex] = Integer.MAX_VALUE;
            isInMST[vertexIndex] = Boolean.FALSE;
        }

        keyValues[0] = 0;
        parent[0] = -1;

        for (int edgeCount = 0; edgeCount < NUM_VERTICES - 1; edgeCount++) {
            int currentVertex = getMinKeyVertex(keyValues, isInMST);
            isInMST[currentVertex] = Boolean.TRUE;

            for (int adjacentVertex = 0; adjacentVertex < NUM_VERTICES; adjacentVertex++) {
                if (adjacencyMatrix[currentVertex][adjacentVertex] != 0
                        && !isInMST[adjacentVertex]
                        && adjacencyMatrix[currentVertex][adjacentVertex] < keyValues[adjacentVertex]) {
                    parent[adjacentVertex] = currentVertex;
                    keyValues[adjacentVertex] = adjacencyMatrix[currentVertex][adjacentVertex];
                }
            }
        }

        return parent;
    }
}