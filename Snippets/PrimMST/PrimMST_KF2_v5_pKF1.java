package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int NUMBER_OF_VERTICES = 5;

    private int getVertexWithMinimumKey(int[] minimumEdgeWeights, boolean[] isVertexIncludedInMST) {
        int minimumKey = Integer.MAX_VALUE;
        int vertexWithMinimumKey = -1;

        for (int vertexIndex = 0; vertexIndex < NUMBER_OF_VERTICES; vertexIndex++) {
            if (!isVertexIncludedInMST[vertexIndex] && minimumEdgeWeights[vertexIndex] < minimumKey) {
                minimumKey = minimumEdgeWeights[vertexIndex];
                vertexWithMinimumKey = vertexIndex;
            }
        }

        return vertexWithMinimumKey;
    }

    public int[] computeMinimumSpanningTree(int[][] adjacencyMatrix) {
        int[] parentVertex = new int[NUMBER_OF_VERTICES];
        int[] minimumEdgeWeights = new int[NUMBER_OF_VERTICES];
        boolean[] isVertexIncludedInMST = new boolean[NUMBER_OF_VERTICES];

        for (int vertexIndex = 0; vertexIndex < NUMBER_OF_VERTICES; vertexIndex++) {
            minimumEdgeWeights[vertexIndex] = Integer.MAX_VALUE;
            isVertexIncludedInMST[vertexIndex] = false;
        }

        minimumEdgeWeights[0] = 0;
        parentVertex[0] = -1;

        for (int iteration = 0; iteration < NUMBER_OF_VERTICES - 1; iteration++) {
            int currentVertex = getVertexWithMinimumKey(minimumEdgeWeights, isVertexIncludedInMST);
            isVertexIncludedInMST[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < NUMBER_OF_VERTICES; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (edgeWeight != 0
                        && !isVertexIncludedInMST[neighborVertex]
                        && edgeWeight < minimumEdgeWeights[neighborVertex]) {
                    parentVertex[neighborVertex] = currentVertex;
                    minimumEdgeWeights[neighborVertex] = edgeWeight;
                }
            }
        }

        return parentVertex;
    }
}