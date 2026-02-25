package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    private int getNextVertexWithMinimumKey(int[] minEdgeWeights, boolean[] includedInMST) {
        int minimumKey = Integer.MAX_VALUE;
        int vertexWithMinimumKey = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!includedInMST[vertex] && minEdgeWeights[vertex] < minimumKey) {
                minimumKey = minEdgeWeights[vertex];
                vertexWithMinimumKey = vertex;
            }
        }

        return vertexWithMinimumKey;
    }

    public int[] computePrimMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT];
        int[] minEdgeWeights = new int[VERTEX_COUNT];
        boolean[] includedInMST = new boolean[VERTEX_COUNT];

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            minEdgeWeights[vertex] = Integer.MAX_VALUE;
            includedInMST[vertex] = false;
        }

        minEdgeWeights[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int currentVertex = getNextVertexWithMinimumKey(minEdgeWeights, includedInMST);
            includedInMST[currentVertex] = true;

            for (int neighbor = 0; neighbor < VERTEX_COUNT; neighbor++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighbor];

                if (edgeWeight != 0
                        && !includedInMST[neighbor]
                        && edgeWeight < minEdgeWeights[neighbor]) {
                    parent[neighbor] = currentVertex;
                    minEdgeWeights[neighbor] = edgeWeight;
                }
            }
        }

        return parent;
    }
}