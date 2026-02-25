package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    private int getVertexWithMinimumKey(int[] minEdgeWeightToTree, boolean[] isVertexInMST) {
        int minimumKey = Integer.MAX_VALUE;
        int vertexWithMinimumKey = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!isVertexInMST[vertex] && minEdgeWeightToTree[vertex] < minimumKey) {
                minimumKey = minEdgeWeightToTree[vertex];
                vertexWithMinimumKey = vertex;
            }
        }

        return vertexWithMinimumKey;
    }

    public int[] computePrimMST(int[][] adjacencyMatrix) {
        int[] parentOfVertexInMST = new int[VERTEX_COUNT];
        int[] minEdgeWeightToTree = new int[VERTEX_COUNT];
        boolean[] isVertexInMST = new boolean[VERTEX_COUNT];

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            minEdgeWeightToTree[vertex] = Integer.MAX_VALUE;
            isVertexInMST[vertex] = false;
        }

        minEdgeWeightToTree[0] = 0;
        parentOfVertexInMST[0] = -1;

        for (int edgeIndex = 0; edgeIndex < VERTEX_COUNT - 1; edgeIndex++) {
            int currentVertex = getVertexWithMinimumKey(minEdgeWeightToTree, isVertexInMST);
            isVertexInMST[currentVertex] = true;

            for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
                if (adjacencyMatrix[currentVertex][adjacentVertex] != 0
                        && !isVertexInMST[adjacentVertex]
                        && adjacencyMatrix[currentVertex][adjacentVertex] < minEdgeWeightToTree[adjacentVertex]) {
                    parentOfVertexInMST[adjacentVertex] = currentVertex;
                    minEdgeWeightToTree[adjacentVertex] = adjacencyMatrix[currentVertex][adjacentVertex];
                }
            }
        }

        return parentOfVertexInMST;
    }
}