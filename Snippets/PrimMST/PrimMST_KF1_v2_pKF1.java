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
    int getMinKeyVertex(int[] keyValues, Boolean[] isVertexIncludedInMST) {
        int minimumKeyValue = Integer.MAX_VALUE;
        int minimumKeyVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            if (!isVertexIncludedInMST[vertexIndex] && keyValues[vertexIndex] < minimumKeyValue) {
                minimumKeyValue = keyValues[vertexIndex];
                minimumKeyVertexIndex = vertexIndex;
            }
        }

        return minimumKeyVertexIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * of a graph given its adjacency matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parentVertices = new int[VERTEX_COUNT];
        int[] keyValues = new int[VERTEX_COUNT];
        Boolean[] isVertexIncludedInMST = new Boolean[VERTEX_COUNT];

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            keyValues[vertexIndex] = Integer.MAX_VALUE;
            isVertexIncludedInMST[vertexIndex] = Boolean.FALSE;
        }

        keyValues[0] = 0;
        parentVertices[0] = -1;

        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            int currentVertex = getMinKeyVertex(keyValues, isVertexIncludedInMST);
            isVertexIncludedInMST[currentVertex] = Boolean.TRUE;

            for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
                if (adjacencyMatrix[currentVertex][adjacentVertex] != 0
                        && !isVertexIncludedInMST[adjacentVertex]
                        && adjacencyMatrix[currentVertex][adjacentVertex] < keyValues[adjacentVertex]) {
                    parentVertices[adjacentVertex] = currentVertex;
                    keyValues[adjacentVertex] = adjacencyMatrix[currentVertex][adjacentVertex];
                }
            }
        }

        return parentVertices;
    }
}