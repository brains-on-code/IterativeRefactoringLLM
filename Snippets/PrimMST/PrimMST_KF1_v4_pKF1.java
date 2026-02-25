package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * for a graph represented by an adjacency matrix.
 */
public class PrimMinimumSpanningTree {

    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the index of the vertex with the minimum key value from
     * the set of vertices not yet included in the MST.
     */
    int getMinKeyVertex(int[] minEdgeWeights, boolean[] isVertexInMST) {
        int minimumWeight = Integer.MAX_VALUE;
        int minimumWeightVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            if (!isVertexInMST[vertexIndex] && minEdgeWeights[vertexIndex] < minimumWeight) {
                minimumWeight = minEdgeWeights[vertexIndex];
                minimumWeightVertexIndex = vertexIndex;
            }
        }

        return minimumWeightVertexIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * of a graph given its adjacency matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parentVertex = new int[VERTEX_COUNT];
        int[] minEdgeWeights = new int[VERTEX_COUNT];
        boolean[] isVertexInMST = new boolean[VERTEX_COUNT];

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            minEdgeWeights[vertexIndex] = Integer.MAX_VALUE;
            isVertexInMST[vertexIndex] = false;
        }

        minEdgeWeights[0] = 0;
        parentVertex[0] = -1;

        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            int currentVertex = getMinKeyVertex(minEdgeWeights, isVertexInMST);
            isVertexInMST[currentVertex] = true;

            for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
                if (adjacencyMatrix[currentVertex][adjacentVertex] != 0
                        && !isVertexInMST[adjacentVertex]
                        && adjacencyMatrix[currentVertex][adjacentVertex] < minEdgeWeights[adjacentVertex]) {
                    parentVertex[adjacentVertex] = currentVertex;
                    minEdgeWeights[adjacentVertex] = adjacencyMatrix[currentVertex][adjacentVertex];
                }
            }
        }

        return parentVertex;
    }
}