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
        int minWeight = Integer.MAX_VALUE;
        int minWeightVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            if (!isVertexInMST[vertexIndex] && minEdgeWeights[vertexIndex] < minWeight) {
                minWeight = minEdgeWeights[vertexIndex];
                minWeightVertexIndex = vertexIndex;
            }
        }

        return minWeightVertexIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * of a graph given its adjacency matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT];
        int[] minEdgeWeights = new int[VERTEX_COUNT];
        boolean[] isVertexInMST = new boolean[VERTEX_COUNT];

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            minEdgeWeights[vertexIndex] = Integer.MAX_VALUE;
            isVertexInMST[vertexIndex] = false;
        }

        minEdgeWeights[0] = 0;
        parent[0] = -1;

        for (int mstEdgeCount = 0; mstEdgeCount < VERTEX_COUNT - 1; mstEdgeCount++) {
            int currentVertex = getMinKeyVertex(minEdgeWeights, isVertexInMST);
            isVertexInMST[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < VERTEX_COUNT; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (edgeWeight != 0
                        && !isVertexInMST[neighborVertex]
                        && edgeWeight < minEdgeWeights[neighborVertex]) {
                    parent[neighborVertex] = currentVertex;
                    minEdgeWeights[neighborVertex] = edgeWeight;
                }
            }
        }

        return parent;
    }
}